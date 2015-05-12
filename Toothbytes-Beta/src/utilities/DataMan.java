/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package utilities;

import java.util.Calendar;

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
    
}