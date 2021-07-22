/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.ui;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class ButtonPug extends FocusPanel {
	
	private Label lblTitle;
	private boolean enabled;
	public final static String BUTTON_COLOR_BLUE = "gwt-button-css"; 
	public final static String BUTTON_COLOR_RED = "gwt-button-css-red"; 
	public final static String BUTTON_COLOR_WHITE_LABEL = "gwt-button-css-label";
	
	/**
	 * @wbp.parser.constructor
	 */
	public ButtonPug() {
		layout("gwt-button-css", "gwt-button-css-label");
	}

	public ButtonPug(String label) {
		layout("gwt-button-css", "gwt-button-css-label");
		setLabel(label);
	}
	
	public ButtonPug(String label, String css, String cssLabel) {
		layout(css, cssLabel);
		setLabel(label);
	}
	
	private void layout(String css, String cssLabel) {
		this.enabled = true;
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setFocus(false);
			}
		});
		this.setStyleName(css);
		this.setSize("50px", "25px");		
		
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setSize("100%", "100%");
		
		lblTitle = new Label("Button");
		lblTitle.setStyleName(cssLabel);
		verticalPanel.add(lblTitle);
	}
		
	public void setLabel(String text) {
		lblTitle.setText(text);
	}
	
	public String getLabel() {
		return lblTitle.getText();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
