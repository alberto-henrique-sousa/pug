package br.com.pug.showcase.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import com.pugsource.bean.Utils;

@ManagedBean
public class MenuBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2973821282798965074L;
	private boolean master;

	@PostConstruct
	public void init() {
		try {
			this.master = true;
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage() != null ? e.getMessage() : "Erro interno!";
			errorMsg += " - " +(e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "");
			Utils.addFacesMessage("message_failed_created", FacesMessage.SEVERITY_ERROR, errorMsg);
		}
	}
	
	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}
		
}
