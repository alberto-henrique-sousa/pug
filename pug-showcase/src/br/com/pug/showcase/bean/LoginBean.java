/*******************************************************************************
 * Generator by Pug Framework 1.6.1
 *******************************************************************************/
package br.com.pug.showcase.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import com.pugsource.bean.Utils;

@ManagedBean
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7316680055989205098L;
	private String nome;
	private String senha;

	public String login() {
		String page = null;
		try {
			if (nome != null && !nome.isEmpty() && senha != null && !senha.isEmpty()) {
				if (nome.equalsIgnoreCase("admin") && senha .equalsIgnoreCase("admin")) {
					Utils.session().setAttribute("usuarioPug", nome);					
					Utils.addFacesMessage("message_successfully_login", FacesMessage.SEVERITY_INFO, nome);
				} else {
					Utils.addFacesMessage("message_failed_login", FacesMessage.SEVERITY_WARN, "");
				}
			} else {
				Utils.addFacesMessage("message_failed_login", FacesMessage.SEVERITY_WARN, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage() != null ? e.getMessage() : "Erro interno!";
			errorMsg += " - " +(e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "");
			Utils.addFacesMessage("message_failed_login", FacesMessage.SEVERITY_ERROR, errorMsg);
		}
		return page;
	}

	public String logoff() {
        Utils.session().setAttribute("usuarioPug", null);				
        return "#";
	}	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}	
	
}