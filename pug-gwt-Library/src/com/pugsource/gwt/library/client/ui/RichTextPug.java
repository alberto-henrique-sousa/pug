package com.pugsource.gwt.library.client.ui;

import com.google.gwt.dom.client.FrameElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RichTextPug extends Composite {

	private RichTextArea richTextAreaNotes;
	//private boolean textResize;
	private RichTextToolbarPug toolBar;
	private SimplePanel simplePanel;
	
	public RichTextPug() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		richTextAreaNotes = new RichTextArea();
		richTextAreaNotes.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				simplePanel.setStyleName("gwt-Box-Editor");
			}
		});
		//textResize = true;
		richTextAreaNotes.addInitializeHandler(new InitializeHandler() {
			public void onInitialize(InitializeEvent event) {
				if (richTextAreaNotes != null && richTextAreaNotes.getElement() != null) {
					FrameElement frame = FrameElement.as(richTextAreaNotes.getElement());
					Style s = frame.getContentDocument().getBody().getStyle(); 
					s.setProperty("fontFamily", "arial,tahoma,verdana,helvetica,sans-serif"); 					
					s.setProperty("fontSize", "14px");
				}
			}
		});
		richTextAreaNotes.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				//if (textResize) {
				//	richTextAreaNotes.setHeight("150px");
				//	textResize = false;
				//}	
				simplePanel.setStyleName("gwt-InputBox-Focus");
			}
		});
		richTextAreaNotes.setSize("749px", "150px");

		toolBar = new RichTextToolbarPug(richTextAreaNotes);
		verticalPanel.add(toolBar);		
		
		simplePanel = new SimplePanel();
		simplePanel.setStyleName("gwt-Box-Editor");
		verticalPanel.add(simplePanel);
		simplePanel.setWidth("749px");

		simplePanel.add(richTextAreaNotes);
		
	}

	public void setFocus() {
		this.richTextAreaNotes.setFocus(true);
	}
	
	public String getHTML() {
		return this.richTextAreaNotes.getHTML();
	}
	
	public String getText() {
		return this.richTextAreaNotes.getText();
	}	
	
	public void setHTML(String html) {
		this.richTextAreaNotes.setHTML(html);
	}
	
	public void setText(String text) {
		richTextAreaNotes.setText(text);
	}
	
	public void setHeightText(String height) {
		this.richTextAreaNotes.setHeight(height);
		//this.textResize = false;
	}
}
