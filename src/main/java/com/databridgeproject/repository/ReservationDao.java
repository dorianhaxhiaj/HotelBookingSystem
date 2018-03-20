package com.databridgeproject.repository;

import java.util.List;

import com.databridgeproject.domain.Pager;
import com.databridgeproject.domain.Reservation;

/**
 * Data Access Object Interface for
 * accessing and manipulating Reservation entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface ReservationDao {
	
	public void saveReservation(Reservation reservation);
	public Reservation getReservationById(Integer reservationId);
	public void removeReservation(Integer reservationId);
	public void removeReservation(Integer reservationId, String username);
	public List<Reservation> getReservationList(Pager pager);
	public List<Reservation> getReservationListByUsername(String username, Pager pager);
	public void removeReservationByRoomId(Integer roomId);
	public Long getNumberOfReservations();
	public Long getNumberOfReservationsOfUser(String username);
}
