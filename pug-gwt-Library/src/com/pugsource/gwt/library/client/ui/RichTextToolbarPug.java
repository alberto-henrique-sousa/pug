package com.pugsource.gwt.library.client.ui;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.pugsource.gwt.library.client.popup.ColorPopupPanel;
import com.pugsource.gwt.library.client.popup.InputBoxPug;
import com.pugsource.gwt.library.client.popup.SimplePopupPanel;

public class RichTextToolbarPug extends Composite {
	private static final String CSS_ROOT_NAME = "RichTextToolbar";	

	public final static HashMap<String,String> GUI_FONTLIST = new HashMap<String,String>();
	static {
		GUI_FONTLIST.put("Arial", "Arial");
		GUI_FONTLIST.put("Courier New", "Courier New");
		GUI_FONTLIST.put("Georgia", "Georgia");
		GUI_FONTLIST.put("Times New Roman", "Times New Roman");
		GUI_FONTLIST.put("Trebuchet", "Trebuchet");
		GUI_FONTLIST.put("Verdana", "Verdana");
	}
	public final static HashMap<String,String> GUI_FONTSIZELIST = new HashMap<String,String>();
	static {		
		GUI_FONTSIZELIST.put("Pequeno", "xx-small");
		GUI_FONTSIZELIST.put("Médio", "medium");
		GUI_FONTSIZELIST.put("Grande", "x-large");
		GUI_FONTSIZELIST.put("Enorme", "xx-large");
	}

	private VerticalPanel outer;
	private HorizontalPanel topPanel;

	private RichTextArea styleText;
	private Formatter styleTextFormatter;
	private Long idAnotador;
	private String nameAnotador;

	private EventHandler evHandler;

	private ToggleButton bold;
	private ToggleButton italic;
	private ToggleButton underline;
	private ToggleButton stroke;
	private ToggleButton subscript;
	private ToggleButton superscript;
	private PushButton alignleft;
	private PushButton alignmiddle;
	private PushButton alignright;
	private PushButton orderlist;
	private PushButton unorderlist;
	private PushButton indentleft;
	private PushButton indentright;
	private PushButton generatelink;
	private PushButton breaklink;
	private PushButton insertline;
	private PushButton insertimage;
	private PushButton removeformatting;
	private PushButton markerfind;
	private PushButton markeredit;

	private ListBox fontList;
	private ListBox fontSizeList;
	private ListBox markerList;
	private PushButton font;
	private PushButton fontSize;
	private PushButton colorText;
	private PushButton colorBack;

	private final SimplePopupPanel fontPopup = new SimplePopupPanel();
	private final SimplePopupPanel fontSizePopup = new SimplePopupPanel();
	private final SimplePopupPanel markerPopup = new SimplePopupPanel();
	private final ColorPopupPanel colorTextPopup = new ColorPopupPanel();
	private final ColorPopupPanel colorBackPopup = new ColorPopupPanel();

	private JsArrayString tx;
	private String txbuffer;
	private Integer startpos;
	private String selectedText;

	public RichTextToolbarPug(RichTextArea richtext) {
		outer = new VerticalPanel();

		topPanel = new HorizontalPanel();
		topPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		topPanel.setStyleName(CSS_ROOT_NAME);

		styleText = richtext;
		styleTextFormatter = styleText.getFormatter();

		topPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);

		outer.add(topPanel);

		outer.setWidth("100%");
		outer.setStyleName(CSS_ROOT_NAME);
		initWidget(outer);

		evHandler = new EventHandler();

		styleText.addKeyUpHandler(evHandler);
		styleText.addClickHandler(evHandler);

		buildTools();		
	}

	private class EventHandler implements ClickHandler,KeyUpHandler, ChangeHandler {
		public void onClick(ClickEvent event) {
			if (event.getSource().equals(bold)) {
				styleTextFormatter.toggleBold();
			} else if (event.getSource().equals(italic)) {
				styleTextFormatter.toggleItalic();
			} else if (event.getSource().equals(underline)) {
				styleTextFormatter.toggleUnderline();
			} else if (event.getSource().equals(stroke)) {
				styleTextFormatter.toggleStrikethrough();
			} else if (event.getSource().equals(subscript)) {
				styleTextFormatter.toggleSubscript();
			} else if (event.getSource().equals(superscript)) {
				styleTextFormatter.toggleSuperscript();
			} else if (event.getSource().equals(alignleft)) {
				styleTextFormatter.setJustification(RichTextArea.Justification.LEFT);
			} else if (event.getSource().equals(alignmiddle)) {
				styleTextFormatter.setJustification(RichTextArea.Justification.CENTER);
			} else if (event.getSource().equals(alignright)) {
				styleTextFormatter.setJustification(RichTextArea.Justification.RIGHT);
			} else if (event.getSource().equals(orderlist)) {
				styleTextFormatter.insertOrderedList();
			} else if (event.getSource().equals(unorderlist)) {
				styleTextFormatter.insertUnorderedList();
			} else if (event.getSource().equals(indentright)) {
				styleTextFormatter.rightIndent();
			} else if (event.getSource().equals(indentleft)) {
				styleTextFormatter.leftIndent();
			} else if (event.getSource().equals(generatelink)) {
				if (!documentSelection(styleText.getElement())) {
					InputBoxPug.Util.showInput("Informe o link URL:", "URL:", "http://", 260, (new CloseHandler<PopupPanel>() {
						public void onClose(CloseEvent<PopupPanel> event) {
							if (InputBoxPug.Util.getInstance().isOK()) {
								styleTextFormatter.createLink(InputBoxPug.Util.getInstance().getName());
							}
						};
					}));
				} else {
					String url = Window.prompt("Informe o link URL:", "http://");
					if (url != null) {
						styleTextFormatter.createLink(url);
					}					
				}					
			} else if (event.getSource().equals(breaklink)) {
				styleTextFormatter.removeLink();
			} else if (event.getSource().equals(insertimage)) {
				if (!documentSelection(styleText.getElement())) {
					InputBoxPug.Util.showInput("Informe a imagem URL:", "URL:", "http://", 260, (new CloseHandler<PopupPanel>() {
						public void onClose(CloseEvent<PopupPanel> event) {
							if (InputBoxPug.Util.getInstance().isOK()) {
								styleTextFormatter.insertImage(InputBoxPug.Util.getInstance().getName());
							}
						};
					}));
				} else {
					String url = Window.prompt("Informe a imagem URL:", "http://");
					if (url != null) {
						styleTextFormatter.insertImage(url);
					}					
				}
			}  else if (event.getSource().equals(insertline)) {
				styleTextFormatter.insertHorizontalRule();
			} else if (event.getSource().equals(removeformatting)) {
				styleTextFormatter.removeFormat();
			} else if (event.getSource().equals(font)) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + 20;
				fontPopup.setPopupPosition(left, top);
				fontList.setSelectedIndex(0);
				fontPopup.show();
			} else if (event.getSource().equals(fontSize)) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + 20;
				fontSizePopup.setPopupPosition(left, top);
				fontSizeList.setSelectedIndex(0);
				fontSizePopup.show();
			} else if (event.getSource().equals(colorText)) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + 20;	            
				colorTextPopup.setPopupPosition(left, top);
				colorTextPopup.show();

			} else if (event.getSource().equals(colorBack)) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + 20;	            
				colorBackPopup.setPopupPosition(left, top);
				colorBackPopup.show();
			} else if (event.getSource().equals(markerfind)) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + 20;
				markerPopup.setPopupPosition(left, top);
			} else if (event.getSource().equals(markeredit)) {
/*				MarcadoresPopupPanel marcadoresPopupPanel = new MarcadoresPopupPanel();
				marcadoresPopupPanel.center();
				marcadoresPopupPanel.setIdAnotador(idAnotador);
				marcadoresPopupPanel.show();
				marcadoresPopupPanel.init();
				marcadoresPopupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {
					public void onClose(CloseEvent<PopupPanel> event) {
						if (parentInstance != null) {							
							callMarker(parentInstance);
						}	
					}
				});  				
*/			} else if (event.getSource().equals(styleText)) {
				//Change invoked by the richtextArea
			}
			updateStatus();
		}

		public void onKeyUp(KeyUpEvent event) {
			updateStatus();
		}

		public void onChange(ChangeEvent event) {
			if (event.getSource().equals(fontList)) {
				if (fontList.getSelectedIndex() > 0) {
					styleTextFormatter.setFontName(fontList.getValue(fontList.getSelectedIndex()));					
					//styleTextFormatter.insertHTML("<span style=\"font-family:\""+ fontList.getValue(fontList.getSelectedIndex()) + "></span>");
					//changeHtmlStyle("<span style=\"font-family:"+ fontList.getValue(fontList.getSelectedIndex()) + "\">", "</span>");
				}	
			} else if (event.getSource().equals(fontSizeList)) {
				if (fontSizeList.getSelectedIndex() > 0) {
					switch (fontSizeList.getSelectedIndex()) {
					case 1:
						styleTextFormatter.setFontSize(RichTextArea.FontSize.XX_SMALL);
						break;
					case 2:
						styleTextFormatter.setFontSize(RichTextArea.FontSize.MEDIUM);
						break;
					case 3:
						styleTextFormatter.setFontSize(RichTextArea.FontSize.X_LARGE);
						break;
					case 4:
						styleTextFormatter.setFontSize(RichTextArea.FontSize.XX_LARGE);
						break;
					}
				}	
			} else if (event.getSource().equals(markerList)) {
				if (markerList.getSelectedIndex() > 0)
					styleTextFormatter.insertHTML(markerList.getValue(markerList.getSelectedIndex()));
				styleText.setFocus(true);
			}
		}
	}

	public static native boolean documentSelection(Element elem) /*-{
		return (elem.contentWindow.document.selection != null);
	}-*/;	

	/** Native JavaScript that returns the selected text and position of the start **/
	public static native JsArrayString getSelection(Element elem) /*-{
		var txt = "";
		var pos = 0;
		var range;
    	var parentElement;
    	var container;

        if (elem.contentWindow.getSelection) {
        	txt = elem.contentWindow.getSelection();
        	pos = elem.contentWindow.getSelection().getRangeAt(0).startOffset;
        } else if (elem.contentWindow.document.getSelection) {
        	txt = elem.contentWindow.document.getSelection();
        	pos = elem.contentWindow.document.getSelection().getRangeAt(0).startOffset;
  		} else if (elem.contentWindow.document.selection) {
  			range = elem.contentWindow.document.selection.createRange();
        	txt = range.htmlText;
        	parentElement = range.parentElement();
        	container = range.duplicate();
        	container.moveToElementText(parentElement);
        	container.setEndPoint('EndToEnd', range);
        	pos = container.htmlText.length - range.htmlText.length;
        	alert(container.htmlText + " = " + range.htmlText);
        }
  		return [""+txt,""+pos];
	}-*/;

	private void changeHtmlStyle(String startTag, String stopTag, boolean init) {
		if (init) {
			this.tx = getSelection(styleText.getElement());
			this.txbuffer = styleText.getHTML();
			this.startpos = Integer.parseInt(tx.get(1));
			this.selectedText = tx.get(0);
		} else {	
			String tx_value = txbuffer.substring(0, startpos)+startTag+selectedText+stopTag+txbuffer.substring(startpos+selectedText.length());
			styleText.setText(tx_value);
			styleText.setHTML(styleText.getText());
		}	
	}	

	private void updateStatus() {
		if (styleTextFormatter != null) {
			bold.setDown(styleTextFormatter.isBold());
			italic.setDown(styleTextFormatter.isItalic());
			underline.setDown(styleTextFormatter.isUnderlined());
			subscript.setDown(styleTextFormatter.isSubscript());
			superscript.setDown(styleTextFormatter.isSuperscript());
			stroke.setDown(styleTextFormatter.isStrikethrough());
		}

		removeformatting.setEnabled(true);
		indentleft.setEnabled(true);
		//breaklink.setEnabled(true);
	}

	private void buildTools() {
		String HTTP_STATIC_ICONS_GIF = GWT.getModuleBaseURL() + "img/editorToolbar.png";
		
		topPanel.add(bold = createToggleButton(HTTP_STATIC_ICONS_GIF,0,0,18,18,"Negrito"));
		topPanel.add(italic = createToggleButton(HTTP_STATIC_ICONS_GIF,0,18,18,18,"Itálico"));
		topPanel.add(underline = createToggleButton(HTTP_STATIC_ICONS_GIF,0,36,18,18,"Sublinhado"));
		topPanel.add(font = createPushButton(HTTP_STATIC_ICONS_GIF,0,54,18,18,"Fonte"));
		fontList = createFontList();
		fontList.setVisibleItemCount(fontList.getItemCount());
		fontPopup.add(fontList);
		fontPopup.setAnimationEnabled(true);        
		//topPanel.add(new HTML("&nbsp;"));
		topPanel.add(fontSize = createPushButton(HTTP_STATIC_ICONS_GIF,0,72,18,18,"Tamanho da fonte"));
		fontSizeList = createFontSizeList();
		fontSizeList.setVisibleItemCount(fontSizeList.getItemCount());
		fontSizePopup.add(fontSizeList);
		fontSizePopup.setAnimationEnabled(true);                
		//topPanel.add(new HTML("&nbsp;"));
		topPanel.add(colorText = createPushButton(HTTP_STATIC_ICONS_GIF,0,90,18,18,"Cor do texto"));
		colorTextPopup.setAnimationEnabled(true);
		colorTextPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
			public void onClose(CloseEvent<PopupPanel> event) {
				if (colorTextPopup.getColor() != null && colorTextPopup.getColor().length() > 0)
					styleTextFormatter.setForeColor(colorTextPopup.getColor());
			}
		});
		//topPanel.add(new HTML("&nbsp;"));
		topPanel.add(colorBack = createPushButton(HTTP_STATIC_ICONS_GIF,0,108,18,18,"Destacar texto"));
		colorBackPopup.setAnimationEnabled(true);
		colorBackPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
			public void onClose(CloseEvent<PopupPanel> event) {
				if (colorBackPopup.getColor() != null && colorBackPopup.getColor().length() > 0)
					styleTextFormatter.setBackColor(colorBackPopup.getColor());
			}
		});
		//topPanel.add(new HTML("&nbsp;"));		
		topPanel.add(stroke = createToggleButton(HTTP_STATIC_ICONS_GIF,0,342,18,18,"Tachado"));
		//topPanel.add(new HTML("&nbsp;"));
		topPanel.add(subscript = createToggleButton(HTTP_STATIC_ICONS_GIF,0,378,18,18,"Subscrito"));
		topPanel.add(superscript = createToggleButton(HTTP_STATIC_ICONS_GIF,0,396,18,18,"Sobrescrito"));
		//topPanel.add(new HTML("&nbsp;"));
		topPanel.add(alignleft = createPushButton(HTTP_STATIC_ICONS_GIF,0,234,18,18,"Alinhar texto à esquerda"));
		topPanel.add(alignmiddle = createPushButton(HTTP_STATIC_ICONS_GIF,0,252,18,18,"Centralizar texto"));
		topPanel.add(alignright = createPushButton(HTTP_STATIC_ICONS_GIF,0,270,18,18,"Alinhar texto à direita"));
		//topPanel.add(new HTML("&nbsp;"));
		topPanel.add(orderlist = createPushButton(HTTP_STATIC_ICONS_GIF,0,144,18,18,"Lista numerada"));
		topPanel.add(unorderlist = createPushButton(HTTP_STATIC_ICONS_GIF,0,162,18,18,"Lista com marcadores"));
		topPanel.add(indentright = createPushButton(HTTP_STATIC_ICONS_GIF,0,198,18,18,"Aumentar recuo"));
		topPanel.add(indentleft = createPushButton(HTTP_STATIC_ICONS_GIF,0,180,18,18,"Diminuir recuo"));
		topPanel.add(new HTML("&nbsp;"));
		topPanel.add(generatelink = createPushButton(HTTP_STATIC_ICONS_GIF,0,126,18,18,"Link"));
		//topPanel.add(breaklink = createPushButton(HTTP_STATIC_ICONS_GIF,0,414,18,18,CONSTANTS.richTextToolBarBreakLink()));
		topPanel.add(new HTML("&nbsp;"));
		topPanel.add(insertline = createPushButton(HTTP_STATIC_ICONS_GIF,0,360,18,18,"Inserir linha horizontal"));
		topPanel.add(insertimage = createPushButton(HTTP_STATIC_ICONS_GIF,0,324,18,18,"Inserir imagem"));
		topPanel.add(new HTML("&nbsp;"));
		topPanel.add(removeformatting = createPushButton(HTTP_STATIC_ICONS_GIF,0,288,18,18,"Remover formatação"));
	}

	private ToggleButton createToggleButton(String url, Integer top, Integer left, Integer width, Integer height, String tip) {
		Image extract = new Image(url, left, top, width, height);
		ToggleButton tb = new ToggleButton(extract);
		tb.setHeight(height+"px");
		tb.setWidth(width+"px");
		tb.addClickHandler(evHandler);
		if (tip != null) {
			tb.setTitle(tip);
		}
		return tb;
	}

	private PushButton createPushButton(String url, Integer top, Integer left, Integer width, Integer height, String tip) {
		Image extract = new Image(url, left, top, width, height);
		PushButton tb = new PushButton(extract);
		tb.setHeight(height+"px");
		tb.setWidth(width+"px");
		tb.addClickHandler(evHandler);
		if (tip != null) {
			tb.setTitle(tip);
		}
		return tb;
	}

	private ListBox createFontList() {
		ListBox mylistBox = new ListBox();
		mylistBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fontPopup.hide();
			}
		});
		mylistBox.addChangeHandler(evHandler);
		mylistBox.setVisibleItemCount(1);

		mylistBox.addItem("");
		for (String name: GUI_FONTLIST.keySet()) {
			mylistBox.addItem(name, GUI_FONTLIST.get(name));
		}

		return mylistBox;
	}

	private ListBox createFontSizeList() {
		ListBox mylistBox = new ListBox();
		mylistBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fontSizePopup.hide();
			}
		});
		mylistBox.addChangeHandler(evHandler);
		mylistBox.setVisibleItemCount(1);

		mylistBox.addItem("");
		for (String name: GUI_FONTSIZELIST.keySet()) {
			mylistBox.addItem(name, GUI_FONTSIZELIST.get(name));
		}

		return mylistBox;
	}

}