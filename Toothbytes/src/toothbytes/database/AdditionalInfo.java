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
public class AdditionalInfo {
    public String dentalInsurance;          
    public String effectiveDateYear;      
    public String guardiansName;            
    public String occupation;                   
    public String refferer;                        
    public String reason;                             
    public String previousDentist;           
    public String lastDentalVisitYear;   
    public String nameOfPhysician;          
    public String officeAddress;            
    public String specialization;             
    public String officeNumber;                

    public AdditionalInfo(String dentalInsurance, String effectiveDateYear, String guardianName, String occupation, String referer, String reason,
                        String previousDentist, String lastDentalVisitYear, String physicianName, String officeAddress, String specialization, String officeNumber){
        this.dentalInsurance = dentalInsurance;
        this.effectiveDateYear = effectiveDateYear;
        this.guardiansName = guardianName;
        this.occupation = occupation;
        this.refferer = referer;
        this.reason = reason;
        this.previousDentist = previousDentist;
        this.lastDentalVisitYear = lastDentalVisitYear;
        this.nameOfPhysician = physicianName;
        this.officeAddress = officeAddress;
        this.specialization = specialization;
        this.officeNumber = officeNumber;
    }
    
    public boolean UpdateAdditionalInfo(int patientID){
        try{
            String additionalInfoUpdate = "INSERT INTO Additional_Info (additionalInfoID, patientID, dentalInsurance, effectiveDate, guardianName, guardianOccupation, referral, consulationReason,\n" +
    "				previousDentist, lastDentalVisit, physicianName, physicianOffice, physicianSpecialty, physicianContactNo) \n" +
    "                               VALUES (DEFAULT, "+patientID+", '"+dentalInsurance+"', '"+effectiveDateYear+"', '"+guardiansName+"', '"+occupation+"', '"+refferer+"', '"+reason+"',"
                    + "             '"+previousDentist+"', '"+lastDentalVisitYear+"', '"+nameOfPhysician+"', '"+officeAddress+"', '"+specialization+"', '"+officeNumber+"')";

            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }

    }
    
    public String getDentalInsurance(){
        return dentalInsurance;
    }
    
    public String getEffectiveDateYear(){
        return effectiveDateYear;
    }
    
    public String getGuardianName(){
        return guardiansName;
    }
    
    public String getOccupation(){
        return occupation;
    }
    
    public String getReferer(){
        return refferer;
    }
    
    public String getReason(){
        return reason;
    }
    
    public String getPreviousDentist(){
        return previousDentist;
    }
    
    public String getLastDentalVisitYear(){
        return lastDentalVisitYear;
    }
    
    public String getPhysicianName(){
        return nameOfPhysician;
    }    
    
    public String getSpecialization(){
        return specialization;
    }
    
    public String getOfficeNumber(){
        return officeNumber;
    }
}
