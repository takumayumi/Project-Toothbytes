/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui.components;

import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import toothbytes.database.DBAccess;
import toothbytes.model.Patient;
import toothbytes.model.PatientX;

/**
 *
 * @author Jolas
 */
public class PaymentWindow extends ModuleWindow{
    private PatientListViewer plv;
    private PaymentViewer pv;
    private int currentId;
    
    public PaymentWindow(ArrayList<Patient> pList) {
        plv = new PatientListViewer(pList);
        
        PatientListListener pll = new PatientListListener();
        plv.setListListener(pll);
        
        currentId = 1;
        pv = new PaymentViewer(currentId);
        
        this.addToMainPane(pv);
        this.addPLV(plv);
    }
    
    public void ChangePaymentViewer(){
        pv.removeAll();
        this.remove(pv);
        pv = new PaymentViewer(currentId);
        this.addToMainPane(pv);
        SwingUtilities.updateComponentTreeUI(pv);
        repaint();
        revalidate();
    }
    
    public class PatientListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try{
                if (!e.getValueIsAdjusting()) {
                    if(plv.getSelectedPatient().getId() != currentId){
                        currentId = plv.getSelectedPatient().getId();
                        ChangePaymentViewer();
                    }
                }
            }catch(Exception ex){
                System.out.println(plv.getSelectedPatient().getId());
                System.out.println(ex);
            }
            
        }

    }
}
