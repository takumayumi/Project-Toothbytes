package toothbytes.database;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hsqldb.jdbc.JDBCConnection;
import org.hsqldb.jdbc.JDBCDriver;
import org.hsqldb.jdbc.JDBCResultSet;
import org.hsqldb.jdbc.JDBCStatement;
import toothbytes.model.Patient;
import toothbytes.model.PatientX;

/**
 * <h1>DBAccess</h1>
 * The {@code DBAccess} class connects to the database.
 */
public class DBAccess {
    private static JDBCConnection conn = null;
    private static JDBCResultSet rs = null;
    private static JDBCStatement stmt = null;
    private static String dir = new File("src/db/").getAbsolutePath();
    
    /**
     * This method connects to the database.
     * @throws  ClassNotFoundException
     * @see     ClassNotFoundException
     * @throws  SQLException
     * @see     SQLException
     */
    public static void connectDB() throws ClassNotFoundException, SQLException {
            Class.forName("org.hsqldb.jdbcDriver");
            String dbConn = "jdbc:hsqldb:file:"+dir+";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            stmt = (JDBCStatement) conn.createStatement();
            rs = (JDBCResultSet) stmt.executeQuery("SELECT * FROM PATIENT;");
            
            while (rs.next()){
                System.out.println(String.format("%d, %s", rs.getInt(1), rs.getString(2)));
            }
    }
    
    /**
     * This method returns the last primary key value.
     */
    public static int CallIdentity(){
        try{
            Class.forName("org.hsqldb.jdbcDriver");
            String dbConn = "jdbc:hsqldb:file:"+dir+";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            stmt = (JDBCStatement) conn.createStatement();
            rs = (JDBCResultSet) stmt.executeQuery("CALL IDENTITY();");
            rs.next();
            int i = rs.getInt(1);
            //return sReturn;
        }catch(Exception e){
           System.out.println(e);
        }
        return 0;
    }
    
    /**
     * This method checks for database query success. It will return a boolean 
     * true if it is a success. Otherwise, false if failed.
     * @param   s
     *          Used for the execution of the SQL statement in a 
     *          PreparedStatement object, which may be any kind of SQL 
     *          statement.
     */
    public static boolean dbQuery(String s){
        try{
            Class.forName("org.hsqldb.jdbcDriver");
            String dbConn = "jdbc:hsqldb:file:"+dir+";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            stmt = (JDBCStatement) conn.createStatement();
            rs = (JDBCResultSet) stmt.executeQuery(s);
            rs.next();
            //ALTER TABLE Dental_Records ADD COLUMN toothStatus VARCHAR(45) NULL;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    /**
     * This method initializes a patient list from the database.
     * @return  ArrayList of Patient
     * @throws  SQLException
     * @see     SQLException
     */
    public static ArrayList<Patient> initPatientList() throws SQLException{
        rs = (JDBCResultSet) stmt.executeQuery("SELECT patientID, patient_FirstName, patient_LastName, patient_MiddleInitial FROM PATIENT");
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        while(rs.next()){
            Patient p = new Patient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            patientList.add(p);
        }
        
        for(int i = 0; i < patientList.size(); i++){
            System.out.println("Patient: " + patientList.get(i).getFirstName() + patientList.get(i).getLastName());
        }
        Collections.sort(patientList);
        return patientList;
    }
    
    /**
     * This method gets the information about a patient by an ID.
     * @param   id
     *          The number of the pstientID of a particular patient in the 
     *          database.
     * @return  PatientX
     */
    public static PatientX getData(int id) {
        try {
            rs = (JDBCResultSet) stmt.executeQuery("SELECT * FROM PATIENT WHERE patientID ="+id);
        
        rs.next();
        
        Calendar cal = Calendar.getInstance();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        cal.setTime(sdf.parse(rs.getString(6)));
        
        PatientX p = new PatientX(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), cal,
                    rs.getString(7), rs.getString(8), rs.getString(9).charAt(0), rs.getString(10), rs.getString(11),
                    rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16));
        return p;
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}