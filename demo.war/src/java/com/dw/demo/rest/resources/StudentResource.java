package com.dw.demo.rest.resources;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.dw.demo.ejbs.IStudentService;
import com.dw.demo.entities.Student;

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
	public Response create(@Context UriInfo uriInfo, Student student) {
		final String methodName = "create";
		logger.entering(className, methodName);
		try {
			Student createdStudent = studentService.create(student);
			URI uri = uriInfo.getBaseUriBuilder().path(StudentResource.class).path(createdStudent.getId()).build();
			return Response.created(uri).build();
		} finally {
			logger.exiting(className, methodName);
		}
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
	public Response getAll() {
		final String methodName = "getAll";
		logger.entering(className, methodName);
		try {
			List<Student> students = studentService.get();
			// This preserves type information for the provider
			GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(students) {
			};
			return Response.ok(entity).build();
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
	public Response getById(@PathParam("id") String id) {
		final String methodName = "getById";
		logger.entering(className, methodName);
		try {
			Student student = studentService.findById(id);
			if (student != null) {
				return Response.ok(student).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} finally {
			logger.exiting(className, methodName);
		}
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
