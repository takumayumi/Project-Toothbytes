package toothbytes.model;

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
     * 
     * @return  
     */
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
