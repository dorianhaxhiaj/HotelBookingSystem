package com.databridgeproject.repository;

import java.util.List;

import com.databridgeproject.domain.HotelService;


/**
 * Data Access Object Interface for
 * accessing and manipulating HotelService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface HotelServiceDao {
	
	public void saveHotelService(HotelService hotelService);
	public HotelService getHotelServiceById(Integer hotelServiceId);
	public void removeHotelService(Integer hotelServiceId);
	public List<HotelService> getHotelServiceList();
}
