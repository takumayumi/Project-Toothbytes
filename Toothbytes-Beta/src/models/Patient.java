/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package models;

/**
 * <h1>Patient</h1>
 * The {@code Patient} class constructs the standard representation of Patient.
 */
public class Patient implements Comparable<Patient>{
    private int id;
    private String lastName, firstName, midName;

    /**
     * This method calls the attributes of a Patient as it is.
     * @param   id
     *          The ID of the patient.
     * @param   lastName
     *          The surname of the patient.
     * @param   firstName
     *          The given name of the patient.
     * @param   midName 
     *          The middle name of the patent.
     */
    public Patient(int id, String lastName, String firstName, String midName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.midName = midName;
    }

    /**
     * Returns the middle name of the patient.
     * @return  Middle name.
     */
    public String getMidName() {
        return midName;
    }
    
    /**
     * Sets the middle name attribute of the patient.
     * @param   midName
     *          The middle name of the patient.
     */
    public void setMidName(String midName) {
        this.midName = midName;
    }

    /**
     * Returns the ID of the patient.
     * @return  ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id attribute of the patient.
     * @param   id
     *          The ID of the patient.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the surname of the patient.
     * @return  Last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name attribute of the patient.
     * @param   lastName
     *          The surname of the patient.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the given name of the patient.
     * @return  First name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name attribute of the patient.
     * @param   firstName
     *          The given name of the patient.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Returns the full name of the patient.
     * @return  Full name.
     */
    public String getFullName() {
        return this.firstName + ", " + this.lastName + " " + this.midName + ". ";
    }
    
    /**
     * This method allows the program to compare the full name of the patient 
     * to another full name of patient to provide the alphabetical list of 
     * patients or clients of the dentist.
     * @param   o
     *          Patient object.
     * @return  Alphabetically list of the patient.
     */
    @Override
    public int compareTo(Patient o) {
        return this.getFullName().compareTo(o.getFullName());
    }
    
    /**
     * String representation of the full name of the patient including the 
     * first name, last name and middle name respectively.
     * @return  Full name of the patient in String.
     */
    public String toString() {
        return this.firstName + ", " + this.lastName + " " + this.midName + ". ";
    }
}
