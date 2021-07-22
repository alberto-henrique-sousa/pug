package br.com.pug.showcase.dao;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1817883161302463636L;
	private String username;
	private String password;
	private String level;
	private String folders;
	private boolean admin;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFolders() {
		return folders;
	}

	public void setFolders(String folders) {
		this.folders = folders;
	}

	public boolean isAdmin() {
		this.admin = this.level.equalsIgnoreCase("admin");
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
