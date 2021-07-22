/*******************************************************************************
 * Generator by Pug Plugin %version%
 *******************************************************************************/
package %packageBeanUtil%;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import %servicePackageDAO%.DaoFactory;

@ManagedBean(eager = true)
@SessionScoped
public class DaoFactoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 968270044078862356L;
	private DaoFactory daoFactory;
	
	@PostConstruct
	public void construct() {
    	if (this.daoFactory == null) {
    		this.daoFactory = new DaoFactory();
    	}
	}
	
	@PreDestroy
	public void destroy() {
		if (this.daoFactory != null) {
			daoFactory.close();
		}	
	}

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}