/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Color.WHITE;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.Patient;
import models.PatientX;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hsqldb.jdbc.JDBCConnection;
import org.hsqldb.jdbc.JDBCDriver;
import utilities.DBAccess;
import utilities.DataMan;

/**
 * <h1>RecordsWindow</h1>
 * The {@code RecordsWindow class constructs the Records Window to be able to 
 * see the list of patients and their respective attributes.
 */
public class RecordsWindow extends ModuleWindow {
    private Graphics2D g2d;
    private PatientListViewer plv;
    private JTabbedPane tabsPane;
    private JPanel dentalViewer, infoViewer, gallery;
    private MigLayout layout, formLayout;
    private JScrollPane scrollInfo, scrollDental, scrollGallery;
    private PatientX current;
    private JButton patientRepBut;
    
    final String BUTTON_DIR = "res/buttons/";
    private static JDBCConnection conn = null;
    private static String dir = "data/db";

    /**
     * This constructor layouts the Records Window.
     * @param   pList
     *          Object of array list of Patient.
     */
    public RecordsWindow(ArrayList<Patient> pList) {
        layout = new MigLayout(
                "filly, wrap 12",
                "[fill]push[fill][fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]" //12 columns
        );
        formLayout = new MigLayout(
                "wrap 12",
                "[fill]push[fill]push[fill]push[fill]push"
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
        
        tabsPane = new JTabbedPane();
        tabsPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        
        infoViewer = new JPanel();
        infoViewer.setLayout(formLayout);
        infoViewer.setBackground(Color.white);
        
        dentalViewer = new JPanel();
        dentalViewer.setLayout(formLayout);
        dentalViewer.setBackground(Color.white);
        
        gallery = new JPanel();
        gallery.setLayout(formLayout);
        gallery.setBackground(Color.white);
        
        scrollInfo = new JScrollPane(infoViewer);
        scrollDental = new JScrollPane(dentalViewer);
        scrollGallery = new JScrollPane(gallery);
        
        
        tabsPane.addTab("Personal Info", new ImageIcon(ICON_DIR+"PersonalInfo.png"), scrollInfo);
        tabsPane.addTab("Dental Info", new ImageIcon(ICON_DIR+"DentalRecords.png"), scrollDental);
        tabsPane.addTab("Gallery", new ImageIcon(ICON_DIR+"Images.png"), scrollGallery);
        
        super.addToMainPane(plv, "span 2, grow");
        super.addToMainPane(tabsPane, "span 10, grow");
    }
    
    private JFrame getFrame() {
        JFrame f =  (JFrame)this.getParent();
        return f;
    }

    private final String ICON_DIR = "res/buttons/";
    JButton checkup;
    JLabel chart;
    public void showDental(Patient p) {
        //if there is a selected patient clear the viewer
        if(this.current != null) {
            dentalViewer.removeAll();
        }
        
        File last = new File("saves\\"+p.getId()+"\\latest.png");
        if(last.exists()) {
            chart = new JLabel();
            chart.setIcon(new ImageIcon(last.getAbsolutePath()));
            
        } else {
            chart = new JLabel("Chart for last treatment unavailable");
        }
        
        
        checkup = new JButton("Start Checkup!");
        checkup.setIcon(new ImageIcon(ICON_DIR+"btn\\BeginTreatment.png"));
        checkup.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreatmentWindow tw = new TreatmentWindow(getFrame(), p);
            
            }
        });
        dentalViewer.add(chart, BorderLayout.CENTER);
        dentalViewer.add(checkup, BorderLayout.SOUTH);
        SwingUtilities.updateComponentTreeUI(dentalViewer);
    }
    


    /**
     * This method shows the information about the Patient from the database.
     * @param   p
     *          Object representation of PatientX.
     */

    public void showInfo(PatientX p) {
        //if there is a selected patient clear the viewer
        if(this.current != null) {
            infoViewer.removeAll();
        }
        
        this.current = p;
        
        File f = new File("res/images/" + p.getId() + ".jpg");
        
        JLabel photo = new JLabel();
        
        JLabel[] name = new JLabel[]{new JLabel(p.getLastName()), new JLabel(p.getFirstName()), new JLabel(p.getMidName())};
        for(JLabel n : name) {
            n.setFont(new Font("Calibri", Font.BOLD, 14));
        }
        
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
        
        if(f.exists()) {
            photo.setIcon(new ImageIcon(f.getAbsolutePath()));
        } else {
            photo.setIcon(new ImageIcon("res/images/patient.png"));
        }
        
        infoViewer.add(photo, "skip 13, span 2");
        
        infoViewer.add(name[0], "span 2");
        infoViewer.add(name[1], "skip1, span 2");
        infoViewer.add(name[2], "skip1, span 1");
        
        infoViewer.add(lblAge, "skip 3, span 2");
        
        infoViewer.add(age, "span 7");
        
        infoViewer.add(lblBdate, "skip 3, span 2");
        infoViewer.add(bdate, "span 7");
        
        infoViewer.add(lblOccupation, "skip 3, span 2");
        infoViewer.add(occupation, "span 7");
        
        infoViewer.add(lblCivstat, "skip 3, span 2");
        infoViewer.add(civstat, "span 7");
        
        infoViewer.add(lblGender, "skip 3, span 2");
        infoViewer.add(gender, "span 7");
        
        infoViewer.add(lblNname, "skip 3, span 2");
        infoViewer.add(nName, "span 7");
        
        infoViewer.add(lblHomeadd, "skip 3, span 2");
        infoViewer.add(homeadd, "span 7");
        
        infoViewer.add(lblHomeno, "skip 3, span 2");
        infoViewer.add(homeno, "span 7");
        
        infoViewer.add(lblOfficeno, "skip 3, span 2");
        infoViewer.add(officeno, "span 7");
        
        infoViewer.add(lblFaxno, "skip 3, span 2");
        infoViewer.add(faxno, "span 7");
        
        infoViewer.add(lblFaxno, "skip 3, span 2");
        infoViewer.add(faxno, "span 7");
        
        infoViewer.add(lblCellno, "skip 3, span 2");
        infoViewer.add(cellno, "span 7");
        
        infoViewer.add(lblEadd, "skip 3, span 2");
        infoViewer.add(eAdd, "span 7");
        
        patientRepBut = new JButton(new ImageIcon(BUTTON_DIR + "ReportGenPatient-30x30.png"));
        patientRepBut.setBackground(WHITE);
        patientRepBut.setToolTipText("Print Patient Records");
        
        PatientRecordsReport prr = new PatientRecordsReport();
        infoViewer.add(patientRepBut);
        patientRepBut.addActionListener(prr);
        
        SwingUtilities.updateComponentTreeUI(infoViewer);
    }
    
    public void printPatientRecords(){        
        try{
            Class.forName("org.hsqldb.jdbcDriver");
            String dbConn = "jdbc:hsqldb:file:"+dir+";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            File path = new File("Reports/patientRecords.jrxml");
            String reportPath = path.getCanonicalPath();
            JasperDesign jd = JRXmlLoader.load(reportPath);
            String sql = "SELECT patientPhoto, CONCAT(pa.patient_FirstName, ' ', pa.patient_MiddleInitial, '.', ' ', pa.patient_LastName) AS \"PATIENT NAME\", nickname, gender, birthdate, occupation, civilStatus, cellNo, homeNo, officeNo, faxNo, emailAddress, homeAddress, treatmentDate, procedure, amountCharged, balance  FROM patient pa\n" +
                        "JOIN dental_records dr ON pa.patientID = dr.patientID\n" +
                        "JOIN payments py ON dr.dentalRecordID = py.dentalRecordID\n" +
                        "WHERE patientID =" + current.getId();                                         
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jrCompile = JasperCompileManager.compileReport(jd);
            JasperPrint jpPrint = JasperFillManager.fillReport(jrCompile, null, conn);
            JasperViewer.viewReport(jpPrint, false);
            conn.close();
        }catch(IOException | ClassNotFoundException | SQLException | JRException error){
            JOptionPane.showMessageDialog(null,error);
        }
    }
    
    /**
     * <h1>PatientListListener</h1>
     * The {@code PatientListListener} class implements ListSelectionListener 
     * for retrieving the Patient data in database.
     */
    public class PatientListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Patient p = plv.getSelectedPatient();
                PatientX px = DBAccess.getData(p.getId());
                showInfo(px);
                showDental(p);
            }
        }
    }
    
    public class PatientRecordsReport implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == patientRepBut) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        printPatientRecords();
                    }
                }
                );
            }         
        }
    }
}
