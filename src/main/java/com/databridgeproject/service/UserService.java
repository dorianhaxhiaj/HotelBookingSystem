package com.databridgeproject.service;

import com.databridgeproject.domain.User;
import com.databridgeproject.dto.ProfileForm;
import com.databridgeproject.dto.UserForm;
import com.databridgeproject.dto.UserPage;
import com.databridgeproject.error.ResourceNotFoundException;


/**
 * Service Layer Interface exposing business methods for
 * accessing and manipulating User entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface UserService {
	
	/**
	 * Add a new User entity to the datastore.
	 * 
	 */
	public void addUser(UserForm newUserForm);
	
	/**
	 * Add a new User entity with Admin role to the datastore.
	 * 
	 */
	public void addAdminUser(UserForm userForm);
	
	/**
	 * Update a modified User entity.
	 * 
	 */
	public void updateUser(User modifiedUser);
	
	/**
	 * Remove User entity using its username.
	 * 
	 */
	public void removeUser(String username);
	
	/**
	 * Fetch User entity using its username.
	 * 
	 * @return
	 * A form filled with the data of the user identified by username.
	 * 
	 */
	public UserForm getUserFormByUsername(String username);
	
	/**
	 * Returns the ProfileForm DTO constructed by the attributes of the
	 * currently logged in user.
	 * 
	 * @return
	 * ProfileForm DTO of currently logged in user.
	 * 
	 */
	public ProfileForm getProfileFormOfCurrentUser();
	
	public void saveProfileFormOfCurrentUser(ProfileForm profileForm);
	
	/**
	 * Check if username parameter is already in use by another
	 * user entity in the system.
	 * 
	 * @param username
	 * The username that is going to be checked for uniqueness.
	 * 
	 * @return
	 * True if the username is unique, false otherwise.
	 * 
	 */
	public boolean isUsernameTaken(String username);
	
	/**
	 * Fetch page of User entities.
	 * 
	 * @param currentPage
	 * The number of page to be fetched.
	 * 
	 * @return
	 * The page of user entities.
	 * 
	 */
	public UserPage getUserPage(Integer currentPage) throws ResourceNotFoundException;
}
