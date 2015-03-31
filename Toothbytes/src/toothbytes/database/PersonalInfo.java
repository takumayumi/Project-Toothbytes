/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package toothbytes.database;

/**
 *
 * @author EcchiPowa
 */
public class PersonalInfo {
    String surname;
    String givenName;
    String mi;
    String gender;
    int birthYear;
    int age;
    String nationality;
    String religion;  
    String occupation;
    String homeAddress;   
    String telephoneNo;
    String officeNo;
    String emailAdd;
    String cellphoneNo;
    String faxNo;

    public PersonalInfo(){
        
    }
    
    public PersonalInfo(String surname, String givenName, String middleInitial, String gender, int birthYear, String nationality,
            String religion, String occupation, String homeAddress, String telephoneNo, String officeNo, String emailAdd, String cellphoneNo, String faxNo){
        this.surname = surname;
        this.givenName = givenName;
        mi = middleInitial;
        this.gender = gender;
        this.birthYear = birthYear;
        this.nationality = nationality;
        this.religion = religion;
        this.occupation = occupation;
        this.homeAddress = homeAddress;
        this.telephoneNo = telephoneNo;
        this.officeNo = officeNo;
        this.emailAdd = emailAdd;
        this.cellphoneNo = cellphoneNo;
        this.faxNo = faxNo;
    }
    
    public int UpdatePersonalInfo(int medicalHistoryID){
        String PersonalInfoUpdate = "INSERT INTO Patient "
                + "(patientID, medicalHistoryID, patient_LastName, patient_FirstName, patient_MiddleInitial,"
                + " birthdate, occupation, civilStatus, gender, nickname, homeAddress, homeNo, officeNo,"
                + " faxNo, cellNo, emailAddress) VALUES (DEFAULT, "+medicalHistoryID+", '"+surname+"', '"+givenName+"',"
                + " '"+mi+"', '"+birthYear+"', '"+occupation+"', NULL, "+gender+", NULL, '"+homeAddress+"', "+telephoneNo+","
                + " "+officeNo+", "+faxNo+", "+cellphoneNo+", "+emailAdd+");";
        
         DBAccess.dbQuery(PersonalInfoUpdate);
         return DBAccess.CallIdentity();
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
    
    public String getGender(){
        return gender;
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
    
    public String getHomeAddress(){
        return homeAddress;
    }
    
    public String getTelephoneNo(){
        return telephoneNo;
    }
    
    public String getOfficeNo(){
        return officeNo;
    }
    
    public String getEmailAdd(){
        return emailAdd;
    }
    
    public String getCellphoneNo(){
        return cellphoneNo;
    }
    
    public String getFaxNo(){
        return faxNo;
    }
}
