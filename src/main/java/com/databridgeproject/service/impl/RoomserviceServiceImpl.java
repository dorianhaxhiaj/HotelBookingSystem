package com.databridgeproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.RoomService;
import com.databridgeproject.dto.RoomServiceForm;
import com.databridgeproject.repository.RoomServiceDao;


/**
 * Service Layer Implementation exposing business methods for
 * accessing and manipulating RoomService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Service
@Transactional
public class RoomserviceServiceImpl 
	implements com.databridgeproject.service.RoomserviceService {

	
	/**
	 * Data Access Object Interface for RoomService entity.
	 * 
	 */
	@Autowired
	RoomServiceDao roomServiceDao;


	
	
	/**
	 * Add a new RoomService entity to the datastore.
	 * 
	 */
	@Override
	public void saveRoomService(RoomServiceForm roomServiceForm) {
		
		RoomService roomService;
		
		if (roomServiceForm.getRoomServiceId() == null) {
			roomService = new RoomService();
		}
		else {
			roomService = roomServiceDao.getRoomServiceById(roomServiceForm.getRoomServiceId());
			
			if (roomService == null)
				return;
		}
		
		roomService.setName(roomServiceForm.getName());
		
		roomServiceDao.saveRoomService(roomService);
	}

	
	/**
	 * Fetch RoomServuce entity using its roomServiceId.
	 * 
	 */
	@Override
	public RoomService getRoomServiceById(Integer roomServiceId) {
		
		return roomServiceDao.getRoomServiceById(roomServiceId);
	}

	
	/**
	 * Remove RoomService entity using its roomServiceId.
	 * 
	 */
	@Override
	public void removeRoomService(Integer roomServiceId) {
		
		roomServiceDao.removeRoomService(roomServiceId);
	}

	
	/**
	 * Fetch list of all RoomService entities.
	 * 
	 */
	@Override
	public List<RoomService> getRoomServiceList() {
	
		return roomServiceDao.getRoomServiceList();
	}
}
