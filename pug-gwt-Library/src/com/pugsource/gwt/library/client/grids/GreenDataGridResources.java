/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.grids;

import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.DataGrid.Resources;

public interface GreenDataGridResources extends Resources {
	@Source({DataGrid.Style.DEFAULT_CSS, "greendatagrid.css"})
	CustomStyle dataGridStyle();

	interface CustomStyle extends DataGrid.Style {
	}
}