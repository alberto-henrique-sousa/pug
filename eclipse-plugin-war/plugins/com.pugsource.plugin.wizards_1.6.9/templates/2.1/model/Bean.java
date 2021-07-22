/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %servicePackage%;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import %packageBeanUtil%.BaseBean;
import %packageDao%.%classDAO%;
import %packageLazy%.%classDAO%Lazy;

import com.pugsource.bean.Utils;

@ManagedBean
@ViewScoped
public class %serviceName% extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4675379738650327118L;
	private LazyDataModel<%classDAO%> %instanceDAO%Lazy;
	private %classDAO% %instanceDAO%;
	// list for Combobox
	//private List<Foreign> listForeign;
	
	@Override
	protected void initEntity() {
		%instanceDAO% = new %classDAO%();
	}
	
	@Override
	protected String labelEntity() {
		return "%classDAO%";
	}	
	
	@PostConstruct
	public void init() {
		try {
			/*this.daoFactoryBean.getDaoFactory().beginTransaction();		
			listForeign = this.daoFactoryBean.getDaoFactory().getForeignDao().findAll(null, "Foreign", "", "nome", "", 0, 0);
			this.daoFactoryBean.getDaoFactory().commit();*/					
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage() != null ? e.getMessage() : "Erro interno!";
			errorMsg += " - " +(e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "");
			Utils.addFacesMessage("message_failed_created", FacesMessage.SEVERITY_ERROR, errorMsg);
		}
	}
	
	public void save() {
		try {
			String message = "";
			this.daoFactoryBean.getDaoFactory().beginTransaction();
			if (%instanceDAO%.getId() != null && %instanceDAO%.getId() > 0) {
				this.daoFactoryBean.getDaoFactory().get%classDAO%Dao().merge(%instanceDAO%);
				message = "message_successfully_updated";
			} else {
				this.daoFactoryBean.getDaoFactory().get%classDAO%Dao().save(%instanceDAO%);
				message = "message_successfully_created";
			}
			this.daoFactoryBean.getDaoFactory().commit();
			Utils.addFacesMessage(message, labelEntity());
	        setForm(false);
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage() != null ? e.getMessage() : "Erro interno!";
			errorMsg += " - " +(e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "");
			Utils.addFacesMessage("message_failed_created", FacesMessage.SEVERITY_ERROR, errorMsg);
		}
	}
	
	public void delete() {
		try {
			if (%instanceDAO% != null) {
				this.daoFactoryBean.getDaoFactory().beginTransaction();
				daoFactoryBean.getDaoFactory().get%classDAO%Dao().remove(%instanceDAO%);
				this.daoFactoryBean.getDaoFactory().commit();
				Utils.addFacesMessage("message_successfully_deleted", labelEntity());
				%instanceDAO% = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorMsg = e.getMessage() != null ? e.getMessage() : "Erro interno!";
			errorMsg += " - " +(e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "");
			Utils.addFacesMessage("message_failed_deleted", FacesMessage.SEVERITY_ERROR, errorMsg);
		}
	}	
	
	public LazyDataModel<%classDAO%> get%classDAO%Lazy() {
		if (%instanceDAO%Lazy == null) {
			%instanceDAO%Lazy = new %classDAO%Lazy(daoFactoryBean);
		}
		return %instanceDAO%Lazy;
	}

	public void set%classDAO%Lazy(LazyDataModel<%classDAO%> %instanceDAO%Lazy) {
		this.%instanceDAO%Lazy = %instanceDAO%Lazy;
	}

	public %classDAO% get%classDAO%() {
		return %instanceDAO%;
	}

	public void set%classDAO%(%classDAO% %instanceDAO%) {
		this.%instanceDAO% = %instanceDAO%;
	}	
	
	// Replace Foreign and foreign
	public void dialogForeign() {
		Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentHeight", 500);		
        options.put("contentWidth", 700);
		RequestContext.getCurrentInstance().openDialog("dialogforeign", options, null);
	}
	
	// Replace Foreign and foreign
	public void onSelectForeign(SelectEvent event) {
		//Foreign foreign = (Foreign) event.getObject();
		//%instanceDAO%.setForeign(foreign);
    }
	
	public void closeDialog() {
		RequestContext.getCurrentInstance().closeDialog(%instanceDAO%);
	}
	
	public String getTitle() {
		return labelEntity();
	}

	/*public List<Foreign> getListForeign() {
		return listForeign;
	}*/
	
}