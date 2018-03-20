package com.databridgeproject.dto;

import java.util.List;

import com.databridgeproject.domain.User;

/**
 * DTO representing a page of user data for display,
 * as part of the view model implementation of pagination.
 * 
 * @author Dorian Haxhiaj
 *
 */
public class UserPage {

	/**
	 * The default size of the page, representing the
	 * number of users to be displayed on the page.
	 * 
	 */
	public static final int DEFAULT_PAGE_SIZE = 6;
	
	
	/**
	 * The default size of the pages to be displayed,
	 * representing the number of pages the user can
	 * see and navigate to with one click.
	 * 
	 */
	public static final int DEFAULT_PAGES_DISPLAYED = 6;
	
	
	/**
	 * The number of users to be displayed on the page.
	 * 
	 */
	private int pageSize;
	
	
	/**
	 * The number of pages the user can
	 * see and navigate to with one click.
	 * 
	 */
	private int pagesDisplayed;
	
	
	/**
	 * The current page number.
	 * 
	 */
	private int currentPage;
	
	
	/**
	 * List of users that this page contains.
	 * 
	 */
	private List<User> userList;
	
	
	/**
	 * The entire number of users contained in the
	 * datastore.
	 * 
	 */
	private long numberOfUsers;
	
	
	
	/**
	 * Zero-argument Constructor for UserPage
	 * Sets the defaults.
	 * 
	 */
	public UserPage() {
		
		pageSize = DEFAULT_PAGE_SIZE;
		pagesDisplayed = DEFAULT_PAGES_DISPLAYED;
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
	 * @return the pagesDisplayed
	 */
	public int getPagesDisplayed() {
		return pagesDisplayed;
	}

	/**
	 * @param pagesDisplayed the pagesDisplayed to set
	 */
	public void setPagesDisplayed(int pagesDisplayed) {
		this.pagesDisplayed = pagesDisplayed;
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

	/**
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * @param numberOfHotels the numberOfHotels to set
	 */
	public long getNumberOfUsers() {
		return numberOfUsers;
	}
	
	/**
	 * @param numberOfHotels the numberOfHotels to set
	 */
	public void setNumberOfUsers(long numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	
	/**
	 * Get the first displayed page number, in other words it
	 * gets the first page the user can get to through one click.
	 * 
	 * @return
	 * The first page number, which can be accessed though a direct
	 * click.
	 * 
	 */
	public Integer getStartPage() {
		return (currentPage / pagesDisplayed) * pagesDisplayed + 1;
	}
	
	
	/**
	 * Gets the last displayed page number, in other words it
	 * gets the last page the user can get to though one click.
	 * 
	 * @return
	 * The last page number, which can be accessed through a
	 * direct click.
	 * 
	 */
	public Integer getEndPage() {
		
		Integer endPage = getStartPage() + pagesDisplayed - 1;
		
		return (endPage > getLastPage()) ? getLastPage() : endPage;
	}
	
	
	/**
	 * Gets the last page of the entire user catalog.
	 *  
	 * @return
	 * The number of the last page of the entire user catalog.
	 * 
	 */
	public Integer getLastPage() {
		
		Integer lastCompletePage = (int) numberOfUsers / pageSize;
		
		return (numberOfUsers % pageSize == 0) ? lastCompletePage : lastCompletePage + 1;
	}
}
