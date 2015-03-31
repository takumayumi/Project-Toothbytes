/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Jolas
 */
public class DBAccess {
    private static JDBCConnection conn = null;
    private static JDBCResultSet rs = null;
    private static JDBCStatement stmt = null;
    private static String dir = new File("src/db").getAbsolutePath();
    /**
     *This method connects to the database 
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
    
    public static int CallIdentity(){
        try{
            Class.forName("org.hsqldb.jdbcDriver");
            String dbConn = "jdbc:hsqldb:file:"+dir+";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            stmt = (JDBCStatement) conn.createStatement();
            rs = (JDBCResultSet) stmt.executeQuery("CALL IDENTITY();");
            
            int i = rs.getInt(1);
            //return sReturn;
        }catch(Exception e){
           System.out.println(e);
        }
        return 0;
    }
    
    public static boolean dbQuery(String s){
        try{
            Class.forName("org.hsqldb.jdbcDriver");
            String dbConn = "jdbc:hsqldb:file:"+dir+";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            stmt = (JDBCStatement) conn.createStatement();
            rs = (JDBCResultSet) stmt.executeQuery(s);
            //ALTER TABLE Dental_Records ADD COLUMN toothStatus VARCHAR(45) NULL;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    /**
     * This method initializes a patient list from the Database
     * @return ArrayList<Patient>
     * @throws SQLException 
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
     * This method gets the information about a patient by an ID
     * @param id
     * @return PatientX
     * @throws SQLException
     * @throws ParseException 
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