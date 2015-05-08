/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.model;

/**
 *
 * @author Ecchi Powa
 */
public class Services {
    private int serviceID;
    private String serviceType, serviceLegend, serviceDesc;
    private double serviceFee;
    private boolean serviceAvailable;
    
    public Services(){}
    
    public Services(int serviceID, String serviceType, String serviceLegend, String serviceDesc, double serviceFee, boolean serviceAvailable){
        this.serviceID = serviceID;
        this.serviceType = serviceType;
        this.serviceLegend = serviceLegend;
        this.serviceDesc = serviceDesc;
        this.serviceFee = serviceFee;
        this.serviceAvailable = serviceAvailable;
    }
    
    public void setServiceID(int serviceID){
        this.serviceID = serviceID;
    }
    
    public void setServiceType(String serviceType){
        this.serviceType = serviceType;
    }
    
    public void setServiceLegend(String serviceLegend){
        this.serviceLegend = serviceLegend;
    }
    
    public void setServiceDesc(String serviceDesc){
        this.serviceDesc = serviceDesc;
    }
    
    public void setServiceFee(double serviceFee){
        this.serviceFee = serviceFee;
    }
    
    public void setServiceAvailable(boolean serviceAvailable){
        this.serviceAvailable = serviceAvailable;
    }
    
    public int getServiceID(){
        return serviceID;
    }
    
    public String getServiceType(){
        return serviceType;
    }
    
    public String getServiceLegend(){
        return serviceLegend;
    }
    
    public String getServiceDesc(){
        return serviceDesc;
    }
    
    public double getServiceFee(){
        return serviceFee;
    }
    
    public boolean getServiceAvailable(){
        return serviceAvailable;
    }
}

//private int serviceID;
//    private String serviceType, serviceLegend, serviceDesc;
//    private double serviceFee;
//    private boolean serviceAvailable;