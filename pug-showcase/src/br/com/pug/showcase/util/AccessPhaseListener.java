/*******************************************************************************
 * Generator by Pug Framework 1.6.1
 *******************************************************************************/
package br.com.pug.showcase.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import br.com.pug.showcase.dao.User;

public class AccessPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7291218608631896250L;
	private static final String[] PERMITTED_PATTERN = {"login.xhtml"};

	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			FacesContext context = event.getFacesContext();
			String viewId = context.getViewRoot().getViewId();
			boolean urlProtected = StringUtils.indexOfAny(viewId, PERMITTED_PATTERN) == -1;
			Object userSession = context.getExternalContext()
					.getSessionMap().get("usuario");
			ExternalContext ec = context.getExternalContext();
			if (urlProtected && userSession == null){
				try {
					HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
					if (session != null)
						session.setAttribute("viewId", viewId);										
				} catch (Exception e) {
					e.printStackTrace();
				}
				context.getExternalContext().redirect(ec.getRequestContextPath() + "/login.xhtml?uri=" + viewId);
			} else {
				if (userSession != null) {
					User user = (User) userSession;
					if (viewId.indexOf("editor.xhtml") > -1 && !(user.isAdmin()
							|| user.getLevel().equals("dev"))) {
						context.getExternalContext().redirect(ec.getRequestContextPath() + "/denied.xhtml");												
					} else if (viewId.indexOf("manager.xhtml") > -1 && !(user.isAdmin())) {
						context.getExternalContext().redirect(ec.getRequestContextPath() + "/denied.xhtml");												
					} 
				}
				if (Utils.session() != null)
					Utils.session().setAttribute("page", viewId);
			}
		} catch (IOException e) {
			e.printStackTrace();
            throw new FacesException(e);
        }
	}	

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}