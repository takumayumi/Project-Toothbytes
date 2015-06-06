/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package utilities;

import java.util.ArrayList;
import java.util.Calendar;
import models.OrganizedTreatment;
import models.Treatment;

/**
 * <h1>DataMan</h1>
 * The {@code DataMan} class for data manipulation.
 */
public class DataMan {
    
    /**
     * This method computes and returns the age given by the date.
     * @param   bday
     *          Calendar object.
     * @return  Integer value of age.
     */
    public static int getAge(Calendar bday) {
        long milis = System.currentTimeMillis() - bday.getTime().getTime();
        return (int) (milis/1000/31536000);
    }
    
    
    public static ArrayList<OrganizedTreatment> organizeTreatment(ArrayList<Treatment> tList) {
        int added = 0;
        ArrayList<OrganizedTreatment> newList = new ArrayList<OrganizedTreatment>();
        for(int i=0; i<tList.size(); i++) {
            
            OrganizedTreatment ot = new OrganizedTreatment(tList.get(i).getDate(), 
                    Integer.parseInt(tList.get(i).getTooth()), tList.get(i).getProcedure());
            
            for(int j=i+1; j<tList.size(); j++) {
                
                if(ot.getDate().equals(tList.get(j).getDate())) {
                    ot.getHm().put(Integer.parseInt(tList.get(j).getTooth()), tList.get(j).getProcedure());
                    added++;
                }
            }
            newList.add(ot);
            if(i+added >= tList.size()) {
                break;
            } else {
                i+=added;
            }
        }
        
        return newList;
    }
}