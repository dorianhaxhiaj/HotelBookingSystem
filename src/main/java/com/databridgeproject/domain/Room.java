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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity for room table,
 * hold information about a room in the system:
 * id, type, id of hotel that is part of, and list
 * of services that this room offers.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Entity
@Table(name = "room")
public class Room {
	
	/**
	 * Auto-generated (from the database) primary key of
	 * this entity.
	 * 
	 */
	@Id
	@Column(name = "roomid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomId;
	
	
	/**
	 * Identifying number of room inside its respective hotel.
	 *  
	 */
	@Column(name = "roomnumber")
	private Integer roomNumber;
	
	
	/**
	 * The type of the room, such as Single, Double etc.
	 * 
	 */
	@NotEmpty
	@Column(name = "roomtype")
	private String roomType;
	
	
	/**
	 * The floor number of the room
	 * 
	 */
	@NotNull
	@Column(name = "roomfloor")
	private Integer roomFloor;
	
	
	/**
	 * The id of the hotel, which this room is part of.
	 * 
	 */
	@Column(name = "hotelid", insertable = false, updatable = false)
	private Integer hotelId;
	
	
	/**
	 * The set of services this room offers, such as TV or Fridge etc.
	 * 
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	        name = "room_roomservice",
	        joinColumns = @JoinColumn(name = "roomid"),
	        inverseJoinColumns = @JoinColumn(name = "roomserviceid")
	)
	private Set<RoomService> roomServices;
	
	
	@ManyToOne
	@JoinColumn(name = "hotelid")
	private Hotel hotel;
	
	
	@OneToMany(targetEntity = Reservation.class, mappedBy = "room", fetch = FetchType.LAZY)
	private Set<Reservation> roomReservations;
	

	// *****************************************************
	// List of Setters and Getters *************************
	// *****************************************************
	
	/**
	 * @return the roomId
	 */
	public Integer getRoomId() {
		return roomId;
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
	 * @return the roomServices
	 */
	public Set<RoomService> getRoomServices() {
		return roomServices;
	}


	/**
	 * @param roomServices the roomServices to set
	 */
	public void setRoomServices(Set<RoomService> roomServices) {
		this.roomServices = roomServices;
	}


	/**
	 * @return the hotel
	 */
	public Hotel getHotel() {
		return hotel;
	}


	/**
	 * @param hotel the hotel to set
	 */
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}


	/**
	 * @return the roomReservations
	 */
	public Set<Reservation> getRoomReservations() {
		return roomReservations;
	}


	/**
	 * @param roomReservations the roomReservations to set
	 */
	public void setRoomReservations(Set<Reservation> roomReservations) {
		this.roomReservations = roomReservations;
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
	            append(this.getRoomType()).
	            append(this.getRoomFloor()).
	            append(this.getHotelId()).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Room))
            return false;
        if (obj == this)
            return true;

        Room other = (Room) obj;
        
        return new EqualsBuilder().
            append(this.getRoomId(), other.getRoomId()).
            append(this.getRoomType(), other.getRoomType()).
            append(this.getRoomFloor(), other.getRoomFloor()).
            append(this.getHotelId(), other.getHotelId()).
            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("Room [roomId = %d, roomType = %s, roomFloor = %s, hotelId = %d]",
				this.getRoomId(), this.getRoomType(), this.getRoomFloor(), this.getHotelId());
	}
}
