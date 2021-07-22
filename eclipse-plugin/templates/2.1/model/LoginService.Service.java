/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %servicePackage%;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import %classDTOPackage%.%classDTO%;
import %classDTOPackage%.ResultSaveDTO;

@RemoteServiceRelativePath("%serviceName%")
public interface %serviceName% extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static %serviceName%Async instance;
		public static %serviceName%Async getInstance(){
			if (instance == null) {
				instance = GWT.create(%serviceName%.class);
			}
			return instance;
		}
	}
	
	public ArrayList<%classDTO%> listAll(Integer id, String order, int page, int sizePage);
	
	public ResultSaveDTO save(%classDTO% %instanceDTO%);
	
	public ResultSaveDTO delete(ArrayList<%classDTO%> list%classDTO%);
	
	public %classDTO% login(String username, String password);
	
	public %classDTO% logged();
	
	public void logout();	
	
}
