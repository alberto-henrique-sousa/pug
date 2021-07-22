/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %servicePackage%;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import %classDTOPackage%.%classDTO%;
import %classDTOPackage%.ResultSaveDTO;

public interface %serviceName%Async {
	
	void listAll(Integer id, String order, int page, int sizePage,
			AsyncCallback<ArrayList<%classDTO%>> callback);
	
	void save(%classDTO% %instanceDTO%,
			AsyncCallback<ResultSaveDTO> callback);
	
	void delete(ArrayList<%classDTO%> list%classDTO%,
			AsyncCallback<ResultSaveDTO> callback);
	
}
