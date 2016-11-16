package com.dw.demo.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * The Application sub-class that specifies the JAX-RS resource and providers.
 *
 * @author rgunderson
 *
 */
public class StudentRegistrationApplication extends Application {

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		// Resources
		classes.add(com.dw.demo.rest.resources.StudentResource.class);

		// Providers
		classes.add( com.dw.demo.rest.providers.JsonStudentProvider.class );
		classes.add( com.dw.demo.rest.providers.JsonMappingExceptionExceptionMapper.class );

		return classes;
	}
}
