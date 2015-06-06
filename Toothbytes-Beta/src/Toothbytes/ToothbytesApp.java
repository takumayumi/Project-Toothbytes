/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package Toothbytes;

import components.CalendarWindow;
import components.PaymentWindow;
import components.RecordsWindow;
import components.ReportsGenWindow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import models.Patient;
import utilities.DBAccess;
import window.MainScreen;

/**
 * <h1>ToothbytesApp</h1>
 * The {@code ToothbytesApp} program is the main class that executes the whole 
 * system of Toothbytes.
 */
public class ToothbytesApp {

    public static ArrayList<Patient> patientList;
    
    /**
     * It connects to database, initializes the list of patients, instantiates 
     * the User Interfaces of the program Toothbytes and initializes them.
     */
    public static void main(String[] args) {

        try {
            DBAccess.connectDB(); //connect to database
            patientList = DBAccess.initPatientList(); //initialize list of patients
            RecordsWindow rWin = new RecordsWindow(patientList);
            CalendarWindow aWin = new CalendarWindow();
            PaymentWindow pWin = new PaymentWindow(patientList);
            ReportsGenWindow rgWin = new ReportsGenWindow();

            MainScreen ui = new MainScreen(rWin, aWin, pWin, rgWin); //instantiate UI
            rWin.setOwner(ui);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(ui);
            ui.init(); //initialize UI
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException |
                SQLException ex) {
            Logger.getLogger(ToothbytesApp.class.getName()).log(Level.SEVERE, null, ex);
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }
}
