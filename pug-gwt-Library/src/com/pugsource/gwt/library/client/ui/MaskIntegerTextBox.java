/**
 * Pug Framework
 * 
 * @author Bruno Silva
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.ui;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

public class MaskIntegerTextBox extends TextBox{

	private int size;
	
	public MaskIntegerTextBox(int size){
		this.size = size;
		
		init();
	}

	public void init() {
		addKeyPressHandler(keyPress);
		sinkEvents(Event.ONPASTE);
	}
	
	public MaskIntegerTextBox(){
		this.size = 0;
		
		init();
	}
	
	public int getSize(){
		return size;
	}	
	
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void onBrowserEvent(Event event) {
	    super.onBrowserEvent(event);
	     
	    if (event.getTypeInt() == Event.ONPASTE) {	  	
	    	event.preventDefault();
	    	event.stopPropagation();	        
	    }
	};
	
	public KeyPressHandler keyPress = new KeyPressHandler() {
		
		@Override
		public void onKeyPress(KeyPressEvent event) {
			
			if (getBrowser().contains("firefox")){
			
				if (!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB)  && 
					!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_LEFT) &&
					!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_RIGHT) &&
					!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE) &&
					!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)) {
					
					if (getSize() > 0){
						if (!Character.isDigit(event.getCharCode()) || getText().length() >= getSize())
							 cancelKey();
					}else{
						if (!Character.isDigit(event.getCharCode()))
							 cancelKey();
					}				
				}
					
			}else{
				if (getSize() > 0){
					if (!Character.isDigit(event.getCharCode()) || getText().length() >= getSize())
						 cancelKey();
				}else{
					if (!Character.isDigit(event.getCharCode()))
						 cancelKey();
				}
			}
		}
	};
	
	public Integer getValueInteger(){
		Integer value = 0;		
		try {
			value = new Integer(this.getValue());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	public void setValue(Integer value){
		setText(value != null ? value.toString() : "");
	}
	
	public static native String getBrowser() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;
	
}
