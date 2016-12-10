package com.dw.demo.rest.dto;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;

import com.dw.demo.entities.Gender;

/**
 * DTO for creating a student record
 * 
 * @author rgunderson
 */
public class BaseStudentDto {

	@NotNull
	@FormParam("firstName")
	String firstName;

	@NotNull
	@FormParam("lastName")
	String lastName;

	@NotNull
	@FormParam("email")
	String email;
	
	@NotNull
	@FormParam("gender")
	Gender gender;

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
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseStudentDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender
				+ "]";
	}
}
