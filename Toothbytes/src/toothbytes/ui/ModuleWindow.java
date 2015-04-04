package toothbytes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ModuleWindow extends JPanel{
    private JPanel toolBar, mainPanel;
    private JButton addPatientBut, setAppointmentBut;
    public ModuleWindow(){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        toolBar = new JPanel();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.setBackground(Color.white);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        this.add(toolBar, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        
        addPatientBut = new JButton("New Patient");
        addPatientBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/AddNewPatient.png"));
        
        addPatientBut.addActionListener(new java.awt.event.ActionListener(){
            
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                java.awt.EventQueue.invokeLater(new Runnable() {
                    
                    public void run() {
                        JFrame ctb = new JFrame();
                        PersonalInformation pi = new PersonalInformation(ctb);
                        System.out.println(pi.isVisible());
                        ctb.setSize(pi.getPreferredSize());
                        ctb.add(pi);
                        ctb.pack();
                        ctb.setVisible(true);
                        ctb.setForeground(Color.white);
                        ctb.setBackground(Color.white);
                        ctb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }
                    }
                );
            }
        });
        
        
        setAppointmentBut = new JButton("New Appointment");
        setAppointmentBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/AddNewAppointment.png"));
        toolBar.add(addPatientBut);
        toolBar.add(setAppointmentBut);
    }
    public void setMainPaneLayout(LayoutManager l) {
        mainPanel.setLayout(l);
    }
    public void addToMainPane(JComponent c) {
        mainPanel.add(c);
    }
    public void addToMainPane(JComponent c, String s) {
        mainPanel.add(c, s);
    }
}