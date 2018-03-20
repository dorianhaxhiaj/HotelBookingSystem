package com.databridgeproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Authority;
import com.databridgeproject.domain.Pager;
import com.databridgeproject.domain.User;
import com.databridgeproject.domain.UserProfile;
import com.databridgeproject.dto.ProfileForm;
import com.databridgeproject.dto.UserForm;
import com.databridgeproject.dto.UserPage;
import com.databridgeproject.error.ResourceNotFoundException;
import com.databridgeproject.repository.UserDao;
import com.databridgeproject.service.UserService;


/**
 * Service Layer Implementation exposing methods for
 * accessing and manipulating User entities transactions.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	
	/**
	 * Data Access Object Interface for User entity.
	 * 
	 */
	@Autowired
	private UserDao userDao;
	
	
	/**
	 * Add a new User entity to the datastore.
	 * 
	 */
	@Override
	public void addUser(UserForm newUserForm) {
		
		addUser(newUserForm, "ROLE_USER");
	}
	
	
	/**
	 * Add a new User entity to the datastore.
	 * 
	 */
	@Override
	public void addAdminUser(UserForm userForm) {
		
		addUser(userForm, "ROLE_ADMIN");
	}
	
	
	/**
	 * Update a modified User entity.
	 * 
	 */
	@Override
	public void updateUser(User modifiedUser) {
		
		userDao.saveUser(modifiedUser);	
	}
	
	
	/**
	 * Fetch a UserForm DTO filled with the data of the User entity
	 * identified by his/her username.
	 * 
	 * @param username
	 * The username of User whose data is going to be used to fill
	 * UserForm DTO.
	 * 
	 * @return
	 * UserForm DTO.
	 * 
	 */
	@Override
	public UserForm getUserFormByUsername(String username) {
		
		User fetchedUser = userDao.getUserByUsername(username);
		UserForm userForm = new UserForm(); 
		userForm.setUsername(fetchedUser.getUsername());
		userForm.setPassword("");
		userForm.setConfirmPassword("");
		userForm.setFirstName(fetchedUser.getUserProfile().getForename());
		userForm.setLastName(fetchedUser.getUserProfile().getSurname());
		userForm.setBirthday(fetchedUser.getUserProfile().getBirthday());
		userForm.setEmail(fetchedUser.getUserProfile().getEmail());
		
		return userForm;
	}
	
	
	/**
	 * Fetches a ProfileForm data filled with personal data of the currently
	 * logged in user.
	 * 
	 * @return
	 * A ProfileForm DTO filled with personal data of the currently logged in
	 * user.
	 * 
	 */
	@Override
	public ProfileForm getProfileFormOfCurrentUser() {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserProfile currentUserProfile = userDao.getUserByUsername(username).getUserProfile();
		
		ProfileForm profileForm = new ProfileForm();
		profileForm.setFirstName(currentUserProfile.getForename());
		profileForm.setLastName(currentUserProfile.getSurname());
		profileForm.setBirthday(currentUserProfile.getBirthday());
		profileForm.setEmail(currentUserProfile.getEmail());
		
		return profileForm;
	}
	
	@Override
	public void saveProfileFormOfCurrentUser(ProfileForm profileForm) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userDao.getUserByUsername(username);
		
		UserProfile currentUserProfile = currentUser.getUserProfile();
		currentUserProfile.setForename(profileForm.getFirstName());
		currentUserProfile.setSurname(profileForm.getLastName());
		currentUserProfile.setBirthday(profileForm.getBirthday());
		currentUserProfile.setEmail(profileForm.getEmail());
		
		userDao.saveUser(currentUser);
	}
	
	
	/**
	 * Checks if username parameter is already in used by
	 * other user entity.
	 * 
	 * @param username
	 * The username to be checked.
	 * 
	 */
	@Override
	public boolean isUsernameTaken(String username) {
		
		return userDao.userExists(username); 
	}
	
	/**
	 * Remove User entity using its username.
	 * 
	 */
	@Override
	public void removeUser(String username) {
		
		userDao.removeUser(username);
	}
	
	
	/**
	 * Fetch a page of User entities for display.
	 * 
	 */
	@Override
	public UserPage getUserPage(Integer currentPage) throws ResourceNotFoundException {
		
		Pager roomPager = new Pager(currentPage, UserPage.DEFAULT_PAGE_SIZE);
		
		UserPage userPage = new UserPage();
		userPage.setCurrentPage(currentPage);
		userPage.setUserList(userDao.getUserList(roomPager));
		userPage.setNumberOfUsers(userDao.getNumberOfUsers());
		
		if ((currentPage < 1 || currentPage > userPage.getLastPage()) &&
				userPage.getNumberOfUsers() != 0) {
			
			throw new ResourceNotFoundException("Requested Page Does Not Exist.");
		}
		
		return userPage;
	}
	
	
	/**
	 * Auxiliary method for adding a new user based on userForm data.
	 * The user will take the role specified by the role parameter.
	 * 
	 * @param newUserForm
	 * The DTO whose data will be used to create a new user.
	 * 
	 * @param role
	 * The role of the new user in the system.
	 * 
	 */
	private void addUser(UserForm newUserForm, String role) {
		
		User newUser = new User();
		newUser.setUsername(newUserForm.getUsername());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(newUserForm.getPassword()));
		newUser.setEnabled(true);
		
		Authority normalUserAuth = new Authority();
		normalUserAuth.setUsername(newUserForm.getUsername());
		normalUserAuth.setAuthority(role);
		normalUserAuth.setUser(newUser);
		newUser.setAuthority(normalUserAuth);
		
		UserProfile newUserProfile = new UserProfile();
		newUserProfile.setUsername(newUserForm.getUsername());
		newUserProfile.setForename(newUserForm.getFirstName());
		newUserProfile.setSurname(newUserForm.getLastName());
		newUserProfile.setBirthday(newUserForm.getBirthday());
		newUserProfile.setEmail(newUserForm.getEmail());
		newUserProfile.setUser(newUser);
		newUser.setUserProfile(newUserProfile);
		
		userDao.saveUser(newUser);	
	}
}
