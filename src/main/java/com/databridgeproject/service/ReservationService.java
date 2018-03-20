package com.databridgeproject.service;

import com.databridgeproject.domain.Reservation;
import com.databridgeproject.dto.ReservationPage;
import com.databridgeproject.dto.RoomReservationForm;
import com.databridgeproject.error.ResourceNotFoundException;


/**
 * Service Layer Interface exposing business methods for
 * accessing and manipulating Reservation entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface ReservationService {
	
	/**
	 * Creates a new Reservation entity to the datastore for the currently logged in user.
	 * 
	 * @param roomReservationForm
	 * The form which contains the room reservation data to be added.
	 * 
	 */
	public void createReservationForLoggedInUser(RoomReservationForm roomReservationForm);
	
	/**
	 * Cancels the reservation identified by the reservationId parameter, made by
	 * the currently logged in user.
	 * 
	 * @param reservationId
	 * The id of the reservation to be cancelled.
	 * 
	 */
	public void cancelReservationForLoggedInUser(Integer reservationId);
	
	/**
	 * Fetch Reservation entity using its reservationId.
	 * 
	 * @param reservationId
	 * The id of the reservation being fetched.
	 * 
	 */
	public Reservation getReservationById(Integer reservationId);
	
	/**
	 * Remove Reservation entity using its reservationId.
	 * 
	 * @param reservationId
	 * The id of the reservation being removed.
	 * 
	 */
	public void removeReservation(Integer reservationId);
	
	/**
	 * Fetch a page of Reservation entities.
	 * 
	 * @param currentPage
	 * The number of the page which is being fetched.
	 * 
	 * @throws ResourceNotFoundException
	 * Represent a 404 error in case the currentPage in not part
	 * of the valid range of reservation pages.
	 * 
	 * @return
	 * A page of reservations for display.
	 * 
	 */
	public ReservationPage getReservationPage(Integer currentPage) throws ResourceNotFoundException;
	
	/**
	 * Fetch list of Reservation entities using the username of the
	 * User who made those reservations.
	 * 
	 * @param username
	 * The username of the user whose reservations are being fetched.
	 * 
	 * @param currentPage
	 * The number of the reservations page which is being fetched. 
	 * 
	 * @throws ResourceNotFoundException
	 * Represent a 404 error in case the currentPage in not part
	 * of the valid range of reservation pages.
	 * 
	 * @return
	 * A page of reservations of the user identified by username.
	 * 
	 */
	public ReservationPage getReservationPageByUsername(String username, Integer currentPage) throws ResourceNotFoundException;
}
