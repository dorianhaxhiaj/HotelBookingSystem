package com.databridgeproject.service;

import java.util.List;

import com.databridgeproject.domain.RoomService;
import com.databridgeproject.dto.RoomServiceForm;


/**
 * Service Layer Interface exposing business methods for
 * accessing and manipulating RoomService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface RoomserviceService {
	
	/**
	 * Add a new RoomService entity to the datastore.
	 * 
	 */
	public void saveRoomService(RoomServiceForm roomServiceForm);
	
	/**
	 * Fetch RoomServuce entity using its roomServiceId.
	 * 
	 */
	public RoomService getRoomServiceById(Integer roomServiceId);
	
	/**
	 * Remove RoomService entity using its roomServiceId.
	 * 
	 */
	public void removeRoomService(Integer roomServiceId);
	
	/**
	 * Fetch list of all RoomService entities.
	 * 
	 */
	public List<RoomService> getRoomServiceList();
}
