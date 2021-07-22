/**
 * Pug Framework
 * 
 * @author Bruno Silva
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

public class MaskDecimalTextBox extends TextBox{
	
	private int precision; 

	public MaskDecimalTextBox() {
		init(2);
	}		
	
	public MaskDecimalTextBox(int precision) {
		init(precision);
	}	
	
	public void init(int precision) {
		this.precision = precision;
		
		addKeyPressHandler(keyPress);
		addBlurHandler(blur);
		addKeyDownHandler(keyDown);
	
		sinkEvents(Event.ONPASTE);
	}	
	
	
	public int getPrecision(){
		return precision;
	}
		
	
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	@Override
	public void onBrowserEvent(Event event) {
	    super.onBrowserEvent(event);
	     
	    if (event.getTypeInt() == Event.ONPASTE) {	  	
	    	event.preventDefault();
	    	event.stopPropagation();	        
	    }
	};
	
	public boolean specialKeyDown(int keyCode){
		boolean value = false;
		if (!(keyCode == KeyCodes.KEY_TAB)  && 
			!(keyCode == KeyCodes.KEY_LEFT) &&
			!(keyCode == KeyCodes.KEY_RIGHT) &&
			!(keyCode == KeyCodes.KEY_DELETE) &&
			!(keyCode == KeyCodes.KEY_BACKSPACE))
			value = true;
		
		return value;
	}
	
	public BlurHandler blur = new BlurHandler() {
		
		@Override
		public void onBlur(BlurEvent event) {
			
			if (getText().replaceAll("[,,.]", "").length() == getPrecision())
				setText("0," + getText().replaceAll("[,,.]", ""));
			else if (getText().replaceAll("[,,.]", "").length() < getPrecision()){
				int tamanho = getPrecision() - getText().replaceAll("[,,.]", "").length();
				for (int i = 1; i <= tamanho; i++){
					setText("0" + getText().replaceAll("[,,.]", ""));					
					if (i == tamanho)
						setText("0," + getText());
				}
			}
				
		}
	};

	public KeyDownHandler keyDown = new KeyDownHandler() {
		
		@Override
		public void onKeyDown(KeyDownEvent event) {
			
			if (!getBrowser().contains("firefox")){
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE){ //Back-space
					
					if (getSelectionLength() == getText().length())
						setText("");
					else{				
						String text = getText().replaceAll("[,,.]", "");
						String newText = "";
						char chars[] = text.toCharArray();
						
						setCursorPos(0);
						
						if ((text.length() - 1) > getPrecision()){
						
							if (text.length() > 0){
								for (int i = 1; i < text.length(); i++)
									newText += chars[i];
								
								if (newText.length() > getPrecision()){
									format(newText.substring(0, newText.length() - getPrecision()) + "," + newText.substring(newText.length() - getPrecision(), newText.length()));
									cancelKey();
								}
							}	
						}else{						
							newText = "";
							if (getText().length() > 0){
								char newChars[] = getText().toCharArray();
								for (int i = 1; i < getText().length(); i++)
									newText += newChars[i];
								
								setText(newText);
								
							}
						}
						
						setCursorPos(0);										
					}
					
				}else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE){ //Delete
					
					if (getSelectionLength() == getText().length())					
						setText("");
					else{					
						setCursorPos(0);					
						String text = getText().replaceAll("[,,.]", "");
						String newText = "";
						char chars[] = text.toCharArray();
						
						if (text.length() > getPrecision()){
							
							if (text.length() > 0){
								for (int i = 1; i < text.length(); i++)
									newText += chars[i];
								
								if (newText.length() > getPrecision()){
									format(newText.substring(0, newText.length() - getPrecision()) + "," + newText.substring(newText.length() - getPrecision(), newText.length()));
									cancelKey();
								}
									
							}	
						}
						
						setCursorPos(0);
											
					}
	
				}
			}
		}
	};
	
	public KeyPressHandler keyPress = new KeyPressHandler() {
		
		@Override
		public void onKeyPress(KeyPressEvent event) {

			char charCode = event.getCharCode();
			
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE){ //Back-space
				
				if (getSelectionLength() == getText().length())
					setText("");
				else{				
					String text = getText().replaceAll("[,,.]", "");
					String newText = "";
					char chars[] = text.toCharArray();
					
					setCursorPos(0);
					
					if ((text.length() - 1) > getPrecision()){
					
						if (text.length() > 0){
							for (int i = 1; i < text.length(); i++)
								newText += chars[i];
							
							if (newText.length() > getPrecision()){
								format(newText.substring(0, newText.length() - getPrecision()) + "," + newText.substring(newText.length() - getPrecision(), newText.length()));
								cancelKey();
							}
						}	
					}else{						
						newText = "";
						if (getText().length() > 0){
							char newChars[] = getText().toCharArray();
							for (int i = 1; i < getText().length(); i++)
								newText += newChars[i];
							
							setText(newText);
							
						}
					}
					
					setCursorPos(0);										
				}
				
			}else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE){ //Delete
				
				if (getSelectionLength() == getText().length())					
					setText("");
				else{					
					setCursorPos(0);					
					String text = getText().replaceAll("[,,.]", "");
					String newText = "";
					char chars[] = text.toCharArray();
					
					if (text.length() > getPrecision()){
						
						if (text.length() > 0){
							for (int i = 1; i < text.length(); i++)
								newText += chars[i];
							
							if (newText.length() > getPrecision()){
								format(newText.substring(0, newText.length() - getPrecision()) + "," + newText.substring(newText.length() - getPrecision(), newText.length()));
								cancelKey();
							}
								
						}	
					}
					
					setCursorPos(0);	
				}
				
			}else{
				
				if (specialKeyDown(event.getNativeEvent().getKeyCode())){
					if (!Character.isDigit(event.getCharCode()))
						cancelKey();
					else{
						
						if (getSelectionLength() == getText().length())
							setText("");				
						
						String length[] = getText().replaceAll("[.]", "").split(",");
						boolean limite = false;
						if (length != null){
							if (length[0].length() > 11){
								limite = true;
								cancelKey();
							}
						}				
						
						if (!limite){
							setText(getText() + charCode);	
							
							if (getText().length() > getPrecision()){
								
								String text = getText().replaceAll("[,,.]", "");	
								int pos = text.length() - getPrecision();
								char chars[] = text.toCharArray();
								
								String newText = "";					
								for (int i = 0; i < chars.length; i++){
									if (i == pos)
										newText += "," + chars[i];
									else
										newText += chars[i];
								}					
								
								format(newText);
							}
							
							cancelKey();
						}
						
						setCursorPos(0);					
					}
				}
			}
		}
		
	};
	
	public void format(String text){
		
		String newText[] = text.split(",");
		if (newText != null)			
			setText(NumberFormat.getDecimalFormat().format(Long.parseLong(newText[0])) + "," + newText[1]);
	
	}
	
	@Override
	public String getValue() {
		return getText().replaceAll("[.]", "").replaceAll("[,]", ".");
	}
	
	public void setValue(Double value){
		try {
			if (value != null && !value.equals(0)){			
				String newText[] = value.toString().split("[.]");			
				if (newText != null){
					
					if (newText[1].length() > getPrecision()){
						
						String newValue = "";					
						char chars[] = newText[1].toCharArray();
						for (int i = 0; i < newText[1].length(); i++){
							if (newValue.length() < getPrecision())
								newValue += chars[i];
						}						
						
						newText[1] = newValue;
					}else{
						int size = newText[1].length() - getPrecision();
						for (int i = 0; i < (size * (-1)); i++)
							newText[1] += "0";					
					}
					
					setText(NumberFormat.getDecimalFormat().format(Long.parseLong(newText[0])) + "," + newText[1]);
				}
			} else {
				textDefault();
			}
		} catch (Exception e) {
			textDefault();
		}
	}

	public void textDefault() {
		String dec = "";
		for (int i = 0; i < getPrecision(); i++){
			dec += "0";
		}
		dec = dec.length() > 0 ? "," + dec : "";
		setText(NumberFormat.getDecimalFormat().format(0) + dec);
	}
	
	public Double getValueDouble(){
		Double value = 0.00;	
		try {
			value = Double.valueOf(getValue());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}		
		return value;
	}
	
	public static native String getBrowser() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;
		
}
