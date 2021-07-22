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
package com.pugsource.plugin.wizards.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.wb.internal.core.model.util.WorkspaceUtils;
import org.eclipse.wb.internal.core.utils.ast.AstEditor;
import org.eclipse.wb.internal.core.utils.ast.BodyDeclarationTarget;
import org.eclipse.wb.internal.core.utils.jdt.core.CodeUtils;

import com.google.common.collect.Lists;
import com.pugsource.plugin.util.Tools;
import com.pugsource.plugin.wizards.service.AbstractCreateGWTOperation;

/**
 * Operation for creating new GWT RemoteService.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.operation
 * modified by alberto
 */
public class CreateBeanJSFOperation extends AbstractCreateGWTOperation {
	////////////////////////////////////////////////////////////////////////////
	//
	// Creation
	//
	////////////////////////////////////////////////////////////////////////////
	public void create(IPackageFragment packageFragment, IPackageFragment packageFragmentDAO, String serviceName, String classDTO, boolean login, boolean bean, boolean converter) throws Exception {
		// prepare packages names
		String servicePackageName = packageFragment.getElementName();
		//String serverPackageName = getServerPackageName(packageFragment);
		String classDTOName = classDTO.substring(0, classDTO.indexOf("(")-1).trim();
		String instanceDTO = classDTOName.substring(0, 1).toLowerCase() + classDTOName.substring(1);
		String classDTOPackage = classDTO.substring(classDTO.indexOf("("));
		classDTOPackage = classDTOPackage.replace("(", "").replace(")", ""); 
		String serviceConverter = serviceName.replace("Bean", "Converter");
		String serviceConverterInstance = serviceConverter.substring(0, 1).toLowerCase() + serviceConverter.substring(1);
		// prepare variables
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("version", Tools.VERSION);
		variables.put("servicePackage", servicePackageName);
		variables.put("packageBeanUtil", servicePackageName + ".util");
		variables.put("packageBeanConverter", servicePackageName + ".converter");
		variables.put("packageLazy", servicePackageName.replace(".bean", ".lazy"));
		variables.put("serviceName", serviceName);
		variables.put("layoutName", serviceName.replace("Bean", "").toLowerCase());
		variables.put("serviceConverter", serviceConverter);
		variables.put("instanceConverter", serviceConverterInstance);
		variables.put("classLayoutName", serviceName.replace("Bean", ""));
		variables.put("classDTO", classDTOName);
		variables.put("packageDao", classDTOPackage);
		variables.put("classDAO", classDTOName);
		variables.put("instanceDTO", instanceDTO);
		String instanceDAO = instanceDTO;
		variables.put("instanceDAO", instanceDAO);
		variables.put("classDTOPackage", classDTOPackage);
		// client
		if (bean) {
			createFileFromTemplate(
					packageFragment,
					serviceName + ".java",
					"Bean.java",
					variables);
		}
		if (converter) {
			IPackageFragment serverPackage;
			{
				IPackageFragmentRoot packageFragmentRoot =
						CodeUtils.getPackageFragmentRoot(packageFragment);
				serverPackage = packageFragmentRoot.createPackageFragment(servicePackageName.replace(".bean", ".bean.converter"), false, null);
			}
			
			// create implementation stub
			createFileFromTemplate(
					serverPackage,
					serviceName.replace("Bean", "") + "Converter.java",
					"Converter.java",
					variables);
		}
		// server: create implementation stub
		if (bean) {
			// prepare server package
			IPackageFragment serverPackage;
			{
				IPackageFragmentRoot packageFragmentRoot =
						CodeUtils.getPackageFragmentRoot(packageFragment);
				serverPackage = packageFragmentRoot.createPackageFragment(servicePackageName.replace(".bean", ".lazy"), false, null);
			}

			// create implementation stub
			createFileFromTemplate(
					serverPackage,
					serviceName.replace("Bean", "") + "Lazy.java",
					"Lazy.java",
					variables);
			// open RemoteServiceImpl in editor
			{
				String qualifiedServiceName = packageFragment.getElementName() + "." + serviceName;
				IType type =
						WorkspaceUtils.waitForType(packageFragment.getJavaProject(), qualifiedServiceName);
				JavaUI.openInEditor(type);
			}			
			// DaoFactory
			{
				if (packageFragmentDAO != null) {
					ICompilationUnit factoryUnit =
							packageFragmentDAO.getCompilationUnit("DaoFactory.java");			
					AstEditor editor = new AstEditor(factoryUnit);
					MethodDeclaration methodDeclaration;
					TypeDeclaration factoryPrimaryType = editor.getPrimaryType();
					BodyDeclarationTarget bodyDeclarationTarget =
							new BodyDeclarationTarget(factoryPrimaryType, false);
					//classDAO = classDAO.substring(0, classDAO.indexOf(" ")).trim();
					String classDAONameCamel = classDTOName.substring(0, 1).toUpperCase();
					if (classDTOName.length() > 1)
						classDAONameCamel = classDAONameCamel.substring(0, 1) + classDTOName.substring(1);				
			        String methodHeader = "public Dao<"+classDAONameCamel+"> get"+classDAONameCamel+"Dao()";
			        final List<String> lines = Lists.newArrayList("return new Dao<"+classDAONameCamel+">(this.session, "+classDAONameCamel+".class);");
			        methodDeclaration = editor.addMethodDeclaration(methodHeader, lines, bodyDeclarationTarget);				
			        editor.resolveImports(methodDeclaration);
			        editor.saveChanges(true);
				}
			}		
			
		}
	}

}
