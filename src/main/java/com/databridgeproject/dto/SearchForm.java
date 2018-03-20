package com.databridgeproject.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.databridgeproject.constraint.FirstDateBeforeSecondDate;
import com.databridgeproject.constraint.PresentAndFuture;

/**
 * DTO class that contains form data for
 * searching all available hotels.
 * 
 * @author Dorian Haxhiaj
 *
 */
@FirstDateBeforeSecondDate(first = "checkin", second = "checkout", 
	message = "Check-in Date must be at least one day before Check-out Date.")
public class SearchForm {
	
	/**
	 * The check-in date of reservation.
	 */
	@NotNull(message = "Please insert Check-in date")
	@PresentAndFuture(message = "Check-in date cannot be in the past.")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date checkin;
	
	/**
	 * The check-out date of the reservation.
	 */
	@NotNull(message = "Please insert Check-out date")
	@Future(message = "Check-out date must be after today.")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date checkout;
	
	
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
}
