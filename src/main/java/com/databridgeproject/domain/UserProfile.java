package com.databridgeproject.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Entity for userprofile table,
 * which hold personal information about
 * a particular user account.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Entity
@Table(name = "userprofile")
public class UserProfile {
	
	/**
	 * Primary key of this entity,
	 * username that identifies account in the system.
	 * Also serves as foreign key in a one-to-one relationship
	 * with User entity.
	 * 
	 */
	@Id
	@Column(name = "username")
	private String username;
	
	
	/**
	 * Forename of the user.
	 * 
	 */
	@NotEmpty
	@Column(name = "forename")
	private String forename;
	
	
	/**
	 * Surname of the user.
	 * 
	 */
	@NotEmpty
	@Column(name = "surname")
	private String surname;
	
	
	/**
	 * Birthday of the user.
	 * 
	 */
	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "birthday")
	private Date birthday;
	
	
	/**
	 * Email address of the user.
	 * 
	 */
	@NotEmpty
	@Email
	@Column(name = "email")
	private String email;
	
	
	/**
	 * User entity that holds this information.
	 * One-to-one relationship with User entity,
	 * where User is parent and this UserProfile
	 * entity is child.
	 * 
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
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}


	/**
	 * @param forename the forename to set
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}


	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}


	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
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
	            append(this.getForename()).
	            append(this.getSurname()).
	            append(this.getBirthday()).
	            append(this.getEmail()).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof UserProfile))
            return false;
        if (obj == this)
            return true;

        UserProfile other = (UserProfile) obj;
        
        return new EqualsBuilder().
	    		append(this.getUsername(), other.getUsername()).
	            append(this.getForename(), other.getForename()).
	            append(this.getSurname(), other.getSurname()).
	            append(this.getBirthday(), other.getBirthday()).
	            append(this.getEmail(), other.getEmail()).
	            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("UserProfile [username = %s, forename = %s, surname = %s, birthday = %s, email = %s]",
				this.getUsername(), this.getForename(), this.getSurname(), this.getBirthday(), this.getEmail());
	}
}
