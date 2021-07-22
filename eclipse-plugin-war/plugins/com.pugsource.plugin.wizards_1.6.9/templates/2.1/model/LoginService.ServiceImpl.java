/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %serverPackage%;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.type.StandardBasicTypes;

import %servicePackage%.%serviceName%;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import %classDTOPackage%.%classDTO%;
import %classDTOPackage%.ResultSaveDTO;
import %packageDAO%.DaoFactory;
import %packageDAO%.%classDAO%;
import com.pugsource.util.Security;
import com.pugsource.util.Tools;

public class %serviceName%Impl extends RemoteServiceServlet implements %serviceName% {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;
	
	public void iniSession() {
		request = this.getThreadLocalRequest();
		session = request.getSession();		
	}

	@Override
	public ArrayList<%classDTO%> listAll(Integer id, String order, int page, int sizePage) {
		ArrayList<%classDTO%> list%classDTO% = new ArrayList<%classDTO%>();
		DaoFactory daoFactory = null;
		try {
			daoFactory = new DaoFactory();
			List<%classDAO%> list%classDAO% = null;
			Map<String, Object> toReturn = new HashMap<String, Object>();
			ArrayList<ArrayList<Object>> sqlParameters = new ArrayList<ArrayList<Object>>();
			String where = "";
			if (id != null) {
				Tools.setParametersQuery("id", id, StandardBasicTypes.INTEGER, sqlParameters);
				where = " where id = :id";
			}
			if (order == null)
				order = "";
			if (!order.isEmpty())
				order = " order by " + order;
			daoFactory.beginTransaction();			
			list%classDAO% = daoFactory.get%classDAO%Dao().findAllSQL(toReturn, "from %classDAO%"+where+order, page, sizePage, sqlParameters);								
			for (%classDAO% %instanceDAO% : list%classDAO%) {
				%classDTO% %instanceDTO% = new %classDTO%();
%fieldsList%				
				%instanceDTO%.setSizeData((Integer) toReturn.get("recordCount"));
				list%classDTO%.add(%instanceDTO%);
			}			
			daoFactory.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (daoFactory != null) {
			daoFactory.close();	
		}	
		return list%classDTO%;
	}
	
	@Override
	public ResultSaveDTO save(%classDTO% %instanceDTO%) {
		ResultSaveDTO resultSaveDTO = new ResultSaveDTO();
		DaoFactory daoFactory = null;
		try {
			daoFactory = new DaoFactory();
			%classDAO% %instanceDAO% = new %classDAO%();			

%fieldsSave%						
			daoFactory.beginTransaction();			
			if (%instanceDTO%.getId() != null && %instanceDTO%.getId() > 0)
				daoFactory.get%classDAO%Dao().merge(%instanceDAO%);
			else
				daoFactory.get%classDAO%Dao().persist(%instanceDAO%);			
			daoFactory.commit();
			resultSaveDTO.setId(%instanceDAO%.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			resultSaveDTO.setError(e.getMessage() != null ? e.getMessage() : "Erro interno!");
		}
		if (daoFactory != null) {
			daoFactory.close();	
		}	
		return resultSaveDTO;
	}
	
	@Override
	public ResultSaveDTO delete(ArrayList<%classDTO%> list%classDTO%) {
		ResultSaveDTO resultSaveDTO = new ResultSaveDTO();
		DaoFactory daoFactory = null;
		try {
			daoFactory = new DaoFactory();
			
			daoFactory.beginTransaction();
			for (%classDTO% %instanceDTO% : list%classDTO%) {
				%classDAO% %instanceDAO% = new %classDAO%();
				%instanceDAO%.setId(%instanceDTO%.getId());
				daoFactory.get%classDAO%Dao().remove(%instanceDAO%);
			}
			daoFactory.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			resultSaveDTO.setError(e.getMessage() != null ? e.getMessage() : "Erro interno!");
		}
		if (daoFactory != null) {
			daoFactory.close();	
		}	
		return resultSaveDTO;
	}

	@Override
	public %classDTO% login(String username, String password) {
		%classDTO% %instanceDTO% = null;
		DaoFactory daoFactory = null;
		try {
			iniSession();
			session.setAttribute("%instanceDAO%", null); 					
			daoFactory = new DaoFactory();
			if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {			
				ArrayList<ArrayList<Object>> sqlParameters = new ArrayList<ArrayList<Object>>();
				Tools.setParametersQuery("username", username, StandardBasicTypes.STRING, sqlParameters);			
				Tools.setParametersQuery("password", password, StandardBasicTypes.STRING, sqlParameters);

				daoFactory.beginTransaction();
				
				/*
				 * Password admin in md5 = 21232f297a57a5a743894a0e4a801fc3 
				 */
				List<%classDAO%> list%classDAO% = daoFactory.get%classDAO%Dao().findAllSQL(null, "from %classDAO% where %fieldUsername% = :username and %fieldPassword% = :password", 0, 0, sqlParameters);
				if (list%classDAO% != null && list%classDAO%.size() > 0) {
					%instanceDTO% = new %classDTO%();
					%classDAO% %instanceDAO% = list%classDAO%.get(0);
%fieldsList%
					session.setAttribute("%instanceDAO%", %instanceDAO%);

				}				
				daoFactory.commit();
			}		
		} catch (Exception e) {
			e.printStackTrace();
			%instanceDTO% = null;
		}
		if (daoFactory != null) {
			daoFactory.close();	
		}	
		return %instanceDTO%;
	}
	
	@Override
	public %classDTO% logged() {
		%classDTO% %instanceDTO% = null;
		try {
			iniSession();
			if (session.getAttribute("%instanceDAO%") != null) {
				%instanceDTO% = new %classDTO%();
				%classDAO% %instanceDAO% = (%classDAO%) session.getAttribute("%instanceDAO%");
%fieldsList%				
			}			
		} catch (Exception e) {
			e.printStackTrace();
			%instanceDTO% = null;
		}
		return %instanceDTO%;
	}

	@Override
	public void logout() {
		iniSession();
		session.setAttribute("%instanceDAO%", null);		
	}
	
}
