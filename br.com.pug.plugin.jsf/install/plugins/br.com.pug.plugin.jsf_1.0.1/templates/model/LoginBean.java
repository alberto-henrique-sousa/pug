/*******************************************************************************
 * Generator by Pug Plugin %version%
 *******************************************************************************/
package %packageBean%;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.hibernate.type.StandardBasicTypes;

import %packageBeanUtil%.DaoFactoryBean;

import com.pugsource.bean.Utils;
import com.pugsource.util.Tools;

@ManagedBean
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4184614656930681563L;
	@ManagedProperty(value="#{daoFactoryBean}")
	private DaoFactoryBean daoFactoryBean;
	private String nome;
	private String senha;

	public String login() {
		String page = null;
		try {
			if (nome != null && !nome.isEmpty() && senha != null && !senha.isEmpty()) {
				/*
				ArrayList<ArrayList<Object>> sqlParameters = new ArrayList<ArrayList<Object>>();
				Tools.setParametersQuery("username", nome, StandardBasicTypes.STRING, sqlParameters);			
				Tools.setParametersQuery("password", senha, StandardBasicTypes.STRING, sqlParameters);

				this.daoFactoryBean.getDaoFactory().beginTransaction();
				List<Usuarios> listUsuarios = this.daoFactoryBean.getDaoFactory().getUsuariosDao().findAllSQL(null, "from Usuarios where usuario = :username and senha = :password", 0, 0, sqlParameters);
				if (listUsuarios != null && listUsuarios.size() > 0) {
					page = Utils.loginRedirectURI(ApplicationBean.URI_HOME_REDIRECT); 
					Utils.session().setAttribute("usuario", listUsuarios.get(0));					
				} else {
					Utils.addFacesMessage("message_failed_login", FacesMessage.SEVERITY_WARN, "");
				}
				this.daoFactoryBean.getDaoFactory().commit();
				*/
				if (nome.equals("admin") && senha.equals("admin")) {
					page = Utils.loginRedirectURI(ApplicationBean.URI_HOME_REDIRECT); 
					Utils.session().setAttribute("usuario", "logado");										
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
        Utils.session().setAttribute("usuario", null);				
        return ApplicationBean.URI_HOME_REDIRECT;
	}	
	
	public DaoFactoryBean getDaoFactoryBean() {
		return daoFactoryBean;
	}

	public void setDaoFactoryBean(DaoFactoryBean daoFactoryBean) {
		this.daoFactoryBean = daoFactoryBean;
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