package com.databridgeproject.service;


import java.util.Date;
import java.util.List;

import com.databridgeproject.domain.Room;
import com.databridgeproject.dto.RoomForm;


/**
 * Service Layer Interface exposing business methods for
 * accessing and manipulating Room entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface RoomService {
	
	/**
	 * Add a new Hotel entity to the datastore.
	 * 
	 */
	public void saveRoom(RoomForm roomForm);
	
	/**
	 * Fetch Hotel entity using its hotelId.
	 * 
	 */
	public RoomForm getRoomFormById(Integer roomId);
	
	/**
	 * Remove Room entity using its roomId.
	 * 
	 */
	public void removeRoom(Integer roomId);
	
	public boolean isRoomNumberUniqueInHotel(RoomForm roomForm);
	
	/**
	 * Fetch list of all Room entities.
	 * 
	 */
	public List<Room> getRoomList();
	
	/**
	 * Fetch list of all Room entities using the hotelId of the
	 * Hotel entity these rooms are part of.
	 * 
	 */
	public List<Room> getRoomListByHotelId(Integer hotelId);
	
	/**
	 * Fetch list of Room entities that are free (not booked) between the checkin and checkout
	 * dates (inclusive).
	 * 
	 */
	public List<Room> getAvailableRoomListFromHotel(Integer hotelId, Date checkin, Date checkout);
	
	/**
	 * Fetch list of all Room entities whose roomId is part of roomIdList and are part of the
	 * Hotel entity identified by hotelId.
	 * 
	 */
	public List<Room> getAvailableRoomListInHotel(Integer hotelId, List<Integer> roomIds);
}
