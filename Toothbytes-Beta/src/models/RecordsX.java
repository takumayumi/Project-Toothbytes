/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package models;

public class RecordsX {
    private int recordsID, patientID, toothNo;
    private String treatmentDate;
    private String procedure, dentist;
    private double amountCharged, balance;
    
    public RecordsX(){}
    
    public RecordsX(int recordsID, int patientID, String treatmentDate, int toothNo, String procedure, String dentist, double amountCharged, double balance){
    this.recordsID = recordsID;
    this.patientID = patientID;
    this.toothNo = toothNo;
    this.treatmentDate = treatmentDate;
    this.procedure = procedure;
    this.dentist = dentist;
    this.amountCharged = amountCharged;
    this.balance = balance;
    }
    
    public int getRecordsID(){
        return recordsID;
    }
    
    public int getPatientID(){
        return patientID;
    }
    
    public int getToothNo(){
        return toothNo;
    }
    
    public String getTreatmentDate(){
        return treatmentDate;
    }
    
    public String getProcedure(){
        return procedure;
    }
    
    public String getDentist(){
        return dentist;
    }
    
    public double getAmountCharged(){
        return amountCharged;
    }
    
    public double getBalance(){
        return balance;
    }
    
    public void setRecordsID(int recordsID){
        this.recordsID = recordsID;
    }
    
    public void setPatientID(int patientID){
        this.patientID = patientID;
    }
    
    public void setToothNo(int toothNo){
        this.toothNo = toothNo;
    }
    
    public void setTreatmentDate(String treatmentDate){
        this.treatmentDate = treatmentDate;
    }
    
    public void setProcedure(String procedure){
        this.procedure = procedure;
    }
    
    public void setDentist(String dentist){
        this.dentist = dentist;
    }
    
    public void setAmountCharged(double amountCharged){
        this.amountCharged = amountCharged;
    }
    
    public void setBalance(double balance){
        this.balance = balance;
    }
}
