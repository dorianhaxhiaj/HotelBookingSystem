package com.databridgeproject.controller;



import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.service.ReservationService;

/**
 * Controller servicing authenticated user actions,
 * related to managing the normal authenticated user's personal reservations:
 * showing the reservations and canceling the active reservations.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
public class ReservationController {
	
	
	/**
	 * To log exceptions in this controller.
	 */
	private static final Logger logger = Logger.getLogger(ReservationController.class);
	
	
	/**
	 * Service layer interface for doing reservation entity transaction.
	 */
	@Autowired
	private ReservationService reservationService;
	
	
	/**
	 * Displays a page of the reservations past or active of the currently
	 * logged in user.
	 * 
	 * @param currentPageOpt
	 * The optional page number path variable the is used for pagination.
	 * 
	 * @param model
	 * 
	 * @return
	 * Reservations.jsp
	 * 
	 * @throws ResourceNotFoundException
	 * Represent a 404 Error in case the currentPage path variable is out of
	 * bounds of the valid range of page numbers.
	 * 
	 */
	@GetMapping({ "/Reservations", "/Reservations/Page{currentPage}" })
	public String showReservationsPage(
			@PathVariable("currentPage") Optional<Integer> currentPageOpt,
			Model model) throws ResourceNotFoundException {
		
		Integer currentPage = 1;
		
		if (currentPageOpt.isPresent()) {
			currentPage = currentPageOpt.get();
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		model.addAttribute("reservationPage", 
				reservationService.getReservationPageByUsername(username, currentPage));
		
		return "Reservations";
	}
	
	
	
	/**
	 * Cancels a reservation made by the currently logged in user.
	 *  
	 * @param reservationId
	 * The id of the reservation to be cancelled.
	 * 
	 * @param attr
	 * 
	 * @return
	 * Redirect to /Reservations
	 */
	@PostMapping("/cancelReservation")
	public String cancelReservation(
			@RequestParam("reservationId") Integer reservationId,
			RedirectAttributes attr) {

		try {
	     
			reservationService.cancelReservationForLoggedInUser(reservationId);
		} 
		catch (Exception e) {
			
			logger.error(e);
			attr.addFlashAttribute("errorMessage", "Failure In Canceling Reservation.");
			return "redirect:/Reservations";
		}
		
		attr.addFlashAttribute("successMessage", "Reservation Was Canceled Successfully.");
		
		return "redirect:/Reservations";
	}
}
