/*******************************************************************************
 * Copyright 2016 Pug Plugin. All Rights Reserved.
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
package br.com.pug.plugin.jsf.wizards.xhtml;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wb.internal.core.DesignerPlugin;

import br.com.pug.plugin.jsf.Activator;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class XHTMLWizard extends Wizard implements INewWizard {
  private IPackageFragment m_selectedPackage;
  private XHTMLWizardPage1 m_servicePage1;
  private XHTMLWizardPage2 m_servicePage2;
  private XHTMLWizardPage3 m_servicePage3;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public XHTMLWizard() {
    setDefaultPageImageDescriptor(Activator.getImageDescriptor("wizards/crud/banner.png"));
    setWindowTitle("New CRUD XHTML");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Pages
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public void addPages() {
    m_servicePage1 = new XHTMLWizardPage1(m_selectedPackage);
    m_servicePage2 = new XHTMLWizardPage2(m_selectedPackage, m_servicePage1);
    m_servicePage3 = new XHTMLWizardPage3(m_selectedPackage, m_servicePage1);
        
    addPage(m_servicePage1);    
    addPage(m_servicePage2);
    addPage(m_servicePage3);
    
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
      m_servicePage1.createService();
      return true;
    } catch (Throwable e) {
      DesignerPlugin.log(e);
    }
    return false;
  }
  
  public XHTMLWizardPage1 page1() {
	  return m_servicePage1;
  }
  
  public XHTMLWizardPage2 page2() {
	  return m_servicePage2;
  }
  
}
