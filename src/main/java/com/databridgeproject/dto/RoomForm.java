package com.databridgeproject.dto;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.databridgeproject.constraint.RoomNumberUniqueInHotel;

/**
 * DTO class that contains form data for
 * adding a new room or editing an existing room.
 * 
 * @author Dorian Haxhiaj
 *
 */
@RoomNumberUniqueInHotel(message = "This room number is already taken.")
public class RoomForm {
	
	
	/**
	 * ID of the room.
	 * When the form is adding a new room, this ID is null;
	 * while when the form is editing an existing room, this
	 * ID has the value of the id of the existing room.
	 * 
	 */
	private Integer roomId;
	
	@NotNull(message = "Room number is required.")
	@Min(value = 1, message = "Room Number must be bigger than 0.")
	private Integer roomNumber;
	
	@NotNull(message = "Room floor is required.")
	@Min(value = 1, message = "Room Number must be bigger than 0.")
	private Integer roomFloor;
	
	@NotEmpty(message = "Please select a room type.")
	private String roomType;
	
	@NotNull
	@Min(value = 1)
	private Integer hotelId;
	
	private Set<Integer> roomServiceIdList;

	
	/**
	 * @return the roomId
	 */
	public Integer getRoomId() {
		return roomId;
	}

	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	/**
	 * @return the roomNumber
	 */
	public Integer getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * @return the roomFloor
	 */
	public Integer getRoomFloor() {
		return roomFloor;
	}

	/**
	 * @param roomFloor the roomFloor to set
	 */
	public void setRoomFloor(Integer roomFloor) {
		this.roomFloor = roomFloor;
	}

	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	/**
	 * @return the hotelId
	 */
	public Integer getHotelId() {
		return hotelId;
	}

	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * @return the roomServiceIdList
	 */
	public Set<Integer> getRoomServiceIdList() {
		return roomServiceIdList;
	}

	/**
	 * @param roomServiceIdList the roomServiceIdList to set
	 */
	public void setRoomServiceIdList(Set<Integer> roomServiceIdList) {
		this.roomServiceIdList = roomServiceIdList;
	}
}
