package br.com.pug.showcase.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.pugsource.bean.Utils;

@ManagedBean
@ViewScoped
public class PugBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5056095378159300614L;
	private String folder;
	private String file;
	private Object java;

	@PostConstruct
	public void init() {
		
	}
	
	@PreDestroy
	public void destroy() {
		this.java = null;
	}
	
	public void onload() {
		try {
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "projects/" + folder;
			this.java = Utils.loadClass(file, path, this.getClass().getClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	public Object getJava() {
		return java;
	}

	public void setJava(Object java) {
		this.java = java;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}