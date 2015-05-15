/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package models;

public class PaymentX {
    private int paymentID, dentalRecordID;
    private String paymentDate;
    private double amountPaid;
    
    public PaymentX(){}
    
    public PaymentX(int dentalRecordID){
        
    }
    
    public PaymentX(int paymentID, int dentalRecordID, String paymentDate, double amountPaid){
        this.paymentID = paymentID;
        this.dentalRecordID = dentalRecordID;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
    }
    
    public int getPaymentID(){
        return this.paymentID;
    }
    
    public int getDentalRecordID(){
        return this.dentalRecordID;
    }
    
    public String getPaymentDate(){
        return this.paymentDate;
    }
    
    public double getAmountPaid(){
        return this.amountPaid;
    }
    
    public void setPaymentID(int paymentID){
        this.paymentID = paymentID;
    }
    
    public void setDentalRecordID(int dentalRecordID){
        this.dentalRecordID = dentalRecordID;
    }
    
    public void setPaymentDate(String paymentDate){
        this.paymentDate = paymentDate;
    }
    
    public void setAmountPaid(double amountPaid){
        this.amountPaid = amountPaid;
    }
}
