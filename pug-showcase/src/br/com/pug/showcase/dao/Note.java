package br.com.pug.showcase.dao;

import java.io.Serializable;

public class Note implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 564410457958507314L;
	private String user;
	private String date;
	private String text;
	private String status;
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}	
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
