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
    private JToolBar ctoolbox, ttoolbox;
    private JScrollPane dcScroll, tableScroll;
    private static JTable table;
    private MigLayout layout;
    private String selectedTool;
    private static DefaultTableModel tableModel;
    private ToolsHandler th;
    private JPopupMenu tablePop;
    private JMenuItem deleteRow;

    public TreatmentWindow(JFrame f, Patient p) {
        super(f);
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 75);

        layout = new MigLayout("wrap 6", "[110px][]push[]push[]push[]push[]", "[][][][][][]");

        this.setLayout(layout);

        patientInfo = new JPanel();
        name = new JLabel(p.getFullName());

        save = new JButton("Save", new ImageIcon("res/buttons/save.png"));

        patientInfo.add(name);
        patientInfo.add(save);

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

        dc = new DentalChart();

        dcScroll = new JScrollPane(dc);

        tablePop = new JPopupMenu();
        deleteRow = new JMenuItem("Delete Entry");
        deleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable t = (JTable) e.getSource();
                t.getValueAt(t.getSelectedRow(), 0);
                tableModel.removeRow(t.getSelectedRow());
            }
        });
        tablePop.add(deleteRow);
        table = new JTable();
        table.setComponentPopupMenu(tablePop);
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        setupTable();
        tableScroll = new JScrollPane(table);

        this.add(patientInfo, "north");
        this.add(ctoolbox, "span 1 1");
        this.add(dcScroll, "span 5 2");
        this.add(ttoolbox, "span 1 1");
        this.add(tableScroll, "skip2, span 4 1");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

            }
        });

        dc.setEnabled(true);
    }
    
    public TreatmentWindow(JFrame f, Patient p, DentalChart dc) {
        super(f);
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 75);

        layout = new MigLayout("wrap 6", "[110px][]push[]push[]push[]push[]", "[][][][][][]");

        this.setLayout(layout);

        patientInfo = new JPanel();
        name = new JLabel(p.getFullName());

        save = new JButton("Save", new ImageIcon("res/buttons/save.png"));

        patientInfo.add(name);
        patientInfo.add(save);

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

        camera = new JButton(new ImageIcon("res/buttons/camera.png"));
        patientInfo.add(camera);
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

        this.dc = dc;

        dcScroll = new JScrollPane(dc);

        tablePop = new JPopupMenu();
        deleteRow = new JMenuItem("Delete Entry");
        deleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable t = (JTable) e.getSource();
                t.getValueAt(t.getSelectedRow(), 0);
                tableModel.removeRow(t.getSelectedRow());
            }
        });
        tablePop.add(deleteRow);
        table = new JTable();
        table.setComponentPopupMenu(tablePop);
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        setupTable();
        tableScroll = new JScrollPane(table);

        this.add(patientInfo, "north");
        this.add(ctoolbox, "span 1 1");
        this.add(dcScroll, "span 5 2");
        this.add(ttoolbox, "span 1 1");
        this.add(tableScroll, "span 5 2");
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

    public void setupTable() {
        tableModel.addColumn("Tooth No.");
        tableModel.addColumn("Condition");
        tableModel.addColumn("Remarks");
        tableModel.addColumn("Delete");

        table = new JTable(tableModel);

        table.getColumnModel().getColumn(0).setMinWidth(100);
        table.getColumnModel().getColumn(1).setMinWidth(60);
        table.getColumnModel().getColumn(2).setMinWidth(100);

        table.getColumnModel().getColumn(0).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(300);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getTableHeader().setReorderingAllowed(false);
    }

    public static void updateTable(int n, String s) {
        tableModel.addRow(new Object[]{"", "", "", ""});

        table.setValueAt(n, tableModel.getRowCount() - 1, 0);

        table.setValueAt(s, tableModel.getRowCount() - 1, 1);

        table.setValueAt("", tableModel.getRowCount() - 1, 2);

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
