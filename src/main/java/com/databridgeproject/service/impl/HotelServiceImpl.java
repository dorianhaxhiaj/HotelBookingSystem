package com.databridgeproject.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Hotel;
import com.databridgeproject.domain.HotelService;
import com.databridgeproject.domain.Pager;
import com.databridgeproject.domain.Room;
import com.databridgeproject.domain.RoomService;
import com.databridgeproject.dto.HotelForm;
import com.databridgeproject.dto.HotelPage;
import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.repository.HotelDao;
import com.databridgeproject.repository.HotelServiceDao;
import com.databridgeproject.repository.ReservationDao;
import com.databridgeproject.repository.RoomDao;


/**
 * Service Layer Implementation exposing business methods for
 * accessing and manipulating Hotel entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Service
@Transactional
public class HotelServiceImpl 
	implements com.databridgeproject.service.HotelService {
	
	
	/**
	 * Data Access Object Interface for Hotel entity.
	 * 
	 */
	@Autowired
	private HotelDao hotelDao;	
	
	
	@Autowired
	private HotelServiceDao hotelServiceDao;
	
	
	/**
	 * Data Access Object Interface for Room entity.
	 * 
	 */
	@Autowired
	private RoomDao roomDao;
	
	
	/**
	 * Data Access Object Interface for Reservation entity.
	 * 
	 */
	@Autowired
	private ReservationDao reservationDao;
	
	
	
	
	/**
	 * Persist a Hotel entity to the datastore,
	 * based on the attributes of the HotelForm dto.
	 * 
	 */
	@Override
	public void saveHotel(HotelForm hotelForm) {
		
		Hotel hotel;
		
		if (hotelForm.getHotelId() == null) {
			hotel = new Hotel();
		}
		else {
			hotel = hotelDao.getHotelById(hotelForm.getHotelId());
			
			if (hotel == null)
				return;
		}
		
		
		hotel.setHotelName(hotelForm.getHotelName());
		hotel.setHotelStars(hotelForm.getHotelStars());
		hotel.setHotelServices(hotelServiceDao.getHotelServiceList().stream()
				.filter((hs) -> hotelForm.getHotelServiceIdList().contains(hs.getHotelServiceId()))
				.collect(Collectors.toSet()));
		
		hotelDao.saveHotel(hotel);
	}
	
	
	
	/**
	 * Fetch Hotel entity using its hotelId.
	 * 
	 */
	@Override
	public HotelForm getHotelFormById(Integer hotelId)
		throws ResourceNotFoundException {
		
		Hotel hotel = hotelDao.getHotelById(hotelId);
		
		HotelForm hotelForm = new HotelForm();
		hotelForm.setHotelId(hotel.getHotelId());
		hotelForm.setHotelName(hotel.getHotelName());
		hotelForm.setHotelStars(hotel.getHotelStars());
		hotelForm.setHotelServiceIdList(hotel.getHotelServices().stream()
				.map(hs -> hs.getHotelServiceId())
				.collect(Collectors.toSet()));
		
		return hotelForm;
	}
	
	
	/**
	 * Remove Hotel from datastore using its hotelId.
	 * 
	 */
	@Override
	public void removeHotel(Integer hotelId)  {
		

		Hotel hotelToDelete = hotelDao.getHotelById(hotelId);
		
		if (hotelToDelete == null)
			return;
		
		List<Room> hotelRooms = roomDao.getRoomListByHotelId(hotelId);
		
		for (Room room : hotelRooms) {
			
			reservationDao.removeReservationByRoomId(room.getRoomId());
			room.setRoomServices(new HashSet<RoomService>());
			roomDao.removeRoom(room.getRoomId());
		}
		
		hotelToDelete.setHotelServices(new HashSet<HotelService>());
		
		hotelDao.removeHotel(hotelId);
	}
	
	
	/**
	 * Fetch list of all hotels from datastore.
	 * 
	 */
	@Override
	public HotelPage getHotelPage(Integer currentPage) 
		throws ResourceNotFoundException {
		
		Pager hotelPager = new Pager(currentPage, HotelPage.DEFAULT_PAGE_SIZE);
		
		HotelPage hotelPage = new HotelPage();
		hotelPage.setCurrentPage(currentPage);
		hotelPage.setHotelList(hotelDao.getHotelList(hotelPager));
		hotelPage.setNumberOfHotels(hotelDao.getNumberOfHotels());
		
		if ((currentPage < 1 || currentPage > hotelPage.getLastPage()) &&
				hotelPage.getNumberOfHotels() != 0) {
			
			throw new ResourceNotFoundException("Requested Page Does Not Exist.");
		}

		return hotelPage;
	}

	Logger logger  =	Logger.getLogger(HotelService.class);
	/**
	 * Fetch list of all Hotel entities whose hotelIds are part of hotelIdList.
	 * 
	 */
	@Override
	public HotelPage getAvailableHotelPage(Date checkin, Date checkout, Integer currentPage) 
		throws ResourceNotFoundException {
		
		Pager hotelPager = new Pager(currentPage, HotelPage.DEFAULT_PAGE_SIZE);
		
		HotelPage hotelPage = new HotelPage();
		hotelPage.setCurrentPage(currentPage);
		hotelPage.setHotelList(hotelDao.getAvailableHotelList(checkin, checkout, hotelPager));
		hotelPage.setNumberOfHotels(hotelDao.getNumberOfAvailableHotels(checkin, checkout));
		
		if ((currentPage < 1 || currentPage > hotelPage.getLastPage()) &&
				hotelPage.getNumberOfHotels() != 0) {
			
			throw new ResourceNotFoundException("Requested Page Does Not Exist.");
		}
		
		return hotelPage;
	}
}
