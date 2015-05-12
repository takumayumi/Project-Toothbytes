/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package models;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 * <h1>TBListModel</h1>
 * The {@code TBListModel} class represents the list of Patients.
 */
public class TBListModel extends AbstractListModel{
    ArrayList<Patient> list;
    
    /**
     * This method is the main method of TBList model class that constructs 
     * the list of Patients.
     * @param   list
     *          ArrayList object of Patient.
     */
    public TBListModel(ArrayList<Patient> list) {
        this.list = list;
    }
    
    /**
     * Return the integer representation of the size of the list of Patients.
     * @return  Size of the list.
     */
    @Override
    public int getSize() {
        return list.size();
    }

    /**
     * This method gets the element at index n.
     * @param   index
     *          Integer representation of the index.
     * @return  Index
     */
    @Override
    public Object getElementAt(int index) {
       return list.get(index);
    }
    
    /**
     * This method gets the element ID at index n.
     * @param   index
     *          Integer representation of the index.
     * @return  ID
     */
    public int getElementID(int index) {
        return list.get(index).getId();
    }
}
