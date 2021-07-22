/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %servicePackageDTO%;

import com.pugsource.gwt.library.client.dto.DTO;

public class ResultSaveDTO extends DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String error;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public boolean isError() {
		return this.error == null || this.error.trim().isEmpty();
	}

}