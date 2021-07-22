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
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;

import com.pugsource.plugin.util.Tools;
import com.pugsource.plugin.wizards.service.AbstractCreateGWTOperation;

/**
 * Operation for creating new GWT EntryPoint.
 * 
 * @author sablin_aa
 * @coverage gwt.wizard.operation
 * modified by alberto
 */
public class CreateEntryPointGWTOperationPre21 extends AbstractCreateGWTOperation {
  protected final IPackageFragmentRoot root;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public CreateEntryPointGWTOperationPre21(IPackageFragmentRoot root) {
    this.root = root;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Configuration
  //
  ////////////////////////////////////////////////////////////////////////////
  public static abstract class EntryPointPre21Configuration {
    public abstract String getPackageName();

    public abstract String getEntryPointName();
    
    public abstract String getLibrary();

    public abstract int getTemplate();
    
    public abstract String getLoginForm();
    
    public abstract String getServiceForm();

    public abstract List<String> getMenuOptions();
    
    public Map<String, String> getVariables() {
      Map<String, String> variables = new HashMap<String, String>();
      variables.put("packageName", getPackageName());
      variables.put("entryPointName", getEntryPointName());
      return variables;
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Creation 
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * Create EntryPoint.
   */
  public IFile create(EntryPointPre21Configuration configuration) throws Exception {
    String className = configuration.getEntryPointName();
    String packageName = configuration.getPackageName();
    IPackageFragment packageFragment = getPackage(root, packageName);
    // prepare variables
    Map<String, String> variables = configuration.getVariables();
    variables.put("className", className);
    String fields = "";
    String optionsHistory = "";
    String instancesOptions = "";
    String menuItem = "";
    String callModules = "";
    String importForms = "";
    String packageForm = configuration.getLibrary();
    packageForm = packageForm.substring(0, packageForm.indexOf(".library"));
    String packageDTO = packageForm + ".library.client.dto.";
    String packageService = packageForm + ".library.client.service.";
    packageForm = packageForm + ".library.client.form.";
    boolean first = true;
    for (String option : configuration.getMenuOptions()) {
		String optionCamel = option.substring(0, 1).toLowerCase() + option.substring(1);
  		callModules += "			"+(!first ? "} else " : "")+"if (form.equals(\""+option+"\")) {\n";
  		callModules += "				"+option+" "+optionCamel+" = new "+option+"();\n";
  		callModules += "				verticalPanelArea.add("+optionCamel+");\n";
	  	importForms += "import "+packageForm+option+";\n";
	  	menuItem += "		MenuItem mntm"+option+" = new MenuItem(\""+option+"\", false, new Command() {\n";
	  	menuItem += "			public void execute() {\n";
	  	menuItem += "				callModule(\""+option+"\");\n";
	  	menuItem += "			}\n";
	  	menuItem += "		});\n";
	  	menuItem += "		menuBar_1.addItem(mntm"+option+");\n\n";
	  	first = false;
    }
    callModules += "			}\n";
    String instanceLoginForm = "";
    String classLoginService = configuration.getServiceForm();
    String classLoginForm = configuration.getLoginForm();
    String instanceLoginService = "";
    if (!classLoginService.isEmpty()) {
    	classLoginService = classLoginService.replace("Service", "");
    	instanceLoginService = classLoginService.substring(0, 1).toLowerCase() + classLoginService.substring(1);
    }
    if (configuration.getLoginForm() != null && !configuration.getLoginForm().isEmpty()) {
    	instanceLoginForm = classLoginForm.substring(0, 1).toLowerCase() + classLoginForm.substring(1);
	  	importForms += "import "+packageForm+classLoginForm+";\n";
	  	importForms += "import "+packageDTO+classLoginService+"DTO;\n";
	  	importForms += "import "+packageService+classLoginService+"Service;\n";
	  	importForms += "import "+packageService+classLoginService+"ServiceAsync;\n";
	  	importForms += "import "+packageService+"PathServices;\n";
    }
	variables.put("version", Tools.VERSION);
    variables.put("menuOptionsFields", fields);
    variables.put("optionsHistory", optionsHistory);
    variables.put("instancesOptions", instancesOptions);
    variables.put("menuItem", menuItem);
    variables.put("callModules", callModules);    
    variables.put("importForms", importForms);    
    variables.put("classLoginForm", classLoginForm);    
    variables.put("instanceLoginForm", instanceLoginForm);
    variables.put("classLoginService", classLoginService);    
    variables.put("instanceLoginService", instanceLoginService);        
    variables.put("pathLoginService", classLoginService.toUpperCase());    
    // create files from templates
    return createFileFromTemplate(packageFragment, className + ".java", "Module.template."+configuration.getTemplate()+".java", variables);
  }
}
