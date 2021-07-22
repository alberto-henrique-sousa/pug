/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client.grids;

public class PaginationPug {

	private int pageNum;
	private int pageCount;
	private int pageSize;

	public PaginationPug(int pageSize) {
		super();
		this.pageSize = pageSize;
		this.pageNum = 1;
		this.pageCount = 1;
	}

	public void calcPageCount(int recordTotal) {
		try {
			int division = recordTotal % this.pageSize;
			this.pageCount = recordTotal / this.pageSize;
			if (division > 0){
				this.pageCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	    	        		       			
	}	

	public boolean firstPage() {
		this.pageNum = 1;
		return true; 
	}

	public boolean priorPage() {
		if (this.pageNum -1 > 0)
		{
			this.pageNum--;
			return true;
		} else
			return false;
	}

	public boolean nextPage() {
		if (this.pageNum +1 <= this.pageCount)
		{
			this.pageNum++;
			return true;
		} else
			return false;
	}	

	public boolean lastPage() {
		this.pageNum = this.pageCount;
		return true;
	}

	public boolean goToPage(int pageNum) {
		if ((pageNum > 0) && (pageNum <= this.pageCount)) {
			this.pageNum = pageNum;
			return true;
		} else
			return false;
	}					

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
