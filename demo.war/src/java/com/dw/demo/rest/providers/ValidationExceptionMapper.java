package com.dw.demo.rest.providers;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This class provides an exception mapper for ensuring form input that doesn't pass validation is reported nicely
 * instead of a HTTP 500 (SERVER ERROR).
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	@Override
	public Response toResponse(ValidationException ex) {
		if (ex instanceof ConstraintViolationException) {
			return doHandleConstraintViolationException((ConstraintViolationException) ex);
		}

		return Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity("bad: " + unwrapException(ex)).build();
	}

	private Response doHandleConstraintViolationException(ConstraintViolationException ex) {
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
			String lastName = "";
			for (Node n : v.getPropertyPath()) {
				lastName = n.getName();
			}
			sb.append("Error: '" + lastName + "' " + v.getMessage() + "\n");
		}
		return Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(sb.toString()).build();
	}

	protected String unwrapException(Throwable t) {
		StringBuffer sb = new StringBuffer();
		doUnwrapException(sb, t);
		return sb.toString();
	}

	private void doUnwrapException(StringBuffer sb, Throwable t) {
		if (t == null) {
			return;
		}
		sb.append(t.getMessage());
		if (t.getCause() != null && t != t.getCause()) {
			sb.append('[');
			doUnwrapException(sb, t.getCause());
			sb.append(']');
		}
	}
}
