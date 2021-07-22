/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pugsource.gwt.library.client.GlobalResources;
import com.pugsource.gwt.library.client.ui.ButtonPug;

public class MessageBoxPug extends DialogBox {
	private HTML html;
	private Label lblTitle;
	private ButtonPug btnOk;

	public static class Util {
		private static MessageBoxPug instance;
		private static HandlerRegistration registrationClose;
		public static MessageBoxPug getInstance(){
			if (instance == null) {
				instance = GWT.create(MessageBoxPug.class);
			}
			return instance;
		}		
		public static void showMessage(String title, String msg, CloseHandler<PopupPanel> handler) {
			MessageBoxPug messageBox = MessageBoxPug.Util.getInstance();
			if (MessageBoxPug.Util.registrationClose != null) {
				MessageBoxPug.Util.registrationClose.removeHandler();
				MessageBoxPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				MessageBoxPug.Util.registrationClose = messageBox.addCloseHandler(handler);				
			}	
			messageBox.setTitleMsg(title);
			messageBox.setMsg(msg);
			messageBox.center();
			messageBox.show();		
			messageBox.btnOk.setFocus(true);
		}
		public static void showMessageRelativeTo(String title, String msg, CloseHandler<PopupPanel> handler, UIObject target) {
			MessageBoxPug messageBox = MessageBoxPug.Util.getInstance();
			if (MessageBoxPug.Util.registrationClose != null) {
				MessageBoxPug.Util.registrationClose.removeHandler();
				MessageBoxPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				MessageBoxPug.Util.registrationClose = messageBox.addCloseHandler(handler);				
			}	
			messageBox.setTitleMsg(title);
			messageBox.setMsg(msg);
			messageBox.showRelativeTo(target);
			messageBox.btnOk.setFocus(true);
		}		
	}	
	
	public MessageBoxPug() {
		final GlobalResources globalResources = GWT.create(GlobalResources.class);
		
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setStyleName("gwt-DialogBox-Pug");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("gwt-Panel-Notes");
		setWidget(verticalPanel);
		verticalPanel.setSize("365px", "100px");

		HorizontalPanel panelTitle = new HorizontalPanel();
		panelTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		panelTitle.setStyleName("gwt-MessageBox-Title");
		panelTitle.setSize("100%", "");
		panelTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(panelTitle);
		
		lblTitle = new Label("");
		lblTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTitle.setStyleName("gwt-Label-Title-Assunto");
		panelTitle.add(lblTitle);
		lblTitle.setWidth("");
		
		Image btnClose = new Image(globalResources.closeOutWhite());
		btnClose.setStyleName("gwt-header-login");
		btnClose.setTitle("Fechar");
		btnClose.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Image) event.getSource()).setResource(globalResources.closeOutWhite());
			}
		});
		btnClose.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				((Image) event.getSource()).setResource(globalResources.closeOutWhite());
			}
		});
		btnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		panelTitle.add(btnClose);
		
		Label label = new Label("");
		label.setHeight("20px");
		verticalPanel.add(label);
		
		html = new HTML("", true);
		html.setHeight("");
		verticalPanel.add(html);
		
		Label label_1 = new Label("");
		label_1.setHeight("20px");
		verticalPanel.add(label_1);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setStyleName("gwt-Gallery-Panel-Buttons");
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "35px");
		
		btnOk = new ButtonPug("Ok");
		btnOk.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					hide();					
				}
			}
		});
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel.add(btnOk);
		btnOk.setWidth("75px");
		
		Element td = getCellElement(0, 1);
		DOM.removeChild(td, (Element) td.getFirstChildElement());
		DOM.appendChild(td, panelTitle.getElement());
		
	}
	
	public String getTitleMsg() {
		return this.lblTitle.getText();
	}

	public void setTitleMsg(String text) {
		this.lblTitle.setText(" " + text);
	}
	
	public String getMsg() {
		return this.html.getHTML();
	}

	public void setMsg(String html) {
		this.html.setHTML("<div class=\"gwt-messagebox-html\">" + html + "</div>");
	}
}
