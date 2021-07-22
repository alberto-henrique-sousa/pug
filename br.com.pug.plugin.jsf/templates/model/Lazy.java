/*******************************************************************************
 * Generator by Pug Plugin %version%
 *******************************************************************************/
package %packageLazy%;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StandardBasicTypes;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import %packageBeanUtil%.DaoFactoryBean;
import %packageDao%.%classDAO%;

import com.pugsource.util.Tools;

public class %classDAO%Lazy extends LazyDataModel<%classDAO%> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<%classDAO%> list%classDAO%;
	private DaoFactoryBean daoFactoryBean;
	
	public %classDAO%Lazy(DaoFactoryBean daoFactoryBean) {
		super();
		this.daoFactoryBean = daoFactoryBean;
	}
		
	@Override
	public List<%classDAO%> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		try {
			Map<String, Object> toReturn = new HashMap<String, Object>();
			ArrayList<ArrayList<Object>> sqlParameters = new ArrayList<ArrayList<Object>>();

			String where = "";
						
			for (Map.Entry<String, Object> entry : filters.entrySet()) {
			    if (entry.getKey().equals("id")) {
					Tools.setParametersQuery("id", Integer.valueOf((String) entry.getValue()), StandardBasicTypes.INTEGER, sqlParameters);
					where += (!where.trim().isEmpty() ? " and " : "") +  "id = :id";			    	
			    } else if (entry.getKey().equals("nome")) {
					Tools.setParametersQuery("nome", "%" + ((String) entry.getValue()).toLowerCase() + "%", StandardBasicTypes.STRING, sqlParameters);
					where += (!where.trim().isEmpty() ? " and " : "") +  "lower(nome) like :nome";			    	
			    }			    	
			}
			if (sortField == null || sortField.isEmpty())
				sortField = "id";
			String orderDir = sortOrder.equals(SortOrder.DESCENDING) ? " desc" : "";				
						
			if (first > 0)
				first = first/pageSize;
			
			this.daoFactoryBean.getDaoFactory().beginTransaction();			
			list%classDAO% = this.daoFactoryBean.getDaoFactory().get%classDAO%Dao().findAll(toReturn, "%classDAO%", where, sortField, orderDir, first, pageSize, sqlParameters);
			this.daoFactoryBean.getDaoFactory().commit();
			
			setRowCount((Integer) toReturn.get("recordCount"));
			setPageSize(pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list%classDAO%;
	}

	@Override
	public %classDAO% getRowData(String rowKey) {
		for (%classDAO% %instanceDAO% : list%classDAO%) {
			if (rowKey.equals(String.valueOf(%instanceDAO%.getId())))
				return %instanceDAO%;
		}
		return null;
	}

	@Override
	public Object getRowKey(%classDAO% %instanceDAO%) {
		return %instanceDAO%.getId();
	}

	@Override
	public void setRowIndex(int rowIndex) {
		if (rowIndex == -1 || getPageSize() == 0)
			super.setRowIndex(-1);
		else
			super.setRowIndex(rowIndex % getPageSize());
	}
	
}