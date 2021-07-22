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
package com.pugsource.plugin.wizards.mvp;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wb.internal.core.DesignerPlugin;

import com.pugsource.plugin.wizards.Activator;

/**
 * Wizard for new GWT MVP view.
 * 
 * @author sablin_aa
 * @coverage gwt.wizard.ui
 */
public class ViewGWTWizard extends Wizard implements INewWizard {
  private IPackageFragment initialPkg;
  private ViewGWTWizardPage viewPage;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public ViewGWTWizard() {
    setDefaultPageImageDescriptor(Activator.getImageDescriptor("wizards/module/banner.gif"));
    setWindowTitle("New GWT MVP View");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Pages
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public void addPages() {
    viewPage = new ViewGWTWizardPage(initialPkg);
    addPage(viewPage);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // INewWizard
  //
  ////////////////////////////////////////////////////////////////////////////
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    try {
      Object selectedObject = selection.getFirstElement();
      if (selectedObject instanceof IJavaElement) {
        IJavaElement element = (IJavaElement) selectedObject;
        initialPkg = (IPackageFragment) element.getAncestor(IJavaElement.PACKAGE_FRAGMENT);
      }
    } catch (Throwable e) {
      DesignerPlugin.log(e);
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Finish
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean performFinish() {
    try {
      viewPage.createView();
      return true;
    } catch (Throwable e) {
      DesignerPlugin.log(e);
    }
    return false;
  }
}
