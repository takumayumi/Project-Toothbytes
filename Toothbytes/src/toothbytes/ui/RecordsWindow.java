/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import toothbytes.model.Patient;

/**
 *
 * @author Jolas
 */
public class RecordsWindow extends ModuleWindow{
    private PatientListViewer plv;
    private JPanel patientViewer;
    private MigLayout layout;
    private JScrollPane scroll;
    public RecordsWindow(ArrayList<Patient> pList) {
        layout = new MigLayout(
                "filly, wrap 12",
                 "[fill]push[fill]push[fill]push[fill]push"
                        + "[fill]push[fill]push[fill]push[fill]push"
                        + "[fill]push[fill]push[fill]push[fill]" //12 columns
        );
        super.setMainPaneLayout(layout);
        plv = new PatientListViewer(pList);
        patientViewer = new JPanel();
        scroll = new JScrollPane(patientViewer);
        super.addToMainPane(plv, "span 2, grow");
        super.addToMainPane(scroll, "span 10, grow");
    }
}
