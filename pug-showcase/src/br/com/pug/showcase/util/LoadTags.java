package br.com.pug.showcase.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.com.pug.showcase.dao.Component;

public class LoadTags {
	
	private List<Component> listComponents = new ArrayList<Component>();

	public LoadTags() {
		loadTagLib("META-INF/primefaces-p.taglib.xml", "p", "tag-name");
		loadTagLib("META-INF/html_basic.tld", "h", "name");
		loadTagLib("META-INF/jsf_core.tld", "f", "name");
		loadTagLib("META-INF/primefaces-extensions.taglib.xml", "pe", "tag-name");
		loadTagLib("com/sun/faces/metadata/taglib/ui.taglib.xml", "ui", "tag-name");
	}

	private void loadTagLib(String file, String prefix, String tag) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Reader reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(file) ,"UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			Document doc = builder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList nTags = doc.getElementsByTagName("tag");

			for(int i = 0 ; i < nTags.getLength();i++) {				
				Element el = (Element)nTags.item(i);
				Component component = new Component();
				String tagName = getTextValue(el, tag);
				component.setName(prefix + ":" + tagName);
				component.setSyntax("<"+prefix+":"+tagName+" ?>\n</"+prefix+":"+tagName+">");
				List<String> listAttr = new ArrayList<String>();
				NodeList nComp = el.getElementsByTagName("attribute");
				if (nComp != null) {
					for (int j = 0; j < nComp.getLength(); j++) {
						Element el2 = (Element)nComp.item(j);
						listAttr.add(getTextValue(el2, "name"));
					}
				}
				component.setAttributes(listAttr);
				this.listComponents.add(component);
			}			
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal != null ? textVal.trim() : textVal;
	}

	public List<Component> getListComponents() {
		return listComponents;
	}

}
