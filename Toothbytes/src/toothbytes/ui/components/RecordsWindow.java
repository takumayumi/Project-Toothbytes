package toothbytes.ui.components;

import toothbytes.ui.components.PatientListViewer;
import toothbytes.ui.components.ModuleWindow;
import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import toothbytes.database.DBAccess;
import toothbytes.model.Patient;
import toothbytes.model.PatientX;
import toothbytes.util.DataMan;

public class RecordsWindow extends ModuleWindow {

    private PatientListViewer plv;
    private JTabbedPane tabsPane;
    private JPanel dentalViewer, infoViewer, gallery;
    private MigLayout layout, formLayout;
    private JScrollPane scrollInfo, scrollDental, scrollGallery;
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
        
        tabsPane.addTab("Personal Info", new ImageIcon("src/toothbytes/res/icons/btn/PersonalInfo.png"), scrollInfo);
        tabsPane.addTab("Dental Info", new ImageIcon("src/toothbytes/res/icons/btn/DentalRecords.png"), scrollDental);
        tabsPane.addTab("Gallery", new ImageIcon("src/toothbytes/res/icons/btn/Images.png"), scrollGallery);
        
        super.addToMainPane(plv, "span 2, grow");
        super.addToMainPane(tabsPane, "span 10, grow");
    }
    
    public void showDental(Patient p) {
        //dental chart
        //table listing treatments and condition
    }
    
    public void showInfo(PatientX p) {
        //if there is a selected patient clear the viewer
        if(this.current != null) {
            infoViewer.removeAll();
        }
        
        this.current = p;
        
        File f = new File("src/toothbytes/res/photos/" + p.getId() + ".jpg");
        JLabel photo = new JLabel();
        JLabel name = new JLabel(p.getFirstName() + " " + p.getMidName() + ". " + p.getLastName());
        JLabel lblName = new JLabel("Name:");
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
            photo.setIcon(new ImageIcon("src/toothbytes/res/photos/patient.png"));
        }
        
        infoViewer.add(photo, "span 3");
        
        infoViewer.add(lblName, "skip 12, span 2");
        infoViewer.add(name, "span 7");
        
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
        
        SwingUtilities.updateComponentTreeUI(infoViewer);
    }
    
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
}
