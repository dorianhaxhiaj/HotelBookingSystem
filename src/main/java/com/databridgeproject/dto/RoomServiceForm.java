package com.databridgeproject.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class RoomServiceForm {

	private Integer roomServiceId;
	
	@NotEmpty(message = "Please enter the name of this service.")
	private String name;

	/**
	 * @return the roomServiceId
	 */
	public Integer getRoomServiceId() {
		return roomServiceId;
	}

	/**
	 * @param roomServiceId the roomServiceId to set
	 */
	public void setRoomServiceId(Integer roomServiceId) {
		this.roomServiceId = roomServiceId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
