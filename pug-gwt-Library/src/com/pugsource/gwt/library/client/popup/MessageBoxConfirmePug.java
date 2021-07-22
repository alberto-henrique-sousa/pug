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
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pugsource.gwt.library.client.GlobalResources;
import com.pugsource.gwt.library.client.effects.Effects;
import com.pugsource.gwt.library.client.ui.ButtonPug;

public class MessageBoxConfirmePug extends DialogBox {
	private HTML html;
	private Label lblTitle;
	private TextBox textBoxCode;
	private HorizontalPanel horizontalPanelCode;
	private Label lblCode;
	private Label lblDivCode;
	private Label lblMsg;
	private boolean OK;
	private ButtonPug btnOk;
	private FormPanel formPanel;

	public static class Util {
		private static MessageBoxConfirmePug instance;
		private static HandlerRegistration registrationClose;
		public static MessageBoxConfirmePug getInstance(){
			if (instance == null) {
				instance = GWT.create(MessageBoxConfirmePug.class);
			}
			return instance;
		}		
		public static void showMessage(String title, String msg, boolean security, CloseHandler<PopupPanel> handler) {
			MessageBoxConfirmePug messageBoxConfirme = MessageBoxConfirmePug.Util.getInstance();
			if (MessageBoxConfirmePug.Util.registrationClose != null) {
				MessageBoxConfirmePug.Util.registrationClose.removeHandler();
				MessageBoxConfirmePug.Util.registrationClose = null;
			}	
			if (handler != null) {
				MessageBoxConfirmePug.Util.registrationClose = messageBoxConfirme.addCloseHandler(handler);				
			}	
			messageBoxConfirme.lblMsg.setText("(Digite o código para confirmar)");
			messageBoxConfirme.lblMsg.setStyleName("gwt-Label-Login-Wait");
			messageBoxConfirme.setOK(false);
			messageBoxConfirme.horizontalPanelCode.setVisible(security);
			messageBoxConfirme.lblCode.setText(String.valueOf(Random.nextInt(1000)));
			messageBoxConfirme.setTitleMsg(title);
			messageBoxConfirme.setMsg(msg);
			messageBoxConfirme.textBoxCode.setText("");
			messageBoxConfirme.center();
			messageBoxConfirme.show();
			if (security)
				messageBoxConfirme.textBoxCode.setFocus(true);
			else
				messageBoxConfirme.btnOk.setFocus(true);
		}
	}	
	
	public MessageBoxConfirmePug() {
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
		
		formPanel = new FormPanel();
		formPanel.addSubmitHandler(new SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				event.cancel();
				if (!horizontalPanelCode.isVisible()) {
					setOK(true);
					hide();					
				} else {
					if (lblCode.getText().equals(textBoxCode.getText())) {
						setOK(true);
						hide();
					} else {
						lblMsg.setText("(Código não confere, digite novamente.)");
						lblMsg.setStyleName("gwt-Label-Login-Error");
						textBoxCode.setFocus(true);
					}
				}
			}
		});
		formPanel.setMethod(FormPanel.METHOD_POST);
		verticalPanel.add(formPanel);
		
		horizontalPanelCode = new HorizontalPanel();
		formPanel.setWidget(horizontalPanelCode);
		horizontalPanelCode.setSize("", "100%");
		horizontalPanelCode.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label lblNewLabel = new Label("");
		horizontalPanelCode.add(lblNewLabel);
		lblNewLabel.setWidth("5px");
		
		Label lblCdigo = new Label("Código:");
		horizontalPanelCode.add(lblCdigo);
		
		Label label_6 = new Label("");
		horizontalPanelCode.add(label_6);
		label_6.setWidth("5px");
		
		lblCode = new Label("123456");
		lblCode.setStyleName("gwt-Label-Code-Confirme");
		horizontalPanelCode.add(lblCode);
		
		Label label_7 = new Label("");
		horizontalPanelCode.add(label_7);
		label_7.setWidth("5px");
		
		textBoxCode = new TextBox();
		Effects.textBoxEffect(textBoxCode);
		textBoxCode.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					formPanel.submit();
				}
			}
		});
		horizontalPanelCode.add(textBoxCode);
		textBoxCode.setWidth("50px");
		
		Label label_8 = new Label("");
		horizontalPanelCode.add(label_8);
		label_8.setWidth("5px");
		
		lblMsg = new Label("(Digite o código para confirmar)");
		lblMsg.setStyleName("gwt-Label-Login-Wait");
		horizontalPanelCode.add(lblMsg);
		
		lblDivCode = new Label("");
		lblDivCode.setHeight("20px");
		verticalPanel.add(lblDivCode);
		
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
					formPanel.submit();
				}
			}
		});
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		
		Label label_5 = new Label("");
		horizontalPanel.add(label_5);
		label_5.setWidth("100px");
		horizontalPanel.add(btnOk);
		btnOk.setWidth("75px");
		
		ButtonPug btnCancel = new ButtonPug("Cancelar");
		btnCancel.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					hide();
				}
			}
		});
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		horizontalPanel.add(btnCancel);
		btnCancel.setWidth("75px");
		
		Label label_2 = new Label("");
		horizontalPanel.add(label_2);
		label_2.setWidth("100px");
		
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

	public boolean isOK() {
		return OK;
	}

	public void setOK(boolean oK) {
		OK = oK;
	}
}
