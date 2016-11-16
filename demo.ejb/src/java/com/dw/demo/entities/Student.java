package com.dw.demo.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Student
 *
 */
@Entity
@Table( name = "STUDENT", schema="DEMO" )
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String studentNumber;

    private Calendar registeredOn;

    private Gender gender;

    public Student() {
        super();
    }

    @Id
    @Column( length = 36 )
    public String getId() {
        return this.id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    @Column( length = 100 )
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    @Column( length = 100 )
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    @Enumerated( EnumType.STRING )
    @Column( length = 10 )
    public Gender getGender() {
        return this.gender;
    }

    public void setGender( Gender gender ) {
        this.gender = gender;
    }

    /**
     * @return the email
     */
    @Column( length = 255 )
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail( String email ) {
        this.email = email;
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
    public void setStudentNumber( String studentNumber ) {
        this.studentNumber = studentNumber;
    }

    /**
     * @return the registeredOn
     */
    @Temporal( TemporalType.TIMESTAMP )
    public Calendar getRegisteredOn() {
        return registeredOn;
    }

    /**
     * @param registeredOn the registeredOn to set
     */
    public void setRegisteredOn( Calendar registeredOn ) {
        this.registeredOn = registeredOn;
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return Objects.hash(id);
    }

    /**
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj != null && getClass() == obj.getClass() ) {
            Student other = (Student) obj;
            return Objects.equals(id, other.id);
        }
        return false;
    }
}
