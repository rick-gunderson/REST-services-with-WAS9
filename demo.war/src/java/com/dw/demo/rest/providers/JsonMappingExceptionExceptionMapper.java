package com.dw.demo.rest.providers;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonMappingException.Reference;

/**
 * This class provides an exception mapper for ensuring invalid input JSON is reported as a HTTP 400 (BAD REQUEST) instead of a HTTP
 * 500 (SERVER ERROR).
 * 
 * @author rgunderson
 *
 */
@Provider
public class JsonMappingExceptionExceptionMapper implements ExceptionMapper<JsonMappingException> {

	@Override
	public Response toResponse(JsonMappingException e) {
		// Get the mapping path at which the object mapper detected the error
		List<Reference> references = e.getPath();
		// Get the LAST reference from the list
		String message = references.get(references.size() - 1).getFieldName();
		return Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
				.entity("The supplied JSON contained an invalid property: " + message).build();
	}
}
