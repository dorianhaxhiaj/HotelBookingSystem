package com.databridgeproject.error;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles global ResourceNotFoundExceptions, by replying
 * with a custom 404 Error page and HTTP 404 status.
 * 
 * @author Dorian Haxhiaj
 *
 */
@ControllerAdvice
public class ResourceNotFoundMapper {

	@ExceptionHandler({ ResourceNotFoundException.class, ObjectNotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleNotFound(Exception e, Model model) {
		
		model.addAttribute("exception", e);
		return "errors/Error404";
	}
}
