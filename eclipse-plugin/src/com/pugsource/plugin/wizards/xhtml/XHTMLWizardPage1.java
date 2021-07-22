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
package com.pugsource.plugin.wizards.xhtml;

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
public class XHTMLWizardPage1 extends AbstractGwtWizardPage {
  private IPackageFragment m_selectedPackage;
  public XHTMLComposite1 m_serviceComposite1;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public XHTMLWizardPage1(IPackageFragment selectedPackage) {
    m_selectedPackage = selectedPackage;
    setTitle("New CRUD (Create, Read, Update and Delete)");
    setMessage("Create a new Form CRUD XHTML...");
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
          new XHTMLComposite1(parent, SWT.NONE, messagesContainer, m_selectedPackage);
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
    classDAO = classDAO.substring(0, classDAO.indexOf(" ")-1);
    String title = m_serviceComposite1.getTitle();
    String externalForm = m_serviceComposite1.getExternalForm();
    TableItem[] grid = m_serviceComposite1.getGrid();
    TableItem[] form = m_serviceComposite1.getForm();
    int titlePosition = m_serviceComposite1.getTitlePosition();
    String webContext = m_serviceComposite1.getWebContext();
    
    CreateXHTMLperation operation = new CreateXHTMLperation();
    operation.create(packageFragment, webContext, serviceName, title, titlePosition, classDAO, grid, form, externalForm);
  }
}
