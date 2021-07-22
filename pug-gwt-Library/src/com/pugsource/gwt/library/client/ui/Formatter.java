/**
 * Pug Framework
 * 
 * @author Bruno Silva
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.ui;

public class Formatter {
	
	public static String format(String mask, String value){
		char chars[] = mask.toCharArray();
		char charsValue[] = value.toCharArray();
		
		int aux = 0;
		for (int i = 0; i < mask.length(); i++){
			if (chars[i] == '9' || chars[i] == 'X' || chars[i] == 'x'){
				if (aux < value.length()){
					chars[i] = charsValue[aux];
					aux++;
				}
			}			
		}
		return String.valueOf(chars);		
	}
	
}
