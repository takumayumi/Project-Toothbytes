/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.SimpleDateFormat;
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
                "10px[fill]push[fill]push[fill]20px[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]", //12 columns
                "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]" //14 rows
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
        JLabel lName = new JLabel(p.getLastName());
        JLabel lblLname = new JLabel("Last Name:");
        JLabel fName = new JLabel(p.getFirstName());
        JLabel lblFname = new JLabel("First Name:");
        JLabel mName = new JLabel(p.getMidName());
        JLabel lblMname = new JLabel("Middle Initial:");
        JLabel age = new JLabel(DataMan.getAge(p.getBdate())+"");
        JLabel lblAge = new JLabel("Age:");
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String bday = format1.format(p.getBdate().getTime());
        JLabel bdate = new JLabel(bday);
        JLabel lblBdate = new JLabel("Birthdate:");
        JLabel occupation = new JLabel(p.getOccupation());
        JLabel lblOccupation = new JLabel("Occupation:");
        JLabel civstat = new JLabel(p.getCivilStatus());
        JLabel lblCivstat = new JLabel("Civil Status:");
        JLabel gender = new JLabel(p.getGender()+"");
        JLabel lblGender = new JLabel("Gender:");
        JLabel nName = new JLabel(p.getNickname());
        JLabel lblNname = new JLabel("Nickname:");
        JLabel homeadd = new JLabel(p.getHomeAddress());
        JLabel lblHomeadd = new JLabel("Home Address:");
        JLabel homeno = new JLabel(p.getHomeNo());
        JLabel lblHomeno = new JLabel("Home Number:");
        JLabel officeno = new JLabel(p.getOfficeNo());
        JLabel lblOfficeno = new JLabel("Office Number:");
        JLabel faxno = new JLabel(p.getFaxNo());
        JLabel lblFaxno = new JLabel("Fax Number:");
        JLabel cellno = new JLabel(p.getCellNo());
        JLabel lblCellno = new JLabel("Cellphone Number:");
        JLabel eAdd = new JLabel(p.getEmailAdd());
        JLabel lblEadd = new JLabel("Email Address:");
        
        //lName.setFont(new Font("Calibri", Font.PLAIN, 12));
        //lName.setEditable(false);
        
        if(f.exists()) {
            photo.setIcon(new ImageIcon(f.getAbsolutePath()));
        } else {
            photo.setIcon(new ImageIcon("src/toothbytes/res/photos/patient.png"));
        }
        
        patientViewer.add(photo, "span 3");
        
        patientViewer.add(lblLname, "skip 12, span 2");
        patientViewer.add(lName, "span 7");
        
        patientViewer.add(lblFname, "skip 3, span 2");
        patientViewer.add(fName, "span 7");
        
        patientViewer.add(lblMname, "skip 3, span 2");
        patientViewer.add(mName, "span 7");
        
        patientViewer.add(lblAge, "skip 3, span 2");
        patientViewer.add(age, "span 7");
        
        patientViewer.add(lblBdate, "skip 3, span 2");
        patientViewer.add(bdate, "span 7");
        
        patientViewer.add(lblOccupation, "skip 3, span 2");
        patientViewer.add(occupation, "span 7");
        
        patientViewer.add(lblCivstat, "skip 3, span 2");
        patientViewer.add(civstat, "span 7");
        
        patientViewer.add(lblGender, "skip 3, span 2");
        patientViewer.add(gender, "span 7");
        
        patientViewer.add(lblNname, "skip 3, span 2");
        patientViewer.add(nName, "span 7");
        
        patientViewer.add(lblHomeadd, "skip 3, span 2");
        patientViewer.add(homeadd, "span 7");
        
        patientViewer.add(lblHomeno, "skip 3, span 2");
        patientViewer.add(homeno, "span 7");
        
        patientViewer.add(lblOfficeno, "skip 3, span 2");
        patientViewer.add(officeno, "span 7");
        
        patientViewer.add(lblFaxno, "skip 3, span 2");
        patientViewer.add(faxno, "span 7");
        
        patientViewer.add(lblFaxno, "skip 3, span 2");
        patientViewer.add(faxno, "span 7");
        
        patientViewer.add(lblCellno, "skip 3, span 2");
        patientViewer.add(cellno, "span 7");
        
        patientViewer.add(lblEadd, "skip 3, span 2");
        patientViewer.add(eAdd, "span 7");
        
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
