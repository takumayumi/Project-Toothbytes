/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.database;

/**
 *
 * @author Ecchi Powa
 */
public class MedicalCond {
    public MedicalCond(){
        
    }
    
    public MedicalCond(boolean q1ans, boolean q2ans, String q2ansTf, boolean q3ans, String q3ansTF, boolean q5ans,
                        String q5ansTF, boolean q6ans, boolean q7ans, boolean q8ans, boolean q9ans, boolean q10ans,
                        boolean q11CB1ans, boolean q11CB2ans, boolean q11CB3ans, boolean q11CB4ans, String q11CB4ansTF,
                        boolean q12opt1ans, boolean q12opt2ans, boolean q12opt3ans, boolean q12opt4ans, boolean q12opt5ans,
                        boolean q12opt6ans, boolean q12opt7ans, boolean q12opt8ans, boolean q12opt9ans, boolean q12opt10ans,
                        boolean q12opt11ans, boolean q12opt12ans, boolean q12opt13ans, boolean q12opt14ans, boolean q12opt15ans){
        
    }
    
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
    
    public String Update(){
        String UpdateMedicalCon = "INSERT INTO Medical_History (q1_goodHealth, q2_condition, q3_seriousIllness, q4_hospitalized, q5_prescription, \n" +
"							q6_tobacco, q7_drugs, q8_pregnant, q8_nursing, q8_birthControl, q9_allergy, q9_otherAllergy, \n" +
"							q10_bloodType, q11_bloodPressure, q12_illness, q12_otherIllness)"
                + "                                      VALUES ('"+q1ans+"', '"+q2ans+"', '"+q3ans+"', '"+q4ans+"', '"+q5ans+"', '"+q6ans+"',"
                + "                                             '"+q7ans+"', '"+q8ans+"', '"+q9ans+"', '"+q10ans+"', '"+q11CB1ans+", "+q11CB2ans+""
                + "                                             ,"+q11CB3ans+","+q11CB4ans+","+q11CB4ansTF+"',"
                + "                                             '"+q12opt1ans+", "+q12opt2ans+", "+q12opt3ans+", "+q12opt4ans+","
                + "                                              "+q12opt5ans+", "+q12opt6ans+", "+q12opt7ans+", "+q12opt8ans+","
                + "                                              "+q12opt9ans+", "+q12opt10ans+", "+q12opt11ans+", "+q12opt12ans+""
                + "                                              "+q12opt13ans+", "+q12opt14ans+", "+q12opt15ans+"' );";
        return UpdateMedicalCon;
    }
}