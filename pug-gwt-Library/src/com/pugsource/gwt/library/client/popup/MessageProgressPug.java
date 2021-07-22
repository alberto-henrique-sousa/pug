/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MessageProgressPug extends PopupPanel {
	private Label lblMsg;
	private boolean isProgress = false;

	public static class Util {
		private static MessageProgressPug instance;
		
		public static MessageProgressPug getInstance(){
			if (instance == null) {
				instance = GWT.create(MessageProgressPug.class);
			}
			return instance;
		}
		
		public static void show(String msg) {
			show(msg, false);
		}		
		
		public static void show(String msg, boolean wait) {
			MessageProgressPug message = MessageProgressPug.Util.getInstance();
			message.setGlassEnabled(wait);
			message.setModal(wait);
			message.setAutoHideEnabled(!wait);
			message.lblMsg.setText(msg != null && msg.length() > 0 ? msg : "Aguarde...");
		    int left = (Window.getClientWidth() - message.getOffsetWidth()) >> 1;
		    message.setPopupPosition(Math.max(Window.getScrollLeft() + left, 0), Math.max(
		        Window.getScrollTop(), 0) + 9);
		    message.isProgress = true;
			message.show();						
		}
		
		public static void close() {
			MessageProgressPug message = MessageProgressPug.Util.getInstance();
			if (message != null) {
				message.isProgress = false;
				message.hide();
			}	
		}
		
		public static boolean isProgress() {
			MessageProgressPug message = MessageProgressPug.Util.getInstance();
			return message != null ? message.isProgress : false;
		}
	}	
	
	public MessageProgressPug() {
		super(true);
		
		setStyleName("gwt-Progress");
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSpacing(5);
		verticalPanel.setStyleName("gwt-Box-Progress");
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		setWidget(verticalPanel);
		verticalPanel.setWidth("");
		
		lblMsg = new Label("CONSTANTS.progressTitle()");
		lblMsg.setStyleName("gwt-Box-Progress-Label");
		verticalPanel.add(lblMsg);
	}

}
