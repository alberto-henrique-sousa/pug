/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client;

public class ConfigFormPug {
	
	private boolean title = true;
	private boolean grid = true;
	private boolean form = true;
	private boolean order = true;
	private boolean add = true;
	private boolean edit = true;
	private boolean delete = true;
	private boolean pagination = true;
	private boolean popupSearch = false;
	
	public boolean isTitle() {
		return title;
	}
	
	public void setTitle(boolean title) {
		this.title = title;
	}
	
	public boolean isGrid() {
		return grid;
	}
	
	public void setGrid(boolean grid) {
		this.grid = grid;
	}
	
	public boolean isForm() {
		return form;
	}
	
	public void setForm(boolean form) {
		this.form = form;
	}
	
	public boolean isOrder() {
		return order;
	}
	
	public void setOrder(boolean order) {
		this.order = order;
	}
	
	public boolean isAdd() {
		return add;
	}
	
	public void setAdd(boolean add) {
		this.add = add;
	}
	
	public boolean isEdit() {
		return edit;
	}
	
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	public boolean isDelete() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	public boolean isPagination() {
		return pagination;
	}
	
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public boolean isPopupSearch() {
		return popupSearch;
	}

	public void setPopupSearch(boolean popupSearch) {
		this.popupSearch = popupSearch;
	}
	
}
