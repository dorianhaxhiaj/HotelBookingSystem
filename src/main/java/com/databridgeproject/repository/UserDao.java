package com.databridgeproject.repository;

import java.util.List;

import com.databridgeproject.domain.Pager;
import com.databridgeproject.domain.User;


/**
 * Data Access Object Interface for
 * accessing and manipulating User entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
public interface UserDao {
	
	public void saveUser(User newUser);
	public void removeUser(String username);
	public User getUserByUsername(String username);
	public boolean userExists(String username);
	public List<User> getUserList(Pager pager);
	public Long getNumberOfUsers();
}
