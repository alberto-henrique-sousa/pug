/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %packageBeanUtil%;

import javax.faces.bean.ManagedProperty;

import com.pugsource.bean.MessageFactory;

public abstract class BaseBean {

	@ManagedProperty(value="#{daoFactoryBean}")
	protected DaoFactoryBean daoFactoryBean;
	protected String titlePanelForm;
	protected boolean form;
	protected boolean view;
	
	protected abstract void initEntity();
	
	protected abstract String labelEntity();
	
	public void showCreate() {
		this.view = false;
		initEntity();
		titlePanelForm = MessageFactory.getMessage("label_create", labelEntity()).getDetail();
		setForm(true);
	}
	
	public void showEdit() {
		this.view = false;
		titlePanelForm = MessageFactory.getMessage("label_edit", labelEntity()).getDetail();
		setForm(true);
	}
	
	public void showView() {
		this.view = true;
		titlePanelForm = MessageFactory.getMessage("label_view", labelEntity()).getDetail();
		setForm(true);
	}
	
	public void showGrid() {
		this.view = false;
		setForm(false);
	}	
	
	public DaoFactoryBean getDaoFactoryBean() {
		return daoFactoryBean;
	}

	public void setDaoFactoryBean(DaoFactoryBean daoFactoryBean) {
		this.daoFactoryBean = daoFactoryBean;
	}

	public String getTitlePanelForm() {
		return titlePanelForm;
	}

	public void setTitlePanelForm(String titlePanelForm) {
		this.titlePanelForm = titlePanelForm;
	}

	public boolean isForm() {
		return form;
	}

	public void setForm(boolean form) {
		this.form = form;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}	
}