/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import components.Camera;
import components.LoadingScreen;
import components.OtherTreatmentDialog;
import components.listener.ChartListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.DentalChart;
import models.Patient;
import models.SaveFile;
import net.miginfocom.swing.MigLayout;
import utilities.Configuration;
import utilities.DBAccess;
import utilities.DataMan;

/**
 *
 * @author Jolas
 */
public class TreatmentWindow extends JDialog implements ChartListener {

    private JPanel mainP, patientInfo;
    private JLabel photo, name, bp, bpIcon, today;
    private JToggleButton crownB, decayB, uneruptB, normalB, laserB, extractB,
            bridgeB, missingB, fillB, otherT;
    private JButton save, camera;
    private DentalChart dc;
    private JToolBar toolbox, ctoolbox, ttoolbox;
    private JScrollPane dcScroll;
    private MigLayout layout;
    private ToolsHandler th;
    private OtherTreatmentDialog otd;
    private ButtonGroup toolsGroup;
    private LoadingScreen ls;
    private Date date;
    
    private ObjectOutputStream oos;
    private int pID;
    public TreatmentWindow(JFrame f, Patient p) {
        super(f);
        dc = new DentalChart(true);
        dc.addChartListener(this);
        initComponents(p);
//        this.pack();
    }

    public TreatmentWindow(JFrame f, Patient p, DentalChart dcPassed) {
        super(f);
        this.dc = new DentalChart(true);
        dc.addChartListener(this);
        for (int i = 0; i < dcPassed.getTable().getRowCount(); i++) {
            this.dc.updateTooth((Integer) dcPassed.getTable().getValueAt(i, 0), (String) dcPassed.getTable().getValueAt(i, 1), false);
        }
        initComponents(p);
//        this.pack();
    }

    private final String PATIENTS_DIR = "res/patients/";

    public void initComponents(Patient p) {
        pID = p.getId();
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 75);

        ls = new LoadingScreen("Treatment Starting...");
        this.setGlassPane(ls);
        this.getGlassPane().setVisible(true);

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
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = sdf.parse(month+"/"+day+"/"+year);
        } catch (ParseException ex) {
            Logger.getLogger(TreatmentWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        today = new JLabel(month + "/" + day + "/" + year);
        today.setFont(Configuration.TB_FONT_BANNER);

        save = new JButton("Save & Finish", new ImageIcon("res/buttons/save.png"));
        save.setEnabled(false);
        save.addActionListener((ActionEvent e) -> {
            FinishDialog fd = new FinishDialog((JDialog) save.getParent().getParent().getParent().getParent(), dc.getBreakDown());
            fd.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
            fd.setVisible(true);
        });

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

        camera = new JButton("Intraoral Camera", new ImageIcon("res/buttons/Camera v2.png"));
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
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {
                unlock();
            }
        });

        dc.setEnabled(true);

        File sav = new File("data/tooth/" + p.getId() + ".tbf");
        try {
            if (sav.exists()) {
                oos = new ObjectOutputStream(
                        new FileOutputStream(p.getId() + ".tbf"));
            } else {
                sav.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(TreatmentWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void unlock() {
        this.getGlassPane().setVisible(false);
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

    @Override
    public void notify(boolean changed) {
        save.setEnabled(changed);
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

    public class FinishDialog extends JDialog {

        private JPanel panel, buttons;
        private JTable bdTable;
        private JLabel logo, info;
        private JPasswordField pwd;
        private JButton yes, no;
        private boolean granted;
        private DefaultTableModel fdtm;

        /**
         * This constructor creates the login window and layouts it's
         * components.
         */
        public FinishDialog(JDialog d, Object[][] bDown) {
            super(d);
            this.setTitle("Total Fee");
            this.setSize(500, 400);
            this.setLocationRelativeTo(null);
            this.setResizable(false);

            panel = new JPanel(new MigLayout("fill"));
            this.setContentPane(panel);

            info = new JLabel("You can still edit the desired Amount to your desired Price before saving it", JLabel.LEFT);
            info.setFont(Configuration.TB_FONT_HEADER);
            info.setForeground(Color.GREEN);
            panel.add(info, "gapx 10 0, gapy 10 10,north");

            fdtm = new DefaultTableModel(bDown, new Object[]{"Treatments", "Quantity", "Amount"}) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    if (col == 2 && !(row == this.getRowCount() - 1)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

            bdTable = new JTable(fdtm);
            bdTable.getColumn("Amount").setCellEditor(new FeeEditor(new JTextField()));
            bdTable.setFont(Configuration.TB_FONT_HEADER);
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.LEFT);
            bdTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            totalFee();

            JScrollPane scrollTable = new JScrollPane(bdTable);
            panel.add(scrollTable, "center");

            buttons = new JPanel();

            panel.add(buttons, "south");
            yes = new JButton("Confirm");
            no = new JButton("Cancel");
            buttons.add(yes);
            buttons.add(no);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowActivated(WindowEvent e) {
                    d.setGlassPane(new JComponent() {
                        public void paintComponent(Graphics g) {
                            g.setColor(new Color(0, 0, 0, 200));
                            g.fillRect(0, 0, getWidth(), getHeight());
                            super.paintComponents(g);
                        }
                    });
                    d.getGlassPane().setVisible(true);
                }
            });

            yes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    ArrayList<SaveFile> sf = dc.getSaveList();
                    
                        DBAccess.saveTreatment(pID, today.getText(), dc.getSaveList());
//                            try {
//                                oos.writeObject(date);
//                                oos.writeObject(sf.get(i).getMark());
//                            } catch (IOException ex) {
//                                Logger.getLogger(TreatmentWindow.class.getName()).log(Level.SEVERE, null, ex);
//                            }                        
                }
            });

            no.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelled();
                }
            });
        }

        public void cancelled() {
            this.dispose();
            JDialog f = (JDialog) super.getOwner();
            f.getGlassPane().setVisible(false);
        }
        
        double total = 0;
        public void totalFee() {
            
            boolean hasTotal = false;
            for (int i = 0; i < bdTable.getRowCount(); i++) {
                System.out.println("Amount: " + bdTable.getValueAt(i, 2));
                if (bdTable.getValueAt(i, 1).equals("TOTAL")) {
                    hasTotal = true;
                    break;
                } else {
                    System.out.println("casting" + bdTable.getValueAt(i, 2));
                    String temp = bdTable.getValueAt(i, 2) + "";
                    total += Double.parseDouble(temp);
                }

            }

            if (hasTotal) {
                bdTable.setValueAt(total, bdTable.getRowCount() - 1, 2);
            } else {
//                fdtm.addRow(new Object[]{});
                fdtm.addRow(new Object[]{"", "TOTAL", total});
            }

        }

        public void lockButton() {
            yes.setEnabled(false);
        }

        public void unlockButton() {
            yes.setEnabled(true);
        }

        class FeeEditor extends DefaultCellEditor {

            protected JTextField text;

            private String label;

            private boolean isPushed;
            private int delRow;

            public FeeEditor(JTextField text) {
                super(text);
                this.text = text;
                text = new JTextField();
                text.setOpaque(true);
                text.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fireEditingStopped();
                    }
                });
            }

            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                if (isSelected) {
                    text.setForeground(table.getSelectionForeground());
                    text.setBackground(table.getSelectionBackground());
                } else {
                    text.setForeground(table.getForeground());
                    text.setBackground(table.getBackground());
                }
                label = (value == null) ? "" : value.toString();
                delRow = row;
                isPushed = true;
                return text;
            }

            public Object getCellEditorValue() {
                return new String(text.getText());
            }

            private boolean validateCell() {
                try {
                    Double.parseDouble(text.getText());
                } catch (NumberFormatException e) {
                    text.setText("");
                    lockButton();
                    JOptionPane.showMessageDialog(null, "Invalid Amount", "You must enter a number for the amount", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                unlockButton();
                return true;
            }

            public boolean stopCellEditing() {
                return super.stopCellEditing();
            }

            protected void fireEditingStopped() {
                if (validateCell()) {
                    super.fireEditingStopped();
                    bdTable.updateUI();
                    totalFee();
                } else {
                    super.cancelCellEditing();
                }
            }
        }
    }

//    class ButtonRenderer extends JButton implements TableCellRenderer {
//
//        public ButtonRenderer() {
//            setOpaque(true);
//        }
//
//        public Component getTableCellRendererComponent(JTable table, Object value,
//                boolean isSelected, boolean hasFocus, int row, int column) {
//            if (isSelected) {
//                setForeground(table.getSelectionForeground());
//                setBackground(table.getSelectionBackground());
//            } else {
//                setForeground(table.getForeground());
//                setBackground(UIManager.getColor("Button.background"));
//            }
//            setText((value == null) ? "" : value.toString());
//            return this;
//        }
//    }
}
