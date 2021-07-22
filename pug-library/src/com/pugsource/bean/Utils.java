package com.pugsource.bean;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

public class Utils {

	public static String loginRedirectURI(String uriHome) {
		String page = "";
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);			
			
			String viewId = session.getAttribute("viewId") != null ? session.getAttribute("viewId").toString() : null;
			if (viewId != null && viewId.indexOf("?faces-redirect=true") == -1) {
				viewId += "?faces-redirect=true";
			}
			page = (viewId != null && !viewId.isEmpty()) ? viewId : uriHome;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public static String getParameterURL(String name) {
		String value = "";
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, String> params = facesContext.
					getExternalContext().getRequestParameterMap();
			value = params.get(name);					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}	
	
	public static void addFacesMessage(String messageId, Severity severity, String message) {
		FacesMessage facesMessage = MessageFactory.getMessage(messageId, severity, message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);		
	}
	
	public static void addFacesMessage(String messageId, String message) {
		FacesMessage facesMessage = MessageFactory.getMessage(messageId, message);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);		
	}
	
	public static HttpSession session() {
		FacesContext ctx = FacesContext.getCurrentInstance();
        return (HttpSession) ctx.getExternalContext().getSession(false);
	}
	
	public static void console(String msg) {
    	RequestContext context = RequestContext.getCurrentInstance();
    	context.execute("console.log('"+msg.replace("'", "\\x27").replace("\n", "\\r\\n")+"')");		
	}
	
	@SuppressWarnings("resource")
	public static Object loadClass(String name, String folder, ClassLoader classLoader) {
		Object obj = null;
		try {			
			File fileClass = new File(folder);
			@SuppressWarnings("deprecation")
			URL url = fileClass.toURL();
			URL[] urls = new URL[]{url};
			ClassLoader cl = new URLClassLoader(urls, classLoader);
			obj = cl.loadClass(name).newInstance();			
		} catch (Exception e) {
			e.printStackTrace();
		}					
		return obj;
	}
	
	public static Object loadClass(String name, Object objPath) {
		Object obj = null;
		try {
			String path = objPath.getClass().getResource(name + ".class").getPath();
			path = path.replace(name + ".class", "");
			obj = loadClass(name, path, objPath.getClass().getClassLoader());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}