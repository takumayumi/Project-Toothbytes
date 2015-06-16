/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import components.Camera;
import components.OtherTreatmentDialog;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDateTime;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import models.DentalChart;
import models.Patient;
import net.miginfocom.swing.MigLayout;
import utilities.Configuration;
import utilities.DataMan;

/**
 *
 * @author Jolas
 */
public class TreatmentWindow extends JDialog {

    private JPanel mainP, patientInfo;
    private JLabel photo, name, bp, bpIcon, today;
    private JToggleButton crownB, decayB, uneruptB, normalB, laserB, extractB,
            bridgeB, missingB, fillB, otherT;
    private JButton save, camera;
    private DentalChart dc;
    private JToolBar toolbox, ctoolbox, ttoolbox;
    private JScrollPane dcScroll;
    private static JTable table;
    private MigLayout layout;
    private String selectedTool;
    private ToolsHandler th;
    private OtherTreatmentDialog otd;
    private ButtonGroup toolsGroup;

    public TreatmentWindow(JFrame f, Patient p) {
        super(f);
        dc = new DentalChart(true);
        initComponents(p);
//        this.pack();
    }

    public TreatmentWindow(JFrame f, Patient p, DentalChart dcPassed) {
        super(f);
        this.dc = new DentalChart(true);
        for (int i = 0; i < dcPassed.getTable().getRowCount(); i++) {
            this.dc.updateTooth((Integer) dcPassed.getTable().getValueAt(i, 0), (String) dcPassed.getTable().getValueAt(i, 1), false);
        }
        initComponents(p);
//        this.pack();
    }

    private final String PATIENTS_DIR = "res/patients/";

    public void initComponents(Patient p) {
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 75);

        layout = new MigLayout("wrap 6", "[110px][fill]push[fill]push[fill]push[fill]push[fill]", "[]");

        mainP = new JPanel(layout);
        this.setContentPane(mainP);

        patientInfo = new JPanel(new MigLayout("fill"));

        File f = new File(PATIENTS_DIR + p.getId() + ".jpg");

        if (f.exists()) {
            photo = new JLabel(DataMan.ResizeImage(f.getAbsolutePath(), 70, 70));
        } else {
            photo = new JLabel(DataMan.ResizeImage("res/images/patient_rev.png", 70, 70));
        }

        name = new JLabel(p.getFullName());
        name.setFont(Configuration.TB_FONT_HEADER);

        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();

        today = new JLabel(month + "/" + day + "/" + year);
        today.setFont(Configuration.TB_FONT_BANNER);

        save = new JButton("Save & Finish", new ImageIcon("res/buttons/save.png"));
        mainP.add(save, "width 50:150:150, height 40:40:40,gapy 0 5,south, center");

        name.setForeground(new Color(65, 200, 115));
        today.setForeground(new Color(65, 200, 115));

        patientInfo.setBackground(new Color(100, 100, 100));
        patientInfo.add(photo, "west");
        patientInfo.add(name, "gapx 10 0, left");
        patientInfo.add(today, "east, gapx 0 10");

        toolbox = new JToolBar("toolbox");
        toolbox.setOrientation(JToolBar.VERTICAL);
        toolbox.setFloatable(false);

        ctoolbox = createToolBar("Conditions");

        ttoolbox = createToolBar("Treatments");

        th = new ToolsHandler();

        toolsGroup = new ButtonGroup() {
            @Override
            public void setSelected(ButtonModel model, boolean selected) {
                if (selected) {
                    super.setSelected(model, selected);
                } else {
                    clearSelection();
                }
            }
        };

        decayB = createToolButton("res/teeth/decayed.png", "Decaying");
        uneruptB = createToolButton("res/teeth/unerupted_s.png", "Unerupted");
        missingB = createToolButton("res/teeth/missing_s.png", "Missing");
        normalB = createToolButton("res/teeth/normal_s.png", "Normal Condition");
        normalB.setSelected(true);

        laserB = createToolButton("res/teeth/laser_s.png", "Laser");
        bridgeB = createToolButton("res/teeth/bridge_s.png", "Bridge");
        crownB = createToolButton("res/teeth/crown_s.png", "Crowning");
        fillB = createToolButton("res/teeth/amal_s.png", "Filling");
        extractB = createToolButton("res/teeth/extract_s.png", "Extraction");

        otherT = createToolButton("res/teeth/normal_s.png", "Other Treatment");
        otd = new OtherTreatmentDialog(this);

        ctoolbox.add(normalB);
        ctoolbox.add(uneruptB);
        ctoolbox.add(decayB);
        ctoolbox.add(missingB);
        ctoolbox.setSize(toolbox.getWidth(), ctoolbox.getHeight());

        ttoolbox.add(laserB);
        ttoolbox.add(bridgeB);
        ttoolbox.add(crownB);
        ttoolbox.add(fillB);
        ttoolbox.add(extractB);
        ttoolbox.add(otherT);

        toolbox.add(ctoolbox);
        toolbox.add(ttoolbox);

        camera = new JButton( "Intraoral Camera", new ImageIcon("res/buttons/camera.png"));
        camera.setFont(Configuration.TB_FONT_HEADER);
        camera.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        Camera camera = new Camera(p.getId());
                    }
                });
            }

        });
        toolbox.add(camera);

        dcScroll = new JScrollPane(dc);

        mainP.add(patientInfo, "north, gapx 5 0, gapy 15 15, grow");
        mainP.add(toolbox, "gapx 5 1,west, grow");
        mainP.add(dcScroll, "span 5 1, growx");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });

        dc.setEnabled(true);

    }

    private JToolBar createToolBar(String title) {
        JToolBar t = new JToolBar(title);
        t.setOrientation(JToolBar.VERTICAL);
        t.setBorder(new TitledBorder(null, title, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, Configuration.TB_FONT_HEADER_B));
        t.setFloatable(false);
        return t;
    }

    private JToggleButton createToolButton(String icon, String name) {
        JToggleButton temp = new JToggleButton(name, new ImageIcon(icon));
        temp.setFont(Configuration.TB_FONT_HEADER);
        temp.setSize(25, 80);
        temp.addActionListener(th);
        toolsGroup.add(temp);
        return temp;
    }

    public class ToolsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(normalB)) {
                dc.updatePreState("normal");
            }
            if (e.getSource().equals(uneruptB)) {
                dc.updatePreState("unerupted");
            }
            if (e.getSource().equals(decayB)) {
                dc.updatePreState("decayed");
            }
            if (e.getSource().equals(missingB)) {
                dc.updatePreState("missing");
            }
            if (e.getSource().equals(laserB)) {
                dc.updatePreState("laser bleaching");
            }
            if (e.getSource().equals(bridgeB)) {
                dc.updatePreState("bridge");
            }
            if (e.getSource().equals(crownB)) {
                dc.updatePreState("crown");
            }
            if (e.getSource().equals(fillB)) {
                dc.updatePreState("filling");
            }
            if (e.getSource().equals(extractB)) {
                dc.updatePreState("extraction");
            }
            if (e.getSource().equals(otherT)) {
                otd.setModalityType(JDialog.ModalityType.MODELESS);
                otd.setBounds(otherT.getLocationOnScreen().x + 120, otherT.getLocationOnScreen().y, 200, 100);
                otd.setVisible(true);
                String[] temp = otd.getTool().split(":");
                dc.updatePreState(temp[0], temp[1]);
            }
        }

    }

    public void refresh() {
        String[] temp = otd.getTool().split(":");
        dc.updatePreState(temp[0], temp[1]);
    }

//    public static void main(String[] args) {
//        JFrame f = new JFrame();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setVisible(true);
//        TreatmentWindow tw = new TreatmentWindow(f, new Patient(1, "asd", "asd", "a"));
//        tw.setVisible(true);
//    }
}
