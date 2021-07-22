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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import br.com.pug.plugin.jsf.util.Tools;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class AddPugJSFWizardPage extends WizardPage {
	private Text containerText;
	private Text webInfSrcText;
	private Text webContentText;
	private Text packageText;
	private Text packageBeanText;
	private Text packageBeanUtilText;
	private Text packageDaoText;
	private Text packageLazyText;
	private Text packageConverterText;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public AddPugJSFWizardPage(ISelection selection) {
		super("wizardPage");		
		setTitle(Tools.pluginJSFTitle()+" - Add Pug");
		setDescription("Add Pug in the project");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		IStructuredSelection ssel = (IStructuredSelection)selection;
		Object obj = ssel.getFirstElement();
		IResource  resource  = null;
		IContainer containerWork = null;
		if (obj instanceof IResource){
			resource = (IResource)obj;
		} else if(obj instanceof IAdaptable){
			resource = (IResource)((IAdaptable)obj).getAdapter(IResource.class);
		}
		if(resource!=null){
			if(resource instanceof IContainer){
				containerWork = (IContainer)resource;
			} else {
				containerWork = resource.getParent();
			}
		}

		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		if (containerWork!=null){
			containerText.setText(containerWork.getFullPath().toString());
		}		
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.widthHint = 75;
		button.setLayoutData(gd);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
				
		label = new Label(container, SWT.NULL);
		label.setText("&src:");
		
		webInfSrcText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		webInfSrcText.setLayoutData(gd);
		webInfSrcText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");

		label = new Label(container, SWT.NULL);
		label.setText("&Package Basic:");
		
		packageText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		packageText.setLayoutData(gd);
		packageText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");
		
		label = new Label(container, SWT.NULL);
		label.setText("Package &Bean:");
		
		packageBeanText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		packageBeanText.setLayoutData(gd);
		packageBeanText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");
		
		label = new Label(container, SWT.NULL);
		label.setText("Package Bean &Util:");
		
		packageBeanUtilText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		packageBeanUtilText.setLayoutData(gd);
		packageBeanUtilText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");
		
		label = new Label(container, SWT.NULL);
		label.setText("Package &Converter:");
		
		packageConverterText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		packageConverterText.setLayoutData(gd);
		packageConverterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");										
		
		label = new Label(container, SWT.NULL);
		label.setText("Package &DAO:");
		
		packageDaoText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		packageDaoText.setLayoutData(gd);
		packageDaoText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");
		
		label = new Label(container, SWT.NULL);
		label.setText("Package &Lazy:");
		
		packageLazyText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		packageLazyText.setLayoutData(gd);
		packageLazyText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");	
		
		label = new Label(container, SWT.NULL);
		label.setText("&WebContent:");
		
		webContentText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		webContentText.setLayoutData(gd);
		webContentText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");
		
		label = new Label(container, SWT.NULL);
		label.setText("");
		               
        initialize();
		dialogChanged();
		setControl(container);
		new Label(container, SWT.NONE);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		webInfSrcText.setText("/src");
		webContentText.setText("/WebContent");
		packageText.setText("br.com.company");
		packageBeanText.setText("bean");
		packageBeanUtilText.setText("bean.util");
		packageDaoText.setText("dao");
		packageLazyText.setText("lazy");
		packageConverterText.setText("bean.converter");
		containerText.setFocus();
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select Container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
		.findMember(new Path(getContainerName()));
		IStatus val = null;

		if (getContainerName().trim().length() == 0) {
			updateStatus("Container is empty.");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("Container '"+getContainerName()+"' does not exist.");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Container without access.");
			return;
		}
		if (getWebInfSrc().trim().length() == 0) {
			updateStatus("WEB-INF src is empty.");
			return;
		}		
		if (getWebContent().trim().length() == 0) {
			updateStatus("WebContent is empty.");
			return;
		}
		if (getPackage().trim().length() == 0) {
			updateStatus("Package Basic is empty.");
			return;
		}
		if (getPackage().trim().length() > 0) {
			val = JavaConventions.validatePackageName(getPackage(), "1.5", "1.5");
	        if (val.getSeverity() == IStatus.ERROR || val.getSeverity() == IStatus.WARNING) {
				updateStatus("Basic - " + val.getMessage());
				return;
	        }					
		}		
		if (getPackageBean().trim().length() == 0) {
			updateStatus("Package Bean is empty.");
			return;
		}
		if (getPackageBean().trim().length() > 0) {
			val = JavaConventions.validatePackageName(getPackageBean(), "1.5", "1.5");
	        if (val.getSeverity() == IStatus.ERROR || val.getSeverity() == IStatus.WARNING) {
				updateStatus("Bean - " + val.getMessage());
				return;
	        }					
		}
		if (getPackageBeanUtil().trim().length() == 0) {
			updateStatus("Package Bean Util is empty.");
			return;
		}
		if (getPackageBeanUtil().trim().length() > 0) {
			val = JavaConventions.validatePackageName(getPackageBeanUtil(), "1.5", "1.5");
	        if (val.getSeverity() == IStatus.ERROR || val.getSeverity() == IStatus.WARNING) {
				updateStatus("Util - " + val.getMessage());
				return;
	        }					
		}
		if (getPackageConverter().trim().length() == 0) {
			updateStatus("Package Converter is empty.");
			return;
		}
		if (getPackageConverter().trim().length() > 0) {
			val = JavaConventions.validatePackageName(getPackageConverter(), "1.5", "1.5");
	        if (val.getSeverity() == IStatus.ERROR || val.getSeverity() == IStatus.WARNING) {
				updateStatus("Converter - " + val.getMessage());
				return;
	        }					
		}						
		if (getPackageDao().trim().length() == 0) {
			updateStatus("Package DAO is empty.");
			return;
		}
		if (getPackageDao().trim().length() > 0) {
			val = JavaConventions.validatePackageName(getPackageDao(), "1.5", "1.5");
	        if (val.getSeverity() == IStatus.ERROR || val.getSeverity() == IStatus.WARNING) {
				updateStatus("DAO - " + val.getMessage());
				return;
	        }					
		}		
		if (getPackageLazy().trim().length() == 0) {
			updateStatus("Package Lazy is empty.");
			return;
		}
		if (getPackageLazy().trim().length() > 0) {
			val = JavaConventions.validatePackageName(getPackageLazy(), "1.5", "1.5");
	        if (val.getSeverity() == IStatus.ERROR || val.getSeverity() == IStatus.WARNING) {
				updateStatus("Lazy - " + val.getMessage());
				return;
	        }					
		}
		
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText().trim();
	}

	public String getWebInfSrc() {
		return webInfSrcText.getText().trim();
	}	

	public String getWebContent() {
		return webContentText.getText().trim();
	}

	public String getPackageBean() {
		return packageBeanText.getText().trim();
	}

	public String getPackageBeanUtil() {
		return packageBeanUtilText.getText().trim();
	}

	public String getPackageDao() {
		return packageDaoText.getText().trim();
	}

	public String getPackageLazy() {
		return packageLazyText.getText().trim();
	}
	
	public String getPackageConverter() {
		return packageConverterText.getText().trim();
	}	
	
	public String getPackage() {
		return packageText.getText().trim();
	}

}