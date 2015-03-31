/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import toothbytes.database.DBAccess;
import toothbytes.model.Patient;
import toothbytes.model.PatientX;
import toothbytes.util.DataMan;

/**
 *
 * @author Jolas
 */
public class RecordsWindow extends ModuleWindow {

    private PatientListViewer plv;
    private JPanel patientViewer;
    private MigLayout layout, formLayout;
    private JScrollPane scroll;
    private PatientX current;

    public RecordsWindow(ArrayList<Patient> pList) {
        layout = new MigLayout(
                "filly, wrap 12",
                "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]" //12 columns
        );
        formLayout = new MigLayout(
                "wrap 12",
                "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]" //12 columns
        );
        
        super.setMainPaneLayout(layout);
        plv = new PatientListViewer(pList);
        
        PatientListListener pll = new PatientListListener();
        plv.setListListener(pll);
        
        patientViewer = new JPanel();
        patientViewer.setLayout(formLayout);
        patientViewer.setBackground(Color.white);
        
        scroll = new JScrollPane(patientViewer);
        super.addToMainPane(plv, "span 2, grow");
        super.addToMainPane(scroll, "span 10, grow");
    }

    public void showInfo(PatientX p) {
        if(this.current != null) {
            patientViewer.removeAll();
        }
        
        this.current = p;
        
        File f = new File("src/toothbytes/res/photos/" + p.getId() + ".jpg");
        JLabel photo = new JLabel();
        JTextField lName = new JTextField(p.getLastName().toUpperCase());
        JTextField fName = new JTextField(p.getFirstName());
        JTextField mName = new JTextField(p.getMidName());
        
        JTextField age = new JTextField(DataMan.getAge(p.getBdate())+"");
        
        lName.setFont(new Font("Calibri", Font.PLAIN, 18));
        lName.setEditable(false);
        
        if(f.exists()) {
            photo.setIcon(new ImageIcon(f.getAbsolutePath()));
        } else {
            photo.setIcon(new ImageIcon("src/toothbytes/res/photos/default_img_1.jpg"));
        }
        patientViewer.add(photo, "span 2");
        patientViewer.add(lName, "span 4");
        patientViewer.add(fName, "span 4");
        patientViewer.add(mName, "span 1");
        patientViewer.add(age, "span 1");
        
        
        SwingUtilities.updateComponentTreeUI(patientViewer);
    }
    
    public class PatientListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Patient p = plv.getSelectedPatient();
                showInfo(DBAccess.getData(p.getId()));
            }
        }

    }
}
