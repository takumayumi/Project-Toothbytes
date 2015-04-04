package toothbytes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import toothbytes.database.DBAccess;
import toothbytes.model.Patient;
import toothbytes.ui.RecordsWindow;
import toothbytes.ui.AppointmentsWindow;
import toothbytes.ui.PaymentsWindow;
import toothbytes.ui.UIScaffold;

public class ToothbytesApp {
    /**
     * @param args the command line arguments
     */
    public static ArrayList <Patient> patientList;
    public static void main(String[] args) {
        try {
            DBAccess.connectDB(); //connect to database
            patientList = DBAccess.initPatientList(); //initialize list of patients
            //AppointmentWindow aw = new RecordsWindow(patientList);
            //ui.addToModulePanel(aw);
            RecordsWindow rWin = new RecordsWindow(patientList);
            AppointmentsWindow aWin = new AppointmentsWindow();
            PaymentsWindow pWin = new PaymentsWindow();
            
            UIScaffold ui = new UIScaffold(rWin, aWin, pWin); //instantiate UI
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(ui);
            ui.init(); //initialize UI
        } catch (
                ClassNotFoundException | 
                InstantiationException | 
                IllegalAccessException | 
                javax.swing.UnsupportedLookAndFeelException | 
                SQLException ex
                ) {
            Logger.getLogger(ToothbytesApp.class.getName()).log(Level.SEVERE, null, ex);
            java.util.logging.Logger.getLogger(UIScaffold.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
//        try {
//            System.out.println(DBAccess.getData(3).toString());
//        } catch (SQLException ex) {
//            Logger.getLogger(ToothbytesApp.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParseException ex) {
//            Logger.getLogger(ToothbytesApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
