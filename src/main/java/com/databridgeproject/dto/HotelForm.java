package com.databridgeproject.dto;

import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * DTO class that contains form data for
 * adding and editing a Hotel entity.
 * 
 * @author Dorian Haxhiaj
 *
 */
public class HotelForm {

	/**
	 * ID of the hotel.
	 * When the form is adding a new hotel, this ID is null;
	 * while when the form is editing an existing hotel, this
	 * ID has the value of the id of the existing hotel.
	 * 
	 */
	private Integer hotelId;
	
	/**
	 * Name of the hotel.
	 */
	@NotEmpty(message = "Please enter the name of the hotel.")
	private String hotelName;
	
	/**
	 * Rating of the hotel in stars. 
	 */
	@NotNull(message = "Please select a rating for the hotel.")
	@Min(value = 1, message = "Please select a rating from 1 to 5 stars.")
	@Max(value = 5, message = "Please select a rating from 1 to 5 stars.")
	private Integer hotelStars;
	
	/**
	 * List of IDs for the hotel services offered by the hotel.
	 */
	private Set<Integer> hotelServiceIdList;


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
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}


	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}


	/**
	 * @return the hotelStars
	 */
	public Integer getHotelStars() {
		return hotelStars;
	}


	/**
	 * @param hotelStars the hotelStars to set
	 */
	public void setHotelStars(Integer hotelStars) {
		this.hotelStars = hotelStars;
	}


	/**
	 * @return the hotelServiceIdList
	 */
	public Set<Integer> getHotelServiceIdList() {
		return hotelServiceIdList;
	}


	/**
	 * @param hotelServiceIdList the hotelServiceIdList to set
	 */
	public void setHotelServiceIdList(Set<Integer> hotelServiceIdList) {
		this.hotelServiceIdList = hotelServiceIdList;
	}
}
