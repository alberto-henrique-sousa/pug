package br.com.pug.showcase.dao;

import java.io.Serializable;

public class Layout implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4150026233913807454L;
	private String version;
	private String publish;
	private String userCreate;
	private String dateCreate;
	private String xhtml;	
	private String java;
	private String userUpdate;
	private String dateUpdate;
	
	public String getPublish() {
		return publish;
	}
	
	public void setPublish(String publish) {
		this.publish = publish;
	}
		
	public String getUserCreate() {
		return userCreate;
	}
	
	public void setUserCreate(String userCreate) {
		this.userCreate = userCreate;
	}
	
	public String getDateCreate() {
		return dateCreate;
	}
	
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	
	public String getXhtml() {
		return xhtml;
	}
	
	public void setXhtml(String xhtml) {
		this.xhtml = xhtml;
	}
	
	public String getUserUpdate() {
		return userUpdate;
	}
	
	public void setUserUpdate(String userUpdate) {
		this.userUpdate = userUpdate;
	}
	
	public String getDateUpdate() {
		return dateUpdate;
	}
	
	public void setDateUpdate(String dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getJava() {
		return java;
	}

	public void setJava(String java) {
		this.java = java;
	}	

}
