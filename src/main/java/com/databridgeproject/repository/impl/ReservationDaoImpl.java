package com.databridgeproject.repository.impl;


import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Pager;
import com.databridgeproject.domain.Reservation;
import com.databridgeproject.repository.ReservationDao;


/**
 * Data Access Object Implementation for
 * accessing and manipulating Reservation entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Repository
@Transactional
public class ReservationDaoImpl implements ReservationDao {
	
	
	/**
	 * Used for logging dao operations.
	 * 
	 */
	private static final Logger logger = Logger.getLogger(ReservationDaoImpl.class);
	
	
	/**
	 * Factory used for retrieving a session object.
	 * 
	 */
	@Autowired
	private SessionFactory sessionFactory;

	

	/**
	 * Persist reservation to datastore.
	 *  
	 */
	@Override
	public void saveReservation(Reservation reservation) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(reservation);
		
		logger.info("Reservation saved successfully, Reservation Details=" + reservation);
	}

	
	/**
	 * Update modifiedHotel in datastore.
	 * 
	 */
	@Override
	public Reservation getReservationById(Integer reservationId) {
		
		Session session = sessionFactory.getCurrentSession();
		Reservation fetchedReservation = session.load(Reservation.class, reservationId);
		
		logger.info("Reservation loaded successfully, Reservation Details = " + fetchedReservation);
		
		return fetchedReservation;
	}

	
	/**
	 * Fetch hotel entity from datastore using its hotelId.
	 * 
	 */
	@Override
	public void removeReservation(Integer reservationId) {
		
		Session session = sessionFactory.getCurrentSession();
		Reservation reservationToDelete = session.load(Reservation.class, reservationId);
		
		if (null != reservationToDelete) {
			session.delete(reservationToDelete);
		}
		
		logger.info("Reservation deleted successfully, Reservation Details = " + reservationToDelete);
	}
	
	
	/**
	 * Fetch hotel entity from datastore using its hotelId.
	 * 
	 */
	@Override
	public void removeReservation(Integer reservationId, String username) {
		
		Session session = sessionFactory.getCurrentSession();
		Reservation reservationToDelete = session.load(Reservation.class, reservationId);
		
		if (reservationToDelete != null && reservationToDelete.getUsername().equals(username)) {
			session.delete(reservationToDelete);
		}
		
		logger.info("Reservation deleted successfully, Reservation Details = " + reservationToDelete);
	}
	
	/**
	 * Remove Reservation entities from datastore that are reservations
	 * on the room identified by roomId.
	 * 
	 */
	@Override
	public void removeReservationByRoomId(Integer roomId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		int deletedCount = session.createQuery(
				"DELETE FROM Reservation res WHERE res.roomId= :roomId")
				.setParameter("roomId", roomId).executeUpdate();
		
		if(deletedCount > 0) {
			
			logger.info("Reservation deleted successfully, Row Affected " + deletedCount);
		} 
		else {
			
			logger.info("Reservation deleted failed, Row Affected " + deletedCount);
		}
	}	
	

	/**
	 * Fetch list of all Reservation entities from datastore.
	 * 
	 */
	@Override
	public List<Reservation> getReservationList(Pager pager) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Reservation> reservationListQuery = session
				.createQuery("from Reservation", Reservation.class)
				.setFirstResult((pager.getCurrentPage() - 1) * pager.getPageSize())
				.setMaxResults(pager.getPageSize());
				
		List<Reservation> reservationList = reservationListQuery.getResultList();
		
		return reservationList;
	}
	

	/**
	 * Fetch list of all Reservation entities done by the user identified by
	 * username parameter.
	 * 
	 */
	@Override
	public List<Reservation> getReservationListByUsername(String username, Pager pager) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Reservation> reservationListQuery= session.createQuery(
				"FROM Reservation WHERE username = :username", Reservation.class);
		
		reservationListQuery.setParameter("username", username);
		
		List<Reservation> reservationList = reservationListQuery.getResultList();
		
		return reservationList;
	}
	
	@Override
	public Long getNumberOfReservations() {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<Long> countQuery = session.createQuery(
				"SELECT COUNT(res) FROM Reservation res",
				Long.class);
		
		return countQuery.getSingleResult();
	}
	
	@Override
	public Long getNumberOfReservationsOfUser(String username) {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<Long> countQuery = session.createQuery(
				"SELECT COUNT(res) FROM Reservation res WHERE res.username = :username",
				Long.class);
		
		countQuery.setParameter("username", username);
		
		return countQuery.getSingleResult();
	}
}
