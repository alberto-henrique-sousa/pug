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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pugsource.gwt.library.client.GlobalResources;
import com.pugsource.gwt.library.client.Tools;
import com.pugsource.gwt.library.client.effects.Effects;
import com.pugsource.gwt.library.client.ui.ButtonPug;

public class MessageBoxEmailPug extends DialogBox {
	private HTML html;
	private Label lblTitle;
	private Label lblDivCode;
	private boolean OK;
	private TextBox textBoxName;
	private TextBox textBoxEmail;
	private Label lblMsg;
	private TextArea textAreaMsg;
	private HorizontalPanel horizontalPanelName;
	private HorizontalPanel horizontalPanelTextArea;
	private FormPanel formPanel;
	private final GlobalResources globalResources = GWT.create(GlobalResources.class);

	public static class Util {
		private static MessageBoxEmailPug instance;
		private static HandlerRegistration registrationClose;
		public static MessageBoxEmailPug getInstance(){
			if (instance == null) {
				instance = GWT.create(MessageBoxEmailPug.class);
			}
			return instance;
		}		
		public static void showMessage(String title, String msg, boolean nameVisible, boolean textAreaVisible, CloseHandler<PopupPanel> handler) {
			MessageBoxEmailPug messageBoxEmail = MessageBoxEmailPug.Util.getInstance();
			if (MessageBoxEmailPug.Util.registrationClose != null) {
				MessageBoxEmailPug.Util.registrationClose.removeHandler();
				MessageBoxEmailPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				MessageBoxEmailPug.Util.registrationClose = messageBoxEmail.addCloseHandler(handler);				
			}	
			messageBoxEmail.horizontalPanelName.setVisible(nameVisible);
			messageBoxEmail.horizontalPanelTextArea.setVisible(textAreaVisible);
			messageBoxEmail.setOK(false);
			messageBoxEmail.setTitleMsg(title);
			messageBoxEmail.setMsg(msg);
			messageBoxEmail.textBoxName.setText("");
			messageBoxEmail.textBoxEmail.setText("");
			messageBoxEmail.textAreaMsg.setText("");
			messageBoxEmail.lblMsg.setText("");
			messageBoxEmail.center();
			messageBoxEmail.show();
			if (messageBoxEmail.horizontalPanelName.isVisible())
				messageBoxEmail.textBoxName.setFocus(true);
			else
				messageBoxEmail.textBoxEmail.setFocus(true);
		}
	}	
	
	public MessageBoxEmailPug() {
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setStyleName("gwt-DialogBox-Pug");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("gwt-Panel-Notes");
		setWidget(verticalPanel);
		verticalPanel.setSize("365px", "120px");

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
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.addSubmitHandler(new SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				event.cancel();
				if (horizontalPanelName.isVisible() && textBoxName.getText().trim().isEmpty()) {
					lblMsg.setText("Informe o nome.");
					textBoxName.setFocus(true);
				} else if (!Tools.validEmail(textBoxEmail.getText())) {  
					lblMsg.setText("e-Mail inválido.");
					textBoxEmail.setFocus(true);
				} else if (horizontalPanelTextArea.isVisible() && textAreaMsg.getText().trim().isEmpty()) {  
					lblMsg.setText("Informe o texto da mensagem.");
					textAreaMsg.setFocus(true);
				} else {
					setOK(true);
					hide();
				}	
			}
		});
		verticalPanel.add(formPanel);
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		formPanel.setWidget(verticalPanel_1);
		verticalPanel_1.setSize("", "100%");
		
		horizontalPanelName = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanelName);
		horizontalPanelName.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label lblNewLabel_4 = new Label("");
		horizontalPanelName.add(lblNewLabel_4);
		lblNewLabel_4.setWidth("5px");
		
		Label lblNome = new Label("Nome:");
		lblNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanelName.add(lblNome);
		lblNome.setWidth("70px");
		
		Label label_6 = new Label("");
		horizontalPanelName.add(label_6);
		label_6.setWidth("5px");
		
		textBoxName = new TextBox();
		Effects.textBoxEffect(textBoxName);
		textBoxName.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					formPanel.submit();
				}
			}
		});
		horizontalPanelName.add(textBoxName);
		textBoxName.setWidth("330px");
		
		Label lblNewLabel = new Label("");
		verticalPanel_1.add(lblNewLabel);
		lblNewLabel.setHeight("5px");
		
		HorizontalPanel horizontalPanelEmail = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanelEmail);
		horizontalPanelEmail.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label lblNewLabel_5 = new Label("");
		horizontalPanelEmail.add(lblNewLabel_5);
		lblNewLabel_5.setWidth("5px");
		
		Label lblEmail = new Label("e-Mail:");
		lblEmail.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanelEmail.add(lblEmail);
		lblEmail.setWidth("70px");
		
		Label label_7 = new Label("");
		horizontalPanelEmail.add(label_7);
		label_7.setWidth("5px");
		
		textBoxEmail = new TextBox();
		Effects.textBoxEffect(textBoxEmail);
		textBoxEmail.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					formPanel.submit();
				}
			}
		});
		horizontalPanelEmail.add(textBoxEmail);
		textBoxEmail.setWidth("330px");
		
		Label lblNewLabel_3 = new Label("");
		verticalPanel_1.add(lblNewLabel_3);
		lblNewLabel_3.setHeight("5px");
		
		horizontalPanelTextArea = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanelTextArea);
		
		Label lblNewLabel_6 = new Label("");
		horizontalPanelTextArea.add(lblNewLabel_6);
		lblNewLabel_6.setWidth("5px");
		
		Label lblNewLabel_1 = new Label("Mensagem:");
		lblNewLabel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanelTextArea.add(lblNewLabel_1);
		lblNewLabel_1.setWidth("70px");
		
		Label lblNewLabel_2 = new Label("");
		horizontalPanelTextArea.add(lblNewLabel_2);
		lblNewLabel_2.setWidth("5px");
		
		textAreaMsg = new TextArea();
		Effects.textAreaEffect(textAreaMsg);
		textAreaMsg.setVisibleLines(4);
		horizontalPanelTextArea.add(textAreaMsg);
		textAreaMsg.setWidth("326px");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_2);
		
		Label label_8 = new Label("");
		horizontalPanel_2.add(label_8);
		label_8.setWidth("75px");
		
		lblMsg = new Label("");
		lblMsg.setStyleName("gwt-Label-Login-Error");
		horizontalPanel_2.add(lblMsg);
		lblMsg.setHeight("16px");
		
		lblDivCode = new Label("");
		lblDivCode.setHeight("20px");
		verticalPanel.add(lblDivCode);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setStyleName("gwt-Gallery-Panel-Buttons");
		horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "35px");
		
		ButtonPug btnOk = new ButtonPug("Ok");
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

	public String getName() {
		return textBoxName.getText();
	}

	public String getEmail() {
		return textBoxEmail.getText();
	}	
	
	public String getTextMsg() {
		return textAreaMsg.getText();
	}	
}
