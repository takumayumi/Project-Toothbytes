/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui.components;

import java.awt.BorderLayout;
import static java.awt.Color.WHITE;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jolas
 */
public class SidePanel extends JPanel{
    
    private JTabbedPane sideTabsPane;
    private JPanel sideAppointment, sidePayment;
    private JList sideAppList, sidePayList;
    private JScrollPane sideAppScroll, sidePayScroll;
    private JButton viewSched;
    
    public SidePanel() {
        this.setLayout(new BorderLayout());
        
        sideTabsPane = new JTabbedPane();
        this.add(sideTabsPane);
        
        //APPOINTMENTS
        sideAppointment = new JPanel();
        sideAppointment.setLayout(new BorderLayout());
        sideTabsPane.add("Patients for today", sideAppointment);
        
        sideAppList = new JList();
        sideAppScroll = new JScrollPane(sideAppList);
        sideAppointment.add(sideAppScroll, BorderLayout.CENTER);
        
        viewSched = new JButton("View Full Schedule");
        viewSched.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/WholeCalendar.png"));
        sideAppointment.add(viewSched, BorderLayout.SOUTH);
        
        //PAYMENTS
        sidePayment = new JPanel();
        sidePayment.setLayout(new BorderLayout());
        sideTabsPane.add("Payments Tracker", sidePayment);
        sidePayment.setBackground(WHITE);
    }
}
