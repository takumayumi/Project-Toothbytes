/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.model;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import toothbytes.model.Patient;

/**
 *
 * @author Jolas
 */
public class TBListModel extends AbstractListModel{
    ArrayList<Patient> list;
    
    public TBListModel(ArrayList<Patient> list) {
        this.list = list;
    }
    
    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getElementAt(int index) {
       return list.get(index);
    }
    
    public int getElementID(int index) {
        return list.get(index).getId();
    }
}
