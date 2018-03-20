package com.databridgeproject.repository.impl;


import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Room;
import com.databridgeproject.repository.RoomDao;


/**
 * Data Access Object Implementation for
 * accessing and manipulating Room entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Repository
@Transactional
public class RoomDaoImpl implements RoomDao {
	
	
	/**
	 * Used for logging dao operations.
	 * 
	 */
	private static final Logger logger = Logger.getLogger(RoomDaoImpl.class);

	
	/**
	 * Factory used for retrieving a session object.
	 * 
	 */
	@Autowired
	private SessionFactory sessionFactory;

	
	
	/**
	 * Persist Room entity to datastore.
	 *  
	 */
	@Override
	public void saveRoom(Room room) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(room);
		
		logger.info("Room saved successfully, Room Details = " + room);
	}
	
	
	/**
	 * Fetch Room entity from datastore using its roomId.
	 * 
	 */
	@Override
	public Room getRoomById(Integer roomId) {
		
		Session session = sessionFactory.getCurrentSession();
		Room fetchedRoom = session.load(Room.class, roomId);
		
		logger.info("Room loaded successfully, Room Details = " + fetchedRoom);
		
		return fetchedRoom;
	}
	
	
	/**
	 * Remove Room entity from datastore using its roomId.
	 * 
	 */
	@Override
	public void removeRoom(Integer roomId) {
		
		Session session = sessionFactory.getCurrentSession();
		Room roomToDelete = session.load(Room.class, roomId);
	
		if(null != roomToDelete) {
			session.delete(roomToDelete);
		}
		
		logger.info("Room deleted successfully, Room Details = " + roomToDelete);
	}
	
	
	@Override
	public boolean isRoomNumberUniqueInHotel(Integer roomId, Integer roomNumber, Integer hotelId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		if (roomNumber == null || hotelId == null)
			return false;
		
		TypedQuery<Long> countQuery;
		
		if (roomId == null) {
			
			countQuery = session.createQuery(
					"SELECT COUNT(r) FROM Room r WHERE r.roomNumber = :roomNumber AND r.hotelId = :hotelId",
					Long.class);
			countQuery.setParameter("roomNumber", roomNumber);
			countQuery.setParameter("hotelId", hotelId);
		}
		else {
			countQuery = session.createQuery(
					"SELECT COUNT(r) FROM Room r WHERE r.roomId != :roomId AND r.roomNumber = :roomNumber AND r.hotelId = :hotelId",
					Long.class);
			countQuery.setParameter("roomId", roomId);
			countQuery.setParameter("roomNumber", roomNumber);
			countQuery.setParameter("hotelId", hotelId);
		}
		
		return countQuery.getSingleResult() == 0;
	}
	
	
	/**
	 * Fetch list of all Room entities from datastore.
	 * 
	 */
	@Override
	public List<Room> getRoomList() {
		
		Session session = sessionFactory.getCurrentSession();
	
		Query<Room> roomListQuery= session.createQuery("from Room", Room.class);
		List<Room> roomList= roomListQuery.getResultList();
		
		return roomList;
	}
	
	
	/**
	 * Fetch list of all Room entities parting to a Hotel entity,
	 * identified by its hotelId.
	 * 
	 */
	@Override
	public List<Room> getRoomListByHotelId(Integer hotelId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Room> roomListQuery = session.createQuery(
				"SELECT r FROM Room r WHERE r.hotelId = :hotelId ORDER BY r.roomNumber", 
				Room.class);
		
		roomListQuery.setParameter("hotelId", hotelId);
		
		List<Room> roomList = roomListQuery.getResultList();
		
		return roomList;
	}
	
	
	
	/**
	 * Fetch list of all Room entities that are available (not booked) between the dates checkin and
	 * checkout (inclusive).
	 * 
	 */
	@Override
	public List<Room> getAvailableRoomListFromHotel(Integer hotelId, Date checkin, Date checkout) {
		
		Session session = this.sessionFactory.getCurrentSession();

		Query<Room> roomListQuery = session.createQuery(
				"SELECT r FROM Room r LEFT JOIN r.roomReservations res WHERE"
					+ " r.hotelId = :hotelId AND ("
					+ "	res IS NULL OR NOT ((checkin <= :checkin AND checkout >= :checkin) OR"
               		+ " (checkin <= :checkout AND checkout >= :checkout) OR"
       				+ " (checkin >= :checkin AND checkout <= :checkout)))", Room.class);
		
		roomListQuery.setParameter("hotelId", hotelId);
		roomListQuery.setParameter("checkin", checkin);
		roomListQuery.setParameter("checkout", checkout);
        
		return roomListQuery.getResultList();
	}
	
	
	
	/**
	 * Fetch list of all Room entities whose id is part of the list of roomIdList, and that
	 * are part of the Hotel entity identified by hotelId.
	 * 
	 */
	@Override
	public List<Room> getAvailableRoomListInHotel(Integer hotelId, List<Integer> roomIdList) {
		
		Session session = this.sessionFactory.getCurrentSession();
		  
		Query<Room> roomListQuery;
		roomListQuery = session.createQuery("FROM Room WHERE roomId IN (:roomIdList) AND hotelId = :hotelId", Room.class);
		roomListQuery.setParameterList("roomIdList", roomIdList);
		roomListQuery.setParameter("hotelId", hotelId);
        
		List<Room> roomList = roomListQuery.getResultList();
		
		return roomList;
	}
}
