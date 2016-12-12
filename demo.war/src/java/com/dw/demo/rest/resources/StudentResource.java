package com.dw.demo.rest.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.dw.demo.ejbs.IStudentService;
import com.dw.demo.entities.Student;
import com.dw.demo.rest.dto.BaseStudentDto;
import com.dw.demo.rest.dto.HateoasStudentDto;

/**
 * This class provides the "students" REST service. It supports the following HTTP methods:
 * <p>
 * GET - get all student records or a particular student record
 * </p>
 * <p>
 * POST - create a new student record
 * </p>
 * <p>
 * PUT - update a student record
 * </p>
 * <p>
 * DELETE - delete a student record
 * </p>
 *
 * @author rgunderson
 *
 */
@Path("/students")
@Stateless
public class StudentResource {

	/**
	 * The name of this class
	 */
	public static final String className = StudentResource.class.getName();

	/**
	 * The logger to use for this class
	 */
	private static final Logger logger = Logger.getLogger(className);

	private static final String LINK_SELF = "self";

	@EJB
	IStudentService studentService;

	/**
	 * Create a new student record
	 *
	 * @param uriInfo
	 *            URI information for the current request
	 * @param student
	 *            Student record to create
	 * @return JAX-RS Response object with the URI (location) of the new student record
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Context UriInfo uriInfo, BaseStudentDto student) {
		final String methodName = "create";
		logger.entering(className, methodName);
		try {
			Student newStudent = createNewStudent(student);
			URI uri = uriInfo.getBaseUriBuilder().path(StudentResource.class).path(newStudent.getId()).build();
			return Response.created(uri).build();
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 * Create a new student record from a submitted form using a bean parameter to aggregate the form parameters
	 * 
	 * @param uriInfo
	 * @param student
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createFromForm(@Context UriInfo uriInfo, @Valid @BeanParam BaseStudentDto student) {
		final String methodName = "createFromForm";
		logger.entering(className, methodName);
		try {
			Student newStudent = createNewStudent(student);
			URI uri = uriInfo.getBaseUriBuilder().path(StudentResource.class).path(newStudent.getId()).build();
			return Response.created(uri).build();
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/*
	 * Helper for creating new student records
	 */
	private Student createNewStudent(BaseStudentDto student) {
		Student newStudent = new Student();
		newStudent.setEmail(student.getEmail());
		newStudent.setFirstName(student.getFirstName());
		newStudent.setLastName(student.getLastName());
		newStudent.setGender(student.getGender());
		newStudent = studentService.create(newStudent);
		return newStudent;
	}

	/**
	 * Delete a particular student record
	 *
	 * @param id
	 *            The id of the student record to delete
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		final String methodName = "delete";
		logger.entering(className, methodName);
		try {
			studentService.delete(id);
			return Response.noContent().build();
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 * Get all student records
	 * 
	 * @return A list of all student records
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@Context UriInfo uriInfo) {
		final String methodName = "getAll";
		logger.entering(className, methodName);
		try {
			List<Student> students = studentService.get();
			List<HateoasStudentDto> wrappedStudents = new ArrayList<HateoasStudentDto>();
			for (Student student : students) {
				HateoasStudentDto wrappedStudent = new HateoasStudentDto(student);
				Link link = buildSelfLink(uriInfo, wrappedStudent);
				wrappedStudent.addLink(link);
				wrappedStudents.add(wrappedStudent);
			}

			return Response.ok(wrappedStudents).build();
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 * Get a student record by its unique id
	 *
	 * @param id
	 *            The id of the student record to retrieve
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@Context UriInfo uriInfo, @PathParam("id") String id) {
		final String methodName = "getById";
		logger.entering(className, methodName);
		try {
			Student student = studentService.findById(id);
			if (student != null) {
				HateoasStudentDto wrappedStudent = new HateoasStudentDto(student);
				Link link = buildSelfLink(uriInfo, wrappedStudent);
				wrappedStudent.addLink(link);
				return Response.ok(wrappedStudent).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 * Build a Link representing a HATEOAS 'self' relation.
	 * 
	 * @param uriInfo
	 *            UriInfo
	 * @param student
	 *            the student record to generate the link for
	 * @return a Link object
	 */
	private Link buildSelfLink(UriInfo uriInfo, HateoasStudentDto student) {
		URI uri = uriInfo.getBaseUriBuilder().path(StudentResource.class).path(student.getId()).build();
		Link link = Link.fromUri(uri).rel(LINK_SELF).type(MediaType.APPLICATION_JSON).build();
		return link;
	}

	/**
	 * Update a student record
	 *
	 * @param uriInfo
	 * @param student
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Student student) {
		final String methodName = "update";
		logger.entering(className, methodName);
		try {
			Student updatedStudent = studentService.update(student);
			return Response.ok(updatedStudent).build();
		} finally {
			logger.exiting(className, methodName);
		}
	}
}
