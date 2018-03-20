package com.databridgeproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Pager;
import com.databridgeproject.domain.Reservation;
import com.databridgeproject.dto.ReservationPage;
import com.databridgeproject.dto.RoomReservationForm;
import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.repository.ReservationDao;
import com.databridgeproject.repository.RoomDao;
import com.databridgeproject.repository.UserDao;
import com.databridgeproject.service.ReservationService;


/**
 * Service Layer Implementation exposing business methods for
 * accessing and manipulating Reservation entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
	
	
	/**
	 * Data Access Object Interface for Reservation entity.
	 * 
	 */
	@Autowired
	private ReservationDao reservationDao;

	
	@Autowired
	private RoomDao roomDao;
	
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * Add a new Reservation entity to the datastore.
	 * 
	 */
	@Override
	public void createReservationForLoggedInUser(RoomReservationForm roomReservationForm) {
		
		Reservation newReservation = new Reservation();
		newReservation.setCheckin(roomReservationForm.getCheckin());
		newReservation.setCheckout(roomReservationForm.getCheckout());
		newReservation.setRoom(roomDao.getRoomById(roomReservationForm.getRoomId()));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		newReservation.setUser(userDao.getUserByUsername(username));
		
		reservationDao.saveReservation(newReservation);
	}

	
	/**
	 * Update a modified Reservation entity.
	 * 
	 */
	@Override
	public void cancelReservationForLoggedInUser(Integer reservationId) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		reservationDao.removeReservation(reservationId, username);
	}
	
	
	/**
	 * Fetch Reservation entity using its reservationId.
	 * 
	 */
	@Override
	public Reservation getReservationById(Integer reservationId) {
		
		return reservationDao.getReservationById(reservationId);
	}

	
	/**
	 * Remove Reservation entity using its reservationId.
	 * 
	 */
	@Override
	public void removeReservation(Integer reservationId) {
		
		reservationDao.removeReservation(reservationId);	
	}


	/**
	 * Fetch list of all Reservation entities.
	 * 
	 */
	@Override
	public ReservationPage getReservationPage(Integer currentPage) 
		throws ResourceNotFoundException {
		
		Pager reservationPager = new Pager(currentPage, ReservationPage.DEFAULT_PAGE_SIZE);
		
		ReservationPage reservationPage = new ReservationPage();
		reservationPage.setCurrentPage(currentPage);
		reservationPage.setReservationList(reservationDao.getReservationList(reservationPager));
		reservationPage.setNumberOfReservations(reservationDao.getNumberOfReservations());
		
		if ((currentPage < 1 || currentPage > reservationPage.getLastPage()) &&
				reservationPage.getNumberOfReservations() != 0) {
			
			throw new ResourceNotFoundException("Requested Page Does Not Exist.");
		}

		return reservationPage;
	}


	/**
	 * Fetch page of Reservation entities using the username of the
	 * User who made those reservations.
	 * 
	 * @param username
	 * 
	 * 
	 * @param currentPage
	 * 
	 * 
	 */
	@Override
	public ReservationPage getReservationPageByUsername(String username, Integer currentPage)
		throws ResourceNotFoundException {
		
		Pager reservationPager = new Pager(currentPage, ReservationPage.DEFAULT_PAGE_SIZE);
		
		ReservationPage reservationPage = new ReservationPage();
		reservationPage.setCurrentPage(currentPage);
		reservationPage.setReservationList(reservationDao.getReservationListByUsername(username, reservationPager));
		reservationPage.setNumberOfReservations(reservationDao.getNumberOfReservationsOfUser(username));
		
		if ((currentPage < 1 || currentPage > reservationPage.getLastPage()) &&
				reservationPage.getNumberOfReservations() != 0) {
			
			throw new ResourceNotFoundException("Requested Page Does Not Exist.");
		}

		return reservationPage;
	}
}
