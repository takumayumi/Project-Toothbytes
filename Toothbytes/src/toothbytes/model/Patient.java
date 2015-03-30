/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.model;

/**
 *
 * @author Jolas
 */
public class Patient implements Comparable<Patient>{
    private int id;
    private String lastName, firstName, midName;

    public Patient(int id, String lastName, String firstName, String midName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.midName = midName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getFullName() {
        return this.firstName+" "+this.midName+". "+this.lastName;
    }

    @Override
    public int compareTo(Patient o) {
        return this.getFullName().compareTo(o.getFullName());
    }
    
    public String toString() {
        return this.firstName+" "+this.midName+". "+this.lastName;
    }
    
}
