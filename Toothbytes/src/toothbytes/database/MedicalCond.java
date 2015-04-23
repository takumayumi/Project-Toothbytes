package toothbytes.database;

/**
 * <h1>MedicalCond</h1>
 * The {@code MedicalCond} class retrieves and updates the data the user 
 * manipulates for the Medical_History table of Toothbytes database. It 
 * represents the variables of additional information about the user that are 
 * used to the forms and database.
 */
public class MedicalCond {
    
    public MedicalCond() {}
    
    /**
     * This method constructs the variables to be used in the Medical_History
     * table of Toothbytes database schema.
     * @param   q1ans
     *          Are you in a good health?
     * @param   q2ans
     *          Are you under medical treatment now?
     * @param   q2ansTf
     *          If so, what is the condition being treated?
     * @param   q3ans
     *          Have you ever had serious illness or surgical operation?
     * @param   q3ansTF
     *          If so, what illness or operation?
     * @param   q4ans
     *          Have you ever been hospitalized?
     * @param   q4ansTF
     *          If so, when and why?
     * @param   q5ans
     *          Are you taking any prescription / non-prescription medication?
     * @param   q5ansTF
     *          If so, please specify
     * @param   q6ans
     *          Do you use tobacco products?
     * @param   q7ans
     *          Do you use alcohol or any other drugs?
     * @param   q8ans
     *          For WOMEN ONLY: Are you pregnant?
     *                          Are you nursing?
     *                          Are you taking birth control pills?
     * @param   q9ans
     *          Are you allergic to any of the following?
     * @param   q10ans
     * 
     * @param   q11CB1ans
     * 
     * @param   q11CB2ans
     *
     * @param   q11CB3ans
     * 
     * @param   q11CB4ans
     * 
     * @param   q11CB4ansTF
     * 
     * @param   q12opt1ans
     * 
     * @param   q12opt2ans
     * 
     * @param   q12opt3ans
     * 
     * @param   q12opt4ans
     * 
     * @param   q12opt5ans
     * 
     * @param   q12opt6ans
     * 
     * @param   q12opt7ans
     * 
     * @param   q12opt8ans
     * 
     * @param   q12opt9ans
     * 
     * @param   q12opt10ans
     * 
     * @param   q12opt11ans
     * 
     * @param   q12opt12ans
     * 
     * @param   q12opt13ans
     * 
     * @param   q12opt14ans
     * 
     * @param   q12opt15ans 
     * 
     */
    public MedicalCond(boolean q1ans, boolean q2ans, String q2ansTf, boolean q3ans, String q3ansTF, boolean q4ans, 
        String q4ansTF, boolean q5ans, String q5ansTF, boolean q6ans, boolean q7ans, boolean q8ans, boolean q9ans, 
        boolean q10ans, boolean q11CB1ans, boolean q11CB2ans, boolean q11CB3ans, boolean q11CB4ans, String q11CB4ansTF,
        boolean q12opt1ans, boolean q12opt2ans, boolean q12opt3ans, boolean q12opt4ans, boolean q12opt5ans,
        boolean q12opt6ans, boolean q12opt7ans, boolean q12opt8ans, boolean q12opt9ans, boolean q12opt10ans,
        boolean q12opt11ans, boolean q12opt12ans, boolean q12opt13ans, boolean q12opt14ans, boolean q12opt15ans) {}
    
    public boolean q1ans;
    public boolean q2ans;
    public String q2ansTF;
    public boolean q3ans;
    public String q3ansTF;
    public boolean q4ans;
    public String q4ansTF;
    public boolean q5ans;
    public String q5ansTF;
    public boolean q6ans;
    public boolean q7ans;
    public boolean q8ans;
    public boolean q9ans;
    public boolean q10ans;
    public boolean q11CB1ans;
    public boolean q11CB2ans;
    public boolean q11CB3ans;
    public boolean q11CB4ans;
    public String q11CB4ansTF;
    public boolean q12opt1ans;
    public boolean q12opt2ans;
    public boolean q12opt3ans;
    public boolean q12opt4ans;
    public boolean q12opt5ans;
    public boolean q12opt6ans;
    public boolean q12opt7ans;
    public boolean q12opt8ans;
    public boolean q12opt9ans;
    public boolean q12opt10ans;
    public boolean q12opt11ans;
    public boolean q12opt12ans;
    public boolean q12opt13ans;
    public boolean q12opt14ans;
    public boolean q12opt15ans;
    
    /**
     * This method injects data the user input that will update the 
     * Medical_History table from the Toothbytes database schema.
     * @return  Identity value while inserting records into database.
     */
    public int Update() {
        String UpdateMedicalCon = "INSERT INTO Medical_History (medicalHistoryID, "
            + "q1_goodHealth, q2_condition, q3_seriousIllness, q4_hospitalized, "
            + "q5_prescription, \n" + "q6_tobacco, q7_drugs, q8_pregnant, "
            + "q8_nursing, q8_birthControl, q9_allergy, q9_otherAllergy, \n" 
            + "q10_bloodType, q11_bloodPressure, q12_illness, q12_otherIllness)"
            + "VALUES (DEFAULT, " + q1ans + ", " + q2ans + ", " + q3ans + ", " 
            + q4ans + ", " + q5ans + "', '" + q6ans + ", " + q7ans + ", " 
            + q8ans + ", " + q9ans + ", " + q10ans + ", " + q11CB1ans + ", " 
            + q11CB2ans + ", " + q11CB3ans + ", " + q11CB4ans + ", " 
            + q11CB4ansTF + ", " + ", " + q12opt1ans + ", " + q12opt2ans + ", " 
            + q12opt3ans + ", " + q12opt4ans + ", " + q12opt5ans + ", " 
            + q12opt6ans + ", " + q12opt7ans + ", " + q12opt8ans + ", " 
            + q12opt9ans + ", " + q12opt10ans + ", " + q12opt11ans + ", " 
            + q12opt12ans + ", " + q12opt13ans + ", " + q12opt14ans + ", " 
            + q12opt15ans + "' );";
        
        DBAccess.dbQuery(UpdateMedicalCon);
        return DBAccess.CallIdentity();
    }
}