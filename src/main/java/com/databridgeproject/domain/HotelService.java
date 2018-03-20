package com.databridgeproject.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity for hotelservice table,
 * hold information about a service that can
 * be offered by a hotel, such as parking or
 * laundry etc.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Entity
@Table(name = "hotelservice")
public class HotelService {
	
	
	/**
	 * Auto-generated (from the database) primary key for
	 * this entity.
	 * 
	 */
	@Id
	@Column(name = "hotelserviceid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer hotelServiceId;
	
	
	/**
	 * Name of the service.
	 * 
	 */
	@NotEmpty(message = "Please enter the service name.")
	@Column(name = "name")
	private String name;

	
	/**
	 * List of hotels that use this service.
	 * 
	 */
	@ManyToMany(mappedBy = "hotelServices", fetch = FetchType.LAZY)
	public Set<Hotel> hotels;
	
	
	
	// *****************************************************
	// List of Setters and Getters *************************
	// *****************************************************
	
	/**
	 * @return the hotelServiceId
	 */
	public Integer getHotelServiceId() {
		return hotelServiceId;
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


	/**
	 * @return the hotels
	 */
	public Set<Hotel> getHotels() {
		return hotels;
	}


	/**
	 * @param hotels the hotels to set
	 */
	public void setHotels(Set<Hotel> hotels) {
		this.hotels = hotels;
	}
	
	// *****************************************************
	// END of Setters and Getters **************************
	// *****************************************************



	/**
	 * Calculates the hash code for an instance of this class.
	 * 
	 */
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 31).
	            append(this.getName()).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof HotelService))
            return false;
        if (obj == this)
            return true;

        HotelService other = (HotelService) obj;
        
        return new EqualsBuilder().
            append(this.getHotelServiceId(), other.getHotelServiceId()).
            append(this.getName(), other.getName()).
            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("HotelService [hotelServiceId = %d, name = %s]", this.getHotelServiceId(), this.getName());
	}
}
