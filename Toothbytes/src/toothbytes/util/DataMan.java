/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jolas
 */
public class DataMan {
    public static int getAge(Calendar bday) {
        long milis = System.currentTimeMillis() - bday.getTime().getTime();
        return (int) (milis/1000/31536000);
    }
    public static String dateToString(Date d) {
        return d.toString();
    }
}