package toothbytes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import toothbytes.database.DBAccess;
import toothbytes.model.Patient;
import toothbytes.ui.LoginWindow;
import toothbytes.ui.MainScreen;
import toothbytes.ui.RecordsWindow;
import toothbytes.ui.components.AppointmentsWindow;
import toothbytes.ui.components.PaymentsWindow;

public class ToothbytesApp {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<Patient> patientList;

    public static void main(String[] args) {

        try {
//            LoginWindow lw = new LoginWindow();
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            SwingUtilities.updateComponentTreeUI(lw);
//            lw.init();
            DBAccess.connectDB(); //connect to database
            patientList = DBAccess.initPatientList(); //initialize list of patients
            RecordsWindow rWin = new RecordsWindow(patientList);
            AppointmentsWindow aWin = new AppointmentsWindow();
            PaymentsWindow pWin = new PaymentsWindow();

            MainScreen ui = new MainScreen(rWin, aWin, pWin); //instantiate UI
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
