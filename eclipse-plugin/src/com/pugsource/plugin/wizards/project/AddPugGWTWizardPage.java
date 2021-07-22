/*******************************************************************************
 * Copyright 2013 Pug Framework. All Rights Reserved.
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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.pugsource.plugin.util.Tools;

/**
 * @author alberto
 */
public class AddPugGWTWizardPage extends WizardPage {
	private Text containerText;
	private Text webInfSrcText;
	private Text webInfLibText;
	private Button generateLibrary;
	private Button generateConfig;
	private Button vraptor;
	private ISelection selection;
	private Label lblsrc;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public AddPugGWTWizardPage(ISelection selection) {
		super("wizardPage");		
		setTitle(Tools.pluginGWTTitle()+" - Add Pug");
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
				
		lblsrc = new Label(container, SWT.NULL);
		lblsrc.setText("&src:");
		
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
		label.setText("WEB-INF li&b:");
		
		webInfLibText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		webInfLibText.setLayoutData(gd);
		webInfLibText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("");
		
		label = new Label(container, SWT.NULL);
		label.setText("");
		
        Group group = new Group(container, SWT.SHADOW_ETCHED_IN);
        // Layout has 4 columns of non-equal size
        group.setLayout(new GridLayout());
        GridData gd_group = new GridData(GridData.FILL_BOTH);
        group.setLayoutData(gd_group);
        group.setFont(parent.getFont());
        group.setText("Code Generation");
        
        generateLibrary = new Button(group, SWT.CHECK);
        generateLibrary.setText("Copy libraries to WEB-INF lib");
        generateLibrary.setSelection(true);
        
        generateConfig = new Button(group, SWT.CHECK);
        generateConfig.setText("Create Config (Properties, XML)");
        generateConfig.setSelection(true);
        
        vraptor = new Button(group, SWT.CHECK);
        vraptor.setText("Add VRaptor 3.5.3");
        vraptor.setSelection(true);        
        
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
		webInfLibText.setText("/war/WEB-INF/lib");
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
		if (getWebInfLib().trim().length() == 0) {
			updateStatus("WEB-INF lib is empty.");
			return;
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

	public String getWebInfLib() {
		return webInfLibText.getText().trim();
	}		
			
	public Boolean getGenerateLibrary() {
		return getWebInfLib().length() > 0 ? generateLibrary.getSelection() : false;
	}
			
	public Boolean getGenerateConfig() {
		return getWebInfSrc().length() > 0 ? generateConfig.getSelection() : false;
	}
	
	public Boolean getVRaptor() {
		return getWebInfSrc().length() > 0 ? vraptor.getSelection() : false;
	}
		
}