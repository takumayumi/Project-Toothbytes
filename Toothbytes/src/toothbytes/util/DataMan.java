/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.util;

import java.util.Date;

/**
 *
 * @author Jolas
 */
public class DataMan {
    public static Date getAge(Date bday) {
        return new Date(System.currentTimeMillis() - bday.getTime());
    }
}