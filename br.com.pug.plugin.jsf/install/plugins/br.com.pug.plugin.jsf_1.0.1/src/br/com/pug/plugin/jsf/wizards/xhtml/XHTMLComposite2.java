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
import org.eclipse.wb.internal.core.utils.dialogfields.IMessageContainer;

import br.com.pug.plugin.jsf.wizards.AbstractComposite;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class XHTMLComposite2 extends AbstractComposite {
	public TableDialogField tableDialogFieldGrid;

	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	public XHTMLComposite2(Composite parent,
			int style,
			IMessageContainer messageContainer,
			IPackageFragment selectedPackage) {
		super(parent, style, messageContainer);
		//
		int columns = 3;
		setLayout(new GridLayout(columns, false));
		{
		    Label labelGrid = new Label(this, SWT.NONE);
		    labelGrid.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		    labelGrid.setText("&DataGrid:");
			
		    tableDialogFieldGrid = new TableDialogField(this, 0, "Data Grid", 3);

		    final TableColumn titleTableColumn = new TableColumn(tableDialogFieldGrid.getTable(), SWT.NONE);
		    titleTableColumn.setWidth(160);
		    titleTableColumn.setText("Title");		    
			
		    final TableColumn fieldTableColumn = new TableColumn(tableDialogFieldGrid.getTable(), SWT.NONE);
		    fieldTableColumn.setWidth(160);
		    fieldTableColumn.setText("Field");
		    
		    //final TableColumn objectTableColumn = new TableColumn(tableDialogFieldGrid.getTable(), SWT.NONE);
		    //objectTableColumn.setWidth(160);
		    //objectTableColumn.setText("Object");		    
		    
		    final TableColumn typeColTableColumn = new TableColumn(tableDialogFieldGrid.getTable(), SWT.NONE);
		    typeColTableColumn.setWidth(160);
		    typeColTableColumn.setText("Type Column");
		    
		    //final TableColumn typeTableColumn = new TableColumn(tableDialogFieldGrid.getTable(), SWT.NONE);
		    //typeTableColumn.setWidth(160);
		    //typeTableColumn.setText("Type");		    
		    
		    //final TableColumn widthTableColumn = new TableColumn(tableDialogFieldGrid.getTable(), SWT.NONE);
		    //widthTableColumn.setWidth(60);
		    //widthTableColumn.setText("Width");		    		    		    		    		    
		    		    
		    //final TableColumn maskTableColumn = new TableColumn(tableDialogFieldGrid.getTable(), SWT.NONE);
		    //maskTableColumn.setWidth(160);
		    //maskTableColumn.setText("Mask");
		    
		    new Label(this, SWT.NONE);
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
}
