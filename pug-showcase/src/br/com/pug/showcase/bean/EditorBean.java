package br.com.pug.showcase.bean;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.pug.showcase.dao.Document;
import br.com.pug.showcase.dao.Layout;
import br.com.pug.showcase.dao.Note;
import br.com.pug.showcase.dao.Source;
import br.com.pug.showcase.dao.User;
import br.com.pug.showcase.util.Utils;

@ManagedBean
@ViewScoped
public class EditorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 567559283969432147L;
	private static String VERSION = "1.0-beta";
	private Source source;
	private String stateCommand;
	private String path;
	private String file;
	private String folder;
	private TreeNode explorer;    
	private Document selectedDocument;
	private boolean visibleEditor;
	private String fileNewLayout;
	private String fileNewFolder;
	private String fileRename;
	private User user;
	private int typeInterface;
	private String console;
	private boolean visibleConsole;
	private String version;
	
	@PostConstruct
	public void init() {
		try {
			path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "projects/";
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void onLoad(int typeInterface) {
		try {
			this.typeInterface = typeInterface;
			explorer = createDocuments();
			RequestContext context = RequestContext.getCurrentInstance();
			this.source = new Source(path, folder, file);
			this.source.readAll();
			if (isVisibleEditor()) {
				context.execute("readLayout('"+folder + "', '" + file +"')");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void compiler() {
		try {
			String errorEditor = "";
			this.console = "";
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			
			File fileFolder = new File(path + folder + "/");
			File fileJava = new File(path + folder + "/" + file + Source.JAVA);
			FileUtils.writeStringToFile(fileJava, this.source.getPhtml().getJava(), "UTF-8");
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(fileFolder));
			File fileLibs = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "WEB-INF/lib");
			fileManager.setLocation(StandardLocation.CLASS_PATH, Arrays.asList(fileLibs.listFiles()));
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			if (!compiler.getTask(null,
		               fileManager,
		               diagnostics,
		               null,
		               null,
		               fileManager.getJavaFileObjectsFromFiles(Arrays.asList(fileJava)))
		            .call()) {
				for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
					String msg = "Error: ("+(diagnostic.getLineNumber()) + "," + diagnostic.getColumnNumber() + ") " + diagnostic.getMessage(null).replace("'", "\\x27").replace("\n", "\\r\\n");
					errorEditor += "updateErrors("+(diagnostic.getLineNumber()-1)+", '"+msg+"');";
			    }				
			}
		    fileManager.close();
		    if (!errorEditor.isEmpty()) {
		    	this.console = diagnostics.getDiagnostics().size() + " erro(s) encontrado(s)!";
		    	errorEditor = "clearErrors();" + errorEditor + "goTabIndexCompile();";
		    } else {
		    	errorEditor = "clearErrors();goTabIndexCompile();";
		    }
	    	RequestContext context = RequestContext.getCurrentInstance();
	    	context.execute(errorEditor);
		} catch (Exception e) {
			this.console = e.getMessage();
			e.printStackTrace();
		}
	}
	
	private void updateXHTML() {
		try {
			File fileXHTML = new File(path + folder + "/" + file + Source.XHTML);
			FileUtils.writeStringToFile(fileXHTML, fullCode(this.source.getPhtml().getXhtml()), "UTF-8");
			compiler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void callRefreshJS() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("refreshSource();");		
	}	

	private void saveSource(String name, String code, boolean newLayout, boolean updateXHTML) {
		try {
			File file = new File(path + folder  + "/" + name);
			if (!newLayout || (newLayout && !file.exists()))
				FileUtils.writeStringToFile(file, code, "UTF-8");

			if (updateXHTML) {
				updateXHTML();			
				callRefreshJS();
			}	
		} catch (Exception e) {
			e.printStackTrace();
			stateCommand = e.getMessage();
			errorMessage(FacesMessage.SEVERITY_FATAL);
		}				
	}
	
	public void copy(ActionEvent actionEvent) {
		Utils.session().setAttribute("source", this.source);
		stateCommand = "Códigos XHTML e Java copiados.";
		errorMessage(FacesMessage.SEVERITY_INFO);		
	}
	
	public void paste(ActionEvent actionEvent) {
		Object obj = Utils.session().getAttribute("source");
		if (obj != null) {
			Source sourcePaste = (Source) obj;
			this.source.getPhtml().setUserUpdate(this.user.getUsername());
			this.source.getPhtml().setDateUpdate(currentDate());
			this.source.saveJson(file + Source.PHTML, sourcePaste.getPhtml());
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("location.reload();");					
		} else {
			stateCommand = "Área de transferência está vazia!";
			errorMessage(FacesMessage.SEVERITY_WARN);					
		}
	}	

	public void saveAll(ActionEvent actionEvent) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("saveAll();");				
	}
	
	public void savePHTML(ActionEvent actionEvent) {
		this.source.getPhtml().setUserUpdate(this.user.getUsername());
		this.source.getPhtml().setDateUpdate(currentDate());
		this.source.saveJson(file + Source.PHTML, this.source.getPhtml());
		updateXHTML();
		callRefreshJS();
	}

	public void saveCSS(ActionEvent actionEvent) {
		saveSource(Source.STANDARD + Source.CSS, this.source.getCss(), false, true);
	}

	public void saveJS(ActionEvent actionEvent) {
		saveSource(Source.STANDARD + Source.JS, this.source.getJs(), false, true);
	}

	public void saveNote1(ActionEvent actionEvent) {
		this.source.getNote1().setUser(this.user.getUsername());
		this.source.getNote1().setDate(currentDate());
		this.source.saveJson(file + Source.NOTE1, this.source.getNote1());
		callRefreshJS();
	}

	public void saveNote2(ActionEvent actionEvent) {
		this.source.getNote2().setUser(this.user.getUsername());
		this.source.getNote2().setDate(currentDate());
		this.source.saveJson(file + Source.NOTE2, this.source.getNote2());
		callRefreshJS();
	}

	public void errorMessage(FacesMessage.Severity severity) {		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(severity, "Mensagem:",  stateCommand) );		
	}

	public String fullCode(String code) {
		long now = Calendar.getInstance().getTimeInMillis();
		code = "<!DOCTYPE html>\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n"
				+ "      xmlns:f=\"http://java.sun.com/jsf/core\"\n"
				+ "      xmlns:h=\"http://java.sun.com/jsf/html\"\n"
				+ "      xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n"
				+ "      xmlns:p=\"http://primefaces.org/ui\"\n"
				+ "		 xmlns:pe=\"http://primefaces.org/ui/extensions\">\n"
				+ "	<f:view locale=\"pt-BR\" contentType=\"text/html\" encoding=\"UTF-8\">\n"
				+ "		<h:head>\n"
				+ "			<title>Pug Showcase</title>\n"
				+ "			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
				+ "			<meta http-equiv=\"pragma\" content=\"no-cache\" />\n"
				+ "			<meta http-equiv=\"expires\" content=\"0\" />\n"
				+ "			<link type=\"text/css\" rel=\"stylesheet\" href=\"standard.css?_=" + now + "\" />\n"
				+ "			<script type=\"text/javascript\" src=\"standard.js?_=" + now + "\"></script>\n"
				+ "		</h:head>\n"
				+ "		<h:body onload=\"init()\">\n"
				+ "			<f:metadata>\n"
				+ "    			<f:viewParam id=\"folder\" name=\"folder\" value=\"#{pugBean.folder}\" />\n"
				+ "    			<f:viewParam id=\"file\" name=\"file\" value=\"#{pugBean.file}\" />\n"
				+ "				<f:viewAction action=\"#{pugBean.onload()}\" />\n"
				+ "			</f:metadata>\n"						
				+ code + "\n"
				+ "		</h:body>\n"
				+ "	</f:view>\n"
				+ "</html>\n";
		return code;
	}
	
	private Source readPHTML(String folderName, String name) {
		Source source = new Source(path, folderName, name);
		try {
			source.readPHTML();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return source;
	}
	
	private Source readNote2(String folderName, String name) {
		Source source = new Source(path, folderName, name);
		try {
			source.readNote2();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return source;
	}	

	private void addNode(TreeNode node, File currentFile, boolean level1) {
		boolean level1R = level1;
		if (getUser().isAdmin())
			level1R = false;
		for (File file : currentFile.listFiles()) {
			String link = file.getAbsolutePath();
			link = link.substring(link.indexOf("projects"));
			link = link.substring(9);
			link = link.replace("\\", "/");
			String name = file.getName().replace(Source.PHTML, "").replace("\\", "/");
			String folderName = link.replace("/" + name + Source.PHTML, "");
			if (file.isDirectory()) {
				boolean ok = true;
				if (level1 && !file.getName().equalsIgnoreCase(getUser().getUsername()) && !getUser().isAdmin()) {
					ok = false;
					String[] folders = getUser().getFolders().split(";");
					for (int i = 0; i < folders.length && !ok; i++) {
						if (folders[i].equalsIgnoreCase(file.getName())) {
							ok = true;
						}	
					}
					if (!ok && this.folder != null && !this.folder.trim().isEmpty()) {
						for (int i = 0; i < folders.length && !ok; i++) {
							if (folders[i].equalsIgnoreCase(this.folder) || this.folder.indexOf(folders[i] + "/") == 0) {
								ok = true;
							}	
						}
					}
				}	
				if (ok) {	
					TreeNode folderNode = new DefaultTreeNode(new Document(file.getName(), true, false, "", file.getAbsolutePath(), folderName, "", "", level1R, getUser().isAdmin()), node);
					addNode(folderNode, file, false);
				}	
			} else if (file.isFile() && file.getName().indexOf(Source.PHTML) > 0) {
				Source sourcePhtml = readPHTML(folderName, name);
				Source sourceNote2 = readNote2(folderName, name);
				link = link.replace(Source.PHTML, "");
				if (!sourcePhtml.getPhtml().getPublish().equals("0") || typeInterface == 1)
					new DefaultTreeNode(new Document(name, false, false, link, file.getAbsolutePath(), folderName, sourcePhtml.getPhtml().getPublish(), sourceNote2.getNote2().getStatus(), level1R, getUser().isAdmin()), node);
			}
		}		
	}

	private TreeNode createDocuments() {
		TreeNode root = new DefaultTreeNode(new Document("Files", true, true, "Folder", path, "", "", "", true, getUser().isAdmin()), null);        
		new DefaultTreeNode(new Document("/", true, true, "", path, "", "", "", true, getUser().isAdmin()), root);
		File folderProjects = new File(path + (file==null && folder!=null && !folder.trim().isEmpty() ? folder : ""));
		if (folderProjects.exists())
			addNode(root, folderProjects, true);
		return root;
	}	

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}		

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public TreeNode getExplorer() {
		return explorer;
	}

	public void setExplorer(TreeNode explorer) {
		this.explorer = explorer;
	}

	public Document getSelectedDocument() {
		return selectedDocument;
	}

	public void setSelectedDocument(Document selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	public boolean isVisibleEditor() {
		visibleEditor = (file != null && !file.trim().isEmpty() && source.isExist());
		return visibleEditor;
	}

	public String getFileNewLayout() {
		return fileNewLayout;
	}

	public void setFileNewLayout(String fileNewLayout) {
		this.fileNewLayout = fileNewLayout;
	}
	
	public boolean layoutExist() {
		String folder = this.selectedDocument.getFile();
		folder = folder.substring(folder.indexOf("projects"));
		folder = folder.substring(9);
		folder = folder.replace("\\", "/");
		File file = new File(path + folder + "/" + fileNewLayout + Source.PHTML);
		return file.exists();
	}
	
	private String codeJava() {
		String code = "import java.io.Serializable;\n"
				+ "import java.text.DateFormat;\n"
				+ "import java.text.SimpleDateFormat;\n"
				+ "import java.util.Arrays;\n"
				+ "import java.util.Calendar;\n"
				+ "import java.util.ArrayList;\n"
				+ "import java.util.List;\n"
				+ "import java.util.Date;\n"
				+ "\n"
				+ "import com.google.gson.Gson;\n"
				+ "\n"
				+ "import javax.faces.application.FacesMessage;\n"
				+ "import javax.faces.context.FacesContext;\n"
				+ "import javax.faces.event.ActionEvent;\n"
				+ "\n"
				+ "import com.pugsource.bean.Utils;\n"
				+ "\n"
				+ "public class "+file+" implements Serializable {\n"
				+ "\n"
				+ "	/**\n"
				+ "	 * \n"
				+ "	 */\n"
				+ "	private static final long serialVersionUID = 1L;\n"
				+ "\n"
				+ "	public "+file+"() {\n"
				+ "\n"
				+ "	}\n"				
				+ "\n"    
				+ "}";    
		return code;
	}

	public void createLayout() {
		if (fileNewLayout != null && !fileNewLayout.trim().isEmpty() && selectedDocument != null) {
			try {
				if (!layoutExist()) {
					this.fileNewLayout = this.fileNewLayout.toLowerCase();
					String dateCreate = currentDate();
					
					folder = this.selectedDocument.getFile();
					folder = folder.substring(folder.indexOf("projects"));
					folder = folder.substring(9);
					folder = folder.replace("\\", "/");
					file = fileNewLayout;
					
					this.source.setFolder(folder);
					this.source.setPhtml(new Layout());
					this.source.getPhtml().setVersion(VERSION);
					this.source.getPhtml().setXhtml("");
					this.source.getPhtml().setJava(codeJava());
					this.source.getPhtml().setDateCreate(dateCreate);
					this.source.getPhtml().setUserCreate(this.user.getUsername());
					this.source.getPhtml().setUserUpdate(this.user.getUsername());
					this.source.getPhtml().setDateUpdate(dateCreate);
					this.source.getPhtml().setPublish("1");
					this.source.setNote1(new Note());
					this.source.setNote2(new Note());
					this.source.getNote2().setStatus("0");
					this.source.saveJson(fileNewLayout + Source.PHTML, this.source.getPhtml());
					saveSource(Source.STANDARD + Source.CSS, "/* CSS design standard */\n", true, false);
					saveSource(Source.STANDARD + Source.JS, "/* JS design standard */\n\n/* function executada no 'onload' do 'body', não remova! */\nfunction init() {\n\n}\n", true, false);
					this.source.saveJson(fileNewLayout + Source.NOTE1, this.source.getNote1());
					this.source.saveJson(fileNewLayout + Source.NOTE2, this.source.getNote2());
					updateXHTML();
									
					FacesContext.getCurrentInstance().getExternalContext().redirect("editor.xhtml?folder=" + folder + "&file=" + fileNewLayout);
				} else {
					stateCommand = "Layout já existe!";
					errorMessage(FacesMessage.SEVERITY_WARN);								
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			stateCommand = "Nome ou Pasta não selecionado!";
			errorMessage(FacesMessage.SEVERITY_WARN);			
		}
	}

	private String currentDate() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String date = format.format(Calendar.getInstance().getTime());
		return date;
	}

	public String getFileNewFolder() {
		return fileNewFolder;
	}

	public void setFileNewFolder(String fileNewFolder) {
		this.fileNewFolder = fileNewFolder;
	}

	public void createFolder() {
		if (fileNewFolder != null && !fileNewFolder.trim().isEmpty() && selectedDocument != null) {
			try {	
				this.fileNewFolder = this.fileNewFolder.toLowerCase();
				File file = new File(selectedDocument.getFile() + "/" + fileNewFolder);
				if (file.mkdir())
					FacesContext.getCurrentInstance().getExternalContext().redirect("editor.xhtml");
				else {
					stateCommand = "Erro ao criar pasta!";
					errorMessage(FacesMessage.SEVERITY_FATAL);					
				}
			} catch (Exception e) {
				e.printStackTrace();
				stateCommand = e.getMessage();
				errorMessage(FacesMessage.SEVERITY_FATAL);
			}
		} else {
			stateCommand = "Nome ou Pasta não selecionado!";
			errorMessage(FacesMessage.SEVERITY_WARN);			
		}
	}

	public void deleteFile() {
		if (selectedDocument != null) {
			try {
				boolean ret = false;
				if (selectedDocument.isFolder())
					ret = this.source.delete(selectedDocument.getFile());
				else
					ret = this.source.deleteAll(selectedDocument.getFile(), selectedDocument.getName(), selectedDocument.getFolderName());
				if (!ret) {
					stateCommand = "Erro ao excluir!";
					errorMessage(FacesMessage.SEVERITY_FATAL);					
				} else
					FacesContext.getCurrentInstance().getExternalContext().redirect("editor.xhtml");					
			} catch (Exception e) {
				e.printStackTrace();
				stateCommand = e.getMessage();
				errorMessage(FacesMessage.SEVERITY_FATAL);
			}
		} else {
			stateCommand = "Nome ou Pasta não selecionado!";
			errorMessage(FacesMessage.SEVERITY_WARN);			
		}
	}

	public String getFileRename() {
		return fileRename;
	}

	public void setFileRename(String fileRename) {
		this.fileRename = fileRename;
	}
	
	public void renameFile() {
		if (selectedDocument != null && fileRename != null && !fileRename.trim().isEmpty()) {
			try {		
				this.fileRename = this.fileRename.toLowerCase();
				File file = new File(selectedDocument.getFile());
				String name = "";
				if (selectedDocument.isFolder())		
					name = selectedDocument.getFile().replace(selectedDocument.getName(), fileRename);
				else
					name = selectedDocument.getFile().replace(selectedDocument.getName() + Source.PHTML, fileRename + Source.PHTML);
				File fileNew = new File(name);
				
				if (file.renameTo(fileNew)) {
					if (!selectedDocument.isFolder()) {
						this.source.rename(selectedDocument.getFile(), selectedDocument.getName(), fileRename, Source.XHTML);
						this.source.rename(selectedDocument.getFile(), selectedDocument.getName(), fileRename, Source.NOTE1);
						this.source.rename(selectedDocument.getFile(), selectedDocument.getName(), fileRename, Source.NOTE2);
					}
					FacesContext.getCurrentInstance().getExternalContext().redirect("editor.xhtml");
				} else {
					stateCommand = "Erro ao renomear!";
					errorMessage(FacesMessage.SEVERITY_FATAL);					
				}
			} catch (Exception e) {
				e.printStackTrace();
				stateCommand = e.getMessage();
				errorMessage(FacesMessage.SEVERITY_FATAL);
			}
		} else {
			stateCommand = "Nome ou Pasta não selecionado!";
			errorMessage(FacesMessage.SEVERITY_WARN);			
		}
	}
	
	public void refreshIFrame() {
		updateXHTML();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("updateIFrame();");				
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public User getUser() {
		this.user = null;
		try {
			this.user = (User) Utils.session().getAttribute("usuario");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.user;
	}


	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getVersion() {
		this.version = VERSION;
		return this.version;
	}

	public boolean getVisibleConsole() {
		this.visibleConsole = (this.console != null && !this.console.isEmpty());
		return visibleConsole;
	}

	public void setVisibleConsole(boolean visibleConsole) {
		this.visibleConsole = visibleConsole;
	}

}