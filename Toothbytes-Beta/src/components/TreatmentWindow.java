/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import com.sun.javafx.tk.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import models.DentalChart;
import models.Patient;

/**
 *
 * @author Jolas
 */
public class TreatmentWindow extends JDialog{

    private JPanel patientInfo;
    private JLabel photo, name, bp, bpIcon;
    private JButton editMedHistory;
    private DentalChart dc;
    private JToolBar toolbox;
    private JScrollPane dcScroll, tableScroll;
    private JTable table;
    
    public TreatmentWindow(JFrame f, Patient p) {
        super(f);
        this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        
        patientInfo = new JPanel();
        name = new JLabel(p.getFullName());
    }
}
