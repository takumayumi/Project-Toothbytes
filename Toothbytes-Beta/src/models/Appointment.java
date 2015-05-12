/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package models;

public class Appointment {
    private int appointmentID, patientID;
    private String appointmentDate;
    private String appointmentTime, appointmentEndTime;
    private String appointmentRemarks;
    
    public Appointment(){}
    
    public Appointment(int appointmentID, int patientID, String appointmentDate, String appointmentTime, String appointmentEndTime, String appointmentRemarks){
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentEndTime = appointmentEndTime;
        this.appointmentRemarks = appointmentRemarks;
    }
    
    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }
    
    public void setPatientID(int patientID){
        this.patientID = patientID;
    }
    
    public void setAppointmentDate(String appointmentDate){
        this.appointmentDate = appointmentDate;
    }
    
    public void setAppointmentTime(String appointmentTime){
        this.appointmentTime = appointmentTime;
    }
    
    public void setAppointmentEndTime(String appointmentEndTime){
        this.appointmentEndTime = appointmentEndTime;
    }
    
    public void setAppointmentRemarks(String appointmentRemarks){
        this.appointmentRemarks = appointmentRemarks;
    }
    
    public int getAppointmentID(){
        return appointmentID;
    }
    
    public int getPatientID(){
        return patientID;
    }
    
    public String getAppointmentDate(){
        return appointmentDate;
    }
    
    public String getAppointmentTime(){
        return appointmentTime;
    }
    
    public String getAppointmentEndTime(){
        return appointmentEndTime;
    }
    
    public String getAppointmentRemarks(){
        return appointmentRemarks;
    }
}
