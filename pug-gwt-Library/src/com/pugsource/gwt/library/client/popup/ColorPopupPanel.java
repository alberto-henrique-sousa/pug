package com.pugsource.gwt.library.client.popup;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class ColorPopupPanel extends PopupPanel {
	
	private String color;
	
	public static String[] colors = {
			"#FFFFFF", "#CCCCCC", "#C0C0C0", "#999999", "#666666", "#333333", "#000000",
			"#FFCCCC", "#FF6666", "#FF0000", "#CC0000", "#990000", "#660000", "#330000",
			"#FFCC99", "#FF9966", "#FF9900", "#FF6600", "#CC6600", "#993300", "#993300",
			"#FFFF99", "#FFFF66", "#FFCC66", "#FFCC33", "#CC9933", "#996633", "#663333",
			"#FFFFCC", "#FFFF33", "#FFFF00", "#FFCC00", "#999900", "#666600", "#333300",
			"#99FF99", "#66FF99", "#33FF33", "#33CC00", "#009900", "#006600", "#003300",
			"#99FFFF", "#33FFFF", "#66CCCC", "#00CCCC", "#339999", "#336666", "#003333",
			"#CCFFFF", "#66FFFF", "#33CCFF", "#3366FF", "#3333FF", "#000099", "#000066",
			"#CCCCFF", "#9999FF", "#6666CC", "#6633FF", "#6600CC", "#333399", "#330099",
			"#FFCCFF", "#FF99FF", "#CC66CC", "#CC33CC", "#993399", "#663366", "#330033"
			};
	private Grid grid;

	public ColorPopupPanel() {
		super(true);
		setStyleName("ColorPopupPanel");

		grid = new Grid(10, 7);
		setWidget(grid);

		int numRows = grid.getRowCount();
		int numColumns = grid.getColumnCount();
		int pos = 0;
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				HTML html = new HTML("<div style=\"color: "+colors[pos]+";background-color:"+colors[pos]+";width:14px;height:12px;cursor: pointer;cursor: hand;\"></div>", true);
				DOM.setElementAttribute(html.getElement(), "id", "colorpopup_row_grid_" + String.valueOf(pos));
				pos ++;
				html.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						String id = DOM.getElementAttribute(((HTML) event.getSource()).getElement(), "id");
						id = id.substring(20);
						color = colors[Integer.parseInt(id)];
						hide();
					}
				});
				html.setSize("14px", "12px");
				grid.setWidget(row, col, html);
			}
		}
		grid.setSize("120px", "142px");
	}
	
	public String getColor() {
		return this.color;
	}

	public void setColor(String value) {
		this.color = value;
	}	
}
