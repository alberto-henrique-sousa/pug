package br.com.pug.showcase.bean;

import java.io.File;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.pug.showcase.dao.User;
import br.com.pug.showcase.util.Utils;

@ManagedBean
public class LoginPugBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2448891196367075888L;
	private User user = new User();
	private String uri;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String login() {
		String page = null;
		try {
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "WEB-INF/classes/users.json";
			File file = new File(path);
			User userFind = null;
			boolean search = false;
			Gson gson = new Gson();
			User[] listUsers = gson.fromJson(FileUtils.readFileToString(file), User[].class);			
			for (int i = 0; i < listUsers.length && !search; i++) {
				userFind = listUsers[i];
				if (this.user.getUsername().equalsIgnoreCase(userFind.getUsername()) && Utils.md5(this.user.getPassword().toLowerCase()).equals(userFind.getPassword())) {					
					search = true;
				}				
			}
			if (search) {
				uri = uri == null || uri.trim().isEmpty() ? "/index.xhtml" : uri;
				page = Utils.loginRedirectURI(uri + "?faces-redirect=true");
				Utils.session().setAttribute("usuario", userFind);
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensagem:",  "Usuário e/ou senha inválido(s)!") );		
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	public String logoff() {
		String page = (String) Utils.session().getAttribute("page");
		if (page == null)
			page = "/index.xhtml";
        Utils.session().setAttribute("usuario", null);				
        Utils.session().setAttribute("page", null);				
        return page + "?faces-redirect=true";
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}	
	
}
