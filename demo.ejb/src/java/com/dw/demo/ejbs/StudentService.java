package com.dw.demo.ejbs;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.dw.demo.entities.Student;

/**
 * Session Bean implementation class StudentService
 */
@Stateless
@Local(IStudentService.class)
public class StudentService implements IStudentService {

	private static final String className = StudentService.class.getName();

	private static final Logger logger = Logger.getLogger(className);

	@PersistenceContext(unitName = "demo.ejb")
	private EntityManager em;

	/**
	 * Default constructor.
	 */
	public StudentService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * (non-Javadoc)
	 *
	 * @see IStudentService#create(Student)
	 */
	public Student create(Student newStudent) {
		final String methodName = "create";
		logger.entering(className, methodName);
		try {
			newStudent.setId(UUID.randomUUID().toString());
			newStudent.setRegisteredOn(Calendar.getInstance());
			em.persist(newStudent);
			return newStudent;
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 *
	 * (non-Javadoc)
	 *
	 * @see com.dw.demo.ejbs.IStudentService#delete(java.lang.String)
	 */
	public void delete(String id) {
		final String methodName = "delete";
		logger.entering(className, methodName);
		try {
			Student student = this.findById(id);
			if (student != null) {
				em.remove(student);
			}
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 *
	 * (non-Javadoc)
	 *
	 * @see IStudentService#findById(String)
	 */
	public Student findById(String id) {
		final String methodName = "findById";
		logger.entering(className, methodName);
		try {
			return em.find(Student.class, id);
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 *
	 * (non-Javadoc)
	 *
	 * @see com.dw.demo.ejbs.IStudentService#get()
	 */
	public List<Student> get() {
		final String methodName = "get";
		logger.entering(className, methodName);

		try {
			TypedQuery<Student> studentQuery = em.createQuery("select s from Student s", Student.class);
			List<Student> result = studentQuery.getResultList();
			return result;
		} finally {
			logger.exiting(className, methodName);
		}
	}

	/**
	 *
	 * (non-Javadoc)
	 *
	 * @see com.dw.demo.ejbs.IStudentService#update(com.dw.demo.entities.Student)
	 */
	public Student update(Student student) {
		final String methodName = "update";
		logger.entering(className, methodName);
		try {
			return em.merge(student);
		} finally {
			logger.exiting(className, methodName);
		}
	}
}
