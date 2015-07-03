/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package window;

import components.ModuleWindow;
import components.PatientListViewer;
import components.PaymentViewer;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.Patient;
import net.miginfocom.swing.MigLayout;

public class PaymentWindow extends ModuleWindow{
    private PatientListViewer plv;
    private PaymentViewer pv;
    private int currentId;
    private MigLayout layout;
    private JScrollPane pay;
    
    public PaymentWindow(ArrayList<Patient> pList) {
        layout = new MigLayout(
                "filly, wrap 12",
                "[fill]push[fill][fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]" //12 columns
        );
        
        super.setMainPaneLayout(layout);
        pay = new JScrollPane(pv);
        plv = new PatientListViewer(pList);
        
        PatientListListener pll = new PatientListListener();
        plv.setListListener(pll);
        
        currentId = 1;
        pv = new PaymentViewer(currentId);
        
        super.addToMainPane(plv, "span 2, grow");
        super.addToMainPane(pay, "span 10, grow");
    }
    
    public void ChangePaymentViewer(){
        
        this.removeMainComponent(1);
        pv = new PaymentViewer(currentId);
        pay = new JScrollPane(pv);
        super.addToMainPane(pay, "span 10, grow");
        SwingUtilities.updateComponentTreeUI(this);
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
