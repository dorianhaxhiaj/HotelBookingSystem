package com.databridgeproject.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Entity of reservation table,
 * that hold information about room reservations:
 * check-in and check-out dates of the reservations,
 * id of the room being reserved and the username of
 * the user doing the reservation. 
 * 
 * @author Dorian Hoxhiaj
 *
 */
@Entity
@Table(name = "reservation")
public class Reservation {
	
	
	/**
	 * Auto-generated (from the database) primary key
	 * of this entity.
	 * 
	 */
	@Id
	@Column(name = "reservationid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;
	
	
	/**
	 * Check-in date of the reservation.
	 * 
	 */
	@Column(name = "checkin")
	private Date checkin;
	
	
	/**
	 * Check-out date of the reservation.
	 * 
	 */
	@Column(name = "checkout")
	private Date checkout;
	
	
	/**
	 * The id of the room which will be reserved.
	 * 
	 */
	@Column(name = "roomid", insertable = false, updatable = false)
	private Integer roomId;
	
	
	/**
	 * The username of the user that is doing the reservation.
	 * 
	 */
	@Column(name = "username", insertable = false, updatable = false)
	private String username;
	
	
	
	@ManyToOne
	@JoinColumn(name = "roomid")
	private Room room;
	
	
	@ManyToOne
	@JoinColumn(name = "username")
	private User user;
	
	
	
	// *****************************************************
	// List of Setters and Getters *************************
	// *****************************************************
	
	/**
	 * @return the reservationId
	 */
	public Integer getReservationId() {
		return reservationId;
	}
	
	
	/**
	 * @return the checkin
	 */
	public Date getCheckin() {
		return checkin;
	}


	/**
	 * @param checkin the checkin to set
	 */
	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}


	/**
	 * @return the checkout
	 */
	public Date getCheckout() {
		return checkout;
	}


	/**
	 * @param checkout the checkout to set
	 */
	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}


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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}


	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	            append(checkin).
	            append(checkout).
	            append(roomId).
	            append(username).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Reservation))
            return false;
        if (obj == this)
            return true;

        Reservation other = (Reservation) obj;
        
        return new EqualsBuilder().
            append(reservationId, other.reservationId).
            append(checkin, other.checkin).
            append(checkout, other.checkout).
            append(roomId, other.roomId).
            append(username, other.username).
            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("Reservation [reservationId = %d, checkin = %s, checkout = %s, roomId = %d, username = %s]",
				this.getReservationId() == null ? -1 : this.getReservationId(),
				this.getCheckin(), this.getCheckout(),
				this.getRoomId(), this.getUsername());
	}
}
