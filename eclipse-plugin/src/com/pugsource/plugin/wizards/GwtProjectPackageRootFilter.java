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
package com.pugsource.plugin.wizards;

import com.google.gdt.eclipse.designer.util.Utils;

import org.eclipse.wb.internal.core.utils.jdt.ui.IPackageRootFilter;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;

/**
 * {@link IPackageRootFilter} that selects only GWT projects.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard
 * modified by alberto
 */
public class GwtProjectPackageRootFilter implements IPackageRootFilter {
  public boolean select(IJavaProject javaProject) {
    return Utils.isGWTProject(javaProject);
  }

  public boolean select(IPackageFragmentRoot packageFragmentRoot) {
    return true;
  }
}
