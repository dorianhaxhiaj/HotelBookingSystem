package com.databridgeproject.domain;

/**
 * Holds pagination information that is used
 * to limit the number of entities being fetched
 * in the data store.
 * 
 * @author Dorian Haxhiaj
 *
 */
public class Pager {

	/**
	 * The size of the page; in other
	 * words the number of entity items that
	 * are to be fetched in a query.
	 * 
	 */
	private int pageSize;
	
	
	/**
	 * The current page number to be fetched.
	 * 
	 */
	private int currentPage;

	
	public Pager(int pageSize) {
		
		this.currentPage = 1;
		this.pageSize = pageSize;
	}
	
	public Pager(int currentPage, int pageSize) {
	
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
