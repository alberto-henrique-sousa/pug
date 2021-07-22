/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
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
package com.pugsource.plugin.wizards.project;

import com.google.gdt.eclipse.designer.model.module.AbstractModuleElement;

import org.eclipse.wb.internal.core.utils.xml.DocumentTextNode;

/**
 * @author scheglov_ke
 * @coverage gwt.model.web
 */
public class FilterNameElement extends AbstractModuleElement {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public FilterNameElement() {
    super("filter-name");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  public String getName() {
    DocumentTextNode textNode = getTextNode();
    return textNode.getText();
  }

  public void setName(String name) {
    DocumentTextNode textNode = getTextNode();
    if (textNode != null) {
      textNode.setText(name);
    } else {
      textNode = new DocumentTextNode(false);
      textNode.setText(name);
      setTextNode(textNode);
    }
  }
}
