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
package br.com.pug.plugin.jsf.wizards.xhtml;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.internal.core.utils.dialogfields.ComboDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;

import br.com.pug.plugin.jsf.wizards.AbstractComposite;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class XHTMLComposite3 extends AbstractComposite {
	public TableDialogField tableDialogFieldForm;
	//public ComboDialogField dialogBoxForm;
	public ComboDialogField labelPosition;

	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	public XHTMLComposite3(Composite parent,
			int style,
			IMessageContainer messageContainer,
			IPackageFragment selectedPackage) {
		super(parent, style, messageContainer);
		//
		int columns = 3;
		setLayout(new GridLayout(columns, false));
		{
			labelPosition = new ComboDialogField(SWT.READ_ONLY);
			labelPosition.setItems(new String[]{
	                "Left",
	                "Top"});		
			labelPosition.setLabelText("Position Title:");
			labelPosition.setDialogFieldListener(m_validateListener);
			labelPosition.doFillIntoGrid(this, columns);
			labelPosition.selectItem(0);
		}
		
		{
		    Label labelGrid = new Label(this, SWT.NONE);
		    labelGrid.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		    labelGrid.setText("&Form:");
			
		    tableDialogFieldForm = new TableDialogField(this, 0, "Form", 4);

		    final TableColumn titleTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    titleTableColumn.setWidth(160);
		    titleTableColumn.setText("Title");		    
			
		    final TableColumn fieldTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    fieldTableColumn.setWidth(160);
		    fieldTableColumn.setText("Field");
		    
		    //final TableColumn keyTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    //keyTableColumn.setWidth(60);
		    //keyTableColumn.setText("Key");		    
		    
		    //final TableColumn objectTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    //objectTableColumn.setWidth(160);
		    //objectTableColumn.setText("Object");		    
		    	    
		    //final TableColumn formTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    //formTableColumn.setWidth(160);
		    //formTableColumn.setText("Form Search");		    
		    
		    final TableColumn maxLengthTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    maxLengthTableColumn.setWidth(100);
		    maxLengthTableColumn.setText("Max Length");
		    
		    final TableColumn widthTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    widthTableColumn.setWidth(60);
		    widthTableColumn.setText("Style Width (250px)");
		    
		    final TableColumn typeTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    typeTableColumn.setWidth(160);
		    typeTableColumn.setText("Type");		    		    

		    final TableColumn maskTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    maskTableColumn.setWidth(160);
		    maskTableColumn.setText("Mask");
		    
		    final TableColumn validTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    validTableColumn.setWidth(80);
		    validTableColumn.setText("Required");
		    
		    final TableColumn msgErrorTableColumn = new TableColumn(tableDialogFieldForm.getTable(), SWT.NONE);
		    msgErrorTableColumn.setWidth(160);
		    msgErrorTableColumn.setText("Required Msg");		    		    
		    
		    new Label(this, SWT.NONE);
		}		
		
		{
			/*dialogBoxForm = new ComboDialogField(SWT.READ_ONLY);
			dialogBoxForm.setLabelText("or External Form:");
			dialogBoxForm.addItem("");
			dialogBoxForm.setDialogFieldListener(m_validateListener);
			dialogBoxForm.doFillIntoGrid(this, columns);*/			
		}
		
					
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Validation
	//
	////////////////////////////////////////////////////////////////////////////
	@Override
	protected String validate() {
		return null;
	}
	
	public String getFormDialogBox() {
		String name = "";
		//if (dialogBoxForm.getSelectionIndex() > -1)
		//	name = dialogBoxForm.getText();
		return name;
	}	
	
}
