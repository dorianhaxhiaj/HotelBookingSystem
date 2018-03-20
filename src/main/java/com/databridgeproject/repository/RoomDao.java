package com.databridgeproject.repository;

import java.util.Date;
import java.util.List;

import com.databridgeproject.domain.Room;

/**
 * Data Access Object Interface for
 * accessing and manipulating Room entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface RoomDao {
	
	public void saveRoom(Room room);
	public Room getRoomById(Integer roomId);
	public void removeRoom(Integer roomId);
	public boolean isRoomNumberUniqueInHotel(Integer roomId, Integer roomNumber, Integer hotelId);
	public List<Room> getRoomList();
	public List<Room> getRoomListByHotelId(Integer hotelId);
	public List<Room> getAvailableRoomListFromHotel(Integer hotelId, Date checkin, Date checkout);
	public List<Room> getAvailableRoomListInHotel(Integer hotelId, List<Integer> roomIdList);
}

