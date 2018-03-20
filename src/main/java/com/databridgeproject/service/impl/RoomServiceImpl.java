package com.databridgeproject.service.impl;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Room;
import com.databridgeproject.domain.RoomService;
import com.databridgeproject.dto.RoomForm;
import com.databridgeproject.repository.HotelDao;
import com.databridgeproject.repository.ReservationDao;
import com.databridgeproject.repository.RoomDao;
import com.databridgeproject.repository.RoomServiceDao;


/**
 * Service Layer Implementation exposing business methods for
 * accessing and manipulating Room entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Service
@Transactional
public class RoomServiceImpl 
	implements com.databridgeproject.service.RoomService {
	
	
	/**
	 * Data Access Object Interface for Room entity.
	 * 
	 */
	@Autowired
	private RoomDao roomDao;
	
	
	/**
	 * Data Access Object Interface for Reservation entity.
	 * 
	 */
	@Autowired
	private ReservationDao reservationDao;
	
	
	/**
	 * Data Access Object Interface for RoomService entity.
	 * 
	 */
	@Autowired
	private RoomServiceDao roomServiceDao;
	
	@Autowired
	private HotelDao hotelDao;
	
	
	/**
	 * Add a new Hotel entity to the datastore.
	 * 
	 */
	@Override
	public void saveRoom(RoomForm roomForm) {
		
		Room room;
		
		if (roomForm.getRoomId() == null) {
			room = new Room();
		}
		else {
			room = roomDao.getRoomById(roomForm.getRoomId());
			
			if (room == null) 
				return;
		}
		
		room.setRoomNumber(roomForm.getRoomNumber());
		room.setRoomFloor(roomForm.getRoomFloor());
		room.setRoomType(roomForm.getRoomType());
		room.setHotel(hotelDao.getHotelById(roomForm.getHotelId()));
		room.setRoomServices(roomServiceDao.getRoomServiceListByIds(
				roomForm.getRoomServiceIdList()).stream().collect(Collectors.toSet()));
		roomDao.saveRoom(room);
	}
	
	
	/**
	 * Fetch Hotel entity using its hotelId.
	 * 
	 */
	@Override
	public RoomForm getRoomFormById(Integer roomId) {
		
		Room room = roomDao.getRoomById(roomId);
		
		if (room == null)
			return new RoomForm();
		
		RoomForm roomForm = new RoomForm();
		roomForm.setRoomId(room.getRoomId());
		roomForm.setRoomNumber(room.getRoomNumber());
		roomForm.setRoomFloor(room.getRoomFloor());
		roomForm.setRoomType(room.getRoomType());
		roomForm.setHotelId(room.getHotelId());
		roomForm.setRoomServiceIdList(
				room.getRoomServices().stream()
				.map(rs -> rs.getRoomServiceId())
				.collect(Collectors.toSet()));
		
		return roomForm;
	}
	
	
	/**
	 * Remove Room entity using its roomId.
	 * 
	 */
	@Override
	public void removeRoom(Integer roomId) {
		
	    reservationDao.removeReservationByRoomId(roomId);
	    Room roomToBeRemoved = roomDao.getRoomById(roomId);
	    roomToBeRemoved.setRoomServices(new HashSet<RoomService>());
	    
		roomDao.removeRoom(roomId);	
	}
	
	
	public boolean isRoomNumberUniqueInHotel(RoomForm roomForm) {
		
		Integer roomId = roomForm.getRoomId();
		Integer roomNumber = roomForm.getRoomNumber();
		Integer hotelId = roomForm.getHotelId();
		
		return roomDao.isRoomNumberUniqueInHotel(roomId, roomNumber, hotelId);
	}
	
	
	/**
	 * Fetch list of all Room entities.
	 * 
	 */
	@Override
	public List<Room> getRoomList() {
		
		return roomDao.getRoomList();
	}
	
	
	/**
	 * Fetch list of all Room entities using the hotelId of the
	 * Hotel entity these rooms are part of.
	 * 
	 */
	@Override
	public List<Room> getRoomListByHotelId(Integer hotelId) {
		
		return roomDao.getRoomListByHotelId(hotelId);
	}
	
	
	/**
	 * Fetch list of Room entities that are free (not booked) between the checkin and checkout
	 * dates (inclusive).
	 * 
	 */
	@Override
	public List<Room> getAvailableRoomListFromHotel(Integer hotelId, Date checkin, Date checkout) {
		
		return roomDao.getAvailableRoomListFromHotel(hotelId, checkin, checkout);
	}
	
	
	/**
	 * Fetch list of all Room entities whose roomId is part of roomIdList and are part of the
	 * Hotel entity identified by hotelId.
	 * 
	 */
	@Override
	public List<Room> getAvailableRoomListInHotel(Integer hotelId, List<Integer> roomIdList) {
		
		return roomDao.getAvailableRoomListInHotel(hotelId, roomIdList);
	}
}
