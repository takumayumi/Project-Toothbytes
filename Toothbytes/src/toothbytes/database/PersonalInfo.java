/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toothbytes.database;

import java.util.Calendar;

/**
 *
 * @author EcchiPowa
 */
public class PersonalInfo {
    String surname;
    String givenName;
    String mi;
    int birthYear;
    int age;
    String nationality;
    String religion;  
    String occupation;
    String homeAddress;   

    public PersonalInfo(){
        
    }
    
    public PersonalInfo(String surname, String givenName, String middleInitial, int birthYear, String nationality, String religion, String occupation, String homeAddress){
        this.surname = surname;
        this.givenName = givenName;
        mi = middleInitial;
        this.birthYear = birthYear;
        this.nationality = nationality;
        this.religion = religion;
        this.occupation = occupation;
        this.homeAddress = homeAddress;
    }
    
     /*
        Kailangan ng civilStatus, gender, nickname, homeNo, cellNo, emailAdd
    */
    public String Update(){
         String PersonalInfoUpdate = "INSERT INTO Patient (patient_LastName, patient_FirstName, patient_MiddleInitial, birthdate, occupation,\n" +
"				civilStatus, gender, nickname, homeAddress, homeNo, officeNo, cellNo, emailAddress) \n" +
"                               VALUES ('"+ surname +"','"+givenName+"','"+mi+"','"+birthYear+"','"+nationality+"','"+religion+"','"+occupation+"','"+homeAddress+"')";
        return PersonalInfoUpdate;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public String getGivenName(){
        return givenName;
    }
    
    public String getMI(){
        return mi;
    }
    
    public int getBirthYear(){
        return birthYear;
    }
    
    public String getNationality(){
        return nationality;
    }
    
    public String getReligion(){
        return religion;
    }
    
    public String getOccupation(){
        return occupation;
    }
    
    public String homeAddress(){
        return homeAddress;
    }
}
