package com.pugsource.gwt.library.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

public class MaskTextBox extends TextBox{

	private String specialToken = " .-/():";
	private String mask;
	private String userMask;
	
	private int cursorPos;
	private char chars[];
	private char userChars[];
	
	public MaskTextBox() {
		init("");
	}
	
	public MaskTextBox(String mask){
		init(mask);
	}
	
	public void init(String mask){
		this.setMask(mask);		
		
		addKeyPressHandler(keyPress);
		if (!getBrowser().contains("firefox"))
			addKeyDownHandler(keyDown);
		
		addBlurHandler(blur);
		addFocusHandler(focus);
		sinkEvents(Event.ONPASTE);
	}
	
	public String getMask(){
		return this.mask;
	}
	
	public void setMask(String mask){
		this.mask = mask;
		this.setUserMask(this.getMask().replaceAll("[9,X,x]", "_"));
		this.setText(this.getUserMask());
	}
	
	public String getUserMask(){
		return this.userMask;
	}
	
	public void setUserMask(String userMask){
		this.userMask = userMask;
	}
	
	public int getSize(){
		return this.mask.length();
	}
	
	public int getUserMaskSize(){
		return this.mask.replaceAll("[-,., ,/,(,)]","").length();
	}
	
	public boolean isContainsEspecialToken(char token){		
		return specialToken.contains(String.valueOf(token));
	}
	
	public boolean isValidToken(char token){
		boolean value = false;
		
		if ((token >= 48 && token <= 57) || (token >= 65 && token <= 90) || (token >= 97 && token <= 122))
			 value = true;
			
		return value;
	}
	
	public void nextTokenValid(){
		
		if (cursorPos < getSize()){			
			if (isContainsEspecialToken(chars[cursorPos])){
				cursorPos++;
				nextTokenValid();
			}
		}
	}
	
	public void lastTokenValid(){
		if (cursorPos > 0){						
			if (isContainsEspecialToken(chars[cursorPos])){					
				cursorPos --;
				lastTokenValid();
			}
		}
	}
	
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
	
	public KeyPressHandler keyPress = new KeyPressHandler() {
		
		@Override
		public void onKeyPress(KeyPressEvent event) {
			
			if (specialKeyDown(event.getNativeEvent().getKeyCode())){
				if (getValue().length() == 0){					
					setText(getUserMask());
					setCursorPos(0);
				}
			}
					
			cursorPos = getCursorPos();
			chars = getText().toCharArray();
			userChars = getMask().toCharArray();
						
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE){
				
				if (getSelectionLength() == getSize()){
					setText(getUserMask());
					setCursorPos(0);
				}else{
					
					if (cursorPos > 0){
						
						if (cursorPos == getCursorPos())
							cursorPos--;
						
						lastTokenValid();
						if (isValidToken(chars[cursorPos]))
							chars[cursorPos] = '_';
						
						setText(String.valueOf(chars));
						setCursorPos(cursorPos);
					}
				}				
				
				cancelKey();				
			}else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE){				
				
				if (getSelectionLength() == getSize()){
					setText(getUserMask());
					setCursorPos(0);
				}else{
					
					if (cursorPos < getSize()){
	
						if (isContainsEspecialToken(chars[cursorPos]))
							nextTokenValid();
										
						if (cursorPos < getSize()){
							chars[cursorPos] = '_';
							cursorPos++;
						}

						setText(String.valueOf(chars));
						setCursorPos(cursorPos);
					}					
					
				}				
				
				cancelKey();
			}else{
								
				char token = event.getCharCode();
				
				if (cursorPos < getSize()){
					
					if (isValidToken(token)){
												
						if (chars.length >= cursorPos && isContainsEspecialToken(chars[cursorPos]))
							nextTokenValid();
												
						if (cursorPos < getSize()){						
						
							if ((Character.isDigit(userChars[cursorPos]) && !Character.isDigit(token)) ||
							   (!Character.isDigit(userChars[cursorPos]) && Character.isDigit(token)))
								cancelKey();
							else{
								
								if (!Character.isDigit(userChars[cursorPos])){
									if (Character.isUpperCase(userChars[cursorPos]))
										token = Character.toUpperCase(token);
									else								
										token = Character.toLowerCase(token);
								}								
								chars[cursorPos] = token;
								cursorPos++;	
							}
							
							setText(String.valueOf(chars));
							setCursorPos(cursorPos);
							cancelKey();
						}						
						
					}else{
						if (getBrowser().contains("firefox")){
//							if (!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB)  && 
//								!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_LEFT) &&
//								!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_RIGHT))
							if (specialKeyDown(event.getNativeEvent().getKeyCode()))
								cancelKey();
						}else
							cancelKey();						
					}
						
					
				}else{
//					if (!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB)  && 
//						!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_LEFT) &&
//						!(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_RIGHT))
					if (specialKeyDown(event.getNativeEvent().getKeyCode()))
						cancelKey();
				}

			}
			
		}
	};
	
	public KeyDownHandler keyDown = new KeyDownHandler() {

		@Override
		public void onKeyDown(KeyDownEvent event) {
		
			cursorPos = getCursorPos();
			chars = getText().toCharArray();
			userChars = getMask().toCharArray();
			
			if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE){
				
				if (getSelectionLength() == getSize()){
					setText(getUserMask());
					setCursorPos(0);
				}else{
					
					if (cursorPos > 0){						
						if (cursorPos == getCursorPos())
							cursorPos--;
						
						lastTokenValid();
						if (isValidToken(chars[cursorPos]) || chars[cursorPos] == '_')
							chars[cursorPos] = '_';
					}
					
					setText(String.valueOf(chars));
					setCursorPos(cursorPos);
				}				
				
				cancelKey();				
			}else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE){				
				
				if (getSelectionLength() == getSize()){
					setText(getUserMask());
					setCursorPos(0);
				}else{
								
					if (cursorPos < getSize() && chars.length > 0){						
						if (isContainsEspecialToken(chars[cursorPos]))
							nextTokenValid();
			
						if (cursorPos < getSize()){
							chars[cursorPos] = '_';
							cursorPos++;
						}
						
					}					
					setText(String.valueOf(chars));
					setCursorPos(cursorPos);
				}				
				
				cancelKey();
			}
			
		}
		
	};
	
	public BlurHandler blur = new BlurHandler() {
		
		@Override
		public void onBlur(BlurEvent event) {
			
			if (getText().equals(getUserMask()) || (getValue().length() != getUserMaskSize()))				
				setText("");			
			
		}
	};
	
	public FocusHandler focus = new FocusHandler() {
		
		@Override
		public void onFocus(FocusEvent event) {
			
			if (getValue().trim().isEmpty()){				
				setText(getUserMask());
				setCursorPos(0);
			}
			
		}
	};
	
	@Override
	public void onBrowserEvent(Event event) {
	    super.onBrowserEvent(event);
	     
	    if (event.getTypeInt() == Event.ONPASTE) {	  	
	    	event.preventDefault();
	    	event.stopPropagation();	        
	    }
	};
	
	@Override
	public String getValue() {
		return this.getText().replaceAll("[-,., ,/,(,),_]", "");
	};
	
	@Override
	public void setValue(String value) {
		char chars[] = this.getUserMask().toCharArray();
		char charsValue[] = value.toCharArray();
		
		int aux = 0;
		for (int i = 0; i < chars.length; i++){
			if (chars[i] == '_'){
				if (aux < value.length()){
					chars[i] = charsValue[aux];
					aux++;
				}
			}			
		}
		super.setValue(String.valueOf(chars));
	}

	
	public static native String getBrowser() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;
}
