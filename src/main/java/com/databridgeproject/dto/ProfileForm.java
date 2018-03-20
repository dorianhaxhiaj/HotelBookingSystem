package com.databridgeproject.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * DTO class that contains form data for
 * editing the profile of a user.
 * 
 * @author Dorian Haxhiaj
 *
 */
public class ProfileForm {

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
	@NotEmpty(message = "Please enter your email.")
	@Email(message = "Please enter a valid email.")
	private String email;


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
