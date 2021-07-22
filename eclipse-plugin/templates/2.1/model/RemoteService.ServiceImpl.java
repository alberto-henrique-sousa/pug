/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %serverPackage%;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;

import %servicePackage%.%serviceName%;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import %classDTOPackage%.%classDTO%;
import %classDTOPackage%.ResultSaveDTO;
import %packageDAO%.DaoFactory;
import %packageDAO%.%classDAO%;
import com.pugsource.util.Tools;

public class %serviceName%Impl extends RemoteServiceServlet implements %serviceName% {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
				where = "id = :id";
			}
			if (order == null)
				order = "";
			daoFactory.beginTransaction();			
			list%classDAO% = daoFactory.get%classDAO%Dao().findAll(toReturn, "%classDAO%", where, order, "", page, sizePage, sqlParameters);								
			for (%classDAO% %instanceDAO% : list%classDAO%) {
				%classDTO% %instanceDTO% = new %classDTO%();
%fieldsList%				
				%instanceDTO%.setSizeData(toReturn.get("recordCount") != null ? (Integer) toReturn.get("recordCount") : 0);
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
			String errorMsg = e.getMessage() != null ? e.getMessage() : "Erro interno!";
			errorMsg += " - " +(e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "");
			resultSaveDTO.setError(errorMsg);
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
			String errorMsg = e.getMessage() != null ? e.getMessage() : "Erro interno!";
			errorMsg += " - " +(e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "");
			resultSaveDTO.setError(errorMsg);
		}
		if (daoFactory != null) {
			daoFactory.close();	
		}	
		return resultSaveDTO;
	}
	
}
