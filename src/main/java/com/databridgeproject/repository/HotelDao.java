package com.databridgeproject.repository;

import java.util.Date;
import java.util.List;

import com.databridgeproject.domain.Hotel;
import com.databridgeproject.domain.Pager;

/**
 * Data Access Object Interface for
 * accessing and manipulating Hotel entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface HotelDao {
	
	public void saveHotel(Hotel hotel);
	public Hotel getHotelById(Integer hotelId);
	public void removeHotel(Integer hotelId);
	public List<Hotel> getHotelList();
	public List<Hotel> getHotelList(Pager pager);
	public List<Hotel> getAvailableHotelList(Date checkin, Date checkout);
	public List<Hotel> getAvailableHotelList(Date checkin, Date checkout, Pager pager);
	public Long getNumberOfHotels();
	public Long getNumberOfAvailableHotels(Date checkin, Date checkout);
}
