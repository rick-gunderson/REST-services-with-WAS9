package com.dw.demo.rest.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Link;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dw.demo.entities.Student;
import com.dw.demo.rest.serializers.CalendarSerializer;
import com.dw.demo.rest.serializers.LinksSerializer;

/**
 * DTO class for serializing a student record with HATEOAS links.
 * 
 * @author rgunderson
 */
@JsonPropertyOrder({ "id", "firstName", "lastName", "gender", "email", "registeredOn", "studentNumber", "_links" })
@JsonTypeName("student")
public class HateoasStudentDto extends BaseStudentDto {
	
	private String id;
	
	private Calendar registeredOn;
	
	private String studentNumber;

	private List<Link> links;

	public HateoasStudentDto() {
		super();
	}

	public HateoasStudentDto(Student student) {
		this.setEmail(student.getEmail());
		this.setFirstName(student.getFirstName());
		this.setGender(student.getGender());
		this.setId(student.getId());
		this.setLastName(student.getLastName());
		this.setRegisteredOn(student.getRegisteredOn());
		this.setStudentNumber(student.getStudentNumber());
		this.links = new ArrayList<Link>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dw.demo.entities.Student#getRegisteredOn()
	 */
	@JsonSerialize(using = CalendarSerializer.class)
	public Calendar getRegisteredOn() {
		return this.registeredOn;
	}

	/**
	 * @param registeredOn the registeredOn to set
	 */
	public void setRegisteredOn(Calendar registeredOn) {
		this.registeredOn = registeredOn;
	}

	/**
	 * @return the studentNumber
	 */
	public String getStudentNumber() {
		return studentNumber;
	}

	/**
	 * @param studentNumber the studentNumber to set
	 */
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	@JsonProperty("_links")
	@JsonSerialize(using = LinksSerializer.class)
	public List<Link> getLinks() {
		return links;
	}

	public void addLink(Link link) {
		if (this.links == null) {
			this.links = new ArrayList<Link>();
		}
		this.links.add(link);
	}
}
