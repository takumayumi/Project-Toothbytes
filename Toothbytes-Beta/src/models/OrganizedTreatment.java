/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jolas
 */
public class OrganizedTreatment {
    private Calendar date;
    private Map<Integer, String> hm;
    
    public OrganizedTreatment(Calendar date, int i, String proc) {
        this.date = date;
        hm = new HashMap<Integer, String>();
        hm.put(i, proc);
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Map<Integer, String> getHm() {
        return hm;
    }

    public void setHm(Map<Integer, String> hm) {
        this.hm = hm;
    }
    
    
}
