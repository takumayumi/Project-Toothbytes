/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package utilities;

/**
 * <h1>MedicalCond</h1>
 * The {@code MedicalCond} class retrieves and updates the data the user 
 * manipulates for the Medical_History table of Toothbytes database. It 
 * represents the variables of additional information about the user that are 
 * used to the forms and database.
 */
public class MedicalCond {
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    private String q6;
    private String q7;
    private String q8;
    private String q9;
    private String bloodType;
    private String bloodPressure;
    private String q10;
    private String q11;
    private String q12;
    
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
    
    public MedicalCond(String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9,
    String bloodType, String bloodPressure, String q10, String q11, String q12) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.q9 = q9;
        this.bloodType = bloodType;
        this.bloodPressure = bloodPressure;
        this.q10 = q10;
        this.q11 = q11;
        this.q12 = q12;
    }
     
    /**
     * This method injects data the user input that will update the 
     * Medical_History table from the Toothbytes database schema.
     * @return  Identity value while inserting records into database.
     */
    public void Update(int patientID) {
        String UpdateMedicalCon = "INSERT INTO MEDICAL_HISTORY VALUES(DEFAULT, "+patientID+", '"+q1+"', "
                + "'"+q2+"', '"+q3+"', '"+q4+"', '"+q5+"', '"+q6+"', '"+q7+"', '"+q8+"', '"+q9+"', '"+bloodType+"', "
                + "'"+bloodType+"', '"+bloodPressure+"', '"+q10+"', '"+q11+"', '"+q12+"', null)";
        DBAccess.dbQuery(UpdateMedicalCon);
    }
}