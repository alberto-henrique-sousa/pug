package com.pugsource.gwt.library.client.popup;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.PopupPanel;

public class SimplePopupPanel extends PopupPanel {

	public SimplePopupPanel() {
		super(true);		
		setStyleName("gwt-SimplePopupPanel");
	}

	@Override     
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		super.onPreviewNativeEvent(event);         
		switch (event.getTypeInt()) {             
		case Event.ONKEYPRESS:                 
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {                     
				hide();                 
			}                 
			break;         
		}     
	} 	
	
}