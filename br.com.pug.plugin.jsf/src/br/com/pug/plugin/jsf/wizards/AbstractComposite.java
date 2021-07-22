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
package br.com.pug.plugin.jsf.wizards;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.IDialogFieldListener;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class AbstractComposite extends Composite {
  protected final IMessageContainer m_messageContainer;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public AbstractComposite(Composite parent, int style, IMessageContainer messageContainer) {
    super(parent, style);
    m_messageContainer = messageContainer;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Validation
  //
  ////////////////////////////////////////////////////////////////////////////
  protected final IDialogFieldListener m_validateListener = new IDialogFieldListener() {
    public void dialogFieldChanged(DialogField field) {
      validateAll();
    }
  };

  /**
   * Validate all and disable/enable "Ok" button.
   */
  public final void validateAll() {
    String errorMessage = validate();
    m_messageContainer.setErrorMessage(errorMessage);
  }

  /**
   * Validate fields and returns error message or <code>null</code>.
   */
  protected String validate() {
    return null;
  }
}
