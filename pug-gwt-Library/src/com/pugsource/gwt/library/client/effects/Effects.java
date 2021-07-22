/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.effects;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class Effects {

	public static  void textAreaEffect(TextArea textArea) {
		textArea.setStyleName("gwt-InputBox-Blur");
		textArea.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((TextArea) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((TextArea) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		textArea.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((TextArea) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((TextArea) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		textArea.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((TextArea) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		textArea.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((TextArea) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}	

	public static  void textBoxEffect(TextBox textBox) {
		textBox.setStyleName("gwt-InputBox-Blur");
		textBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((TextBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((TextBox) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		textBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((TextBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((TextBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		textBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((TextBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		textBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((TextBox) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}
	
	public static  void textBoxEffectBig(TextBox textBox) {
		textBox.setStyleName("gwt-InputBox-Big-Blur");
		textBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((TextBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((TextBox) event.getSource()).setStyleName("gwt-InputBox-Big-Move");
			}
		});
		textBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((TextBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((TextBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		textBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((TextBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		textBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((TextBox) event.getSource()).setStyleName("gwt-InputBox-Big-Focus");
			}
		});
	}
	
	public static  void integerBoxEffect(IntegerBox integerBox) {
		integerBox.setStyleName("gwt-InputBox-Blur");
		integerBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((IntegerBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		integerBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((IntegerBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		integerBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		integerBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}
	
	public static  void integerBoxEffectBig(IntegerBox integerBox) {
		integerBox.setStyleName("gwt-InputBox-Big-Blur");
		integerBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((IntegerBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Big-Move");
			}
		});
		integerBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((IntegerBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		integerBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		integerBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((IntegerBox) event.getSource()).setStyleName("gwt-InputBox-Big-Focus");
			}
		});
	}		

	public static  void longBoxEffect(LongBox longBox) {
		longBox.setStyleName("gwt-InputBox-Blur");
		longBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((LongBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((LongBox) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		longBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((LongBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((LongBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		longBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((LongBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		longBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((LongBox) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}
	
	public static  void longBoxEffectBig(LongBox longBox) {
		longBox.setStyleName("gwt-InputBox-Big-Blur");
		longBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((LongBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((LongBox) event.getSource()).setStyleName("gwt-InputBox-Big-Move");
			}
		});
		longBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((LongBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((LongBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		longBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((LongBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		longBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((LongBox) event.getSource()).setStyleName("gwt-InputBox-Big-Focus");
			}
		});
	}
	
	public static  void doubleBoxEffect(DoubleBox doubleBox) {
		doubleBox.setStyleName("gwt-InputBox-Blur");
		doubleBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((DoubleBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		doubleBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((DoubleBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		doubleBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		doubleBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}
	
	public static  void doubleBoxEffectBig(DoubleBox doubleBox) {
		doubleBox.setStyleName("gwt-InputBox-Big-Blur");
		doubleBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((DoubleBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Big-Move");
			}
		});
		doubleBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((DoubleBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		doubleBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		doubleBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((DoubleBox) event.getSource()).setStyleName("gwt-InputBox-Big-Focus");
			}
		});
	}
		
	public static  void listBoxEffect(ListBox listBox) {
		listBox.setStyleName("gwt-InputBox-Blur");
		listBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((ListBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((ListBox) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		listBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((ListBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((ListBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		listBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((ListBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		listBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((ListBox) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}
	
	public static  void checkBoxEffect(CheckBox checkBox) {
		checkBox.setStyleName("gwt-InputBox-Blur");
		checkBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((CheckBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		checkBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((CheckBox) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		checkBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		checkBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}
	
	public static  void checkBoxEffectBig(CheckBox checkBox) {
		checkBox.setStyleName("gwt-InputBox-Big-Blur");
		checkBox.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((CheckBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Big-Move");
			}
		});
		checkBox.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((CheckBox) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		checkBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		checkBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((CheckBox) event.getSource()).setStyleName("gwt-InputBox-Big-Focus");
			}
		});
	}	
	
	public static  void radioButtonEffect(RadioButton radioButton) {
		radioButton.setStyleName("gwt-InputBox-Blur");
		radioButton.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((RadioButton) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Move");
			}
		});
		radioButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((RadioButton) event.getSource()).getStyleName().equals("gwt-InputBox-Focus"))
					((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		radioButton.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Blur");
			}
		});
		radioButton.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Focus");
			}
		});
	}
	
	public static  void radioButtonEffectBig(RadioButton radioButton) {
		radioButton.setStyleName("gwt-InputBox-Big-Blur");
		radioButton.addMouseMoveHandler(new MouseMoveHandler() {
			public void onMouseMove(MouseMoveEvent event) {
				if (!((RadioButton) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Big-Move");
			}
		});
		radioButton.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!((RadioButton) event.getSource()).getStyleName().equals("gwt-InputBox-Big-Focus"))
					((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		radioButton.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Big-Blur");
			}
		});
		radioButton.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				((RadioButton) event.getSource()).setStyleName("gwt-InputBox-Big-Focus");
			}
		});
	}	

}