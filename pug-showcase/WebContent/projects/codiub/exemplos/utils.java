import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

import com.pugsource.bean.Utils;

public class utils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public utils() {

	}
    
	public void callConsoleJS() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("console.log('callConsoleJS');");		
	}	    

}