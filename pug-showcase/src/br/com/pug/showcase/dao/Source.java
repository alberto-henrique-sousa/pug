package br.com.pug.showcase.dao;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

public class Source implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3599318367537336662L;
	private String path;
	private String folder;
	private String name;
	private Layout phtml;
	private String xhtml;
	private String css;
	private String js;
	private Note note1;
	private Note note2;
	private boolean exist;
	public static String STANDARD = "standard";
	public static String PHTML = ".phtml";
	public static String XHTML = ".xhtml";
	public static String JAVA = ".java";
	public static String CLASS = ".class";
	public static String CSS = ".css";
	public static String JS = ".js";
	public static String NOTE1 = ".ddoc";
	public static String NOTE2 = ".cdoc";

	public Source(String path, String folder, String name) {
		super();
		this.exist = true;
		this.path = path;
		this.folder = folder;
		this.name = name;
		this.phtml = new Layout();
		this.xhtml = "";
		this.css = "";
		this.js = "";
		this.note1 = new Note();
		this.note2 = new Note();
	}
	
	private String loadFile(String ext, boolean standard) {
		String ret = "";
		try {
			File file = new File(path + folder + "/" + (standard ? STANDARD : name) + ext);
			if (file.exists()) {
				ret = FileUtils.readFileToString(file, "UTF-8");
			} else {
				if (ext.equals(PHTML))
					this.exist = false;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private void loadJson(String ext, int type) {
		try {
			File file = new File(path + folder + "/" +  name + ext);
			if (file.exists()) {
				Gson gson = new Gson();
				if (type == 1)
					this.phtml = gson.fromJson(FileUtils.readFileToString(file, "UTF-8"), Layout.class);
				else if (type == 2)
					this.note1 = gson.fromJson(FileUtils.readFileToString(file, "UTF-8"), Note.class);
				else if (type == 3)
					this.note2 = gson.fromJson(FileUtils.readFileToString(file, "UTF-8"), Note.class);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveJson(String name, Object obj) {
		try {
			File file = new File(path + folder + "/" +  name);
			Gson gson = new Gson();
			FileUtils.writeStringToFile(file, gson.toJson(obj), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readAll() {
		try {
			loadJson(PHTML, 1);
			loadJson(NOTE1, 2);
			loadJson(NOTE2, 3);
			this.xhtml = loadFile(XHTML, false);
			this.css = loadFile(CSS, true);
			this.js = loadFile(JS, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean rename(String file, String fileName, String fileRename, String ext) {
		boolean ret = false;
		try {
			String oldFile = file;
			oldFile = file.replace(Source.PHTML, ext);
			String newFile = file;
			newFile = file.replace(fileName + Source.PHTML, fileRename + ext);			
			File fileOld = new File(oldFile);
			name = fileOld.getName();
			File fileNew = new File(newFile);
			ret = fileOld.renameTo(fileNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;		
	}
	
	public boolean delete(String file) {
		boolean ret = false;
		boolean force = false;
		try {
			File fileD = new File(file);
			if (fileD.isDirectory()) {
				String[] list = fileD.list();
				if (list.length == 2) {
					if ((list[0].indexOf(Source.CSS) > 0 || list[1].indexOf(Source.CSS) > 0) &&
							(list[0].indexOf(Source.JS) > 0 || list[1].indexOf(Source.JS) > 0)) {
						force = true;
					}
				}
			}
			if (force) {
				FileUtils.forceDelete(fileD);
				ret = true;
			} else {
				ret = fileD.delete();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private boolean deleteClass(String name, String folder) {
		boolean ret = false;
		try {
			File fileD = new File(path + folder);
			for (File fileClass : fileD.listFiles()) {
				if (fileClass.getName().indexOf(name) == 0 && fileClass.getName().indexOf(".class") > -1) {
					fileClass.delete();
				}					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;		
	}
	
	public boolean deleteAll(String file, String name, String folder) {
		boolean ret = false;
		try {
			ret = delete(file);
			delete(file.replace(Source.PHTML, Source.XHTML));
			delete(file.replace(Source.PHTML, Source.NOTE1));
			delete(file.replace(Source.PHTML, Source.NOTE2));
			delete(file.replace(Source.PHTML, Source.JAVA));
			delete(file.replace(Source.PHTML, Source.CLASS));
			deleteClass(name, folder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;		
		
	}
	
	public void readPHTML() {
		loadJson(PHTML, 1);
	}

	public void readNote2() {
		loadJson(NOTE2, 3);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Layout getPhtml() {
		return phtml;
	}

	public void setPhtml(Layout phtml) {
		this.phtml = phtml;
	}

	public String getXhtml() {
		return xhtml;
	}

	public void setXhtml(String xhtml) {
		this.xhtml = xhtml;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public Note getNote1() {
		return note1;
	}

	public void setNote1(Note note1) {
		this.note1 = note1;
	}

	public Note getNote2() {
		return note2;
	}

	public void setNote2(Note note2) {
		this.note2 = note2;
	}

	public boolean isExist() {
		this.exist = this.phtml != null && this.phtml.getXhtml() != null; 
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

}
