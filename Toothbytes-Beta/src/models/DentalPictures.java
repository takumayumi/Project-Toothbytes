/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Ecchi Powa
 */
public class DentalPictures {
    private int dentalPicturesID, patientID;
    private String toothPictures;
    private String dateTaken;
    private String pictureRemarks;
    
    public DentalPictures(){}
    
    public DentalPictures(int dentalPicturesID, int patientID, String toothPictures, String dateTaken, String pictureRemarks){
        this.dentalPicturesID = dentalPicturesID;
        this.patientID = patientID;
        this.toothPictures = toothPictures;
        this.dateTaken = dateTaken;
        this.pictureRemarks = pictureRemarks;
    }
    
    public void setDentalPicturesID(int dentalPicturesID){
        this.dentalPicturesID = dentalPicturesID;
    }
    
    public void setPatientID(int patientID){
        this.patientID = patientID;
    }
    
    public void setToothPictures(String toothPictures){
        this.toothPictures = toothPictures;
    }
    
    public void setDateTaken(String dateTaken){
        this.dateTaken = dateTaken;
    }
    
    public void setPictureRemarks(String pictureRemarks){
        this.pictureRemarks = pictureRemarks;
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
    
    public String getDateTaken(){
        return dateTaken;
    }
    
    public String getPictureRemarks(){
        return pictureRemarks;
    }
}

/*
this.dentalPicturesID = dentalPicturesID;
        this.patientID = patientID;
        this.toothPictures = toothPictures;
        this.dateTaken = dateTaken;
        this.pictureRemarks = pictureRemarks;
*/