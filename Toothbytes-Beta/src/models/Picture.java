/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Calendar;

/**
 *
 * @author Ecchi Powa
 */
public class Picture {
    private int dentalPicturesID, patientID;
    private String toothPictures, pictureRemarks, tags, imageLocation;
    private Calendar dateTaken;
    
    public Picture(){}
    
    public Picture(int dentalPicturesID, int patientID, String toothPictures, Calendar dateTaken, String pictureRemarks, String tags, String imageLocation){
        this.dentalPicturesID = dentalPicturesID;
        this.patientID = patientID;
        this.toothPictures = toothPictures;
        this.dateTaken = dateTaken;
        this.pictureRemarks = pictureRemarks;
        this.tags = tags;
        this.imageLocation = imageLocation;
    }
    
    public void setDentalPicturesID(int i){
        dentalPicturesID = i;
    }
    
    public void setPatientID(int i){
        patientID = i;
    }
    
    public void setToothPictures(String s){
        toothPictures = s;
    }
    
    public void setDateTaken(Calendar c){
        dateTaken = c;
    }
    
    public void setPictureRemarks(String s){
        pictureRemarks = s;
    }
    
    public void setTags(String s){
        tags = s;
    }
    
    public void setImageLocation(String s){
        imageLocation = s;
    }
    
    public int getDentalPicturesID(){
        return dentalPicturesID;
    }
    
    public int getPatientID(){
        return patientID;
    }
    
    public String getToothPictures(){
        return toothPictures;
    }
    
    public Calendar getDateTaken(){
        return dateTaken;
    }
    
    public String getPictureRemarks(){
        return pictureRemarks;
    }
    
    public String getTags(){
        return tags;
    }
    
    public String getImageLocation(){
        return imageLocation;
    }
}
