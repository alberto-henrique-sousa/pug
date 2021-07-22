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
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pugsource.gwt.library.client.GlobalResources;

public class DialogBoxPug extends DialogBox {
	private Label lblTitle;
	private VerticalPanel verticalPanelComposite;
	private HorizontalPanel panelTitle;
	
	public DialogBoxPug() {
		final GlobalResources globalResources = GWT.create(GlobalResources.class);
				
		setGlassEnabled(true);
		setAnimationEnabled(true);
		setStyleName("gwt-DialogBox-Pug");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setStyleName("gwt-Panel-Notes");
		setWidget(verticalPanel);
		verticalPanel.setSize("365px", "100%");

		panelTitle = new HorizontalPanel();
		panelTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		panelTitle.setStyleName("gwt-MessageBox-Title");
		panelTitle.setSize("100%", "");
		panelTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(panelTitle);
		
		lblTitle = new Label("");
		panelTitle.add(lblTitle);
		lblTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		lblTitle.setStyleName("gwt-Label-Title-Assunto");
		lblTitle.setWidth("");
		
		Image btnClose = new Image(globalResources.closeOutWhite());
		panelTitle.add(btnClose);
		btnClose.setWidth("");
		btnClose.setStyleName("gwt-header-login");
		btnClose.setTitle("Fechar");
		btnClose.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Image) event.getSource()).setResource(globalResources.closeOutWhite());
			}
		});
		btnClose.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				((Image) event.getSource()).setResource(globalResources.closeOutWhite()); // closeMove
			}
		});
		btnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		verticalPanelComposite = new VerticalPanel();
		verticalPanel.add(verticalPanelComposite);
		verticalPanelComposite.setWidth("100%");
		
		Element td = getCellElement(0, 1);
		DOM.removeChild(td, (Element) td.getFirstChildElement());
		DOM.appendChild(td, panelTitle.getElement());
		
	}

	public static class Util {
		
		private static DialogBoxPug instance;
		private static HandlerRegistration registrationClose;
		
		public static DialogBoxPug getInstance(){
			if (instance == null) {
				instance = GWT.create(DialogBoxPug.class);
			}
			return instance;
		}
		
		public static void showDialogBox(String title, Widget w, String width, String height, CloseHandler<PopupPanel> handler) {			
			DialogBoxPug dialogBoxPug = DialogBoxPug.Util.getInstance();
			if (DialogBoxPug.Util.registrationClose != null) {
				DialogBoxPug.Util.registrationClose.removeHandler();
				DialogBoxPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				DialogBoxPug.Util.registrationClose = dialogBoxPug.addCloseHandler(handler);				
			}
			dialogBoxPug.setWidth(width != null && !width.isEmpty() ? width : "100%");
			dialogBoxPug.setHeight(height != null && !height.isEmpty() ? height : "100%");
			dialogBoxPug.setTitleMsg(title);
			dialogBoxPug.setCompositeWidget(w);
			if (width != null && !width.isEmpty())
				w.setWidth(width);
			if (height != null && !height.isEmpty())
				w.setHeight(height);
			dialogBoxPug.center();
			dialogBoxPug.show();		
		}
		
		public static void showDialogBoxRelativeTo(String title, Widget w, String width, String height, CloseHandler<PopupPanel> handler, UIObject target) {
			DialogBoxPug dialogBoxPug = DialogBoxPug.Util.getInstance();
			if (DialogBoxPug.Util.registrationClose != null) {
				DialogBoxPug.Util.registrationClose.removeHandler();
				DialogBoxPug.Util.registrationClose = null;
			}	
			if (handler != null) {
				DialogBoxPug.Util.registrationClose = dialogBoxPug.addCloseHandler(handler);				
			}	
			dialogBoxPug.setWidth(width != null && !width.isEmpty() ? width : "100%");
			dialogBoxPug.setHeight(height != null && !height.isEmpty() ? height : "100%");
			dialogBoxPug.setTitleMsg(title);
			dialogBoxPug.setCompositeWidget(w);
			if (width != null && !width.isEmpty())
				w.setWidth(width);
			if (height != null && !height.isEmpty())
				w.setHeight(height);
			dialogBoxPug.showRelativeTo(target);
		}		
	}	

	public String getTitleMsg() {
		return this.lblTitle.getText();
	}

	public void setTitleMsg(String text) {
		this.lblTitle.setText(" " + text);
	}
	
	public Widget getCompositeWidget() {
		return this.verticalPanelComposite.getWidgetCount() > 0 ? this.verticalPanelComposite.getWidget(0) : null;
	}
	
	public void setCompositeWidget(Widget w) {
		this.verticalPanelComposite.clear();
		this.verticalPanelComposite.add(w);
	}
	
}