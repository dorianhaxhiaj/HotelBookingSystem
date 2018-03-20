package com.databridgeproject.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.databridgeproject.domain.Room;
import com.databridgeproject.dto.HotelPage;
import com.databridgeproject.dto.RoomReservationForm;
import com.databridgeproject.dto.SearchForm;
import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.service.HotelService;
import com.databridgeproject.service.ReservationService;
import com.databridgeproject.service.RoomService;

/**
 * Controller servicing unauthenticated and authenticated user actions,
 * related to the home page functions, which is the main function of this web app:
 * making a hotel reservation.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
public class HomeController {
	
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ReservationService reservationService;
	
	
	
	/**
	 * 
	 * @param successMessage
	 * @param errorMessage
	 * @param model
	 * @return
	 */
	@GetMapping({ "/", "/Page{currentPage}" })
	public String showHomePage (
			@Valid @ModelAttribute("searchForm") SearchForm searchForm,
			BindingResult bindingResult,
			ModelMap model,
			@RequestParam Map<String,String> allRequestParams,
			@PathVariable("currentPage") Optional<Integer> currentPageOpt) throws ResourceNotFoundException {
		
		
		Integer currentPage = 1;
		
		if (currentPageOpt.isPresent()) {
			
			currentPage = currentPageOpt.get();
		}
		
		
		if (allRequestParams.isEmpty()) {
			
			model.remove("org.springframework.validation.BindingResult.searchForm");
		}
		
		HotelPage hotelPage;
		
		if (bindingResult.hasErrors()) {
			
			hotelPage = hotelService.getHotelPage(currentPage);
		} 
		else {
		
			hotelPage = hotelService.getAvailableHotelPage(
					searchForm.getCheckin(), 
					searchForm.getCheckout(), 
					currentPage);
			
			model.addAttribute("isSearchComplete", true);
		}
		
		model.addAttribute("hotelPage", hotelPage);
	    model.addAttribute("searchForm", searchForm);
	    model.addAttribute("currentPage", currentPage);
	    
	    return "Home";
	}
	
	
	/**
	 * Renders page of available rooms for the check-in and
	 * check-out dates specified in the searchForm of the hotel
	 * the user choose in the Home page.
	 *  
	 * @param searchForm
	 * Contains the check-in and check-out date for filtering
	 * available rooms.
	 * 
	 * @param bindingResult
	 * 
	 * @param hotelId
	 * Id of the hotel whose rooms are part of.
	 * 
	 * @param model
	 * 
	 * @return
	 * BookRoom.jsp
	 * 
	 */
	@GetMapping("/Hotel{hotelId}/AvailableRooms")
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String showRoom( 
			@Valid @ModelAttribute("searchForm") SearchForm searchForm,
			BindingResult bindingResult, 
			@PathVariable("hotelId") Integer hotelId,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			
			return "redirect:/";
		}
		
		List<Room>  availableRoomList = roomService.getAvailableRoomListFromHotel(
				hotelId, searchForm.getCheckin(), searchForm.getCheckout());
		model.addAttribute("roomList", availableRoomList);
		
		RoomReservationForm roomReservationForm = new RoomReservationForm();
		roomReservationForm.setCheckin(searchForm.getCheckin());
		roomReservationForm.setCheckout(searchForm.getCheckout());
		
		model.addAttribute("roomReservationForm", roomReservationForm);
		model.addAttribute("hotelId", hotelId);
	
		return "BookRoom";
	}
	
	
	/**
	 * Makes a reservation based on the data contained in roomReservationForm.
	 * 
	 * @param roomReservationForm
	 * DTO that contains the data about the new reservation.
	 * 
	 * @param bindingResult
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to / (Home page).
	 * 
	 */
	@PostMapping("/makeReservation")
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String saveReservation(
			@Valid @ModelAttribute("roomReservationForm") RoomReservationForm roomReservationForm,
			BindingResult bindingResult,
			RedirectAttributes attr) {
		
		
		if (bindingResult.hasErrors()) {
			
			attr.addFlashAttribute("errorMessage", 
					"System Experienced Unexpected Error While Making Reservation");
			
			return "redirect:/";
		}
		
		reservationService.createReservationForLoggedInUser(roomReservationForm);
		
		attr.addFlashAttribute("successMessage", "Reservation Was Succesful");
		
		return "redirect:/";
	}
	
}
