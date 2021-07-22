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
package com.pugsource.plugin.wizards.module;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;

import com.google.gdt.eclipse.designer.util.ModuleDescription;
import com.google.gdt.eclipse.designer.util.Utils;
import com.google.gdt.eclipse.designer.wizards.model.common.IMessageContainer;
import com.pugsource.plugin.actions.model.IModuleConfigurator;
import com.pugsource.plugin.wizards.AbstractGwtWizardPage;

/**
 * Wizard page for module creation.
 * 
 * @author scheglov_ke
 * @author sablin_aa
 * @coverage gwt.wizard.ui
 * modified by alberto
 */
public class ModuleGWTWizardPage extends AbstractGwtWizardPage {
  private final IPackageFragmentRoot initialRoot;
  private ModuleGWTComposite moduleComposite;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public ModuleGWTWizardPage(IPackageFragmentRoot root) {
    initialRoot = root;
    setTitle("New GWT Module");
    setMessage("Create a new GWT module");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // GUI
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected void createPageControls(Composite parent) {
    // create GWT module parameters composite
    {
      IMessageContainer messagesContainer = IMessageContainer.Util.forWizardPage(this);
      moduleComposite = new ModuleGWTComposite(parent, SWT.NONE, messagesContainer, initialRoot);
      GridDataFactory.create(moduleComposite).grab().fill();
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Creation
  //
  ////////////////////////////////////////////////////////////////////////////
  public void createModule() throws Exception {
    IPackageFragmentRoot root = moduleComposite.getRoot();
    final String packageName = moduleComposite.getPackageName();
    final String moduleName = moduleComposite.getModuleName();
    final boolean isCreateEntryPoint = moduleComposite.createEntryPoint();
    final boolean isMvpEntryPoint = moduleComposite.createEntryPointMVP();
    final String library = moduleComposite.getLibrary();
    final int template = moduleComposite.getTemplate();
    final String loginForm = moduleComposite.getLoginForm();
    final String serviceForm = moduleComposite.getServiceForm();
    final List<String> menuOptions = moduleComposite.getMenuOptions();
    // create module
    IFile moduleFile =
        CreateModuleGWTOperation.create(
            root,
            packageName,
            moduleName,
            library,
            template,
            loginForm,
            serviceForm,
            menuOptions,
            isCreateEntryPoint,
            isMvpEntryPoint,
            false);
    ModuleDescription moduleDescription = Utils.getExactModule(moduleFile);
    // apply configurator
    {
      IModuleConfigurator configurator = moduleComposite.getConfigurator();
      if (configurator != null) {
        configurator.configure(moduleDescription);
      }
    }
  }
}
