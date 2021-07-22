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
package com.pugsource.plugin.wizards.mvp;

import com.google.gdt.eclipse.designer.model.module.ModuleElement;
import com.google.gdt.eclipse.designer.util.DefaultModuleProvider;
import com.google.gdt.eclipse.designer.util.DefaultModuleProvider.ModuleModification;
import com.google.gdt.eclipse.designer.util.ModuleDescription;

import org.eclipse.wb.internal.core.utils.execution.ExecutionUtils;
import org.eclipse.wb.internal.core.utils.execution.RunnableEx;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import java.lang.reflect.InvocationTargetException;

/**
 * Operation for configuring GWT module for using MVP framework.
 * 
 * @author sablin_aa
 * @coverage gwt.wizard.operation
 */
public final class ConfigureMvpGWTOperation extends WorkspaceModifyOperation {
  private final ModuleDescription module;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public ConfigureMvpGWTOperation(ModuleDescription module) {
    this.module = module;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Operation
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
      InterruptedException {
    ExecutionUtils.runRethrow(new RunnableEx() {
      public void run() throws Exception {
        execute0();
      }
    });
  }

  private static final String PLACE_NAME = "com.google.gwt.place.Place";
  private static final String ACTIVITY_NAME = "com.google.gwt.activity.Activity";

  private void execute0() throws Exception {
    // add inherits for Place
    ensureInheritsPlace(module);
    // add inherits for Activity
    ensureInheritsActivity(module);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Utilities
  //
  ////////////////////////////////////////////////////////////////////////////
  public static void ensureInheritsPlace(ModuleDescription module) throws Exception {
    ensureInherits(module, PLACE_NAME);
  }

  public static void ensureInheritsActivity(ModuleDescription module) throws Exception {
    ensureInherits(module, ACTIVITY_NAME);
  }

  private static void ensureInherits(ModuleDescription module, final String newModuleName)
      throws Exception {
    DefaultModuleProvider.modify(module, new ModuleModification() {
      public void modify(ModuleElement moduleElement) throws Exception {
        if (moduleElement.getInheritsElement(newModuleName) == null) {
          moduleElement.addInheritsElement(newModuleName);
        }
      }
    });
  }
}