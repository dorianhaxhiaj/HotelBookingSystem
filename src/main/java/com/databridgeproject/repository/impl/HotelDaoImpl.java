package com.databridgeproject.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Hotel;
import com.databridgeproject.domain.Pager;
import com.databridgeproject.repository.HotelDao;


/**
 * Data Access Object Implementation for
 * accessing and manipulating Hotel entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Repository
@Transactional
public class HotelDaoImpl implements HotelDao {
	
	
	/**
	 * Used for logging dao operations.
	 * 
	 */
	private static final Logger logger = Logger.getLogger(HotelDaoImpl.class);
	
	
	/**
	 * Factory used for retrieving a session object.
	 * 
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	/**
	 * Persist Hotel entity to datastore.
	 *  
	 */
	@Override
	public void saveHotel(Hotel hotel) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(hotel);
		
		logger.info("Hotel saved successfully, Hotel Details = " + hotel);
	}
	
	
	
	/**
	 * Fetch Hotel entity from datastore using its hotelId.
	 * 
	 * @param hotelId
	 * The id of the hotel to be fetched.
	 * 
	 * @return
	 * The Hotel entity.
	 * 
	 */
	@Override
	public Hotel getHotelById(Integer hotelId) {
		
		Session session = sessionFactory.getCurrentSession();
		Hotel fetchedHotel = session.load(Hotel.class, hotelId);
		
		return fetchedHotel;
	}
	
	
	/**
	 * Remove Hotel entity from datastore using its hotelId.
	 * 
	 * @param hotelId
	 * The id of the hotel to be removed.
	 * 
	 */
	@Override
	public void removeHotel(Integer hotelId) {
		
		Session session = sessionFactory.getCurrentSession();
		Hotel hotelToDelete = session.load(Hotel.class, hotelId);
		
		if (null != hotelToDelete) {
			session.delete(hotelToDelete);
		}
		
		logger.info("Hotel deleted successfully, Hotel Details = " + hotelToDelete);
	}
	
	
	
	/**
	 * Fetches list of all Hotel entities from datastore.
	 * 
	 */
	@Override
	public List<Hotel> getHotelList() {
		
		Session session = sessionFactory.getCurrentSession();
		Query<Hotel> hotelListQuery = session.createQuery("from Hotel", Hotel.class);
		List<Hotel> hotelList = hotelListQuery.getResultList();
		
		return hotelList;	
	}
	
	
	/**
	 * Fetches list of all Hotel entities from datastore.
	 * 
	 */
	@Override
	public List<Hotel> getHotelList(Pager pager) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Hotel> hotelListQuery = 
				session.createQuery("from Hotel", Hotel.class)
				.setFirstResult((pager.getCurrentPage() - 1)  * pager.getPageSize())
				.setMaxResults(pager.getPageSize());
		
		List<Hotel> hotelList = hotelListQuery.getResultList();
		
		return hotelList;	
	}
	

	/**
	 * Fetches list of all hotels in the system 
	 * 
	 */
	@Override
	public List<Hotel> getAvailableHotelList(Date checkin, Date checkout) {
		
		Session session = sessionFactory.getCurrentSession();
		Query<Hotel> hotelListQuery = session.createQuery(
				"SELECT DISTINCT h FROM Hotel h JOIN h.rooms r LEFT JOIN r.roomReservations res WHERE"
					+ "	res IS NULL OR NOT ((checkin <= :checkin AND checkout >= :checkin) OR"
               		+ " (checkin<= :checkout AND checkout>= :checkout) OR"
       				+ " (checkin>= :checkin AND checkout<= :checkout))", Hotel.class);
		hotelListQuery.setParameter("checkin", checkin);
		hotelListQuery.setParameter("checkout", checkout);
		
		return hotelListQuery.getResultList();
	}
	
	
	/**
	 * Fetches the list of hotels available between checkin and checkout dates for reservation.
	 * The list is limited in size by pager information, which collects only a part of the
	 * entire hotel catalog.
	 * 
	 * @param checkin
	 * The checkin date for which the hotel must be free.
	 * 
	 * @param checkout
	 * The checkout date for which the hotel must be free.
	 * 
	 * @return
	 * A sublist of all free hotels in the system, constrained by pager info.
	 * 
	 */
	@Override
	public List<Hotel> getAvailableHotelList(Date checkin, Date checkout, Pager pager) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Hotel> hotelListQuery = session.createQuery(
				"SELECT DISTINCT h FROM Hotel h JOIN h.rooms r LEFT JOIN r.roomReservations res WHERE"
					+ "	res IS NULL OR NOT ((checkin <= :checkin AND checkout >= :checkin) OR"
               		+ " (checkin<= :checkout AND checkout>= :checkout) OR"
       				+ " (checkin>= :checkin AND checkout<= :checkout))", Hotel.class)
				.setFirstResult((pager.getCurrentPage() - 1)  * pager.getPageSize())
				.setMaxResults(pager.getPageSize());
		
		hotelListQuery.setParameter("checkin", checkin);
		hotelListQuery.setParameter("checkout", checkout);
		
		return hotelListQuery.getResultList();

	}
	
	
	/**
	 * Fetches the number of hotels stored in the system.
	 * 
	 * @return
	 * The number of hotels stored in the system.
	 * 
	 */
	@Override
	public Long getNumberOfHotels() {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<Long> countQuery = session.createQuery(
				"SELECT COUNT(h) FROM Hotel h",
				Long.class);
		
		return countQuery.getSingleResult();
	}
	
	
	/**
	 * Fetch number of available hotels for reservation between the dates
	 * checkin and checkout.
	 * 
	 * @param checkin
	 * The checkin date that a hotel must be free.
	 * 
	 * @param checkout
	 * The checkout date that a hotel must be free.
	 * 
	 * @return
	 * The number of free hotels for checkin and checkout.
	 * 
	 */
	@Override
	public Long getNumberOfAvailableHotels(Date checkin, Date checkout) {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<Long> countQuery = session.createQuery(
				"SELECT COUNT(DISTINCT h) FROM Hotel h JOIN h.rooms r LEFT JOIN r.roomReservations res WHERE"
					+ "	res IS NULL OR NOT ((checkin <= :checkin AND checkout >= :checkin) OR"
               		+ " (checkin<= :checkout AND checkout>= :checkout) OR"
       				+ " (checkin>= :checkin AND checkout<= :checkout))",
				Long.class);
		
		countQuery.setParameter("checkin", checkin);
		countQuery.setParameter("checkout", checkout);
		
		return countQuery.getSingleResult();
	}
}
