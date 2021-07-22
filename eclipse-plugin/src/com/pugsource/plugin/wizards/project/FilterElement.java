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

import java.util.List;

/**
 * @author scheglov_ke
 * @coverage gwt.model.web
 */
public class FilterElement extends AbstractModuleElement {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public FilterElement() {
    super("filter");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // servlet-name
  //
  ////////////////////////////////////////////////////////////////////////////
  public String getName() {
    List<FilterNameElement> elements = getChildren(FilterNameElement.class);
    return !elements.isEmpty() ? elements.get(0).getName() : null;
  }

  public void setName(String name) {
    List<FilterNameElement> elements = getChildren(FilterNameElement.class);
    FilterNameElement nameElement;
    if (!elements.isEmpty()) {
      nameElement = elements.get(0);
    } else {
      nameElement = new FilterNameElement();
      addChild(nameElement);
    }
    nameElement.setName(name);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // servlet-class
  //
  ////////////////////////////////////////////////////////////////////////////
  public String getClassName() {
    List<FilterClassElement> elements = getChildren(FilterClassElement.class);
    return !elements.isEmpty() ? elements.get(0).getClassName() : null;
  }

  public void setClassName(String name) {
    List<FilterClassElement> elements = getChildren(FilterClassElement.class);
    FilterClassElement classElement;
    if (!elements.isEmpty()) {
      classElement = elements.get(0);
    } else {
      classElement = new FilterClassElement();
      addChild(classElement);
    }
    classElement.setClassName(name);
  }
}
