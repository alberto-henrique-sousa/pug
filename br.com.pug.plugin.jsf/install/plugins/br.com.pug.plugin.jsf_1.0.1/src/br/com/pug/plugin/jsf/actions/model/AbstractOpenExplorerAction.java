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
package br.com.pug.plugin.jsf.actions.model;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import br.com.pug.plugin.jsf.Activator;
import br.com.pug.plugin.jsf.util.OperatingSystem;

/**
 * @author <a href="mailto:samson959@gmail.com">Samson Wu</a>
 * @version 1.4.0
 */
public abstract class AbstractOpenExplorerAction implements IActionDelegate,
        IPropertyChangeListener {
	protected IWorkbenchWindow window = PlatformUI.getWorkbench()
	        .getActiveWorkbenchWindow();
	protected Shell shell;
	protected ISelection currentSelection;

	protected String systemBrowser;

	public AbstractOpenExplorerAction() {
		this.systemBrowser = OperatingSystem.INSTANCE.getSystemBrowser();
		Activator.getDefault().getPreferenceStore()
		        .addPropertyChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse
	 * .jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (OperatingSystem.INSTANCE.isLinux()) {
			this.systemBrowser = OperatingSystem.INSTANCE.getSystemBrowser();
		}
	}

	public void run(IAction action) {
		if (this.currentSelection == null || this.currentSelection.isEmpty()) {
			return;
		}
		if (this.currentSelection instanceof ITreeSelection) {
			ITreeSelection treeSelection = (ITreeSelection) this.currentSelection;

			TreePath[] paths = treeSelection.getPaths();

			for (int i = 0; i < paths.length; i++) {
				TreePath path = paths[i];
				IResource resource = null;
				Object segment = path.getLastSegment();
				if ((segment instanceof IResource))
					resource = (IResource) segment;
				else if ((segment instanceof IJavaElement)) {
					resource = ((IJavaElement) segment).getResource();
				}
				if (resource == null) {
					continue;
				}
				String browser = this.systemBrowser;
				String location = resource.getLocation().toOSString();
				if ((resource instanceof IFile)) {
					location = ((IFile) resource).getParent().getLocation()
					        .toOSString();
					if (OperatingSystem.INSTANCE.isWindows()) {
						browser = this.systemBrowser + " /select,";
						location = ((IFile) resource).getLocation()
						        .toOSString();
					}
				}
				openInBrowser(browser, location);
			}
		} else if (this.currentSelection instanceof ITextSelection
		        || this.currentSelection instanceof IStructuredSelection) {
			// open current editing file
			IEditorPart editor = window.getActivePage().getActiveEditor();
			if (editor != null) {
				IFile current_editing_file = (IFile) editor.getEditorInput()
				        .getAdapter(IFile.class);
				String browser = this.systemBrowser;
				String location = current_editing_file.getParent()
				        .getLocation().toOSString();
				if (OperatingSystem.INSTANCE.isWindows()) {
					browser = this.systemBrowser + " /select,";
					location = current_editing_file.getLocation().toOSString();
				}
				openInBrowser(browser, location);
			}
		}
	}

	protected void openInBrowser(String browser, String location) {
		try {
			if (OperatingSystem.INSTANCE.isWindows()) {
				Runtime.getRuntime().exec(browser + " \"" + location + "\"");
			} else {
				Runtime.getRuntime().exec(new String[] { browser, location });
			}
		} catch (IOException e) {
			MessageDialog.openError(shell, "Error",
			        "Can't Open" + " \"" + location + "\"");
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.currentSelection = selection;
	}
}
