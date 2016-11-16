package com.dw.demo.ejbs;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dw.demo.entities.Student;


/**
 * Session Bean implementation class Initializer
 * <p>
 * This bean initializes the database if it detects the database has not been initialized.
 * </p>
 */
@Singleton
@LocalBean
@Startup
public class Initializer {

    /**
     * The name of this class
     */
    public static final String className = Initializer.class.getName();

    /**
     * The logger to use for this class
     */
    private static final Logger logger = Logger.getLogger( className );

    @PersistenceContext( unitName = "demo.ejb" )
    private EntityManager em;

    /**
     * Default constructor.
     */
    public Initializer() {
    }

    @PostConstruct
    @TransactionAttribute( TransactionAttributeType.REQUIRED )
    public void initialize() {
        final String methodName = "initialize";
		logger.entering(className, methodName);
		try {
			TypedQuery<Long> studentQuery = em.createQuery("select count(s) from Student s", Long.class);
			List<Long> results = studentQuery.getResultList();
			if (results.size() == 0 || results.get(0).longValue() == 0) {
				loadDemoStudents();
			}
		} finally {
			logger.exiting(className, methodName);
		}
    }

    /**
     *
     */
    private void loadDemoStudents() {
        final String methodName = "loadDemoStudents";
		logger.entering(className, methodName);
		try {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream("com/dw/demo/init/resources/students.json");
			// Use the Jackson ObjectMapper class to read the initialization data
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<Student> students = mapper.readValue(is, new TypeReference<List<Student>>() {
				});
				for (Student student : students) {
					em.persist(student);
				}
			} catch (JsonParseException e) {
				logger.logp(Level.SEVERE, className, methodName, "JsonParseException", e);
			} catch (JsonMappingException e) {
				logger.logp(Level.SEVERE, className, methodName, "JsonMappingException", e);
			} catch (IOException e) {
				logger.logp(Level.SEVERE, className, methodName, "IOException", e);
			} catch (Exception e) {
				logger.logp(Level.SEVERE, className, methodName, "Exception", e);
			}
		} finally {
			logger.exiting(className, methodName);
		}
    }
}
