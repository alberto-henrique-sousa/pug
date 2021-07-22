package br.com.pug.designer.terminal;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.com.pug.designer.dao.Component;

@ManagedBean
@ViewScoped
public class TerminalView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 567559283969432147L;
	private String stateCommand;
	private List<Component> listComponents;
	private String realPath;
	private String command;
	private int opLine1;
	private int opLine2;
	private String TAB = "    ";
	private String clipboard = "";

	@PostConstruct
	public void init() {
		try {			
			realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			listComponents = new ArrayList<Component>();
			readComponentsTags();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private void readComponentsTags() {
		loadTagLib("META-INF/primefaces-p.taglib.xml", "p", "tag-name");
		loadTagLib("META-INF/html_basic.tld", "h", "name");
		loadTagLib("META-INF/jsf_core.tld", "f", "name");
		loadTagLib("com/sun/faces/metadata/taglib/ui.taglib.xml", "ui", "tag-name");
		loadTagPug();
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

	public void loadTagPug() {
		try {
			addTagPug("delete", "");
			addTagPug("edit", "");
			addTagPug("copy", "");
			addTagPug("cut", "");
			addTagPug("paste", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTagPug(String name, String... attrs) {
		Component comp = new Component();
		List<String> listAttr = Arrays.asList(attrs);
		comp.setName("pug:" + name);		
		comp.setAttributes(listAttr);
		this.listComponents.add(comp);		
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal != null ? textVal.trim() : textVal;
	}

	public List<String> completeCommand(String query) {
		List<String> results = new ArrayList<String>();
		for (Component component : listComponents) {
			if (component.getName().toLowerCase().indexOf(query.toLowerCase()) == 0)
				results.add(component.getName());
		}
		if (results.size() == 0 && !query.trim().isEmpty()) {
			results.add(query);
			for (Component component : listComponents) {
				String[] commands = query.split(" ");
				if (component.getName().toLowerCase().indexOf(commands[0].trim().toLowerCase()) == 0) {
					for (String attr : component.getAttributes()) {
						if (!attr.trim().isEmpty() && query.trim().indexOf(" " + attr + "=") == -1)
							results.add(query.trim() + " " + attr);
					}            		
				}
			}        	
		}
		return results;
	}

	public void remove(ActionEvent actionEvent) {
		try {
			File fileCommand = new File(realPath + "/views/layout.cmd");
			if (fileCommand.exists())
				fileCommand.delete();
			File fileComponents = new File(realPath + "/views/layout.comp");			
			if (fileComponents.exists())
				fileComponents.delete();
			File fileXHTML = new File(realPath + "/views/layout.xhtml");
			if (fileXHTML.exists())
				fileXHTML.delete();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("removeLayout();");
		} catch (Exception e) {
			e.printStackTrace();
			stateCommand = e.getMessage();
			errorMessage(FacesMessage.SEVERITY_FATAL);
		}
	}

	public void execute(ActionEvent actionEvent) {
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			stateCommand = "Command invalid!";

			if (command.indexOf("pug:") == 0) {
				boolean exec = false;
				if (command.indexOf("pug:edit") == 0) {
					if (opLine1 > 0) {
						File fileComponents = new File(realPath + "/views/layout.comp");
						if (fileComponents.exists()) {
							List<String> listLines = FileUtils.readLines(fileComponents, "UTF-8");
							if (opLine1 <= listLines.size()) {
								String lineValue = listLines.get(opLine1-1).replace("'", "'''");
								exec = true;
								context.execute("editLine('"+lineValue+"');");
							}	
						}															
					}
				} else if (command.indexOf("pug:delete") == 0) {
					exec = deleteCode(context);
				} else if (command.indexOf("pug:copy") == 0) {
					if (opLine1 > 0) {
						String code = copyCode();
						if (!code.trim().isEmpty()) {
							exec = true;
							this.clipboard = code;
							context.execute("focus();");
						}	
					}					
				} else if (command.indexOf("pug:paste") == 0) {
					exec = true;
					insertCode(this.clipboard, context);
					context.execute("focus();");						
				} else if (command.indexOf("pug:cut") == 0) {
					if (opLine1 > 0) {
						String code = copyCode();
						if (!code.trim().isEmpty()) {
							this.clipboard = code;
							context.execute("focus();");												
							exec = deleteCode(context);
						}	
					}					
				} 
				if (!exec)
					errorMessage(FacesMessage.SEVERITY_INFO);
			} else { 			
				insertCode("", context);
			}
		}	
		catch (Exception e) {
			e.printStackTrace();
			stateCommand = e.getMessage();
			errorMessage(FacesMessage.SEVERITY_FATAL);
		}		
	}

	public void insertCode(String code, RequestContext context) {
		try {
			if (code.trim().isEmpty() && command != null && !command.trim().isEmpty()) {
				command = command.trim();
				String[] splitCommand = command.split(" ");

				String sCommand = splitCommand[0].trim();
				code = createComponent(sCommand, splitCommand);
				
				if (code.isEmpty())
					code = command;
			}	

			if (!code.isEmpty()) {					
				String hCode = "";
				String hComp = "";
				File fileCommand = new File(realPath + "/views/layout.cmd");			
				if (fileCommand.exists()) {
					hCode = FileUtils.readFileToString(fileCommand, "UTF-8") + "\n";
				}
				FileUtils.writeStringToFile(fileCommand, hCode + command, "UTF-8");

				File fileComponents = new File(realPath + "/views/layout.comp");
				List<String> linesCode = null;
				if (fileComponents.exists()) {
					hComp = FileUtils.readFileToString(fileComponents, "UTF-8") + "\n";
					linesCode = FileUtils.readLines(fileComponents, "UTF-8");
				}
				String blockCode = hComp + code;
				if (linesCode != null && this.opLine1 > 0) {
					hComp = "";
					String lastSpace = "";
					for (int i = 0; i < linesCode.size(); i++) {
						if (!linesCode.get(i).trim().isEmpty()) {
							hComp += (!hComp.isEmpty() ? "\n" : "");
							if (i+1 == this.opLine1) {
								hComp += lastSpace + (code.replace("\n", "\n" + lastSpace )) + "\n";
							}	
							hComp += linesCode.get(i);
							lastSpace = linesCode.get(i).substring(0, linesCode.get(i).indexOf("<"));
							if (linesCode.get(i).indexOf("</") == -1)
								lastSpace += TAB; 
						}	
					}						
					blockCode = hComp;
				}
				this.opLine1 = 0;
				FileUtils.writeStringToFile(fileComponents, blockCode, "UTF-8");			

				File fileXHTML = new File(realPath + "/views/layout.xhtml");
				FileUtils.writeStringToFile(fileXHTML, fullCode(blockCode), "UTF-8");

				//System.out.println(fileXHTML.getAbsolutePath());
				context.execute("updateCodeXHTML('"+"views/layout.comp"+"');");
			} else {
				errorMessage(FacesMessage.SEVERITY_INFO);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			stateCommand = e.getMessage();
			errorMessage(FacesMessage.SEVERITY_FATAL);
		}
	}
	
	public String copyCode() {
		String code = "";
		try {
			int nLine2 = opLine2 > opLine1 ? opLine2 : opLine1;
			File fileComponents = new File(realPath + "/views/layout.comp");
			if (fileComponents.exists()) {
				List<String> listLines = FileUtils.readLines(fileComponents, "UTF-8");
				if (nLine2-1 < listLines.size()) {
					for (int i = opLine1-1; i <= nLine2-1; i++) {
						String lineValue = listLines.get(i);
						code += lineValue.trim() + (i < nLine2-1 ? "\n" : "");
					}
				}	
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
	
	public boolean deleteCode(RequestContext context) {
		boolean ret = false;
		if (opLine1 > 0) {
			int nLine2 = opLine2 > opLine1 ? opLine2 : opLine1;
			ret = deleteLines(command, opLine1, nLine2);
			context.execute("updateCodeXHTML('"+"views/layout.comp"+"');");						
		}		
		return ret;
	}
	
	public boolean deleteLines(String command, int line1, int line2) {
		boolean ret = false;
		try {
			File fileComponents = new File(realPath + "/views/layout.comp");
			if (fileComponents.exists()) {
				List<String> lines = FileUtils.readLines(fileComponents, "UTF-8");
				String hComp = "";
				for (int i = 0; i < lines.size(); i++) {
					int pos = i+1;
					if ((pos < line1 || pos > line2) && !lines.get(i).trim().isEmpty()) {
						String line = lines.get(i);
						hComp += (!hComp.isEmpty() ? "\n" : "") + line;
					} 	
				}
				FileUtils.writeStringToFile(fileComponents, hComp, "UTF-8");
				
				File fileXHTML = new File(realPath + "/views/layout.xhtml");
				FileUtils.writeStringToFile(fileXHTML, fullCode(hComp), "UTF-8");
				
				File fileCommand = new File(realPath + "/views/layout.cmd");
				String hCode = "";
				if (fileCommand.exists()) {
					hCode = FileUtils.readFileToString(fileCommand, "UTF-8");
				}
				FileUtils.writeStringToFile(fileCommand, hCode + "\n" + command, "UTF-8");
				ret = true;
			}							
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void errorMessage(FacesMessage.Severity severity) {		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity, "Message:",  stateCommand) );		
	}

	public String fullCode(String code) {
		code = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n"
				+ "      xmlns:f=\"http://java.sun.com/jsf/core\"\n"
				+ "      xmlns:h=\"http://java.sun.com/jsf/html\"\n"
				+ "      xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n"
				+ "      xmlns:p=\"http://primefaces.org/ui\">\n"
				+ "	<f:view locale=\"pt-br\" contentType=\"text/html\">\n"
				+ "		<h:head>\n"
				+ "			<title>Pug Designer</title>\n"
				+ "		</h:head>\n"
				+ "		<h:body>\n"
				+ code + "\n"
				+ "		</h:body>\n"
				+ "	</f:view>\n"
				+ "</html>\n";
		return code;
	}

	public String createComponent(String sCommand, String[] params) {
		String code = "";
		try {
			String attr = "";
			if (params.length > 1) {
				for (int i = 1; i < params.length; i++) {
					attr += params[i] + " ";					
				}
				attr = attr.trim();
			}				
			if (!sCommand.isEmpty() && this.listComponents != null ) {
				for (int i = 0; i < this.listComponents.size() && code.isEmpty(); i++) {
					if (this.listComponents.get(i).getName().equalsIgnoreCase(sCommand)) {
						code = this.listComponents.get(i).getSyntax();
						code = code.replace("?", attr);
					}
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public int StrToInt(String value) {
		int ret = 0;
		try {
			ret = Integer.valueOf(value);
		} catch (Exception e) {
		}
		return ret;
	}

	public int getOpLine1() {
		return opLine1;
	}

	public void setOpLine1(int opLine1) {
		this.opLine1 = opLine1;
	}

	public int getOpLine2() {
		return opLine2;
	}

	public void setOpLine2(int opLine2) {
		this.opLine2 = opLine2;
	}

}