package com.databridgeproject.service;


import java.util.Date;

import com.databridgeproject.dto.HotelForm;
import com.databridgeproject.dto.HotelPage;
import com.databridgeproject.error.ResourceNotFoundException;


/**
 * Service Layer Interface exposing business methods for
 * accessing and manipulating Hotel entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface HotelService {
	
	/**
	 * Persist a Hotel entity to the datastore.
	 * 
	 */
	public void saveHotel(HotelForm hotelForm);
	
	
	/**
	 * Fetch Hotel entity using its hotelId.
	 * 
	 */
	public HotelForm getHotelFormById(Integer hotelId) throws ResourceNotFoundException;
	
	/**
	 * Remove Hotel from datastore using its hotelId.
	 * 
	 */
	public void removeHotel(Integer hotelId);
	
	/**
	 * Fetch list of all hotels from datastore.
	 * 
	 */
	public HotelPage getHotelPage(Integer currentPage) throws ResourceNotFoundException;
	
	/**
	 * Fetch list of all Hotel entities who have at a room free, between the checkin date
	 * and checkout date.
	 * 
	 */
	public HotelPage getAvailableHotelPage(Date checkin, Date checkout, Integer currentPage) throws ResourceNotFoundException;
	
}
