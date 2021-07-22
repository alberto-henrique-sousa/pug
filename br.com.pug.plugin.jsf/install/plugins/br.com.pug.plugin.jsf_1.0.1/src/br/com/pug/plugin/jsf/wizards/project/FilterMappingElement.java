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

import java.util.List;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class FilterMappingElement extends AbstractModuleElement {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public FilterMappingElement() {
    super("filter-mapping");
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
  // url-pattern
  //
  ////////////////////////////////////////////////////////////////////////////
  public String getPattern() {
    List<UrlPatternElement> elements = getChildren(UrlPatternElement.class);
    return !elements.isEmpty() ? elements.get(0).getPattern() : null;
  }
  
  public String getDispatcher() {
	    List<DispatcherElement> elements = getChildren(DispatcherElement.class);
	    return !elements.isEmpty() ? elements.get(0).getDispatcher() : null;
  }  

  public void setPattern(String pattern) {
    List<UrlPatternElement> elements = getChildren(UrlPatternElement.class);
    UrlPatternElement classElement;
    if (!elements.isEmpty()) {
      classElement = elements.get(0);
    } else {
      classElement = new UrlPatternElement();
      addChild(classElement);
    }
    classElement.setPattern(pattern);
  }
  
  public void setDispatcher(String dispatcher) {
	    //List<DispatcherElement> elements = getChildren(DispatcherElement.class);
	    DispatcherElement classElement;
	    //if (!elements.isEmpty()) {
	    //  classElement = elements.get(0);
	    //} else {
	      classElement = new DispatcherElement();
	      addChild(classElement);
	    //}
	    classElement.setDispatcher(dispatcher);
  }
  
}
