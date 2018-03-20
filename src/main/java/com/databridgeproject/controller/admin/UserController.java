package com.databridgeproject.controller.admin;

import java.util.Optional;

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

import com.databridgeproject.dto.UserForm;
import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.service.UserService;


/**
 * Controller servicing ADMIN actions,
 * related to user management functions such as
 * displaying, adding an ADMIN user and removing users. 
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@Secured({ "ROLE_ADMIN" })
@RequestMapping("/admin")
public class UserController {
	
	/**
	 * Service layer interface for managing user entity transaction.
	 */
	@Autowired
	private UserService userService;

	
	/**
	 * Renders the page for managing users.
	 * 
	 * @param currentPageOpt
	 * Optional path variable for specifying which page of users to be displayed.
	 * 
	 * @param model
	 * 
	 * @return
	 * Users.jsp
	 * 
	 * @throws ResourceNotFoundException
	 * A 404 error in case currentPage path variable is out of bounds.
	 * 
	 */
	@GetMapping({ "/manageUsers", "/manageUsers/Page{currentPage}" })
	public String showUserManagementPage(
			@PathVariable("currentPage") Optional<Integer> currentPageOpt,
			Model model) throws ResourceNotFoundException {
		
		Integer currentPage = 1;
		
		if (currentPageOpt.isPresent()) {
			currentPage = currentPageOpt.get();
		}
		
		if (!model.containsAttribute("userForm")) {
			
			model.addAttribute("userForm", new UserForm());
		}
		
		model.addAttribute("userPage", userService.getUserPage(currentPage));
		     
		return "Users";
	}

	
	/**
	 * Adds a new admin user to the data store.
	 * 
	 * @param userForm
	 * DTO that holds data for adding a new admin user.
	 * 
	 * @param bindingResult
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageUsers
	 * 
	 */
	@PostMapping("/saveAdminUser")
	public String saveAdminUser(@Valid @ModelAttribute("userForm") final UserForm userForm,
			final BindingResult bindingResult, 
			RedirectAttributes attr,
			Model model) {
		
		if (bindingResult.hasErrors()) {
		
			attr.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
			attr.addFlashAttribute("userForm", userForm);
			
			return "redirect:/admin/manageUsers";
	    } 
		else {
			
	    	userService.addAdminUser(userForm); 
			 
			attr.addFlashAttribute("successMessage", "User Was Saved Succesfully");
			 
			return "redirect:/admin/manageUsers";
		}
		
	}


	/**
	 * Removes the user entity identified by username.
	 * 
	 * @param username
	 * The username of the User to be removed.
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to /admin/manageUsers
	 * 
	 */
	@PostMapping("/removeUser")
	public String removeUser(
			@RequestParam("username") String username,
			RedirectAttributes attr) {
		
		userService.removeUser(username);
		attr.addFlashAttribute("successMessage", "User Was Deleted Successfully");
		
		return "redirect:/admin/manageUsers";
	}
}