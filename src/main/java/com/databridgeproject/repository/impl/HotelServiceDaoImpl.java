package com.databridgeproject.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.databridgeproject.domain.HotelService;
import com.databridgeproject.repository.HotelServiceDao;


/**
 * Data Access Object Implementation for
 * accessing and manipulating HotelService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Repository
public class HotelServiceDaoImpl implements HotelServiceDao {
	
	
	/**
	 * Used for logging DAO operations.
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
	 * Persist HotelService entity to datastore.
	 *  
	 */
	@Override
	public void saveHotelService(HotelService hotelService) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(hotelService);
		
		logger.info("HotelService saved successfully, HotelService Details = " + hotelService);
	}

	
	/**
	 * Fetch HotelService entity from datastore using its hotelServiceId.
	 * 
	 */
	@Override
	public HotelService getHotelServiceById(Integer hotelServiceId) {
		
		Session session = sessionFactory.getCurrentSession();
		HotelService fetchedHotelService = (HotelService)session.load(HotelService.class, hotelServiceId);
		
		logger.info("HotelService loaded successfully, HotelService Details = " + fetchedHotelService);
		
		return fetchedHotelService;
	}

	
	/**
	 * Remove HotelService entity with hotelServiceId from datastore.
	 * 
	 */
	@Override
	public void removeHotelService(Integer hotelServiceId) {
		
		Session session = sessionFactory.getCurrentSession();
		HotelService hotelServiceToDelete = session.load(HotelService.class, hotelServiceId);
		
		if(null != hotelServiceToDelete) {
			session.delete(hotelServiceToDelete);
		}
		
		logger.info("Hotel Services deleted successfully, Hotel Services Details = " + hotelServiceToDelete);
	}

	
	/**
	 * Fetch list of all HotelService entities from datastore.
	 * 
	 */
	@Override
	public List<HotelService> getHotelServiceList() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<HotelService> hotelServiceListQuery = session.createQuery(
				"FROM HotelService hs ORDER BY hs.hotelServiceId", 
				HotelService.class);
		
		List<HotelService> hotelServiceList = hotelServiceListQuery.getResultList();
		
		return hotelServiceList;
	}
}
