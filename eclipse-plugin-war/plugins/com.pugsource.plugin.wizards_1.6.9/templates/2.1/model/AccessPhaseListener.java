/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %packageBeanUtil%;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import %packageBean%.ApplicationBean;

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
			Object usuarios = context.getExternalContext()
					.getSessionMap().get("usuario");
			if (urlProtected && usuarios == null){
				try {
					HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
					if (session != null)
						session.setAttribute("viewId", viewId);										
				} catch (Exception e) {
					e.printStackTrace();
				}
				ExternalContext ec = context.getExternalContext();
				context.getExternalContext().redirect(ec.getRequestContextPath() + ApplicationBean.URI_LOGIN + "?uri=" + viewId);
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