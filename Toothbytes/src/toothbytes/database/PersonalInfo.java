package toothbytes.database;

/**
 * <h1>PersonalInfo</h1>
 * The {@code PersonalInfo} class retrieves and updates the data the user 
 * manipulates for the Patient table of Toothbytes database. It represents the 
 * variables of additional information about the user that are used to the 
 * forms and database.
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

    public PersonalInfo() {}
    
    /**
     * This method constructs the variables to be used in the Patient table of 
     * Toothbytes database schema.
     * @param   surname
     *          Last name of the patient.
     * @param   givenName
     *          First name of the patient.
     * @param   middleInitial
     *          Middle initial of the patienet.
     * @param   gender
     *          Gender of the patient.
     * @param   birthYear
     *          Date of birth of the patient.
     * @param   nationality
     *          Nationality of the patient.
     * @param   religion
     *          Religion of the patient.
     * @param   occupation
     *          Occupation of the patient.
     * @param   homeAddress
     *          Home address of the patient.
     * @param   telephoneNo
     *          Telephone number of the patient.
     * @param   officeNo
     *          Office number of the patient.
     * @param   emailAdd
     *          Email address of the patient.
     * @param   cellphoneNo
     *          Mobile number of the patient.
     * @param   faxNo 
     *          Fax number of the patient.
     */
    public PersonalInfo(String surname, String givenName, String middleInitial, 
        String gender, int birthYear, String nationality, String religion, 
        String occupation, String homeAddress, String telephoneNo, 
        String officeNo, String emailAdd, String cellphoneNo, String faxNo) {
        
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
    
    /**
     * This method injects data the user input that will update the 
     * Patient table from the Toothbytes database schema.
     * @param   medicalHistoryID
     *          A foreign key from the Medical_History table.
     * @return  Identity value while inserting records into database.
     */
    public int UpdatePersonalInfo(int medicalHistoryID) {
        String PersonalInfoUpdate = "INSERT INTO Patient " + "(patientID, "
            + "medicalHistoryID, patient_LastName, patient_FirstName, "
            + "patient_MiddleInitial, birthdate, occupation, civilStatus, "
            + "gender, nickname, homeAddress, homeNo, officeNo, faxNo, cellNo, "
            + "emailAddress) VALUES (DEFAULT, " + medicalHistoryID + ", "
            + surname + ", " + givenName + ", " + ", " + mi + ", " + birthYear 
            + ", " + occupation + ", NULL, " + gender + ", NULL, " + homeAddress 
            + ", " + telephoneNo + ", " + officeNo + ", " + faxNo + ", " 
            + cellphoneNo + ", " + emailAdd + ");";
        
        DBAccess.dbQuery(PersonalInfoUpdate);
        return DBAccess.CallIdentity();
    }
    
    /**
     * Returns the last name of the patient
     * @return  Surname.
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * Returns the first name of the patient.
     * @return  Given name.
     */
    public String getGivenName() {
        return givenName;
    }
    
    /**
     * Returns the middle initial of the patient.
     * @return  Middle initial.
     */
    public String getMI() {
        return mi;
    }
    
    /**
     * Returns the gender of the patient.
     * @return  Gender.
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * Returns the date of birth of the patient.
     * @return  Birthday.
     */
    public int getBirthYear() {
        return birthYear;
    }
    
    /**
     * Returns the nationality of the patient.
     * @return  Nationality.
     */
    public String getNationality() {
        return nationality;
    }
    
    /**
     * Returns the religion of the patient.
     * @return  Religion.
     */
    public String getReligion() {
        return religion;
    }
    
    /**
     * Returns the occupation of the patient.
     * @return  Occupation.
     */
    public String getOccupation() {
        return occupation;
    }
    
    /**
     * Returns the home address of the patient.
     * @return  Home address.
     */
    public String getHomeAddress() {
        return homeAddress;
    }
    
    /**
     * Returns the telephone number of the patient.
     * @return  Telephone number.
     */
    public String getTelephoneNo() {
        return telephoneNo;
    }
    
    /**
     * Returns the office number of ten patient.
     * @return  Office number.
     */
    public String getOfficeNo() {
        return officeNo;
    }
    
    /**
     * Returns the email address of the patient.
     * @return  Email address.
     */
    public String getEmailAdd() {
        return emailAdd;
    }
    
    /**
     * Returns the mobile number of the patient
     * @return  Mobile number.
     */
    public String getCellphoneNo() {
        return cellphoneNo;
    }
    
    /**
     * Returns the fax number of the patient.
     * @return  Fax number.
     */
    public String getFaxNo() {
        return faxNo;
    }
}
