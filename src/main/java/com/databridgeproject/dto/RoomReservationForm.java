package com.databridgeproject.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.databridgeproject.constraint.FirstDateBeforeSecondDate;
import com.databridgeproject.constraint.PresentAndFuture;

/**
 * DTO class that contains form data for
 * making a new room reservation.
 * 
 * @author Dorian Haxhiaj
 *
 */
@FirstDateBeforeSecondDate(first = "checkin", second = "checkout",
	message = "Check-in Date must be at least one day before Check-out Date.")
public class RoomReservationForm {

	
	/**
	 * Check-in date of the reservation.
	 */
	@NotNull
	@PresentAndFuture
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date checkin;
	
	
	/**
	 * Check-out date of the reservation.
	 */
	@NotNull
	@Future
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date checkout;
	
	/**
	 * The id of the room to be booked.
	 */
	@NotNull
	private Integer roomId;
	

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
}
