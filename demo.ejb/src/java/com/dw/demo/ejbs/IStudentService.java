package com.dw.demo.ejbs;

import java.util.List;

import com.dw.demo.entities.Student;


/**
 * Declare the local interface
 *
 * @author rgunderson
 *
 */
public interface IStudentService {

    /**
     * Create a student
     *
     * @param newStudent A new student
     * @return The new student
     */
    Student create( Student newStudent );

    /**
     * Delete the student identified by the supplied id
     *
     * @param id
     */
    void delete( String id );

    /**
     * Find a student by the supplied id
     *
     * @param id The student's id
     * @return The student, if found, null otherwise
     */
    Student findById( String id );

    /**
     * Get a list of all students
     *
     * @return
     */
    List<Student> get();

    /**
     * Update a student
     *
     * @param student The student to update
     * @return The updated student
     */
    Student update( Student student );
}
