package toothbytes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import toothbytes.database.DBAccess;
import toothbytes.model.Patient;
import toothbytes.ui.MainScreen;
import toothbytes.ui.components.RecordsWindow;
import toothbytes.ui.components.AppointmentsWindow;
import toothbytes.ui.components.DentalChart;
import toothbytes.ui.components.PaymentsWindow;

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

            DBAccess.connectDB();
            patientList = DBAccess.initPatientList();

//            LoginWindow lw = new LoginWindow();
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            SwingUtilities.updateComponentTreeUI(lw);
//            lw.init();
//            DentalChart dc = new DentalChart();
//            dc.init();
            
            DBAccess.connectDB(); //connect to database
            patientList = DBAccess.initPatientList(); //initialize list of patients

            RecordsWindow rWin = new RecordsWindow(patientList);
            AppointmentsWindow aWin = new AppointmentsWindow();
            PaymentsWindow pWin = new PaymentsWindow();

            MainScreen ui = new MainScreen(rWin, aWin, pWin);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(ui);
            ui.init();
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
