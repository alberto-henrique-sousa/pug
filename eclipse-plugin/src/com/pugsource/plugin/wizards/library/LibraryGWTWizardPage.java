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

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;

import com.google.gdt.eclipse.designer.wizards.model.common.IMessageContainer;
import com.pugsource.plugin.wizards.AbstractGwtWizardPage;

/**
 * Wizard page for GWT library creation.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.ui
 * modified by alberto
 */
public class LibraryGWTWizardPage extends AbstractGwtWizardPage {
  private final IPackageFragmentRoot m_initialRoot;
  private LibraryGWTComposite m_moduleComposite;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public LibraryGWTWizardPage(IPackageFragmentRoot root) {
    m_initialRoot = root;
    setTitle("New GWT Library");
    setMessage("Create a new GWT library with shared Widgets, RemoteService's, public resources, etc.");
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
      m_moduleComposite = new LibraryGWTComposite(parent, SWT.NONE, messagesContainer, m_initialRoot);
      GridDataFactory.create(m_moduleComposite).grab().fill();
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Creation
  //
  ////////////////////////////////////////////////////////////////////////////
  public void createLibrary() throws Exception {
    IPackageFragmentRoot root = m_moduleComposite.getRoot();
    String packageName = m_moduleComposite.getPackageName();
    String moduleName = m_moduleComposite.getModuleName();
    boolean createHTML = m_moduleComposite.createHTML();
    boolean createDTOPackage = m_moduleComposite.createDTOPackage();
    boolean createServerPackage = m_moduleComposite.createServerPackage();
    boolean createDAOPackage = m_moduleComposite.createDAOPackage();
    boolean createFormPackage = m_moduleComposite.createFormPackage();
    boolean createImgPackage = m_moduleComposite.createImgPackage();
    boolean createServicePackage = m_moduleComposite.createServicePackage();
    
    CreateLibraryGWTOperation operation = new CreateLibraryGWTOperation(root);
    operation.create(packageName, moduleName, createHTML, createDTOPackage, createFormPackage, createImgPackage, createServicePackage, createServerPackage, createDAOPackage);
  }
}
