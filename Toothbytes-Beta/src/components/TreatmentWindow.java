/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.DentalChart;
import models.Patient;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jolas
 */
public class TreatmentWindow extends JDialog {

    private JPanel patientInfo;
    private JLabel photo, name, bp, bpIcon;
    private JButton editMedHistory, save, crownB, decayB, uneruptB, normalB, laserB, extractB,
            bridgeB, missingB, fillB, camera;
    private DentalChart dc;
    private JToolBar toolbox, ctoolbox, ttoolbox;
    private JScrollPane dcScroll;
    private static JTable table;
    private MigLayout layout;
    private String selectedTool;
    private ToolsHandler th;

    public TreatmentWindow(JFrame f, Patient p) {
        super(f);
        dc = new DentalChart();
        initComponents(p);
        this.pack();
    }

    public TreatmentWindow(JFrame f, Patient p, DentalChart dc) {
        super(f);
        this.dc = dc;
        initComponents(p);
        this.pack();
    }

    public void initComponents(Patient p) {
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 75);

        layout = new MigLayout("wrap 6", "[110px][]push[]push[]push[]push[]", "[][][][][][]");

        this.setLayout(layout);

        patientInfo = new JPanel();
        name = new JLabel(p.getFullName());

        save = new JButton("Save", new ImageIcon("res/buttons/save.png"));

        patientInfo.add(name);
        patientInfo.add(save);

        toolbox = new JToolBar("toolbox");
        toolbox.setOrientation(JToolBar.VERTICAL);

        ctoolbox = new JToolBar("ctoolbox");
        ctoolbox.setOrientation(JToolBar.VERTICAL);
        ctoolbox.setBorder(new TitledBorder("Conditions"));
        ctoolbox.setFloatable(false);

        ttoolbox = new JToolBar("ttoolbox");
        ttoolbox.setOrientation(JToolBar.VERTICAL);
        ttoolbox.setBorder(new TitledBorder("Treatments"));
        ttoolbox.setFloatable(false);

        th = new ToolsHandler();

        decayB = createToolButton("res/teeth/decayed.png", "Decayed");
        uneruptB = createToolButton("res/teeth/unerupted_s.png", "Unerupted");
        missingB = createToolButton("res/teeth/missing_s.png", "Missing");
        normalB = createToolButton("res/teeth/normal_s.png", "Normal");

        laserB = createToolButton("res/teeth/laser_s.png", "Laser");
        bridgeB = createToolButton("res/teeth/bridge_s.png", "Bridge");
        crownB = createToolButton("res/teeth/crown_s.png", "Crowning");
        fillB = createToolButton("res/teeth/amal_s.png", "Filling");
        extractB = createToolButton("res/teeth/extract_s.png", "Extraction");

        ctoolbox.add(normalB);
        ctoolbox.add(uneruptB);
        ctoolbox.add(decayB);
        ctoolbox.add(missingB);

        ttoolbox.add(laserB);
        ttoolbox.add(bridgeB);
        ttoolbox.add(crownB);
        ttoolbox.add(fillB);
        ttoolbox.add(extractB);

        toolbox.add(ctoolbox);
        toolbox.add(ttoolbox);

        camera = new JButton(new ImageIcon("res/buttons/camera.png"));
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

        dcScroll = new JScrollPane(dc);

        this.add(patientInfo, "north");
        this.add(toolbox, "span 1 1");
        this.add(dcScroll, "span 5 2");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });

        dc.setEnabled(true);

    }

    private JButton createToolButton(String icon, String name) {
        JButton temp = new JButton(name, new ImageIcon(icon));
        temp.setFont(new Font("Calibri", Font.PLAIN, 14));
        temp.setSize(25, 80);
        temp.addActionListener(th);
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
                dc.updatePreState("laser");
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
        }

    }

}
