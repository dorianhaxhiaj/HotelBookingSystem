package com.databridgeproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.databridgeproject.dto.UserForm;
import com.databridgeproject.service.UserService;


/**
 * Controller for managing unauthorized user actions 
 * related to registration: viewing the registration form
 * and submitting it.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@PreAuthorize("isAnonymous()")
public class RegisterController {

	/**
	 * Service layer interface for doing user entity transactions.
	 */
	@Autowired
	private UserService userService;
	
	
	/**
	 * Shows the registration page for new users, by rendering Register.jsp page.
	 * 
	 * @param model
	 * 
	 * 
	 * @return
	 * Register.jsp
	 * 
	 */
	@GetMapping("/register")
	public String showRegistrationPage(Model model) {
		
		if (!model.containsAttribute("userForm")) {
			
			model.addAttribute("userForm", new UserForm());
		}
		
		return "Register";
	}
	
	
	
	/**
	 * Processes form data of Register.jsp, creating a new normal user account
	 * when this data is valid and storing a success message in flash scoped session
	 * to be displayed on Home page redirect. In case the data is invalid, error
	 * messages are stored in flash scoped session and a redirect is issued for /register
	 * GET form to display said errors.
	 * 
	 * @param userForm
	 * Form containing data about a new user.
	 * 
	 * @param bindingResult
	 * 
	 * @param attr
	 * 
	 * @return
	 * A redirect to / (Home page) in case of success,
	 * otherwise a redirect to /register (registration page).
	 * 
	 */
	@PostMapping("/register")
	public String register(
			@Valid @ModelAttribute("userForm") UserForm userForm,
			BindingResult bindingResult, 
			RedirectAttributes attr) {
		
		if (bindingResult.hasErrors()) {
		
			attr.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
			attr.addFlashAttribute("userForm", userForm);
			
			return "redirect:/register";
	    }

		
		userService.addUser(userForm); 
		
		attr.addFlashAttribute("successMessage", "Account Registered Succesfully");
		
		return "redirect:/";
	}
}
