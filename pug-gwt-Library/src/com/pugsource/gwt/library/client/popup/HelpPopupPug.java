/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

public class HelpPopupPug extends PopupPanel {
	private HTML htmlHelp;

	public static class Util {
		private static HelpPopupPug instance;

		public static HelpPopupPug getInstance(){
			if (instance == null) {
				instance = GWT.create(HelpPopupPug.class);
			}
			return instance;
		}		
		
		public static void showHelp(String help, String width, UIObject target) {
			HelpPopupPug helpPopup = HelpPopupPug.Util.getInstance();
			helpPopup.setWidth(width);
			helpPopup.htmlHelp.setHTML(help);
			helpPopup.showRelativeTo(target);
		}
		
		public static void close() {
			HelpPopupPug helpPopup = HelpPopupPug.Util.getInstance();
			helpPopup.hide();			
		}
		
	}
	
	public HelpPopupPug() {
		super(true);
		
		setAnimationEnabled(true);
		setStyleName("gwt-Box-Help");
		
		htmlHelp = new HTML("", true);
		htmlHelp.setStyleName("gwt-Help");
		setWidget(htmlHelp);
		htmlHelp.setSize("100%", "100%");
	}

}
