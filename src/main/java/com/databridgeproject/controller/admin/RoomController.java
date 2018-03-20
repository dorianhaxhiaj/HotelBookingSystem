package com.databridgeproject.controller.admin;

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

import com.databridgeproject.dto.RoomForm;
import com.databridgeproject.service.RoomserviceService;

/**
 * Controller servicing ADMIN actions,
 * related to room management functions such as
 * displaying, adding, editing and removing rooms. 
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@Secured({ "ROLE_ADMIN" })
@RequestMapping("/admin")
public class RoomController {
	
	private static final Logger logger = Logger.getLogger(RoomController.class);
	
	@Autowired
	private com.databridgeproject.service.RoomService roomService;
	
	
	@Autowired
	private RoomserviceService roomserviceService;
	
	
	/**
	 * Displays Room Management Page: Room.jsp.
	 * In case of errors from POST saveRoom, this
	 * page will be rendered with the DTO and its errors. 
	 * 
	 * @param hotelId
	 * The id of the hotel, whose list of rooms this page
	 * manages.
	 * 
	 * @param model
	 * 
	 * @return
	 * Room.jsp
	 * 
	 */
	@GetMapping("/Hotel{hotelId}/manageRooms")
	public String showRoomManagementPage(@PathVariable("hotelId") Integer hotelId,  
			Model model) {
		
		if (!model.containsAttribute("roomForm")) {
			
			RoomForm roomForm = new RoomForm();
			roomForm.setHotelId(hotelId);
			model.addAttribute("roomForm", roomForm);
		}
		
		model.addAttribute("roomList", roomService.getRoomListByHotelId(hotelId));
		model.addAttribute("roomServiceList", roomserviceService.getRoomServiceList());
		
		return "Rooms";
	}
		
	
	
	/**
	 * Persist room entity based on the attributes of the RoomForm DTO.
	 * 
	 * @param roomForm
	 * DTO for collecting and validating UI data about room entity,
	 * which will be persisted.
	 * 
	 * @param bindingResult
	 * @param attr
	 * @param model
	 * 
	 * @return
	 * In case of success or expected user input error, redirect to
	 * /admin/Hotel{hotelId}/manageRooms with an appropriate success or error
	 * message. Otherwise if it is an unexpected error (like form tampering)
	 * redirect to /admin/manageHotels with appropriate error message.
	 * 
	 */
	@PostMapping("/saveRoom")
	public String saveRoom(@Valid @ModelAttribute("roomForm") RoomForm roomForm, 
			BindingResult bindingResult, RedirectAttributes attr) {
		
		if (bindingResult.hasErrors()) {
			
			attr.addFlashAttribute("org.springframework.validation.BindingResult.roomForm", bindingResult);
			attr.addFlashAttribute("roomForm", roomForm);
			return String.format("redirect:/admin/Hotel%d/manageRooms", roomForm.getHotelId());
		} 
		else {
			
			try {
			
				roomService.saveRoom(roomForm);
			} 
			catch (Exception e) {
				
				logger.info(e);
				attr.addFlashAttribute("errorMessage", "Room Service Failed To Save");
				return "redirect:/admin/manageHotels";
			}
			
			attr.addFlashAttribute("successMessage", "Room Was Saved Succesfully");
			return String.format("redirect:/admin/Hotel%s/manageRooms", roomForm.getHotelId());
		}
	}
	
	
	
	/**
	 * Fills the form of Rooms.jsp with the attributes of the room identified by
	 * the path variable roomId. The room entity identified by roomId is displayed
	 * to be modified by end-user.
	 * 
	 * @param roomId
	 * The id of the room to be edited.
	 * 
	 * @param model
	 * 
	 * @return
	 * Room.jsp, whose form is filled with the attributes of the room identified by
	 * roomId path variable.
	 * 
	 */
	@GetMapping("/editRoom/{roomId}")
	public String editRoom(@PathVariable("roomId") Integer roomId, Model model) {
		
		model.addAttribute("roomForm", roomService.getRoomFormById(roomId));
		model.addAttribute("roomServiceList", roomserviceService.getRoomServiceList());
		model.addAttribute("roomList", roomService.getRoomList());
		
	    return "Rooms";
	}
	
	
	
	
	/**
	 * Removes the Room entity identified by the path variable roomId.
	 * 
	 * @param roomId
	 * The id of the room to be removed.
	 * 
	 * @param attr
	 * For passing through the server session, the dto, its error messages
	 * and the success message.
	 * 
	 * @param model
	 * 
	 * @return
	 * A redirect to the Room Management Page - /admin/Hotel{hotelId}/manageRooms
	 * 
	 */
	@PostMapping("/removeRoom")
	public String removeRoom(@RequestParam("roomId") Integer roomId, 
			RedirectAttributes attr){
			
		Integer hotelId = roomService.getRoomFormById(roomId).getHotelId();
		roomService.removeRoom(roomId);
		
		attr.addFlashAttribute("successMessage", "Room Was Successfully Deleted");
		
		return String.format("redirect:/admin/Hotel%d/manageRooms", hotelId);
	}
}
