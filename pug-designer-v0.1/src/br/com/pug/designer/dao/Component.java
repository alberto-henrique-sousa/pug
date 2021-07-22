package br.com.pug.designer.dao;

import java.io.Serializable;
import java.util.List;

public class Component implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6957653459444459684L;
	private String name;
	private String syntax;
	private List<String> attributes;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSyntax() {
		return syntax;
	}
	
	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

}
