package com.databridgeproject.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.HotelService;
import com.databridgeproject.dto.HotelServiceForm;
import com.databridgeproject.repository.HotelServiceDao;
import com.databridgeproject.service.HotelserviceService;


/**
 * Service Layer Implementation exposing business methods for
 * accessing and manipulating HotelService entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Service
@Transactional
public class HotelserviceServiceImpl implements HotelserviceService {
	
	
	/**
	 * Data Access Object Interface for HotelService entity.
	 * 
	 */
	@Autowired
	private HotelServiceDao hotelServiceDao;
	

	
	/**
	 * Add a new HotelService entity to the datastore.
	 * 
	 */
	@Override
	public void saveHotelService(HotelServiceForm hotelServiceForm) {
		
		HotelService hotelService;
		
		if (hotelServiceForm.getHotelServiceId() == null) {
			
			hotelService = new HotelService();
		} 
		else {
			
			hotelService = hotelServiceDao.getHotelServiceById(hotelServiceForm.getHotelServiceId());
			
			if (hotelService == null)
				return;
		}
		
		hotelService.setName(hotelServiceForm.getName());
		
		hotelServiceDao.saveHotelService(hotelService);	
	}
	

	
	/**
	 * Fetch HotelService entity using its hotelServiceId.
	 * 
	 */
	@Override
	public HotelService getHotelServiceById(Integer hotelServiceId) {
		
		return hotelServiceDao.getHotelServiceById(hotelServiceId);
	}

	
	/**
	 * Remove HotelService using its hotelServiceId.
	 */
	@Override
	public void removeHotelService(Integer hotelServiceId) {
		
		hotelServiceDao.removeHotelService(hotelServiceId);		
	}

	
	/**
	 * Fetch list of all HotelService entities.
	 * 
	 */
	@Override
	public List<HotelService> getHotelServiceList() {
		
		return hotelServiceDao.getHotelServiceList();
	}
}
