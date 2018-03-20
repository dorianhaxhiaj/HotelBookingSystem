package com.databridgeproject.domain;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;


import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 * Entity for the users table,
 * part of the default schema for Spring Security.
 * Hold the essential information for validation of the user,
 * together with the Authority entity.
 * Also it contains a reference to one-to-one child
 * entity, UserDetails, that contains personal information
 * about the user.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Entity
@Table(name="users")
public class User{
	
	
	/**
	 * The primary key of this entity.
	 * 
	 */
	@Id
	@Column(name = "username")
	private String username;
	
	
	/**
	 * BCrypt hash of the password entered by the creator of
	 * this user account.
	 * 
	 */
	@NotEmpty
	@Column(name = "password")
	private String password;
	
	
	/**
	 * Flag that checks if the user account is enabled (true)
	 * or disabled (false).
	 * 
	 */
	@NotNull
	@Column(name = "enabled")
	private boolean enabled;
   
	
	/**
	 * One-to-one relationship with Authority entity, which
	 * defines the role for a User entity.
	 *  
	 */
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Authority authority;
   
	
	/**
	 * One-to-one relationship with UserProfile entity, which
	 * holds the personal information of the user.
	 * 
	 */
    @Valid
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER,mappedBy = "user")
    private UserProfile userProfile;
	
    
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}


	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * @return the authority
	 */
	public Authority getAuthority() {
		return authority;
	}


	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}


	/**
	 * @return the userProfile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}


	/**
	 * @param userProfile the userProfile to set
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	// *****************************************************
	// END of Setters and Getters *************************
	// *****************************************************
	


	/**
	 * Calculates the hash code for an instance of this class.
	 * 
	 */
	@Override
	public int hashCode() {
		
		return new HashCodeBuilder(17, 31).
	            append(this.getUsername()).
	            append(this.getPassword()).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof Hotel))
            return false;
        if (obj == this)
            return true;

        User other = (User) obj;
        
        return new EqualsBuilder().
            append(this.getUsername(), other.getUsername()).
            append(this.getPassword(), other.getPassword()).
            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("User [username = %s, password = %s]", this.getUsername(), this.getPassword());
	}
}
