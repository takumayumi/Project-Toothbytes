/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;

import java.awt.BorderLayout;
import static java.awt.Color.WHITE;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import models.Appointment;
import utilities.DBAccess;

/**
 * <h1>SidePanel</h1>
 * The {@code Sidepanel} class constructs a side panel within the main window. 
 * The side panel includes an appointments feature ("Patients for today") and 
 * payments feature ("Payments Tracker").
 */
public class SidePanel extends JPanel{
    
    private JTabbedPane sideTabsPane;
    private JPanel sideAppointment, sidePayment;
    private JList sideAppList, sidePayList;
    private JScrollPane sideAppScroll, sidePayScroll;
    private JButton viewSched;
    
    /**
     * This is the default constructor of the SidePanel class.
     */
    public SidePanel() {
        this.setLayout(new BorderLayout());
        
        sideTabsPane = new JTabbedPane();
        this.add(sideTabsPane);
        
        // APPOINTMENTS
        sideAppointment = new JPanel();
        sideAppointment.setLayout(new BorderLayout());
        sideTabsPane.add("Patients for today", sideAppointment);
        
        sideAppList = new JList();
        sideAppScroll = new JScrollPane(sideAppList);
        sideAppointment.add(sideAppScroll, BorderLayout.CENTER);
        
        viewSched = new JButton("View Full Schedule");
        viewSched.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/WholeCalendar.png"));
        sideAppointment.add(viewSched, BorderLayout.SOUTH);
        
        // PAYMENTS
        sidePayment = new JPanel();
        sideTabsPane.add("Payments Tracker", sidePayment);
        sidePayment.setLayout(new BoxLayout(sidePayment,BoxLayout.Y_AXIS));
        sidePayment.setBackground(WHITE);
        
        ArrayList<Appointment> appointmentX = new ArrayList<>();
        appointmentX = DBAccess.getAppointmentData("");
        
        int x = 0;
        
        for(int i = 0; i < appointmentX.size(); i++){
            try{
                if(checker(appointmentX.get(i))){
                    sidePayment.add(new PaymentSchedule(appointmentX.get(i)));
                    if(x < 10){
                        x++;
                    } else {
                        break;
                    }
                }
            }catch(Exception e){
                
            }
        }
    }
    
    private boolean checker(Appointment appointment){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Calendar now = Calendar.getInstance();
            Calendar then = Calendar.getInstance();
            
            then.setTime(sdf.parse(appointment.getAppointmentDate()));
            int time = then.compareTo(now);
            
            if(appointment.getAppointmentRemarks().equals("Payment") && time == 1){
                return true;
            } else {
                return false;
            }
            
        }catch(Exception e){
            return false;
        }
    }
}
