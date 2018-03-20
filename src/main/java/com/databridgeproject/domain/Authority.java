package com.databridgeproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Entity for authorities table,
 * part of the default schema for Spring Security.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Entity
@Table(name = "authorities")
public class Authority {
	
	
	/**
	 * Primary key of this entity and foreign key of
	 * of User entity
	 */
	@Id
	@Column(name = "username")
	private String username;
	
	
	/**
	 * Defines the role of the of the user
	 */
	@Column(name= "authority")
	private String authority;
	
	
	/**
	 * One to one relationship with entity User,
	 * where User is the parent and this
	 * entity is a child
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private User user;
	
	
	// *****************************************************
	// List of Setters and Getters *************************
	// *****************************************************
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}


	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	// *****************************************************
	// END of Setters and Getters **************************
	// *****************************************************



	/**
	 * Calculates the hash code for an instance of this class.
	 * 
	 */
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 31).
	            append(this.getUsername()).
	            append(this.getAuthority()).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Authority))
            return false;
        if (obj == this)
            return true;

        Authority other = (Authority) obj;
        
        return new EqualsBuilder().
            append(this.getUsername(), other.getUsername()).
            append(this.getAuthority(), other.getAuthority()).
            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("Authority [username = %s, authority = %s]", 
				this.getUsername(), 
				this.getAuthority());
	}
}
