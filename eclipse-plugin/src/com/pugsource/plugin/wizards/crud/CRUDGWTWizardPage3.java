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
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;

import com.pugsource.plugin.wizards.AbstractGwtWizardPage;

/**
 * @author alberto
 */
public class CRUDGWTWizardPage3 extends AbstractGwtWizardPage {
  private IPackageFragment m_selectedPackage;
  private CRUDGWTComposite3 m_serviceComposite;
  private CRUDGWTWizardPage1 crudGWTWizardPage1;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public CRUDGWTWizardPage3(IPackageFragment selectedPackage, CRUDGWTWizardPage1 crudGWTWizardPage1) {
    m_selectedPackage = selectedPackage;
    this.crudGWTWizardPage1 = crudGWTWizardPage1; 
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
      m_serviceComposite =
          new CRUDGWTComposite3(parent, SWT.NONE, messagesContainer, m_selectedPackage);
      this.crudGWTWizardPage1.m_serviceComposite1.pageComposite3 = m_serviceComposite;
      GridDataFactory.create(m_serviceComposite).grab().fill();
    }
  }

}
