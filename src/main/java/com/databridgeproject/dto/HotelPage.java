package com.databridgeproject.dto;

import java.util.List;

import com.databridgeproject.domain.Hotel;

/**
 * DTO representing a page of hotel data for display,
 * as part of the view model implementation of pagination.
 * 
 * @author Dorian Haxhiaj
 *
 */
public class HotelPage {

	/**
	 * The default size of the page, representing the
	 * number of hotels to be displayed on the page.
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
	 * The number of hotels to be displayed on the page.
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
	 * List of hotels that this page contains.
	 * 
	 */
	private List<Hotel> hotelList;
	
	
	/**
	 * The entire number of hotels contained in the
	 * datastore.
	 * 
	 */
	private long numberOfHotels;

	
	
	/**
	 * Zero-argument Constructor for HotelPage
	 * Sets the defaults.
	 * 
	 */
	public HotelPage() {
		
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
	 * @return the page
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param page the page to set
	 */
	public void setCurrentPage(int page) {
		this.currentPage = page;
	}

	/**
	 * @return the hotelList
	 */
	public List<Hotel> getHotelList() {
		return hotelList;
	}

	/**
	 * @param hotelList the hotelList to set
	 */
	public void setHotelList(List<Hotel> hotelList) {
		this.hotelList = hotelList;
	}
	
	/**
	 * @return the numberOfHotels
	 */
	public long getNumberOfHotels() {
		return numberOfHotels;
	}

	/**
	 * @param numberOfHotels the numberOfHotels to set
	 */
	public void setNumberOfHotels(long numberOfHotels) {
		this.numberOfHotels = numberOfHotels;
	}

	
	/**
	 * Get the first displayed page number, in other words it
	 * get the first page the user can get through one click.
	 * 
	 * @return
	 * The first page number, which can be accessed through a
	 * direct click.
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
	 * Gets the last page of the entire hotel catalog.
	 * 
	 * @return
	 * The last page of the entire hotel catalog.
	 * 
	 */
	public Integer getLastPage() {
		
		Integer lastCompletePage = (int) numberOfHotels / pageSize;
		
		return (numberOfHotels % pageSize == 0) ? lastCompletePage : lastCompletePage + 1;
	}
}
