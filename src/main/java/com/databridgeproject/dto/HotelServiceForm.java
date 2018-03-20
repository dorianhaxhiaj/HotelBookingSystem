package com.databridgeproject.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO class that contains form data for
 * adding and editing a HotelService entity.
 * 
 * @author Dorian Haxhiaj
 *
 */
public class HotelServiceForm {
	
	
	/**
	 * ID of the hotel service.
	 * When the form is adding a new hotel service, this ID is null;
	 * while when the form is editing an existing hotel service, this
	 * ID has the value of the id of the existing hotel service.
	 * 
	 */
	private Integer hotelServiceId;
	
	
	/**
	 * The name of the service.
	 */
	@NotEmpty(message = "Please enter a name for this hotel service.")
	private String name;

	
	public Integer getHotelServiceId() {
		return hotelServiceId;
	}

	public void setHotelServiceId(Integer hotelServiceId) {
		this.hotelServiceId = hotelServiceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
