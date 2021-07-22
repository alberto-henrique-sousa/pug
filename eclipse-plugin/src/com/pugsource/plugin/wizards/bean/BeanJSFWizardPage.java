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
package com.pugsource.plugin.wizards.bean;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;
import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;

import com.pugsource.plugin.wizards.AbstractGwtWizardPage;

/**
 * Wizard page for RemoteService creation.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.ui
 * modified by alberto
 */
public class BeanJSFWizardPage extends AbstractGwtWizardPage {
  private final IPackageFragment m_selectedPackage;
  private BeanJSFComposite m_serviceComposite;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public BeanJSFWizardPage(IPackageFragment selectedPackage) {
    m_selectedPackage = selectedPackage;
    setTitle("New Bean");
    setMessage("Create a Bean");
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
          new BeanJSFComposite(parent, SWT.NONE, messagesContainer, m_selectedPackage);
      GridDataFactory.create(m_serviceComposite).grab().fill();
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Creation
  //
  ////////////////////////////////////////////////////////////////////////////
  public void createService() throws Exception {
    IPackageFragment packageFragment = m_serviceComposite.getPackageFragment();
    String serviceName = m_serviceComposite.getServiceName();
    String classDTO = m_serviceComposite.getClassDAO();
    boolean bean = m_serviceComposite.getCreateBean();
    boolean converter = m_serviceComposite.getCreateConverter();
    //
    CreateBeanJSFOperation operation = new CreateBeanJSFOperation();
    operation.create(packageFragment, m_serviceComposite.getPackageFragmentDAO(), serviceName, classDTO, false, bean, converter);
  }
}
