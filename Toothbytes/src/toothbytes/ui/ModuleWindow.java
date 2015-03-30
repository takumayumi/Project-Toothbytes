/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Jolas
 */
public class ModuleWindow extends JPanel{
    private JPanel toolBar, mainPanel;
    private JButton addPatientBut, setAppointmentBut;
    public ModuleWindow(){
        this.setLayout(new BorderLayout());
        toolBar = new JPanel();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.setBackground(Color.white);
        mainPanel = new JPanel();
        this.add(toolBar, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        
        addPatientBut = new JButton("New Patient");
        setAppointmentBut = new JButton("New Appointment");
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