package com.databridgeproject.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.databridgeproject.domain.HotelService;
import com.databridgeproject.dto.HotelForm;
import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.service.HotelserviceService;

/**
 * Controller servicing ADMIN actions,
 * related to hotel management functions such as
 * displaying, adding, editing and removing hotels. 
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@Secured(value = { "ROLE_ADMIN" })
@RequestMapping("/admin")
public class HotelController {
	
	private static final Logger logger = Logger.getLogger(HotelController.class);
	
	/**
	 * Service layer interface for managing hotel transactions.
	 */
	@Autowired
	private com.databridgeproject.service.HotelService hotelService;

	/**
	 * Service layer interface for hotel service transactions.
	 */
	@Autowired
	private HotelserviceService hotelserviceService;

	
	/**
	 * Render the page for managing hotels.
	 * 
	 * @param currentPageOpt
	 * Optional path variable for specifying the page of hotels to be displayed.
	 * 
	 * @param model
	 * 
	 * @return
	 * Hotels.jsp
	 * 
	 * @throws ResourceNotFoundException
	 * A 404 error in case currentPage path variable is out of bounds.
	 * 
	 */
	@GetMapping({ "/manageHotels", "/manageHotels/Page{currentPage}" })
	public String showHotelManagementPage(
			@PathVariable("currentPage") Optional<Integer> currentPageOpt,
			Model model) throws ResourceNotFoundException {
		
		// Default current page
		Integer currentPage = 1;
		
		if (currentPageOpt.isPresent()) {
			
			currentPage = currentPageOpt.get();
		}
		
		
		if (!model.containsAttribute("hotelForm")) {
			
			model.addAttribute("hotelForm", new HotelForm());
		}
		
		
		model.addAttribute("hotelPage", hotelService.getHotelPage(currentPage));
		model.addAttribute("hotelServiceList", hotelserviceService.getHotelServiceList());
	
		return "Hotels";
	}
	
	
	/**
	 * Saves a hotel to the data store. It can be an existing hotel entity that is being edited
	 * or a new hotel entity to be added.
	 * 
	 * @param hotelForm
	 * DTO that holds data for adding or editing a hotel.
	 * 
	 * @param bindingResult
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageHotels
	 * 
	 */
	@PostMapping("/saveHotel")
	public String saveHotel(
			@Valid @ModelAttribute("hotelForm") final HotelForm hotelForm, 
			final BindingResult bindingResult,
			RedirectAttributes attr) {
		
		if (bindingResult.hasErrors()) {
			
			attr.addFlashAttribute("org.springframework.validation.BindingResult.hotelForm", bindingResult);
			attr.addFlashAttribute("hotelForm", hotelForm);
	
			return "redirect:/admin/manageHotels";
		} 
		else {
			
			try {
			
				hotelService.saveHotel(hotelForm);  
			} 
			catch(Exception e) {
				
				logger.error(e);
				attr.addFlashAttribute("errorMessage", "Hotel Failed To Save");
				
				return "redirect:/admin/manageHotels";
			}
		
			attr.addFlashAttribute("successMessage", "Hotel Was Saved Succesfully");
			
		  	return "redirect:/admin/manageHotels";
		}
	}
	
	/**
	 * Renders the page for editing a hotel.
	 * 
	 * @param hotelId
	 * The id of the hotel to be edited.
	 * 
	 * @param model
	 * 
	 * @return
	 * Hotels.jsp whose form is populated with hotel data.
	 * 
	 * @throws ResourceNotFoundException
	 * A 404 error.
	 * 
	 */
	@GetMapping("/editHotel/{hotelId}")
	public String editHotel(
			@PathVariable("hotelId") final Integer hotelId, 
			Model model) throws ResourceNotFoundException {
		
		HotelForm hotelForm = hotelService.getHotelFormById(hotelId);
		List<HotelService> hotelServiceList = hotelserviceService.getHotelServiceList();
		
		model.addAttribute("hotelForm", hotelForm);
		model.addAttribute("hotelPage", hotelService.getHotelPage(1));
		model.addAttribute("hotelServiceList", hotelServiceList);
		
		return "Hotels";
	}
	
	
	/**
	 * Removes the hotel entity identified by hotelId.
	 * 
	 * @param hotelId
	 * The id of the hotel to be removed.
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageHotels
	 * 
	 */
	@PostMapping("/removeHotel")
	public String removeHotel(
			@RequestParam("hotelId") final Integer hotelId,
			RedirectAttributes attr) {
		
		try {
		
			hotelService.removeHotel(hotelId);
		} 
		catch(Exception e) {
			
			logger.error(e);
			attr.addFlashAttribute("errorMessage", "Hotel Deletetion Failed");
			
			return "redirect:/admin/manageHotels";
		}
		
		attr.addFlashAttribute("successMessage", "Hotel Was Deleted Succesfully");
		
		return "redirect:/admin/manageHotels";
	}
}
