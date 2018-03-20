package com.databridgeproject.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.databridgeproject.constraint.FirstEqualsSecond;
import com.databridgeproject.constraint.UsernameUnique;

/**
 * DTO class that contains form data for
 * registering a new user to the system.
 * 
 * @author Dorian Haxhiaj
 *
 */
@FirstEqualsSecond(first = "password", second = "confirmPassword", 
	message = "Passwords do not match.")
public class UserForm {
	
	/**
	 * Username of the new user.
	 */
	@UsernameUnique(message = "This username is already taken.")
	@NotEmpty(message = "Please enter your username.")
	private String username;
	
	
	/**
	 * Clear-text password of the new user.
	 */
	@NotEmpty(message = "Please enter your password.")
	private String password;
	
	
	/**
	 * Re-typed password.
	 */
	private String confirmPassword;
	
	
	/**
	 * First name of the user.
	 */
	@NotEmpty(message = "Please enter your first name.")
	private String firstName;
	
	
	/**
	 * Last name of the user.
	 */
	@NotEmpty(message = "Please enter your last name.")
	private String lastName;
	
	
	/**
	 * Birthday of the user.
	 */
	@NotNull(message = "Please enter your birthday")
	@Past(message = "Please enter a valid birthday")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthday;
	
	/**
	 * Email of the user.
	 */
	@NotEmpty(message = "Please enter your email address.")
	@Email(message = "Please enter a valid email address.")
	private String email;

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}
}
