package com.databridgeproject.repository;

import java.util.Collection;
import java.util.List;

import com.databridgeproject.domain.RoomService;


/**
 * Data Access Object Interface for
 * accessing and manipulating RoomService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface RoomServiceDao {
	
	public void saveRoomService(RoomService newRoomService);
	public RoomService getRoomServiceById(Integer roomServiceId);
	public void removeRoomService(Integer roomServiceId);
	public List<RoomService> getRoomServiceList();
	public List<RoomService> getRoomServiceListByIds(Collection<Integer> ids);
}
