package com.databridgeproject.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.RoomService;
import com.databridgeproject.repository.RoomServiceDao;


/**
 * Data Access Object Implementation for
 * accessing and manipulating RoomService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Repository
@Transactional
public class RoomServiceDaoImpl implements RoomServiceDao {
	
	
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
	 * Persist RoomService entity to datastore.
	 *  
	 */
	@Override
	public void saveRoomService(RoomService roomService) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(roomService);
		
		logger.info("Room Service saved successfully, Room Details = " + roomService);	
	}

	
	/**
	 * Fetch RoomService entity from datastore using its roomServiceId.
	 * 
	 */
	@Override
	public RoomService getRoomServiceById(Integer  roomServiceId) {
		
		Session session = sessionFactory.getCurrentSession();
		RoomService fetchedRoomService = session.load(RoomService.class, roomServiceId);
		
		logger.info("Room Services loaded successfully, Room Details=" + fetchedRoomService);
		
		return fetchedRoomService;
	}

	
	/**
	 * Remove RoomService entity from datastore using its roomServiceId.
	 * 
	 */
	@Override
	public void removeRoomService(Integer roomServiceId) {
		
		Session session = sessionFactory.getCurrentSession();
		RoomService roomServiceToDelete = session.load(RoomService.class, roomServiceId);
		
		if (null != roomServiceToDelete){
			session.delete(roomServiceToDelete);
		}
		
		logger.info("RoomService deleted successfully, RoomService Details = " + roomServiceToDelete);
	}

	
	/**
	 * Fetch list of all RoomService entities from datastore.
	 * 
	 */
	@Override
	public List<RoomService> getRoomServiceList() {
		
		Session session = sessionFactory.getCurrentSession();
		TypedQuery<RoomService> roomServiceListQuery = 
				session.createQuery("from RoomService", RoomService.class);
		
		List<RoomService> roomServiceList= roomServiceListQuery.getResultList();
		
		return roomServiceList;
	}
	
	@Override
	public List<RoomService> getRoomServiceListByIds(Collection<Integer> ids) {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<RoomService> roomServiceListQuery = session.createQuery(
						"FROM RoomService rs WHERE roomServiceId IN (:ids) ORDER BY rs.roomServiceId", 
						RoomService.class);
		
		roomServiceListQuery.setParameter("ids", ids);
		
		List<RoomService> roomServiceList= roomServiceListQuery.getResultList();
		
		return roomServiceList;
	}
}
