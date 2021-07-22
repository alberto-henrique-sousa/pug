package com.pugsource.plugin.wizards.xhtml;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class TableDialogItem extends StatusDialog {

	private String title;
	private Table table;
	private ArrayList<Object> listText;
	private ArrayList<String> listValues;
	private boolean edit;
	private int type;

	public TableDialogItem(Shell parentShell, String title, Table table, boolean edit, int type) {
		super(parentShell);
		this.title = title;
		this.table = table;
		this.edit = edit;
		this.type = type;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle(title);
		//Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		//gridLayout.marginHeight = 8;
		//gridLayout.marginWidth = 8;
		gridLayout.marginLeft = 10;
		gridLayout.marginRight = 10;
		parent.setLayout(gridLayout);

		listText = new ArrayList<Object>();
		for (int j = 0; j < table.getColumnCount(); j++) {
			Label lbl = new Label(parent, SWT.NONE);
			lbl.setText(table.getColumn(j).getText() + ":");
			lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

			if (this.type > 1 && ((table.getColumn(j).getText().equals("Type") && this.type != 3) || table.getColumn(j).getText().equals("Type Column") || table.getColumn(j).getText().equals("Form Search")
					|| table.getColumn(j).getText().equals("Key")) || table.getColumn(j).getText().equals("Required")) {
				Combo field = new Combo(parent, SWT.READ_ONLY);
				field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				if (table.getColumn(j).getText().equals("Form Search")) {
					field.add("");
					for (String form : XHTMLComposite1.listForms) {
						field.add(form);
					}
				} else if (table.getColumn(j).getText().equals("Key") || table.getColumn(j).getText().equals("Required")) {
					field.add("true");
					field.add("false");
				} else {
					if (this.type == 2 || this.type == 4) {
						field.add("buttonSearch");
						field.add("calendar");
						field.add("comboBox");
						field.add("input");
						field.add("inputMask");
						field.add("inputTextarea");
						field.add("selectBooleanCheckbox");
					} else if (this.type == 3) {
						field.add("outputText");
						field.add("convertDateTime");
						field.add("selectBooleanCheckbox");
					}
				}

				if (this.edit) {				
					field.setText(this.table.getItem(this.table.getSelectionIndex()).getText(j));
				} else {
					field.select(0);					
				}

				listText.add(field);
			} else {
				Text field = new Text(parent, SWT.BORDER);
				field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

				if (this.edit) {				
					field.setText(this.table.getItem(this.table.getSelectionIndex()).getText(j).trim());
				}

				listText.add(field);
			}
		}		

		return parent;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		this.listValues = new ArrayList<String>();
		for (Object field : this.listText) {
			if (field instanceof Text)
				this.listValues.add(((Text) field).getText());
			if (field instanceof Combo)
				this.listValues.add(((Combo) field).getText());
		}
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public ArrayList<String> getTexts() {
		return this.listValues;
	}

}
