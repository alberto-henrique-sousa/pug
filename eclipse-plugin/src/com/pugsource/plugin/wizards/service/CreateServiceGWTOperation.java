/*******************************************************************************
 * Copyright 2013 Pug Framework. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.pugsource.plugin.wizards.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.wb.internal.core.model.util.WorkspaceUtils;
import org.eclipse.wb.internal.core.utils.ast.AstEditor;
import org.eclipse.wb.internal.core.utils.ast.BodyDeclarationTarget;
import org.eclipse.wb.internal.core.utils.jdt.core.CodeUtils;

import com.google.common.collect.Lists;
import com.google.gdt.eclipse.designer.model.module.ModuleElement;
import com.google.gdt.eclipse.designer.model.web.WebAppElement;
import com.google.gdt.eclipse.designer.model.web.WebDocumentEditContext;
import com.google.gdt.eclipse.designer.model.web.WebUtils;
import com.google.gdt.eclipse.designer.util.ModuleDescription;
import com.google.gdt.eclipse.designer.util.Utils;
import com.pugsource.plugin.util.Tools;
import com.pugsource.plugin.wizards.dto.CreateDTOGWTOperation;

/**
 * Operation for creating new GWT RemoteService.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.operation
 * modified by alberto
 */
public class CreateServiceGWTOperation extends AbstractCreateGWTOperation {
	////////////////////////////////////////////////////////////////////////////
	//
	// Creation
	//
	////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public void create(IPackageFragment packageFragment, String serviceName, String classDTO, boolean login) throws Exception {
		// prepare packages names
		String servicePackageName = packageFragment.getElementName();
		String serverPackageName = getServerPackageName(packageFragment);
		String classDTOName = classDTO.substring(0, classDTO.indexOf("(")-1).trim();
		String instanceDTO = classDTOName.substring(0, 1).toLowerCase() + classDTOName.substring(1);
		String classDTOPackage = classDTO.substring(classDTO.indexOf("("));
		classDTOPackage = classDTOPackage.replace("(", "").replace(")", ""); 
		// prepare variables
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("version", Tools.VERSION);
		variables.put("servicePackage", servicePackageName);
		variables.put("serviceName", serviceName);
		variables.put("classDTO", classDTOName);
		variables.put("packageDAO", classDTOPackage.replace(".client.", ".server.").replace(".dto", ".dao"));
		variables.put("classDAO", classDTOName.substring(0, classDTOName.length()-3));
		variables.put("instanceDTO", instanceDTO);
		String instanceDAO = instanceDTO.substring(0, instanceDTO.length()-3);
		variables.put("instanceDAO", instanceDAO);
		variables.put("classDTOPackage", classDTOPackage);
		// client
		{
			// create RemoteService interface, "async" interface will be done by builder
			createFileFromTemplate(
					packageFragment,
					serviceName + ".java",
					(login ? "Login" : "Remote") + "Service.Service.java",
					variables);
			createFileFromTemplate(
					packageFragment,
					serviceName + "Async.java",
					(login ? "Login" : "Remote") + "Service.ServiceAsync.java",
					variables);      
		}
		// server: create implementation stub
		{
			// prepare server package
			IPackageFragment serverPackage;
			{
				IPackageFragmentRoot packageFragmentRoot =
						CodeUtils.getPackageFragmentRoot(packageFragment);
				serverPackage = packageFragmentRoot.createPackageFragment(serverPackageName, false, null);
			}

			String fieldsListAll = "";
			String fieldsSave = "";
			String username = "username";
			String password = "password";

			if (!classDTO.isEmpty()) {		
				IPackageFragment[] pkgs = packageFragment.getJavaProject().getPackageFragments();
				for (IPackageFragment iPackageFragment : pkgs) {
					ICompilationUnit[] compUnits = iPackageFragment.getCompilationUnits();
					for (ICompilationUnit iCompilationUnit : compUnits) {
						CompilationUnit ast = CreateDTOGWTOperation.getAst(iCompilationUnit, false);
						for(AbstractTypeDeclaration type : (List<AbstractTypeDeclaration>)ast.types()) {
							if(type instanceof TypeDeclaration) { 
								TypeDeclaration td = (TypeDeclaration)type;						
								if (classDTO.equals(td.resolveBinding().getName() + " (" +td.resolveBinding().getPackage().getName() + ")")) {
									IVariableBinding[] iVariableBinding = td.resolveBinding().getDeclaredFields();
									for (IVariableBinding iVariableBinding2 : iVariableBinding) {
										String fieldName = iVariableBinding2.getName();
										String fieldType = CreateDTOGWTOperation.fieldTypeDTO(iVariableBinding2.getType().getName());
										if (CreateDTOGWTOperation.validFieldName(fieldName, fieldType)) {
											String fieldNameCamel = fieldName.substring(0, 1).toUpperCase();
											if (fieldName.length() > 1)
												fieldNameCamel = fieldNameCamel.substring(0, 1) + fieldName.substring(1);
											fieldsListAll += "				" + instanceDTO + ".set"+fieldNameCamel +"("  + instanceDAO + ".get"+fieldNameCamel+"());\n";
											fieldsSave += "			" + instanceDAO + ".set"+fieldNameCamel +"("  + instanceDTO + ".get"+fieldNameCamel+"());\n";
											if (fieldName.toLowerCase().equals("nome") || fieldName.toLowerCase().equals("usuario"))
												username = fieldName;
											if (fieldName.toLowerCase().equals("senha"))
												password = fieldName;
										}	
									}								
								}
							}
						}
					}
				}
			}	
			variables.put("fieldsList", fieldsListAll);
			variables.put("fieldsSave", fieldsSave);
			variables.put("fieldUsername", username);
			variables.put("fieldPassword", password);

			// create implementation stub
			variables.put("serverPackage", serverPackageName);
			createFileFromTemplate(
					serverPackage,
					serviceName + "Impl.java",
					(login ? "Login" : "Remote") + "Service.ServiceImpl.java",
					variables);
			// Resources
			{
				ICompilationUnit factoryUnit =
						packageFragment.getCompilationUnit("PathServices.java");			
				AstEditor editor = new AstEditor(factoryUnit);
				TypeDeclaration factoryPrimaryType = editor.getPrimaryType();
				BodyDeclarationTarget bodyDeclarationTarget =
						new BodyDeclarationTarget(factoryPrimaryType, false);
				ModuleDescription moduleDescription = Utils.getSingleModule(packageFragment);
				ModuleElement module = Utils.readModule(moduleDescription);
				final List<String> lines = Lists.newArrayList("public static final String "+serviceName.toUpperCase()+" = host() + \"" + module.getName() + "/" + serviceName+"\";");
				editor.addFieldDeclaration(lines, bodyDeclarationTarget);				
				editor.saveChanges(true);
			}					
			// open RemoteServiceImpl in editor
			{
				String qualifiedServiceName = serverPackage.getElementName() + "." + serviceName + "Impl";
				IType type =
						WorkspaceUtils.waitForType(serverPackage.getJavaProject(), qualifiedServiceName);
				JavaUI.openInEditor(type);
			}      
		}
		// declare servlet
		addServlet_intoWebXML(packageFragment, serviceName, serverPackageName);
	}

	public static void addServlet_intoWebXML(IPackageFragment packageFragment,
			String serviceName,
			String serverPackageName) throws CoreException, Exception {
		IProject project = packageFragment.getJavaProject().getProject();
		ModuleDescription moduleDescription = Utils.getSingleModule(packageFragment);
		ModuleElement module = Utils.readModule(moduleDescription);
		// update web.xml
		String webFolderName = WebUtils.getWebFolderName(project);
		IFile webFile = project.getFile(new Path(webFolderName + "/WEB-INF/web.xml"));
		WebDocumentEditContext context = new WebDocumentEditContext(webFile);
		try {
			WebAppElement moduleElement = context.getWebAppElement();
			// add new servlet definition
			{
				String servletClassName = serverPackageName + "." + serviceName + "Impl";
				String pattern = "/" + module.getName() + "/" + serviceName;
				// servlet
				com.google.gdt.eclipse.designer.model.web.ServletElement servletElement =
						new com.google.gdt.eclipse.designer.model.web.ServletElement();
				moduleElement.addChild(servletElement);
				servletElement.setName(serviceName);
				servletElement.setClassName(servletClassName);
				// servlet-mapping
				com.google.gdt.eclipse.designer.model.web.ServletMappingElement servletMappingElement =
						new com.google.gdt.eclipse.designer.model.web.ServletMappingElement();
				moduleElement.addChild(servletMappingElement);
				servletMappingElement.setName(serviceName);
				servletMappingElement.setPattern(pattern);
			}
			// commit modifications
			context.commit();
		} finally {
			context.disconnect();
		}
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Packages names
	//
	////////////////////////////////////////////////////////////////////////////
	/**
	 * For given {@link IPackageFragment} in "source" package, returns {@link IPackageFragment} in
	 * "server" package.
	 */
	public static String getServerPackageName(IPackageFragment sourcePackageFragment) throws Exception {
		// prepare information about module
		ModuleDescription moduleDescription = Utils.getSingleModule(sourcePackageFragment);
		String basePackageName = moduleDescription.getModulePackage().getElementName();
		String sourcePackageName = Utils.getRootSourcePackage(sourcePackageFragment).getElementName();
		// use same sub-package in "server" as sub-package in "client"
		String servicePackageName = sourcePackageFragment.getElementName();
		String serviceSubPackageName = servicePackageName.substring(sourcePackageName.length());
		return basePackageName + ".server" + serviceSubPackageName;
	}
}
