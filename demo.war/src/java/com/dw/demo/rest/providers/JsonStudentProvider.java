package com.dw.demo.rest.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.dw.demo.entities.Student;

/**
 * This provider will ensure that a Student entity, serialized as JSON, will have date-oriented fields formatted as iso8601
 * date-times.
 *
 * @author rgunderson
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JsonStudentProvider implements MessageBodyWriter<Object> {

	/**
	 * The name of this class
	 */
	public static final String className = JsonStudentProvider.class.getName();

	/**
	 * The logger to use for this class
	 */
	private static final Logger logger = Logger.getLogger(className);

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object, java.lang.Class, java.lang.reflect.Type,
	 *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public long getSize(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class, java.lang.reflect.Type,
	 *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// Credit to Christopher Hunt
		// (http://christopherhunt-software.blogspot.ca/2010/08/messagebodywriter-iswriteable-method.html)
		// on describing the correct way to determine if a supplied type is a parameterized type and what the actual type
		// is.
		boolean isWritable = false;
		if (List.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericType;
			Type[] actualTypeArgs = (parameterizedType.getActualTypeArguments());
			isWritable = (actualTypeArgs.length == 1 && actualTypeArgs[0].equals(Student.class));
		} else if (type == Student.class) {
			isWritable = true;
		}
		return isWritable;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type,
	 *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 */
	@Override
	public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();

		// Explicitly use the Jackson ObjectMapper to write dates in ISO8601 format
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

		// Use the global instance of the ObjectMapper
		mapper.writeValue(entityStream, object);
	}
}
