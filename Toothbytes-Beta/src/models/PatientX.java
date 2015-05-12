/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package models;

import java.util.Calendar;

/**
 * <h1>PatientX</h1>
 * The {@code PatientX} class is the extended version of Patient class.
 */
public class PatientX extends Patient {
    private Calendar bdate;
    private String occupation;
    private String civilStatus;
    private char gender;
    private String nickname;
    private String homeAddress;
    private String homeNo;
    private String officeNo;
    private String faxNo;
    private String cellNo;
    private String emailAdd;
    
    /**
     * This method calls the other attributes of a Patient as it is.
     * @param   id
     *          The ID of the patient.
     * @param   lastName
     *          The surname of the patient.
     * @param   firstName
     *          The given name of the patient.
     * @param   midName
     *          The middle name of the patient.
     * @param   bdate
     *          The birth date of the patient.
     * @param   occupation
     *          The occupation of the patient.
     * @param   civilStatus
     *          The civil status of the patient.
     * @param   gender
     *          The gender of the patient.
     * @param   nickname
     *          The nickname of the patient.
     * @param   homeAddress
     *          The home address of the patient.
     * @param   homeNo
     *          The home number of the patient.
     * @param   officeNo
     *          The office number of the patient.
     * @param   faxNo
     *          The fax number of the patient.
     * @param   cellNo
     *          The mobile number of the patient.
     * @param   emailAdd
     *          The email address of the patient.
     */
    public PatientX(int id, String lastName, String firstName, String midName, Calendar bdate, String occupation,
        String civilStatus, char gender, String nickname, String homeAddress, String homeNo, String officeNo, 
        String faxNo, String cellNo, String emailAdd){
        super(id, lastName, firstName, midName);
        this.bdate = bdate;
        this.occupation = occupation;
        this.civilStatus = civilStatus;
        this.gender = gender;
        this.nickname = nickname;
        this.homeAddress = homeAddress;
        this.homeNo = homeNo;
        this.officeNo = officeNo;
        this.faxNo = faxNo;
        this.cellNo = cellNo;
        this.emailAdd = emailAdd;
    }
    
    /**
     * Returns the Calendar representation of the birth date of the patient.
     * @return  Birthday.
     */
    public Calendar getBdate(){
        return bdate;
    }
    
    /**
     * Returns the String representation of the occupation of the patient.
     * @return  Occupation.
     */
    public String getOccupation(){
        return occupation;
    }
    
    /**
     * Returns the String representation of the civil status of the patient.
     * @return  Civil status.
     */
    public String getCivilStatus(){
        return civilStatus;
    }
    
    /**
     * Returns the character representation of the character representation of 
     * the gender.
     * @return  Gender.
     */
    public char getGender(){
        return gender;
    }
    
    /**
     * Returns the String representation of the nickname of the patient.
     * @return  Nickname.
     */
    public String getNickname(){
        return nickname;
    }
    
    /**
     * Returns the String representation of the home address of the patient.
     * @return  Home address.
     */
    public String getHomeAddress(){
        return homeAddress;
    }
    
    /**
     * Returns the String representation of the home number of the patient.
     * @return  Home number.
     */
    public String getHomeNo(){
        return homeNo;
    }
    
    /**
     * Returns the String representation of the office number of the patient.
     * @return  Office number.
     */
    public String getOfficeNo(){
        return officeNo;
    }
    
    /**
     * Returns the String representation of the fax number of the patient.
     * @return  Fax number.
     */
    public String getFaxNo(){
        return faxNo;
    }
    
    /**
     * Returns the String representation of the mobile number of the patient.
     * @return  Cellphone number.
     */
    public String getCellNo(){
        return cellNo;
    }
    
    /**
     * Returns the String representation of the email address of the patient.
     * @return  Email address.
     */
    public String getEmailAdd(){
        return emailAdd;
    }

    /**
     * Returns the String representation of the full name and other attributes 
     * of the patient.
     * @return  String representation of PatientX.
     */
    @Override
    public String toString() {
        return super.getFullName()+"\n"
                + ", bdate=" + bdate +"\n"
                + ", occupation=" + occupation +"\n"
                + ", civilStatus=" + civilStatus +"\n"
                + ", gender=" + gender +"\n"
                + ", nickname=" + nickname +"\n"
                + ", homeAddress=" + homeAddress +"\n"
                + ", homeNo=" + homeNo +"\n"+ ", officeNo=" + officeNo +"\n"
                + ", faxNo=" + faxNo +"\n"+ ", cellNo=" + cellNo +"\n"
                + ", emailAdd=" + emailAdd + '}';
    }
    
}