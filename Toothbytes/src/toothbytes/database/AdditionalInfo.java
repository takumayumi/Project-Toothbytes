package toothbytes.database;

/**
 * <h1>AdditionalInfo</h1>
 * The {@code AdditionalInfo} class retrieves and updates the data the user 
 * manipulates for the Additional_info table of Toothbytes database. It 
 * represents the variables of additional information about the user that are 
 * used to the forms and database.
 */
public class AdditionalInfo {
    public String dentalInsurance;          
    public String effectiveDateYear;      
    public String guardiansName;            
    public String occupation;                   
    public String referrer;                        
    public String reason;                             
    public String previousDentist;           
    public String lastDentalVisitYear;   
    public String nameOfPhysician;          
    public String officeAddress;            
    public String specialization;             
    public String officeNumber;                

    /**
     * This method constructs the variables to be used in the Additional_Info 
     * table of Toothbytes database schema.
     * @param   dentalInsurance
     *          Dental insurance of the patient, if available.
     * @param   effectiveDateYear
     *          Last year of the dental insurance is effective.
     * @param   guardianName
     *          Guardian name of the patient if he/she is below 18 years old.
     * @param   occupation
     *          Occupation of the guardian of the patient.
     * @param   referrer
     *          Doctor or acquaintance of the patient who refer Dr. Pascual 
     *          to the patient.
     * @param   reason
     *          The reason of checkup on that day.
     * @param   previousDentist
     *          Name of the previous dentist of the patient, if available.
     * @param   lastDentalVisitYear
     *          Year when the patient last went to the dental clinic.
     * @param   physicianName
     *          Name of the physician of the patient.
     * @param   officeAddress
     *          Office address of the physician of the patient.
     * @param   specialization
     *          Specialization/s of the physician of the patient.
     * @param   officeNumber
     *          Phone or mobile number the physician of the patient on the 
     *          office or used for his/her work.
     */
    public AdditionalInfo(String dentalInsurance, String effectiveDateYear, String guardianName, String occupation, String referrer, String reason,
                        String previousDentist, String lastDentalVisitYear, String physicianName, String officeAddress, String specialization, String officeNumber) {
        this.dentalInsurance = dentalInsurance;
        this.effectiveDateYear = effectiveDateYear;
        this.guardiansName = guardianName;
        this.occupation = occupation;
        this.referrer = referrer;
        this.reason = reason;
        this.previousDentist = previousDentist;
        this.lastDentalVisitYear = lastDentalVisitYear;
        this.nameOfPhysician = physicianName;
        this.officeAddress = officeAddress;
        this.specialization = specialization;
        this.officeNumber = officeNumber;
    }
    
    /**
     * This method allows the program to insert the data the user input to the 
     * Additional_Info table of the the database.
     * @param   patientID
     *          A unique number given to the patient in the database.
     * @return  false
     *          If the program failed to update or insert the data the user 
     *          input for the database.
     */
    public boolean UpdateAdditionalInfo(int patientID) {
        try{
            String additionalInfoUpdate = "INSERT INTO Additional_Info (additionalInfoID, patientID, dentalInsurance, effectiveDate, guardianName, guardianOccupation, referral, consulationReason,\n" +
    "				previousDentist, lastDentalVisit, physicianName, physicianOffice, physicianSpecialty, physicianContactNo) \n" +
    "                               VALUES (DEFAULT, "+patientID+", '"+dentalInsurance+"', '"+effectiveDateYear+"', '"+guardiansName+"', '"+occupation+"', '"+referrer+"', '"+reason+"',"
                    + "             '"+previousDentist+"', '"+lastDentalVisitYear+"', '"+nameOfPhysician+"', '"+officeAddress+"', '"+specialization+"', '"+officeNumber+"')";
            return true;
        }catch(Exception e) {
            System.out.println(e);
            return false;
        }

    }
    
    /**
     * Returns dental insurance of the patient.
     * @return  Dental insurance.
     */
    public String getDentalInsurance() {
        return dentalInsurance;
    }
    
    /**
     * Returns last year of the dental insurance is effective.
     * @return  Effective date year.
     */
    public String getEffectiveDateYear() {
        return effectiveDateYear;
    }
    
    /**
     * Returns guardian name of the patient if he/she is below 18 years old.
     * @return  Guardian name.
     */
    public String getGuardianName() {
        return guardiansName;
    }
    
    /**
     * Returns the occupation of the guardian of the patient.
     * @return  Occupation of the guardian.
     */
    public String getOccupation() {
        return occupation;
    }
    
    /**
     * Returns the name of the doctor or acquaintance of the patient who refer 
     * Dr. Pascual to the patient.
     * @return  Referrer.
     */
    public String getReferrer() {
        return referrer;
    }
    
    /**
     * Returns the reason of the patient or what is the checkup is all about on 
     * the day of your first meeting.
     * @return  Reason.
     */
    public String getReason() {
        return reason;
    }
    
    /**
     * Returns the name of the previous dentist of the patient, if available.
     * @return  Previous dentist.
     */
    public String getPreviousDentist() {
        return previousDentist;
    }
    
    /**
     * Returns the year when the patient last went to the dental clinic.
     * @return  Last dental visit year.
     */
    public String getLastDentalVisitYear() {
        return lastDentalVisitYear;
    }
    
    /**
     * Returns the name of the physician of the patient.
     * @return  Physician name.
     */
    public String getPhysicianName() {
        return nameOfPhysician;
    }    
    
    /**
     * Returns the specialization/s of the physician of the patient.
     * @return  Specialization.
     */
    public String getSpecialization() {
        return specialization;
    }
    
    /**
     * Returns the office number or contact number of the physician of the 
     * patient.
     * @return  Office number.
     */
    public String getOfficeNumber() {
        return officeNumber;
    }
}
