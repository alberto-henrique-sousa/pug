package com.pugsource.plugin.editors.crud;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class CRUDGWTCompositeDataGrid extends Composite {

	public CRUDGWTCompositeDataGrid(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
		GridLayout layout = new GridLayout();
		this.setLayout(layout);
		layout.numColumns = 2;
		
		UpDownListComposite upDownListComposite = new UpDownListComposite(this, SWT.NONE);
		
	}
	
}
