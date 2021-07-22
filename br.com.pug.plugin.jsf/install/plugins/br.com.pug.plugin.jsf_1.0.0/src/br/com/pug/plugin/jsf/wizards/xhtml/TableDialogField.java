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

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author Alberto (alberto.henrique.sousa@gmail.com)
 */
public class TableDialogField extends Composite {
	
	private Table table;
	private Shell shell;
	private String titleTable;
	private int type;

	public TableDialogField(Composite parent, int style, String title, int type) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		this.titleTable = title;
		this.shell = parent.getShell();
		this.type = type;

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.widthHint = 300;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseDoubleClick(MouseEvent e) {
	        	edit();
	        }
	      });		

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		composite.setLayout(new GridLayout(1, false));

		Button btnUp = new Button(composite, SWT.NONE);
		btnUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionIndex() > 0) {
					int pos = table.getSelectionIndex();
					String currentValues = "";
					String beforeValues = "";
					
					TableItem currentItem = table.getItem(pos);
					TableItem beforeItem = table.getItem(pos-1);
					
					for (int j = 0; j < table.getColumnCount(); j++) {
						currentValues += (currentItem.getText(j).isEmpty() ? " " : currentItem.getText(j)) + "~";
						beforeValues += (beforeItem.getText(j).isEmpty() ? " " : beforeItem.getText(j)) + "~";
					}					
					currentValues = currentValues.substring(0, currentValues.length()-1);
					beforeValues = beforeValues.substring(0, beforeValues.length()-1);
					
					currentItem.setText(beforeValues.split("~"));
					beforeItem.setText(currentValues.split("~"));
					
					table.select(pos-1);
				}
			}
		});
		btnUp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnUp.setSize(68, 23);
		btnUp.setText("Up");

		Button btnDown = new Button(composite, SWT.NONE);
		btnDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionIndex() < table.getItemCount()-1) {
					int pos = table.getSelectionIndex();
					String currentValues = "";
					String afterValues = "";
					
					TableItem currentItem = table.getItem(pos);
					TableItem afterItem = table.getItem(pos+1);
					
					for (int j = 0; j < table.getColumnCount(); j++) {
						currentValues += (currentItem.getText(j).isEmpty() ? " " : currentItem.getText(j)) + "~";
						afterValues += (afterItem.getText(j).isEmpty() ? " " : afterItem.getText(j)) + "~";
					}					
					currentValues = currentValues.substring(0, currentValues.length()-1);
					afterValues = afterValues.substring(0, afterValues.length()-1);
					
					currentItem.setText(afterValues.split("~"));
					afterItem.setText(currentValues.split("~"));
					
					table.select(pos+1);
				}
			}
		});
		btnDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDown.setText("Down");

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				add(); 				
			}
		});
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAdd.setText("Add...");
		
		Button btnEdit = new Button(composite, SWT.NONE);
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				edit();
			}
		});
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit");		

		Button btnRemove = new Button(composite, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.getSelectionIndex() > -1) {
					table.remove(table.getSelectionIndex());
				}
			}
		});
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRemove.setText("Remove");
		
		Button btnRemoveAll = new Button(composite, SWT.NONE);
		btnRemoveAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table.removeAll();
			}
		});
		btnRemoveAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRemoveAll.setText("Remove All");
		
	}

	public Table getTable() {
		return this.table;
	}

	public void edit() {
		if (table.getSelection() != null && table.getItemCount() > 0) {
			TableDialogItem dialog = new TableDialogItem(shell, "Edit (" + titleTable + ")", table, true, type);
			dialog.create();
			if (dialog.open() == Window.OK) {
				TableItem item = table.getItem(table.getSelectionIndex());
				String values = "";
				for (String field : dialog.getTexts()) {
					values += (!field.trim().isEmpty() ? field : " " ) + "~";
				}
				values = values.substring(0, values.length()-1);
				item.setText(values.split("~"));
			} 				
		}
	}

	public void add() {
		TableDialogItem dialog = new TableDialogItem(shell, "Add... (" + titleTable + ")", table, false, type);
		dialog.create();
		if (dialog.open() == Window.OK) {
			TableItem item = new TableItem (table, SWT.NONE);
			String values = "";
			for (String field : dialog.getTexts()) {
				values += field + "~";
			}
			values = values.substring(0, values.length()-1);
			item.setText(values.split("~"));
			table.select(table.getItemCount()-1);
			table.showSelection();					
		}
	}

}
