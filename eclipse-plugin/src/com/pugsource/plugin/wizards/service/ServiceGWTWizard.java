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

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wb.internal.core.DesignerPlugin;

import com.pugsource.plugin.wizards.Activator;

/**
 * Wizard for new GWT RemoteModule.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.ui
 * modified by alberto
 */
public class ServiceGWTWizard extends Wizard implements INewWizard {
  private IPackageFragment m_selectedPackage;
  private ServiceGWTWizardPage m_servicePage;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public ServiceGWTWizard() {
    setDefaultPageImageDescriptor(Activator.getImageDescriptor("wizards/service/banner.png"));
    setWindowTitle("New GWT RemoteService");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Pages
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public void addPages() {
    m_servicePage = new ServiceGWTWizardPage(m_selectedPackage);
    addPage(m_servicePage);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // INewWizard
  //
  ////////////////////////////////////////////////////////////////////////////
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    if (selection != null) {
      Object selectedObject = selection.getFirstElement();
      // convert resource to Java model object
      if (selectedObject instanceof IResource) {
        IResource resource = (IResource) selectedObject;
        selectedObject = JavaCore.create(resource);
      }
      // prepare selected package
      if (selectedObject instanceof IJavaElement) {
        IJavaElement selectedJavaElement = (IJavaElement) selectedObject;
        m_selectedPackage =
            (IPackageFragment) selectedJavaElement.getAncestor(IJavaElement.PACKAGE_FRAGMENT);
      } else {
        m_selectedPackage = null;
      }
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
      m_servicePage.createService();
      return true;
    } catch (Throwable e) {
      DesignerPlugin.log(e);
    }
    return false;
  }
}
