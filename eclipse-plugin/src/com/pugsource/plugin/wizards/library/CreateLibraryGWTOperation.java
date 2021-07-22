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
package com.pugsource.plugin.wizards.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.ast.AstEditor;
import org.eclipse.wb.internal.core.utils.ast.BodyDeclarationTarget;

import com.google.common.collect.Lists;
import com.google.gdt.eclipse.designer.model.module.ModuleElement;
import com.google.gdt.eclipse.designer.util.ModuleDescription;
import com.google.gdt.eclipse.designer.util.Utils;
import com.pugsource.plugin.util.Tools;
import com.pugsource.plugin.wizards.service.AbstractCreateGWTOperation;
import com.pugsource.plugin.wizards.service.CreateServiceGWTOperation;

/**
 * Operation for creating new GWT library.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.operation
 * modified by alberto
 */
public class CreateLibraryGWTOperation extends AbstractCreateGWTOperation {
	private final IPackageFragmentRoot m_root;

	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	public CreateLibraryGWTOperation(IPackageFragmentRoot root) {
		m_root = root;
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Creation 
	//
	////////////////////////////////////////////////////////////////////////////
	public void create(String basePackageName,
			String moduleName,
			boolean createHTML,
			boolean createDTOPackage,      
			boolean createFormPackage,
			boolean createImgPackage,
			boolean createServicePackage,
			boolean createServerPackage,
			boolean createDAOPackage) throws Exception {
		// create packages
		IPackageFragment basePackage = getPackage(m_root, basePackageName);
		IPackageFragment dtoPackage = null;
		//IPackageFragment formPackage = null;
		IPackageFragment imgPackage = null;
		IPackageFragment servicePackage = null;
		IPackageFragment serverPackage = null;
		IPackageFragment serverServicePackage = null;
		IPackageFragment daoPackage = null;
		IPackageFragment clientPackage = getPackage(m_root, basePackageName + ".client");    

		if (createDTOPackage){
			dtoPackage = getPackage(m_root, basePackageName + ".client.dto");    	
		}
		if (createFormPackage){
			getPackage(m_root, basePackageName + ".client.form");    	
		}
		if (createImgPackage){
			imgPackage = getPackage(m_root, basePackageName + ".client.assets");    	
		}    
		if (createServicePackage){
			servicePackage = getPackage(m_root, basePackageName + ".client.service");    	
		}
		if (createServerPackage) {
			serverPackage = getPackage(m_root, basePackageName + ".server");
			serverServicePackage = getPackage(m_root, basePackageName + ".server.service");
		}
		if (createServerPackage && createDAOPackage) {
			daoPackage = getPackage(m_root, basePackageName + ".server.dao");
		}    
		// create folders
		IFolder baseFolder = (IFolder) basePackage.getUnderlyingResource();
		IFolder publicFolder = baseFolder.getFolder("public");
		publicFolder.create(false, true, new NullProgressMonitor());
		// create module
		IFile moduleFile;
		{
			// prepare variables
			Map<String, String> variables = new HashMap<String, String>();
			variables.put("version", Tools.VERSION);
			variables.put("basePackage", basePackageName);
			variables.put("servicePackageDAO", daoPackage != null ? daoPackage.getElementName() : "");
			variables.put("servicePackageDTO", dtoPackage != null ? dtoPackage.getElementName() : "");
			variables.put("servicePackageClient", clientPackage != null ? clientPackage.getElementName() : "");
			variables.put("serverPackage", serverPackage != null ? serverPackage.getElementName() : "");
			variables.put("serverServicePackage", serverServicePackage != null ? serverServicePackage.getElementName() : "");
			variables.put("servicePackage", servicePackage != null ? servicePackage.getElementName() : "");
			variables.put("className", moduleName);
			// create files from templates
			moduleFile =
					createFileFromTemplate(basePackage, moduleName + ".gwt.xml", "Library.gwt.xml", variables);
			if (createHTML) {
				createFileFromTemplate(publicFolder, moduleName + ".html", "Library.html", variables);
			}
			createFileFromTemplate(publicFolder, moduleName + ".css", "Library.css", variables);
			copyTemplateFiles(publicFolder, "images");

			if (createServicePackage){
				createFileFromTemplate(servicePackage, "PathServices.java", "PathServices.java", variables);
			}

			if (createImgPackage){
				IFolder imgsResource = ((IFolder) imgPackage.getUnderlyingResource()).getFolder("");
				copyTemplateFiles(imgsResource, "assets");    	  
			}          

			createFileFromTemplate(clientPackage, "Resources.java", "Resources.java", variables);
			createFileFromTemplate(clientPackage, "Theme.java", "Theme.java", variables);

			if (createDTOPackage){
				createFileFromTemplate(dtoPackage, "ResultSaveDTO.java", "ResultSaveDTO.java", variables);    	      	  
			}

			if (createServerPackage && createDAOPackage) {
				createFileFromTemplate(daoPackage, "DaoFactory.java", "DaoFactory.java", variables);    	  
				//createFileFromTemplate(daoPackage, "HibernateUtil.java", "HibernateUtil.java", variables);    	  
			}

			if (createServicePackage) {
				createFileFromTemplate(serverServicePackage, "ReportServiceImpl.java", "ReportServiceImpl.java", variables);    	  
				createFileFromTemplate(serverServicePackage, "UploadServiceImpl.java", "UploadServiceImpl.java", variables);    	  
				CreateServiceGWTOperation.addServlet_intoWebXML(servicePackage, "ReportService", CreateServiceGWTOperation.getServerPackageName(servicePackage));
				CreateServiceGWTOperation.addServlet_intoWebXML(servicePackage, "UploadService", CreateServiceGWTOperation.getServerPackageName(servicePackage));

				{
					ICompilationUnit factoryUnit =
							servicePackage.getCompilationUnit("PathServices.java");			
					AstEditor editor = new AstEditor(factoryUnit);
					TypeDeclaration factoryPrimaryType = editor.getPrimaryType();
					BodyDeclarationTarget bodyDeclarationTarget =
							new BodyDeclarationTarget(factoryPrimaryType, false);
					ModuleDescription moduleDescription = Utils.getSingleModule(servicePackage);
					ModuleElement module = Utils.readModule(moduleDescription);
					final List<String> lines = Lists.newArrayList("public static final String REPORTSERVICE = host() + \""+ module.getName() + "/ReportService\";", 
							"public static final String UPLOADSERVICE = host() + \""+ module.getName() + "/UploadService\";");
					editor.addFieldDeclaration(lines, bodyDeclarationTarget);				
					editor.saveChanges(true);
				}					

			}

		}

		// open *.gwt.xml file in editor
		IDE.openEditor(DesignerPlugin.getActivePage(), moduleFile);
	}
}
