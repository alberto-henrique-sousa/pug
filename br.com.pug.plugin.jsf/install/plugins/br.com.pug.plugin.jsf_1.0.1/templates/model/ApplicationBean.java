/*******************************************************************************
 * Generator by Pug Plugin %version%
 *******************************************************************************/
package %packageBean%;

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
	private static final long serialVersionUID = -3298819292184517757L;
	public static String URI_HOME = "/pages/main.xhtml";
	public static String URI_HOME_REDIRECT = "/pages/main.xhtml?faces-redirect=true";
	public static String URI_LOGIN = "/pages/login.xhtml";
	private String theme;
	private Object usuarios;
	private String userName;
	
    public String getAppName() {
        return "Modelo JSF-Prime";
    }
    
    public String getTheme() {
    	this.theme = "aristo"; 
    	return this.theme;
    }    
    
	public Object getUsuarios() {
		this.usuarios = null;
		try {
			this.usuarios = Utils.session().getAttribute("usuario");
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

	public void setUsuarios(Object usuarios) {
		this.usuarios = usuarios;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}