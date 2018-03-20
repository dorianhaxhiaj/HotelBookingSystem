package com.databridgeproject.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.databridgeproject.dto.RoomServiceForm;
import com.databridgeproject.service.RoomserviceService;


/**
 * Controller servicing ADMIN actions,
 * related to room service management functions such as
 * displaying, adding, editing and removing room services. 
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@Secured({ "ROLE_ADMIN" })
@RequestMapping("/admin")
public class RoomServiceController {
	
	/**
	 * Service layer interface for manipulating room service entity transactions.
	 */
	@Autowired
	RoomserviceService roomserviceService;
	
	
	/**
	 * Render the page for managing room services.
	 * 
	 * @param model
	 * 
	 * @return
	 * RoomServices.jsp
	 * 
	 */
	@GetMapping("/manageRoomServices")
	public String showRoomServiceManagementPage(
			@RequestParam(value = "successMessage", required = false) String successMessage,
			ModelMap model) {
		
		model.put("roomServiceForm", new RoomServiceForm());
		model.put("roomServiceList", roomserviceService.getRoomServiceList());
		model.put("successMessage", successMessage);
		
		return "RoomServices";
	}
	
	
	/**
	 * Saves a room service to the data store. It can be an existing room service entity that is being edited
	 * or a new room service entity to be added.
	 * 
	 * @param roomServiceForm
	 * DTO that holds data for adding or editing a room service.
	 * 
	 * @param bindingResult
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageRoomServices
	 * 
	 */
	@PostMapping("/saveRoomService")
	public String addRoomService(
			@Valid @ModelAttribute("roomServiceForm") RoomServiceForm roomServiceForm, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelMap model) {
		
		if (bindingResult.hasErrors()) {
			
			redirectAttributes.addFlashAttribute(
					"org.springframework.validation.BindingResult.roomServiceForm",
					bindingResult);
			redirectAttributes.addFlashAttribute("roomServiceForm", roomServiceForm);
			
			return "redirect:/admin/manageRoomServices";
		} 
		else {
			
			roomserviceService.saveRoomService(roomServiceForm);
			model.put("successMessage", "Room Service saved sucesfully");
			
			return "redirect:/admin/manageRoomServices";
		}
	}
	
	
	/**
	 * Renders the page for editing a room service.
	 * 
	 * @param roomServiceId
	 * The id of the room service to be edited.
	 * 
	 * @param model
	 * 
	 * @return
	 * RoomServices.jsp whose form is populated with room service data.
	 * 
	 */
	@GetMapping("/editRoomService/{roomServiceId}")
	public String editRoomService(
			@PathVariable("roomServiceId") Integer roomServiceId,
			ModelMap model) {
		
		model.put("roomServiceForm", roomserviceService.getRoomServiceById(roomServiceId));
		model.put("roomServiceList", roomserviceService.getRoomServiceList());
		
		return "RoomServices";
	}
	
	
	
	/**
	 * Removes the room service entity identified by roomServiceId.
	 * 
	 * @param roomServiceId
	 * The id of the room service to be removed.
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageRoomServices
	 * 
	 */
	@PostMapping("/removeRoomService")
	public String removeRoomService(@RequestParam("roomServiceId") Integer roomServiceId,
			RedirectAttributes attr) {
		
		roomserviceService.removeRoomService(roomServiceId);
		
		attr.addFlashAttribute("successMessage", "Room Service Was Deleted Successfully");
		
		return "redirect:/admin/manageRoomServices";
	}
	
}
