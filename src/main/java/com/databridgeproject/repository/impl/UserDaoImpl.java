package com.databridgeproject.repository.impl;


import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.databridgeproject.domain.Pager;
import com.databridgeproject.domain.User;
import com.databridgeproject.repository.UserDao;


/**
 * Data Access Object Implementation for
 * accessing and manipulating User entities.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	
	/**
	 * Used for logging DAO operations.
	 * 
	 */
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	
	/**
	 * Factory used for retrieving a session object.
	 * 
	 */
	@Autowired
	private SessionFactory sessionFactory;


	
	/**
	 * Persist newHotel to datastore.
	 *  
	 */
	@Override
	public void saveUser(User newUser){
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(newUser);
	
		logger.info("User saved successfully, User Details = " + newUser);
	}
	
	
	/**
	 * Fetch hotel entity from datastore using its hotelId.
	 * 
	 */
	@Override
	public void removeUser(String username) {
		
		Session session = sessionFactory.getCurrentSession();
		User userToDelete = (User) session.load(User.class, username);
		
		if (null != userToDelete) {
			session.delete(userToDelete);
		}
		
		logger.info("User deleted successfully, User Details = " + userToDelete);
	}
	
	
	/**
	 * Fetch User entity identified by username parameter.
	 * 
	 */
	@Override
	public User getUserByUsername(String username){
		
		Session session = sessionFactory.getCurrentSession();
		User fetchedUser = (User) session.load(User.class,username);
		
		logger.info("User loaded successfully, User Details = " + fetchedUser);
		return fetchedUser;	
	}
	
	/**
	 * Checks if the user identified by username parameter
	 * is present in the persistent data store.
	 * 
	 * @param username
	 * The identifier of the user.
	 * 
	 * @return
	 * True if the user with such username exists in the
	 * persistent data store, otherwise false.
	 * 
	 */
	@Override
	public boolean userExists(String username) {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<Long> countQuery = session.createQuery(
				"SELECT COUNT(u) FROM User u WHERE u.username = :username",
				Long.class);
		countQuery.setParameter("username", username);
		
		return countQuery.getSingleResult() == 1;
	}
	
	
	/**
	 * Fetch list of all User entities from datastore.
	 * 
	 */
	@Override
	public List<User> getUserList(Pager pager) {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<User> userListQuery = session.createQuery("FROM User u ORDER BY u.username", User.class)
				.setFirstResult((pager.getCurrentPage() - 1) * pager.getPageSize())
				.setMaxResults(pager.getPageSize());
		
		List<User> userList = userListQuery.getResultList();
		
		return userList;
	}
	
	
	/**
	 * Gets the number of all Room entities in the system.
	 * 
	 */
	@Override
	public Long getNumberOfUsers() {
		
		Session session = sessionFactory.getCurrentSession();
		
		TypedQuery<Long> countQuery = session.createQuery(
				"SELECT COUNT(u) FROM User u",
				Long.class);
		
		return countQuery.getSingleResult();
	}
}
