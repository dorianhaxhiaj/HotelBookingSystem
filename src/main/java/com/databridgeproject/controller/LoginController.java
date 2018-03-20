package com.databridgeproject.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller servicing unauthenticated user actions,
 * related to login, AKA authenticating to this system.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@PreAuthorize("isAnonymous()")
public class LoginController {
	
	/**
	 * Show the login page, by rendering the Login.jsp file.
	 * 
	 * @return
	 * Login.jsp
	 * 
	 */
	@GetMapping("/login")
	public String showLoginPage(Model model) {
		
		return "Login";
	}
}
