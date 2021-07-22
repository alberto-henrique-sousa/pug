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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pugsource.gwt.library.client.GlobalResources;
import com.pugsource.gwt.library.client.ui.ButtonPug;

public class InputBoxPug extends DialogBox {
	private Label lblTitle;
	private Label lblDivCode;
	private boolean OK;
	private TextBox textBoxName;
	private Label lblMsg;
	private Label lblNome;
	private Label lblDivGallery;

	public static class Util {
		private static InputBoxPug instance;
		private static HandlerRegistration registrationClose;
		public static InputBoxPug getInstance(){
			if (instance == null) {
				instance = GWT.create(InputBoxPug.class);
			}
			return instance;
		}		
		public static void showInput(String title, String msg, String textDefault, int maxLength, CloseHandler<PopupPanel> handler) {
			InputBoxPug inputBoxSimples = InputBoxPug.Util.getInstance();
			if (InputBoxPug.Util.registrationClose != null) {
				InputBoxPug.Util.registrationClose.removeHandler();
				InputBoxPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				InputBoxPug.Util.registrationClose = inputBoxSimples.addCloseHandler(handler);				
			}	
			inputBoxSimples.setOK(false);
			inputBoxSimples.setTitleMsg(title);
			inputBoxSimples.setMsg(msg);			
			inputBoxSimples.textBoxName.setText(textDefault);
			inputBoxSimples.textBoxName.setMaxLength(maxLength);
			inputBoxSimples.lblMsg.setText("");
			inputBoxSimples.center();
			inputBoxSimples.show();
			inputBoxSimples.textBoxName.setFocus(true);
		}
	}	
	
	public InputBoxPug() {
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
				
		Label label_1 = new Label("");
		label_1.setHeight("20px");
		verticalPanel.add(label_1);
		
		HorizontalPanel horizontalPanel_0 = new HorizontalPanel();
		horizontalPanel_0.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(horizontalPanel_0);
		horizontalPanel_0.setWidth("100%");
		
		lblNome = new Label("Informe: ");
		lblNome.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel_0.add(lblNome);
		lblNome.setWidth("70px");
		
		Label label_6 = new Label("");
		horizontalPanel_0.add(label_6);
		label_6.setWidth("5px");
		
		textBoxName = new TextBox();
		textBoxName.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) 
					clickOk();
			}
		});
		horizontalPanel_0.add(textBoxName);
		textBoxName.setWidth("270px");
		
		lblDivGallery = new Label("");
		verticalPanel.add(lblDivGallery);
		lblDivGallery.setHeight("5px");
						
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_2);
		
		Label label_8 = new Label("");
		horizontalPanel_2.add(label_8);
		label_8.setWidth("75px");
		
		lblMsg = new Label("");
		lblMsg.setStyleName("gwt-Label-Login-Error");
		horizontalPanel_2.add(lblMsg);		
		
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
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clickOk();	
			}
		});
		
		Label label_5 = new Label("");
		horizontalPanel.add(label_5);
		label_5.setWidth("100px");
		horizontalPanel.add(btnOk);
		btnOk.setWidth("75px");
		
		ButtonPug btnCancel = new ButtonPug("Cancelar");
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
		return this.lblNome.getText();
	}

	public void setMsg(String text) {
		this.lblNome.setText(text);
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

	private void clickOk() {
		if (textBoxName.getText().isEmpty()) {
			lblMsg.setText("Informação inválida.");
			textBoxName.setFocus(true);
		} else {
			setOK(true);
			hide();
		}
	}
}
