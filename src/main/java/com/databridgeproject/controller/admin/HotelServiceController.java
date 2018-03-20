package com.databridgeproject.controller.admin;

import javax.validation.Valid;

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
import com.databridgeproject.dto.HotelServiceForm;
import com.databridgeproject.service.HotelserviceService;


/**
 * Controller servicing ADMIN actions,
 * related to hotel service management functions such as
 * displaying, adding, editing and removing hotel services. 
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@Secured(value = { "ROLE_ADMIN" })
@RequestMapping("/admin")
public class HotelServiceController {

	
	/**
	 * Service layer interface for managing hotel service transactions.
	 */
	@Autowired
	private HotelserviceService hotelserviceService;
	
	
	/**
	 * Render the page for managing hotel services.
	 * 
	 * @param model
	 * 
	 * @return
	 * HotelServices.jsp
	 * 
	 */
	@GetMapping("/manageHotelServices")
	public String showHotelServiceManagementPage(Model model) {
		
		if (!model.containsAttribute("hotelServiceForm")) {
			
			model.addAttribute("hotelServiceForm", new HotelService());
		}
	
		model.addAttribute("hotelServiceList", hotelserviceService.getHotelServiceList());
		
		return "HotelServices";	
	}
	
	
	/**
	 * Saves a hotel to the data store. It can be an existing hotel entity that is being edited
	 * or a new hotel entity to be added.
	 * 
	 * @param hotelServiceForm
	 * DTO that holds data for adding or editing a hotel service.
	 * 
	 * @param bindingResult
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageHotelServices
	 * 
	 */
	@PostMapping("/saveHotelService")
	public String saveHotelService(
			@Valid @ModelAttribute("hotelServiceForm") HotelServiceForm hotelServiceForm, 
			BindingResult bindingResult, 
			RedirectAttributes attr) {
		
		if(bindingResult.hasErrors()) {
			
			attr.addFlashAttribute("org.springframework.validation.BindingResult.hotelServiceForm", bindingResult);
			attr.addFlashAttribute("hotelServiceForm", hotelServiceForm);
			
			return "redirect:/admin/manageHotelServices";
		} 
		else {
				
			hotelserviceService.saveHotelService(hotelServiceForm);
			attr.addFlashAttribute("successMessage", "Hotel Service Was Successfully Saved");
			
			return "redirect:/admin/manageHotelServices";
		}
	}
	
	
	/**
	 * Renders the page for editing a hotel service.
	 * 
	 * @param hotelServiceId
	 * The id of the hotel service to be edited.
	 * 
	 * @param model
	 * 
	 * @return
	 * HotelServices.jsp whose form is populated with hotel service data.
	 * 
	 */
	@GetMapping("/editHotelService/{hotelServiceId}")
	public String editHotelService(@PathVariable("hotelServiceId") Integer hotelServiceId, Model model) {
		
		model.addAttribute("hotelServiceForm", hotelserviceService.getHotelServiceById(hotelServiceId));
		model.addAttribute("hotelServiceList", hotelserviceService.getHotelServiceList());
		
		return "HotelServices";
	}
	
	
	/**
	 * Removes the hotel service entity identified by hotelServiceId.
	 * 
	 * @param hotelServiceId
	 * The id of the hotel service to be removed.
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageHotelServices
	 * 
	 */
	@PostMapping("/removeHotelService")
	public String removeHotelService(
			@RequestParam("hotelServiceId") Integer hotelServiceId, 
			RedirectAttributes attr) {
		
		hotelserviceService.removeHotelService(hotelServiceId);
		attr.addFlashAttribute("successMessage", "Hotel Service Was Successfully Deleted");
		
		return "redirect:/admin/manageHotelServices";
	}
	
}
