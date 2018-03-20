package com.databridgeproject.controller.admin;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.service.ReservationService;


/**
 * Controller servicing ADMIN actions,
 * related to hotel management functions such as
 * displaying, adding, editing and removing hotels. 
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller("ReservationAdminController")
@Secured({ "ROLE_ADMIN" })
@RequestMapping("/admin")
public class ReservationController {

	private static final Logger logger = Logger.getLogger(ReservationController.class);

	
	/**
	 * Service layer interface for managing reservation transactions.
	 */
	@Autowired
	private ReservationService reservationService;
	
	
	/**
	 * Render the page for managing reservations.
	 * 
	 * @param currentPageOpt
	 * Optional path variable for specifying the page of reservations to be displayed.
	 * 
	 * @param model
	 * 
	 * @return
	 * AdminReservations.jsp
	 * 
	 * @throws ResourceNotFoundException
	 * A 404 error in case currentPage path variable is out of bounds.
	 * 
	 */
	@GetMapping({ "/manageReservations", "/manageReservations/Page{currentPage}" })
	public String showReservationManagementPage(
			@PathVariable("currentPage") Optional<Integer> currentPageOpt,
			Model model) throws ResourceNotFoundException {
		
		Integer currentPage = 1;
		
		if (currentPageOpt.isPresent()) {
			currentPage = currentPageOpt.get();
		}
		
		model.addAttribute("reservationPage", reservationService.getReservationPage(currentPage));
		
		return "AdminReservations";
	}
	
	
	/**
	 * Removes the hotel entity identified by hotelId.
	 * 
	 * @param reservationId
	 * The id of the reservation to be removed.
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageReservations
	 * 
	 */
	@PostMapping("/removeReservation")
	public String removeReservation(
			@RequestParam("reservationId") Integer reservationId,
			RedirectAttributes attr) {

		try {
	     
			reservationService.removeReservation(reservationId);
		} 
		catch (Exception e) {
			
			logger.error(e);
			attr.addFlashAttribute("errorMessage", "Failed To Delete Reservation.");
			
			return "redirect:/admin/manageReservations";
		}
		
		attr.addFlashAttribute("successMessage", "Reservation Was Deleted Successfully.");
		
		return "redirect:/admin/manageReservations";
	}
}
