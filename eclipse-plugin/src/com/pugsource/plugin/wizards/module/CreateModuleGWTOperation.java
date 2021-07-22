/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
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
package com.pugsource.plugin.wizards.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wb.core.editor.IDesignerEditor;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.model.util.WorkspaceUtils;

import com.google.gdt.eclipse.designer.model.web.WebUtils;
import com.pugsource.plugin.wizards.module.CreateEntryPointGWTOperation.EntryPointConfiguration;
import com.pugsource.plugin.wizards.module.CreateEntryPointGWTOperationPre21.EntryPointPre21Configuration;
import com.pugsource.plugin.wizards.mvp.CreateViewGWTOperation.ViewConfiguration;
import com.pugsource.plugin.wizards.service.AbstractCreateGWTOperation;

/**
 * Operation for creating new GWT module, for GWT 1.6+.
 * 
 * @author scheglov_ke
 * @author sablin_aa
 * @coverage gwt.wizard.operation
 * modified by alberto
 */
public class CreateModuleGWTOperation extends AbstractCreateGWTOperation {
  protected final IPackageFragmentRoot root;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public CreateModuleGWTOperation(IPackageFragmentRoot root) {
    this.root = root;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Configuration
  //
  ////////////////////////////////////////////////////////////////////////////
  public static abstract class ModuleConfiguration {
    public abstract String getPackageName();

    public abstract String getModuleName();

    public abstract String getLibrary();
    
    public abstract int getTemplate();
    
    public abstract String getLoginForm();
    
    public abstract String getServiceForm();

    public abstract List<String> getMenuOptions();    
    
    public abstract boolean isCreateEntryPoint();

    public abstract EntryPointConfiguration getEntryPointConfiguration();

    public Map<String, String> getVariables() {
      Map<String, String> variables = new HashMap<String, String>();
      variables.put("basePackageName", getPackageName());
      variables.put("moduleName", getModuleName());
      {
        // add EntryPoint variables
        EntryPointPre21Configuration entryPointConfiguration = getEntryPointConfiguration();
        if (entryPointConfiguration != null) {
          variables.putAll(entryPointConfiguration.getVariables());
        }
      }
      return variables;
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Creation 
  //
  ////////////////////////////////////////////////////////////////////////////
  public IFile create(ModuleConfiguration configuration) throws Exception {
    String moduleName = configuration.getModuleName();
    String packageName = configuration.getPackageName();
    // create packages
    IPackageFragment packageFragment = getPackage(root, packageName);
    getPackage(root, packageName + ".client");
    getPackage(root, packageName + ".server");
    // 
    // create folders
    IJavaProject javaProject = packageFragment.getJavaProject();
    IProject project = javaProject.getProject();
    String webFolderName = WebUtils.getWebFolderName(project);
    IFolder webFolder = project.getFolder(webFolderName);
    IFolder webInfFolder = project.getFolder(webFolderName + "/WEB-INF");
    // create module
    IFile file;
    if (configuration.isCreateEntryPoint()) {
      // prepare variables
      Map<String, String> variables = configuration.getVariables();
      variables.put("packageName", packageName);
      variables.put("className", moduleName);
      variables.put("htmlName", moduleName.toLowerCase());
      String libraryXML = "";
      if (!configuration.getLibrary().isEmpty())
    	  libraryXML = "<inherits name=\""+configuration.getLibrary()+"\"/>";
      variables.put("libraryXML", libraryXML);
      // prepare 'bootstrap' variable
      String bootstrapPrefix = packageName + "." + moduleName;
      variables.put("bootstrap", bootstrapPrefix + "/" + bootstrapPrefix + ".nocache.js");
      // create module
      file =
          createFileFromTemplate(
              packageFragment,
              moduleName + ".gwt.xml",
              "Module.gwt.xml",
              variables);
      // create EntryPoint
      CreateEntryPointGWTOperation entryPointOperation = new CreateEntryPointGWTOperation(root);
      entryPointOperation.create(configuration.getEntryPointConfiguration());
      // create files from templates
      createFileFromTemplate(webFolder, moduleName.toLowerCase() + ".html", "Module.html", variables);
      createFileFromTemplate(webFolder, moduleName.toLowerCase() + ".css", "Module.css", variables);
      //copyTemplateFiles(webFolder, "images");
      if (!webInfFolder.getFolder("reports").exists()) {
    	  webInfFolder.getFolder("reports").create(true, true, new NullProgressMonitor());
      }	  
      // configure web.xml
      if (!webInfFolder.getFile("web.xml").exists()) {
        variables.put("welcome-file-name", moduleName);
        createFileFromTemplate(webInfFolder, "web.xml", "web.xml", variables);
      }
      // open entry point in editor
      /*
      {
        String qualifiedEntryPoint = packageName + ".client." + moduleName;
        IType type = WorkspaceUtils.waitForType(root.getJavaProject(), qualifiedEntryPoint);
        IDE.openEditor(
            DesignerPlugin.getActivePage(),
            (IFile) type.getUnderlyingResource(),
            IDesignerEditor.ID);
      }*/
    } else {
      // create empty module
      file =
          createFile(
              packageFragment,
              moduleName + ".gwt.xml",
              "<module>\r\n\t<inherits name=\"com.google.gwt.user.User\"/>\r\n</module>");
    }
    return file;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Utilities 
  //
  ////////////////////////////////////////////////////////////////////////////
  public static IFile create(final IPackageFragmentRoot root,
      final String packageName,
      final String moduleName,
      final String library,
      final int template,
      final String loginForm,
      final String serviceForm,
      final List<String> menuOptions,
      final boolean isCreateEntryPoint,
      final boolean isMvpEntryPoint,
      final boolean isMvpViewJavaTemplate) throws Exception {
    final String VIEW_NAME = "Sample";
    final String clientPackageName = packageName + ".client";
    final ViewConfiguration viewConfiguration = new ViewConfiguration() {
      @Override
      public String getViewPackageName() {
        return clientPackageName + ".ui";
      }

      @Override
      public String getViewName() {
        return VIEW_NAME + "View";
      }

      @Override
      public boolean isUseJavaTemplate() {
        return isMvpViewJavaTemplate;
      }

      @Override
      public String getPlacePackageName() {
        return clientPackageName + ".place";
      }

      @Override
      public String getPlaceName() {
        return VIEW_NAME + "Place";
      }

      @Override
      public String getActivityPackageName() {
        return clientPackageName + ".activity";
      }

      @Override
      public String getActivityName() {
        return VIEW_NAME + "Activity";
      }

      @Override
      public String getClientFactoryPackageName() {
        return clientPackageName;
      }

      @Override
      public String getClientFactoryName() {
        return "ClientFactory";
      }

      @Override
      public Map<String, String> getVariables() {
        Map<String, String> variables = super.getVariables();
        variables.put("basePackageName", packageName);
        variables.put("entryPointPackageName", clientPackageName);
        return variables;
      }
    };
    final EntryPointConfiguration entryPointConfiguration = new EntryPointConfiguration() {

      @Override
      public String getLibrary() {
        return library;
      }
      
      @Override
      public int getTemplate() {
        return template;
      }
      
      @Override
      public String getLoginForm() {
    	return loginForm;
      }
    	  
      @Override
      public String getServiceForm() {
    	return serviceForm;
      }

      @Override
      public List<String> getMenuOptions() {
    	return menuOptions;
      }        
    	
      @Override
      public String getPackageName() {
        return clientPackageName;
      }

      @Override
      public String getEntryPointName() {
        return moduleName;
      }

      @Override
      public boolean isUseMvp() {
        return isMvpEntryPoint;
      }

      @Override
      public ViewConfiguration getViewConfiguration() {
        return viewConfiguration;
      }

      @Override
      public String getMappersPackageName() {
        return clientPackageName + ".mvp";
      }

      @Override
      public String getClientFactoryPackageName() {
        return getViewConfiguration().getClientFactoryPackageName();
      }

      @Override
      public String getClientFactoryName() {
        return getViewConfiguration().getClientFactoryName();
      }
    };
    ModuleConfiguration moduleConfiguration = new ModuleConfiguration() {
      @Override
      public String getLibrary() {
        return library;
      }
      
      @Override
      public int getTemplate() {
        return template;
      }      
      
      @Override
      public String getLoginForm() {
    	return loginForm;
      }
    	  
      @Override
      public String getServiceForm() {
    	return serviceForm;
      }

      @Override
      public List<String> getMenuOptions() {
    	return menuOptions;
      }              
    	
      @Override
      public String getPackageName() {
        return packageName;
      }

      @Override
      public String getModuleName() {
        return moduleName;
      }

      @Override
      public boolean isCreateEntryPoint() {
        return isCreateEntryPoint;
      }

      @Override
      public EntryPointConfiguration getEntryPointConfiguration() {
        return entryPointConfiguration;
      }
    };
    CreateModuleGWTOperation operation = new CreateModuleGWTOperation(root);
    return operation.create(moduleConfiguration);
  }
}
