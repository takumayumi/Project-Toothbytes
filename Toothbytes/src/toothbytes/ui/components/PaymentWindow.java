/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui.components;

import java.util.ArrayList;
import toothbytes.model.Patient;

/**
 *
 * @author Jolas
 */
public class PaymentWindow extends ModuleWindow{
    private PatientListViewer plv;
    private PaymentViewer pv;
    
    public PaymentWindow(ArrayList<Patient> pList) {
        plv = new PatientListViewer(pList);
        pv = new PaymentViewer();
        
        this.addToMainPane(pv);
        this.addPLV(plv);
    }
}
