package com.databridgeproject.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Entity for the hotel table,
 * which holds in the system information for a hotel.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Entity
@Table(name = "hotel")
public class Hotel {
	
	/**
	 * Auto-generated (by the database) primary key of this
	 * entity.
	 * 
	 */
	@Id
	@Column(name = "hotelid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer hotelId;
	
	
	/**
	 * The name of the hotel.
	 * 
	 */
	@NotEmpty
	@Column(name = "hotelname")
	private String hotelName;
	
	
	/**
	 * The rating in stars of the hotel.
	 * 
	 */
	@Column(name = "hotelstars")
	private Integer hotelStars;
	
	
	/**
	 * The set of hotel services that this hotel offers.
	 * 
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	        name = "hotel_hotelservice",
	        joinColumns = @JoinColumn(name = "hotelid"),
	        inverseJoinColumns = @JoinColumn(name = "hotelserviceid")
	)
	private Set<HotelService> hotelServices;
	
	
	@OneToMany(targetEntity = Room.class, mappedBy = "hotel", fetch = FetchType.LAZY)
	private Set<Room> rooms;
	
	
	// *****************************************************
	// List of Setters and Getters *************************
	// *****************************************************
	
	/**
	 * @return the hotelId
	 */
	public Integer getHotelId() {
		return hotelId;
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
	 * @return the hotelServices
	 */
	public Set<HotelService> getHotelServices() {
		return hotelServices;
	}


	/**
	 * @param hotelServices the hotelServices to set
	 */
	public void setHotelServices(Set<HotelService> hotelServices) {
		this.hotelServices = hotelServices;
	}


	/**
	 * @return the rooms
	 */
	public Set<Room> getRooms() {
		return rooms;
	}


	/**
	 * @param rooms the rooms to set
	 */
	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
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
	            append(this.getHotelName()).
	            append(this.getHotelStars()).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Hotel))
            return false;
        if (obj == this)
            return true;

        Hotel other = (Hotel) obj;
        
        return new EqualsBuilder().
            append(this.getHotelId(), other.getHotelId()).
            append(this.getHotelName(), other.getHotelName()).
            append(this.getHotelStars(), other.getHotelStars()).
            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("Hotel [hotelId = %d, hotelName = %s, hotelStars = %s]",
				this.getHotelId(), this.getHotelName(), this.getHotelStars());
	}
}
