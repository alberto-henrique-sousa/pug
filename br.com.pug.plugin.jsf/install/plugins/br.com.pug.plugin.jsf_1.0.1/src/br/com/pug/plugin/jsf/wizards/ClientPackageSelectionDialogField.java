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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.IStringButtonAdapter;
import org.eclipse.wb.internal.core.utils.dialogfields.StatusUtils;
import org.eclipse.wb.internal.core.utils.dialogfields.StringButtonDialogField;
import org.eclipse.wb.internal.core.utils.reflect.ReflectionUtils;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public final class ClientPackageSelectionDialogField extends StringButtonDialogField {
  private IPackageFragment m_packageFragment;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public ClientPackageSelectionDialogField(String label, String buttonLabel) {
    super(new ButtonAdapter());
    setLabelText(label);
    setButtonLabel(buttonLabel);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  public void setPackageFragment(IPackageFragment packageFragment) {
    m_packageFragment = packageFragment;
    setText(getPackageString(m_packageFragment));
  }

  public IPackageFragment getPackageFragment() {
    return m_packageFragment;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // DialogField
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public Control[] doFillIntoGrid(Composite parent, int nColumns) {
    Control[] controls = super.doFillIntoGrid(parent, nColumns);
    getTextControl(null).setEnabled(false);
    return controls;
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Utils
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * Return strings presentation of package.
   */
  private static String getPackageString(IPackageFragment packageFragment) {
    try {
      if (packageFragment != null) {
        IPackageFragmentRoot packageFragmentRoot =
            (IPackageFragmentRoot) packageFragment.getParent();
        IJavaProject javaProject = packageFragmentRoot.getJavaProject();
        if (packageFragmentRoot.getUnderlyingResource() == javaProject.getUnderlyingResource()) {
          return javaProject.getElementName() + "/" + packageFragment.getElementName();
        } else {
          return javaProject.getElementName()
              + "/"
              + packageFragmentRoot.getElementName()
              + "/"
              + packageFragment.getElementName();
        }
      }
      return "";
    } catch (Throwable e) {
      throw ReflectionUtils.propagate(e);
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Button adapter
  //
  ////////////////////////////////////////////////////////////////////////////
  private static class ButtonAdapter implements IStringButtonAdapter {
    ////////////////////////////////////////////////////////////////////////////
    //
    // IStringButtonAdapter
    //
    ////////////////////////////////////////////////////////////////////////////
    public void changeControlPressed(DialogField field) {
      ClientPackageSelectionDialogField receiver = (ClientPackageSelectionDialogField) field;
      IPackageFragment packageFragment = selectClientPackage(receiver.getPackageFragment());
      if (packageFragment != null) {
        receiver.setPackageFragment(packageFragment);
      }
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    // Select source folder
    //
    ////////////////////////////////////////////////////////////////////////////
    private IPackageFragment selectClientPackage(IPackageFragment initialSelection) {
      Shell shell = Display.getCurrent().getActiveShell();
      ILabelProvider labelProvider =
          new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
      ITreeContentProvider contentProvider = new StandardJavaElementContentProvider();
      ElementTreeSelectionDialog dialog =
          new ElementTreeSelectionDialog(shell, labelProvider, contentProvider);
      //
      dialog.setTitle("Source folder selection");
      dialog.setMessage("Choose a source folder:");
      dialog.setComparator(new ViewerComparator());
      // set validator to accept only package selection
      dialog.setValidator(new ISelectionStatusValidator() {
        public IStatus validate(Object[] selection) {
          if (selection.length == 1) {
            Object element = selection[0];
            if (isElementValid(element)) {
              return StatusUtils.OK_STATUS;
            }
          }
          return StatusUtils.ERROR_STATUS;
        }

        private boolean isElementValid(Object element) {
          if (element instanceof IPackageFragment) {
            IPackageFragment packageFragment = (IPackageFragment) element;
            try {
              return false; //Utils.isModuleSourcePackage(packageFragment);
            } catch (Throwable e) {
              DesignerPlugin.log(e);
            }
          }
          return false;
        }
      });
      dialog.addFilter(new ViewerFilter() {
        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
          try {
            if (element instanceof IJavaProject) {
              return false; //Utils.isGWTProject((IJavaProject) element);
            }
            if (element instanceof IPackageFragmentRoot) {
              return ((IPackageFragmentRoot) element).getKind() == IPackageFragmentRoot.K_SOURCE;
            }
            if (element instanceof IPackageFragment) {
              IPackageFragment packageFragment = (IPackageFragment) element;
              return false; //Utils.isModuleSourcePackage(packageFragment);
            }
          } catch (Throwable e) {
            DesignerPlugin.log(e);
          }
          return false;
        }
      });
      // configure input and initial selection
      dialog.setInput(JavaCore.create(ResourcesPlugin.getWorkspace().getRoot()));
      dialog.setInitialSelection(initialSelection);
      // open and return result
      if (dialog.open() == Window.OK) {
        return (IPackageFragment) dialog.getFirstResult();
      }
      return null;
    }
  }
}
