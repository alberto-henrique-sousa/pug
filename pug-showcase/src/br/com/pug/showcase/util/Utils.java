package br.com.pug.showcase.util;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
	
	public static HttpSession session() {
		FacesContext ctx = FacesContext.getCurrentInstance();
        return (HttpSession) ctx.getExternalContext().getSession(false);
	}

	static public String md5(String str) {
		try {
			java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");

			char[] charArray = str.toCharArray();
			byte[] byteArray = new byte[charArray.length];

			for (int i=0; i<charArray.length; i++)
				byteArray[i] = (byte) charArray[i];

			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();

			for (int i=0; i<md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i] ) & 0xff;

				if (val < 16)
					hexValue.append("0");

				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();
		} 
		catch (java.security.NoSuchAlgorithmException e) {
			// Ignore
		}

		return "";
	}
}