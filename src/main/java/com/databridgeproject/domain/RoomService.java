package com.databridgeproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity for roomservice table,
 * which is simply a service offered by
 * a room such as Fridge or TV.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Entity
@Table(name = "roomservice")
public class RoomService {
	
	
	/**
	 * Auto-generated (by the database) primary key of
	 * this entity.
	 * 
	 */
	@Id
	@Column(name = "roomserviceid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomServiceId;
	
	
	/**
	 * The name of this service.
	 * 
	 */
	@NotEmpty
	@Column(name = "name")
	private String name;
	
	
	// *****************************************************
	// List of Setters and Getters *************************
	// *****************************************************
	
	/**
	 * @return the roomServiceId
	 */
	public Integer getRoomServiceId() {
		return roomServiceId;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	            append(this.getName()).
	            toHashCode();
	}
	
	
	/**
	 * Checks whether obj passed as parameter and
	 * the instance of this class are the equal.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof RoomService))
            return false;
        if (obj == this)
            return true;

        RoomService other = (RoomService) obj;
        
        return new EqualsBuilder().
            append(this.getRoomServiceId(), other.getRoomServiceId()).
            append(this.getName(), other.getName()).
            isEquals();
	}
	
	
	/**
	 * String representation of the state of an instance of this class.
	 * 
	 */
	@Override
	public String toString() {
		return String.format("RoomService [roomServiceId = %d, name = %s]", this.getRoomServiceId(), this.getName());
	}
}
