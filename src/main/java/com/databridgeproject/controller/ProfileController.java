package com.databridgeproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.databridgeproject.dto.ProfileForm;
import com.databridgeproject.service.UserService;


/**
 * Controller servicing authenticated user actions,
 * related to managing the currently logged in user's
 * own profile information.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Controller
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
public class ProfileController {

	
	/**
	 * Service Layer Interface for managing user transactions.
	 * 
	 */
	@Autowired
	private UserService userService;
	
	
	/**
	 * Shows the page for editing the users own profile page,
	 * by rendering EditProfile.jsp page with the currently logged in 
	 * user profile details.
	 * 
	 * @param model
	 * 
	 * @return
	 * EditProfile.jsp rendered with the currently logged
	 *  in user's profile details.
	 * 
	 */
	@GetMapping("/editProfile")
	public String showEditProfilePage(Model model) {
		
		if (!model.containsAttribute("profileForm")) {
			
			model.addAttribute("profileForm", userService.getProfileFormOfCurrentUser());
		}
		
		return "EditProfile";
	}
	
	
	
	/**
	 * Processes the new profile data of the currently logged in user; in case the
	 * data is valid then it is permanently stored and a success message is stored in the
	 * session, or in case the data is invalid errors messages are prepared in the session
	 * for display. In both cases the user is redirected to the /editProfile GET form, where
	 * the success or error messages will be displayed.
	 * 
	 * @param profileForm
	 * The modified profile data of the currently logged in user.
	 * 
	 * @param bindingResult
	 * To check if view model binding was successful, and to hold the generated error messages.
	 * 
	 * @param attr
	 * For storing the success or error messages in session between the redirect and GET form.
	 * 
	 * @return
	 * A HTTP 302 redirect to /editProfile.
	 * 
	 */
	@PostMapping("/editProfile")
	public String editProfile(
			@Valid @ModelAttribute("profileForm") ProfileForm profileForm,
			BindingResult bindingResult, RedirectAttributes attr) {
		
		if (bindingResult.hasErrors()) {
			
			attr.addFlashAttribute("org.springframework.validation.BindingResult.profileForm", bindingResult);
			attr.addFlashAttribute("profileForm", profileForm);
			
			return "redirect:/editProfile";
		}
		
		userService.saveProfileFormOfCurrentUser(profileForm);
		
		attr.addFlashAttribute("successMessage", "Profile Was Modified Successfully");
		
		return "redirect:/editProfile";
	}
}
