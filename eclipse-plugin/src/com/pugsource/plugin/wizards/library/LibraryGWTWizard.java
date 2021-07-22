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

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.jdt.core.CodeUtils;

import com.pugsource.plugin.wizards.Activator;

/**
 * Wizard for new GWT library.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.ui
 * modified by alberto
 */
public class LibraryGWTWizard extends Wizard implements INewWizard {
  private IPackageFragmentRoot m_sourceFolder;
  private LibraryGWTWizardPage m_libraryPage;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public LibraryGWTWizard() {
    setDefaultPageImageDescriptor(Activator.getImageDescriptor("wizards/library/banner.png"));
    setWindowTitle("New GWT Library");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Pages
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public void addPages() {
    m_libraryPage = new LibraryGWTWizardPage(m_sourceFolder);
    addPage(m_libraryPage);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // INewWizard
  //
  ////////////////////////////////////////////////////////////////////////////
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    m_sourceFolder = null;
    try {
      Object selectedObject = selection.getFirstElement();
      if (selectedObject instanceof IJavaElement) {
        IJavaElement element = (IJavaElement) selectedObject;
        m_sourceFolder = CodeUtils.getPackageFragmentRoot(element);
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
      m_libraryPage.createLibrary();
      return true;
    } catch (Throwable e) {
      DesignerPlugin.log(e);
    }
    return false;
  }
}
