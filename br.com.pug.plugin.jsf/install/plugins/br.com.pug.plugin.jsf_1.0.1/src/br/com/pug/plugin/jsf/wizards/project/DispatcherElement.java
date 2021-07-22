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
package br.com.pug.plugin.jsf.wizards.project;

import org.eclipse.wb.internal.core.utils.xml.DocumentTextNode;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class DispatcherElement extends AbstractModuleElement {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public DispatcherElement() {
    super("dispatcher");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  public String getDispatcher() {
    DocumentTextNode textNode = getTextNode();
    return textNode.getText();
  }

  public void setDispatcher(String dispatcher) {
    DocumentTextNode textNode = getTextNode();
    if (textNode != null) {
      textNode.setText(dispatcher);
    } else {
      textNode = new DocumentTextNode(false);
      textNode.setText(dispatcher);
      setTextNode(textNode);
    }
  }
}
