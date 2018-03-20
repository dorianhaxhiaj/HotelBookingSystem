package com.databridgeproject.service;

import java.util.List;

import com.databridgeproject.domain.HotelService;
import com.databridgeproject.dto.HotelServiceForm;

/**
 * Service Layer Interface exposing business methods for
 * accessing and manipulating HotelService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface HotelserviceService {
	
	/**
	 * Add a new HotelService entity to the datastore.
	 * 
	 */
	public void saveHotelService(HotelServiceForm hotelServiceForm);
	
	
	/**
	 * Fetch HotelService entity using its hotelServiceId.
	 * 
	 */
	public HotelService getHotelServiceById(Integer hotelServiceId);
	
	/**
	 * Remove HotelService using its hotelServiceId.
	 * 
	 */
	public void removeHotelService(Integer hotelServiceId);
	
	/**
	 * Fetch list of all HotelService entities.
	 * 
	 */
	public List<HotelService> getHotelServiceList();
}
