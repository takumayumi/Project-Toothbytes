/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.model;

import java.util.Calendar;

public class PatientX extends Patient {
    private Calendar bdate;
    private String occupation;
    private String civilStatus;
    private char gender;
    private String nickname;
    private String homeAddress;
    private String homeNo;
    private String officeNo;
    private String faxNo;
    private String cellNo;
    private String emailAdd;
    
    public PatientX(int id, String lastName, String firstName, String midName, Calendar bdate, String occupation,
                    String civilStatus, char gender, String nickname, String homeAddress, String homeNo,
                    String officeNo, String faxNo, String cellNo, String emailAdd){
        super(id, lastName, firstName, midName);
        this.bdate = bdate;
        this.occupation = occupation;
        this.civilStatus = civilStatus;
        this.gender = gender;
        this.nickname = nickname;
        this.homeAddress = homeAddress;
        this.homeNo = homeNo;
        this.officeNo = officeNo;
        this.faxNo = faxNo;
        this.cellNo = cellNo;
        this.emailAdd = emailAdd;
    }
    
    public Calendar getBdate(){
        return bdate;
    }
    
    public String getOccupation(){
        return occupation;
    }
    
    public String getCivilStatus(){
        return civilStatus;
    }
    
    public char getGender(){
        return gender;
    }
    
    public String getNickname(){
        return nickname;
    }
    
    public String getHomeAddress(){
        return homeAddress;
    }
    
    public String getHomeNo(){
        return homeNo;
    }
    
    public String getOfficeNo(){
        return officeNo;
    }
    
    public String getFaxNo(){
        return faxNo;
    }
    
    public String getCellNo(){
        return cellNo;
    }
    
    public String getEmailAdd(){
        return emailAdd;
    }

    @Override
    public String toString() {
        return super.getFullName()+"\n"
                + ", bdate=" + bdate +"\n"
                + ", occupation=" + occupation +"\n"
                + ", civilStatus=" + civilStatus +"\n"
                + ", gender=" + gender +"\n"
                + ", nickname=" + nickname +"\n"
                + ", homeAddress=" + homeAddress +"\n"
                + ", homeNo=" + homeNo +"\n"+ ", officeNo=" + officeNo +"\n"
                + ", faxNo=" + faxNo +"\n"+ ", cellNo=" + cellNo +"\n"
                + ", emailAdd=" + emailAdd + '}';
    }
    
}