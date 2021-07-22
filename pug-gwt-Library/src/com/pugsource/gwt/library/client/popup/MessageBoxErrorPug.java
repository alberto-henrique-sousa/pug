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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pugsource.gwt.library.client.GlobalResources;
import com.pugsource.gwt.library.client.ui.ButtonPug;

public class MessageBoxErrorPug extends DialogBox {
	private HTML html;
	private Label lblTitle;
	private ButtonPug btnOk;
	private HTML html_detail;
	private VerticalPanel verticalPanelBox;
	private ScrollPanel scrollPanelDetail;
	private Label lblLinkDetail;

	public static class Util {
		private static MessageBoxErrorPug instance;
		private static HandlerRegistration registrationClose;
		public static MessageBoxErrorPug getInstance(){
			if (instance == null) {
				instance = GWT.create(MessageBoxErrorPug.class);
			}
			return instance;
		}		
		public static void showMessage(String title, String msg, String error, CloseHandler<PopupPanel> handler) {
			MessageBoxErrorPug messageBoxError = MessageBoxErrorPug.Util.getInstance();
			if (MessageBoxErrorPug.Util.registrationClose != null) {
				MessageBoxErrorPug.Util.registrationClose.removeHandler();
				MessageBoxErrorPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				MessageBoxErrorPug.Util.registrationClose = messageBoxError.addCloseHandler(handler);				
			}	
			messageBoxError.setTitleMsg(title);
			messageBoxError.setMsg(msg);
			messageBoxError.setError(error);
			messageBoxError.center();
			messageBoxError.show();		
			messageBoxError.btnOk.setFocus(true);
		}
		public static void showMessageRelativeTo(String title, String msg, CloseHandler<PopupPanel> handler, UIObject target) {
			MessageBoxErrorPug messageBoxError = MessageBoxErrorPug.Util.getInstance();
			if (MessageBoxErrorPug.Util.registrationClose != null) {
				MessageBoxErrorPug.Util.registrationClose.removeHandler();
				MessageBoxErrorPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				MessageBoxErrorPug.Util.registrationClose = messageBoxError.addCloseHandler(handler);				
			}	
			messageBoxError.setTitleMsg(title);
			messageBoxError.setMsg(msg);
			messageBoxError.showRelativeTo(target);
			messageBoxError.btnOk.setFocus(true);
		}		
	}	
	
	public MessageBoxErrorPug() {
		final GlobalResources globalResources = GWT.create(GlobalResources.class);
		
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setStyleName("gwt-DialogBox-Pug");
		
		verticalPanelBox = new VerticalPanel();
		verticalPanelBox.setStyleName("gwt-Panel-Notes");
		setWidget(verticalPanelBox);
		verticalPanelBox.setSize("365px", "100px");

		HorizontalPanel panelTitle = new HorizontalPanel();
		panelTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		panelTitle.setStyleName("gwt-MessageBox-Title");
		panelTitle.setSize("100%", "");
		panelTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanelBox.add(panelTitle);
		
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
		verticalPanelBox.add(label);
		
		html = new HTML("", true);
		html.setHeight("");
		verticalPanelBox.add(html);
		
		Label lblNewLabel = new Label("");
		verticalPanelBox.add(lblNewLabel);
		lblNewLabel.setWidth("10px");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(5);
		verticalPanelBox.add(horizontalPanel_1);
		
		lblLinkDetail = new Label("Detalhar >>");
		horizontalPanel_1.add(lblLinkDetail);
		lblLinkDetail.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!html_detail.isVisible()) {
					verticalPanelBox.setHeight("250px");					
					scrollPanelDetail.setHeight("150px");
				} else {
					verticalPanelBox.setHeight("100px");
					scrollPanelDetail.setHeight("0px");					
				}
				scrollPanelDetail.setVisible(!scrollPanelDetail.isVisible());
				html_detail.setVisible(scrollPanelDetail.isVisible());
				lblLinkDetail.setText(!scrollPanelDetail.isVisible() ? "Detalhar >>" : "Ocultar <<");
			}
		});
		lblLinkDetail.setStyleName("gwt-Link-Box-link");
		
		scrollPanelDetail = new ScrollPanel();
		verticalPanelBox.add(scrollPanelDetail);
		scrollPanelDetail.setVisible(false);
		scrollPanelDetail.setWidth("430px");
		
		html_detail = new HTML("", true);
		scrollPanelDetail.setWidget(html_detail);
		html_detail.setSize("100%", "100%");
		html_detail.setVisible(false);
		
		Label label_1 = new Label("");
		label_1.setHeight("20px");
		verticalPanelBox.add(label_1);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setStyleName("gwt-Gallery-Panel-Buttons");
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanelBox.add(horizontalPanel);
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

	public void setError(String html) {
		this.html_detail.setHTML("<div class=\"gwt-messagebox-html\">" + html + "</div>");
	}
}
