package br.com.pug.showcase.bean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.pugsource.bean.Utils;

@ManagedBean
@ApplicationScoped
public class ApplicationBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6929088767043723316L;
	private String theme;
	private String usuarios;
	private String userName;
	
    public String getAppName() {
        return "Título do Projeto";
    }
    
    public String getTheme() {
    	this.theme = "omega"; 
    	return this.theme;
    }    
    
	public String getUsuarios() {
		this.usuarios = null;
		try {
			this.usuarios = (String) Utils.session().getAttribute("usuarioPug");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.usuarios;
	}

	public String getUserName() {
		this.userName =	getUsuarios() != null ? getUsuarios().toString() : "";
		return this.userName;
	}
    
	public void setTheme(String theme) {
		this.theme = theme;
	}

}