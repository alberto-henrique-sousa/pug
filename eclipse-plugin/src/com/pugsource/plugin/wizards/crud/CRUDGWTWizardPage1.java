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
package com.pugsource.plugin.wizards.crud;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;

import com.pugsource.plugin.wizards.AbstractGwtWizardPage;

/**
 * @author alberto
 */
public class CRUDGWTWizardPage1 extends AbstractGwtWizardPage {
  private IPackageFragment m_selectedPackage;
  public CRUDGWTComposite1 m_serviceComposite1;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public CRUDGWTWizardPage1(IPackageFragment selectedPackage) {
    m_selectedPackage = selectedPackage;
    setTitle("New CRUD (Create, Read, Update and Delete) or other");
    setMessage("Create a new Form CRUD or other...");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // GUI
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected void createPageControls(Composite parent) {
    // create GWT parameters composite
    {
      IMessageContainer messagesContainer = IMessageContainer.Util.forWizardPage(this);
      m_serviceComposite1 =
          new CRUDGWTComposite1(parent, SWT.NONE, messagesContainer, m_selectedPackage);
      GridDataFactory.create(m_serviceComposite1).grab().fill();
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Creation
  //
  ////////////////////////////////////////////////////////////////////////////
  public void createService() throws Exception {
    IPackageFragment packageFragment = m_serviceComposite1.getPackageFragment();
    String serviceName = m_serviceComposite1.getServiceName();
    String classDAO = m_serviceComposite1.getClassDAO();
    String template = m_serviceComposite1.getTemplate();
    String title = m_serviceComposite1.getTitle();
    String externalForm = m_serviceComposite1.getExternalForm();
    String size = m_serviceComposite1.getWinSize();
    TableItem[] order = m_serviceComposite1.getOrder();
    TableItem[] search = m_serviceComposite1.getSearch();
    TableItem[] grid = m_serviceComposite1.getGrid();
    TableItem[] form = m_serviceComposite1.getForm();
    int titlePosition = m_serviceComposite1.getTitlePosition();
    
    CreateCRUDGWTOperation operation = new CreateCRUDGWTOperation();
    operation.create(packageFragment, serviceName, title, titlePosition, size, classDAO, order, search, grid, form, template, externalForm);
  }
}
