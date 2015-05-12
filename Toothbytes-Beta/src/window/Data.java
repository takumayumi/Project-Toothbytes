/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package window;

import java.util.*;

/**
 * <h1>Data</h1>
 * The {@code Data} manages the data used in K-dictionary that are stored in 
 * Hashtable<String, String> hm.
 */
public class Data{
    
 	Hashtable<String,String> hm = new Hashtable<>();

        /**
         * This constructor framed the data used in Appointments Window.
         */
	public Data(){
            hm.put("Add Patient",
                                    "<insert text here.>");
            hm.put("Intraoral Camera",
                                    "<insert text here.>");
            hm.put("Set Appointment",
                                    "<insert text here.>");
            hm.put("Settings",
                                    "<insert text here.>");
            hm.put("Billing Statements",
                                    "<insert text here.>");
        }
}


