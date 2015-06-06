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
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.DentalChart;
import models.OrganizedTreatment;
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
    private MigLayout layout, formLayout, chartLayout;
    private JScrollPane scrollInfo, scrollDental, scrollGallery;
    private PatientX current;
    private JButton patientRepBut;
    
    final String BUTTON_DIR = "res/buttons/";
    private static JDBCConnection conn = null;
    private static String dir = "data/db";
    private JTable theTable;
    private DefaultTableModel tableModel;
    private JFrame owner;

    /**
     * This constructor layouts the Records Window.
     *
     * @param pList Object of array list of Patient.
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
        dentalViewer.setLayout(new BorderLayout());
        dentalViewer.setBackground(Color.white);

        gallery = new JPanel();
        gallery.setLayout(formLayout);
        gallery.setBackground(Color.white);

        scrollInfo = new JScrollPane(infoViewer);
        scrollGallery = new JScrollPane(gallery);

        tabsPane.addTab("Personal Info", new ImageIcon(ICON_DIR + "PersonalInfo.png"), scrollInfo);
        tabsPane.addTab("Dental Info", new ImageIcon(ICON_DIR + "DentalRecords.png"), dentalViewer);
        tabsPane.addTab("Gallery", new ImageIcon(ICON_DIR + "Images.png"), scrollGallery);

        super.addToMainPane(plv, "span 2, grow");
        super.addToMainPane(tabsPane, "span 10, grow");
    }

    public void setOwner(JFrame f) {
        owner = f;
    }

    private JFrame getOwner() {
        return owner;
    }

    public void setupTable() {
        tableModel.addColumn("Date");
        tableModel.addColumn("Tooth No.");
        tableModel.addColumn("Condition");
        tableModel.addColumn("Remarks");

        theTable = new JTable(tableModel);

        theTable.getColumnModel().getColumn(0).setMinWidth(100);
        theTable.getColumnModel().getColumn(1).setMinWidth(60);
        theTable.getColumnModel().getColumn(2).setMinWidth(100);

        theTable.getColumnModel().getColumn(0).setMaxWidth(200);
        theTable.getColumnModel().getColumn(1).setMaxWidth(100);
        theTable.getColumnModel().getColumn(2).setMaxWidth(300);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        theTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        theTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        theTable.getTableHeader().setReorderingAllowed(false);
    }

    private final String ICON_DIR = "res/buttons/";
    JButton checkup;
    JLabel chart;
    private boolean trigger = false;
    private DentalChart dc = new DentalChart();
    private boolean isTriggered() {
        return trigger;
    }
    public void showDental(Patient p) {
        chartLayout = new MigLayout("wrap 4, filly", "[]push[]push[]push[]", "[]");
        //if there is a selected patient clear the viewer
        if (this.current != null) {
            dentalViewer.removeAll();
        }

        JPanel dentalPanel = new JPanel(chartLayout);
        ArrayList<OrganizedTreatment> otList = DataMan.organizeTreatment(DBAccess.getTreatmentList(p.getId()));
        for (int i = 0; i < otList.size(); i++) {
            OrganizedTreatment temp = otList.get(i);
            for (int j = i + 1; j < otList.size(); j++) {
                if (temp.getDate().compareTo(otList.get(j).getDate()) == -1) {
                    otList.set(i, otList.get(j));
                    otList.set(j, temp);
                }
            }
        }
        
        if (otList.size() >= 1) {
            trigger = true;
            //chart
            dc = new DentalChart();
            for (int i = 1; i < 53; i++) {
                if (otList.get(0).getHm().containsKey(i)) {
                    dc.updateTooth(i + 1, otList.get(0).getHm().get(i).toLowerCase());
                    dc.updateUI();
                }
            }
            JScrollPane dcScroll = new JScrollPane(dc);
            dentalPanel.add(dcScroll, "span 4, grow");

            //table
            tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int col) {
//                    if (col == 3) {
//                        return true;
//                    } else {
                    return false;
//                    }
                }
            };

            setupTable();
            JScrollPane tableHolder = new JScrollPane(theTable);

            SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
            for (OrganizedTreatment ot : otList) {

                tableModel.addRow(new Object[]{"", "", "", ""});

                theTable.setValueAt(format1.format(ot.getDate().getTime()), tableModel.getRowCount() - 1, 0);
                for (int i = 0; i < 52; i++) {
                    if (ot.getHm().containsKey(i)) {
                        theTable.setValueAt(i + 1, tableModel.getRowCount() - 1, 1);
                        theTable.setValueAt(ot.getHm().get(i), tableModel.getRowCount() - 1, 2);
                        tableModel.addRow(new Object[]{"", "", "", ""});
                    }
                }
            }

            dentalPanel.add(tableHolder, "span 4, grow");

        } else {
            JLabel info = new JLabel("This patient has still no dental record. Create now by clicking Start checkup!");
            info.setFont(new Font("Calibri", Font.PLAIN, 18));
            dentalPanel.add(info, "span 1 1");
        }

        checkup = new JButton("Start Checkup!");
        checkup.setIcon(new ImageIcon(ICON_DIR + "btn\\BeginTreatment.png"));
        checkup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (trigger) {
                    TreatmentWindow tw = new TreatmentWindow(getOwner(), p, dc);
                    tw.setVisible(true);
                } else {
                    TreatmentWindow tw = new TreatmentWindow(getOwner(), p);
                    tw.setVisible(true);
                }
            }
        });
        dentalViewer.add(dentalPanel, BorderLayout.CENTER);
        dentalViewer.add(checkup, BorderLayout.SOUTH);
        SwingUtilities.updateComponentTreeUI(dentalViewer);
    }

    /**
     * This method shows the information about the Patient from the database.
     *
     * @param p Object representation of PatientX.
     */
    public void showInfo(PatientX p) {
        //if there is a selected patient clear the viewer
        if (this.current != null) {
            infoViewer.removeAll();
        }

        this.current = p;

        File f = new File("res/images/" + p.getId() + ".jpg");

        JLabel photo = new JLabel();

        JLabel[] name = new JLabel[]{new JLabel(p.getLastName()), new JLabel(p.getFirstName()), new JLabel(p.getMidName())};
        for (JLabel n : name) {
            n.setFont(new Font("Calibri", Font.BOLD, 14));
        }

        JLabel age = new JLabel(DataMan.getAge(p.getBdate()) + "");

        JLabel lblAge = new JLabel("Age:");

        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");

        String bday = format1.format(p.getBdate().getTime());

        JLabel bdate = new JLabel(bday);

        JLabel lblBdate = new JLabel("Birthdate:");

        JLabel occupation = new JLabel(p.getOccupation());

        JLabel lblOccupation = new JLabel("Occupation:");

        JLabel civstat = new JLabel(p.getCivilStatus());

        JLabel lblCivstat = new JLabel("Civil Status:");

        JLabel gender = new JLabel(p.getGender() + "");

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

        if (f.exists()) {
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
