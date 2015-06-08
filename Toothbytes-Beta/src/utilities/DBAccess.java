/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
 */
package utilities;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Accounts;
import models.Patient;
import models.PatientX;
import models.PaymentX;
import models.RecordsX;
import models.Services;
import org.hsqldb.jdbc.JDBCConnection;
import org.hsqldb.jdbc.JDBCDriver;
import org.hsqldb.jdbc.JDBCResultSet;
import org.hsqldb.jdbc.JDBCStatement;
import models.Appointment;
import models.Treatment;

/**
 * <h1>DBAccess</h1>
 * The {@code DBAccess} class connects to the database.
 */
public class DBAccess {

    private static JDBCConnection conn = null;
    private static JDBCResultSet rs = null;
    private static JDBCStatement stmt = null;
    private static String dir = "data/db";

    /**
     * This method connects to the database.
     *
     * @throws ClassNotFoundException
     * @see ClassNotFoundException
     * @throws SQLException
     * @see SQLException
     */
    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbcDriver");
        String dbConn = "jdbc:hsqldb:file:" + dir + ";user=root";
        conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
        stmt = (JDBCStatement) conn.createStatement();
        rs = (JDBCResultSet) stmt.executeQuery("SELECT * FROM PATIENT;");

        while (rs.next()) {
            System.out.println(String.format("%d, %s", rs.getInt(1), rs.getString(2)));
        }
    }

    /**
     * This method returns the last primary key value.
     */
    public static int CallIdentity() {

        try {
            Class.forName("org.hsqldb.jdbcDriver");

            String dbConn = "jdbc:hsqldb:file:" + dir + ";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            stmt = (JDBCStatement) conn.createStatement();
            rs = (JDBCResultSet) stmt.executeQuery("CALL IDENTITY();");
            rs.next();
            int i = rs.getInt(1);
            //return sReturn;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * This method checks for database query success. It will return a boolean
     * true if it is a success. Otherwise, false if failed.
     *
     * @param s Used for the execution of the SQL statement in a
     * PreparedStatement object, which may be any kind of SQL statement.
     */
    public static boolean dbQuery(String s) {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String dbConn = "jdbc:hsqldb:file:" + dir + ";user=root";
            conn = (JDBCConnection) JDBCDriver.getConnection(dbConn, null);
            stmt = (JDBCStatement) conn.createStatement();
            rs = (JDBCResultSet) stmt.executeQuery(s);
            rs.next();
            //ALTER TABLE Dental_Records ADD COLUMN toothStatus VARCHAR(45) NULL;
        } catch (Exception e) {
            System.out.println("DBAccess - dbQuery Error: "+e);
            return false;
        }
        return true;
    }

    /**
     * This method initializes a patient list from the database.
     *
     * @return ArrayList of Patient
     * @throws SQLException
     * @see SQLException
     */
    public static ArrayList<Patient> initPatientList() throws SQLException {
        rs = (JDBCResultSet) stmt.executeQuery("SELECT PATIENTID, PATIENT_LASTNAME, PATIENT_FIRSTNAME, PATIENT_MIDDLEINITIAL FROM PATIENT");
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        while (rs.next()) {
            Patient p = new Patient(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4));
            patientList.add(p);
        }

        for (int i = 0; i < patientList.size(); i++) {
            System.out.println("loading database: " + (i / patientList.size() * 100) + "%");
        }
        Collections.sort(patientList);
        return patientList;
    }

    /**
     * This method gets the information about a patient by an ID.
     *
     * @param id The number of the pstientID of a particular patient in the
     * database.
     * @return PatientX
     */
    public static PatientX getData(int id) {
        try {
            rs = (JDBCResultSet) stmt.executeQuery("SELECT * FROM PATIENT WHERE patientID =" + id);

            rs.next();

            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            cal.setTime(sdf.parse(rs.getString(6)));

            PatientX p = new PatientX(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), cal,
                    rs.getString(7), rs.getString(8), rs.getString(9).charAt(0), rs.getString(10), rs.getString(11),
                    rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16));
            return p;
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ArrayList<PaymentX> getPaymentData(int id) {
        try {
            ArrayList<PaymentX> paymentX = new ArrayList<>();
            String getPayment = "SELECT * FROM PAYMENTS WHERE DENTALRECORDID = " + id;
            rs = (JDBCResultSet) stmt.executeQuery(getPayment);

            while (rs.next()) {
                PaymentX px = new PaymentX(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4));
                paymentX.add(px);
            }

            return paymentX;

        } catch (Exception e) {
            System.out.println("getPaymentData Error: " + e);
            return null;
        }
    }

    public static ArrayList<RecordsX> getRecordsData(int id) {
        try {
            ArrayList<RecordsX> recordsX = new ArrayList<>();
            String getRecords = "SELECT * FROM DENTAL_RECORDS WHERE PATIENTID = " + id;

            rs = (JDBCResultSet) stmt.executeQuery(getRecords);

            while (rs.next()) {
                RecordsX rx = new RecordsX(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8));
                recordsX.add(rx);
            }
            return recordsX;
        } catch (Exception e) {
            System.out.println("getRecordsData Error: " + e);
            return null;
        }
    }

    public static ArrayList<Accounts> getAccountsData() {
        try {
            ArrayList<Accounts> accountsX = new ArrayList<>();
            String getAccounts = "SELECT * FROM ACCOUNTS";

            rs = (JDBCResultSet) stmt.executeQuery(getAccounts);

            while (rs.next()) {
                Accounts account = new Accounts(rs.getInt(1), rs.getString(2), rs.getString(3));
                accountsX.add(account);
            }

            return accountsX;
        } catch (Exception e) {
            System.out.println("getAccountsData Error: " + e);
            return null;
        }
    }

    public static ArrayList<Services> getServicesData() {
        try {
            ArrayList<Services> servicesX = new ArrayList<>();
            String getServices = "SELECT * FROM SERVICES";

            rs = (JDBCResultSet) stmt.executeQuery(getServices);

            while (rs.next()) {
                Services service = new Services(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getBoolean(6));
                servicesX.add(service);
            }

            return servicesX;
        } catch (Exception e) {
            System.out.println("getServicesData Error: " + e);
            return null;
        }
    }

    public static void updateAccountsData(Accounts account) {
        String updateUsername = "UPDATE ACCOUNTS SET USERNAME = '" + account.getUsername() + "' WHERE accountsID = " + account.getAccountID() + ";";
        String updatePassword = "UPDATE ACCOUNTS SET PASSWORD = '" + account.getPassword() + "' WHERE accountsID = " + account.getAccountID() + ";";

        try {
            rs = (JDBCResultSet) stmt.executeQuery(updateUsername);
            rs.next();
        } catch (Exception e) {
            System.out.println("updateAccountsData Username Error: " + e);
        }

        try {
            rs = (JDBCResultSet) stmt.executeQuery(updatePassword);
            rs.next();
        } catch (Exception e) {
            System.out.println("updateAccountsData Password Error: " + e);
        }
    }

    public static void updateServicesData(Services service) {
        String updateServiceType = "UPDATE SERVICES SET SERVICETYPE = '" + service.getServiceType() + "' WHERE SERVICEID = " + service.getServiceID() + ";";
        String updateServiceFee = "UPDATE SERVICES SET SERVICEFEE = " + service.getServiceFee() + " WHERE SERVICEID = " + service.getServiceID() + ";";
        String updateServiceAvailable = "UPDATE SERVICES SET SERVICEAVAILABLE = " + service.getServiceAvailable() + " WHERE SERVICEID = " + service.getServiceID() + ";";

        try {
            rs = (JDBCResultSet) stmt.executeQuery(updateServiceType);
            rs.next();
        } catch (Exception e) {
            System.out.println("updateServicesData ServiceType error: " + e);
        }

        try {
            rs = (JDBCResultSet) stmt.executeQuery(updateServiceFee);
            rs.next();
        } catch (Exception e) {
            System.out.println("updateServicesData ServiceFee error: " + e);
        }

        try {
            rs = (JDBCResultSet) stmt.executeQuery(updateServiceAvailable);
            rs.next();
        } catch (Exception e) {
            System.out.println("updateServicesData ServiceAvailable error: " + e);
        }
    }

    public static void addServicesData(Services service) {
        String addServiceData = "INSERT INTO SERVICES VALUES(DEFAULT, '" + service.getServiceType() + "', 'DEFAULT', 'DEFAULT', " + service.getServiceFee() + ", " + service.getServiceAvailable() + ")";
        try {
            rs = (JDBCResultSet) stmt.executeQuery(addServiceData);
            rs.next();
            System.out.println(addServiceData);
        } catch (Exception e) {
            System.out.println("addServicesData Error: " + e);
        }
    }

    public static ArrayList<Appointment> getAppointmentData(String condition) {
        String getAppointmentData = "SELECT * FROM APPOINTMENT" + condition;
        ArrayList<Appointment> appointmentX = new ArrayList<>();

        try {
            rs = (JDBCResultSet) stmt.executeQuery(getAppointmentData);

            while (rs.next()) {
                Appointment appointment = new Appointment(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                appointmentX.add(appointment);
            }
            return appointmentX;
        } catch (Exception e) {
            System.out.println("getAppointmentData Error: " + e);
            return null;
        }
    }

    public static String getPatientName(int patientID) {
        String getPatientName = "SELECT * FROM PATIENT WHERE PATIENTID = " + patientID + ";";
        String patientName = "";

        try {
            rs = (JDBCResultSet) stmt.executeQuery(getPatientName);
            rs.next();
            patientName = rs.getString(2) + ", " + rs.getString(3);
            return patientName;
        } catch (Exception e) {
            System.out.println("getPatientName Error: " + e);
            return "";
        }
    }

    public static int getLastNo(String table) {
        String getLastNo = "SELECT 1 FROM " + table + ";";
        int no = 0;

        try {
            rs = (JDBCResultSet) stmt.executeQuery(getLastNo);
            while (rs.next()) {
                no++;
            }

            return no;
        } catch (Exception e) {
            System.out.println("getLastNo Error: " + e);
            return 0;
        }
    }

    public static void billingUpdate(PaymentX paymentX, double balance) {
        String updatePayment = "INSERT INTO PAYMENTS VALUES(DEFAULT, '" + paymentX.getDentalRecordID() + "', CURRENT_DATE, " + paymentX.getAmountPaid() + ");";
        String updateRecords = "UPDATE DENTAL_RECORDS SET BALANCE = " + balance + " WHERE DENTALRECORDID = " + paymentX.getDentalRecordID() + ";";

        try {
            rs = (JDBCResultSet) stmt.executeQuery(updatePayment);
            rs.next();

            rs = (JDBCResultSet) stmt.executeQuery(updateRecords);
            rs.next();
        } catch (Exception e) {
            System.out.println("billing Update Error: " + e);
        }
    }

    public static ArrayList<PatientX> getPatientXData(String addtl) {
        ArrayList<PatientX> patientX = new ArrayList<>();
        String query = "SELECT * FROM PATIENT" + addtl;
        try {
            rs = (JDBCResultSet) stmt.executeQuery(query);
            while (rs.next()) {
                Calendar cal;
                try {
                    cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5)));
                } catch (Exception e) {
                    cal = null;
                }
                PatientX px = new PatientX(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), cal,
                        rs.getString(6), rs.getString(7), rs.getString(8).charAt(0), rs.getString(9), rs.getString(10), rs.getString(11),
                        rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15));
                patientX.add(px);
            }
        } catch (Exception e) {
            System.out.println("DBAccess - getPatientXData Error: " + e);
        }
        return patientX;
    }

    public static void addAppointmentData(Appointment appointment) throws Exception {
        String query = "INSERT INTO APPOINTMENT VALUES(DEFAULT, " + appointment.getPatientID() + ", '" + appointment.getAppointmentDate() + "', '" + appointment.getAppointmentTime() + ":00', '" + appointment.getAppointmentEndTime() + ":00', '" + appointment.getAppointmentRemarks() + "');";
        System.out.println(query);
        try {
            rs = (JDBCResultSet) stmt.executeQuery(query);
            rs.next();
        } catch (Exception e) {
            System.out.println("DBAccess - addAppointmentData Error: " + e);
        }
    }
    
    public static ArrayList<AdditionalInfo> getAdditionalInfoData(int patientID){
        ArrayList<AdditionalInfo> additionalInfoList = new ArrayList<>();
        String query = "SELECT * FROM ADDITIONAL_INFO WHERE PATIENTID = " + patientID + ";";
        
        try{
            rs = (JDBCResultSet) stmt.executeQuery(query);
                       
            while(rs.next()){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                Calendar effectiveDate = null;
                Calendar lastDentalVisit = null;
                                
                try{
                    effectiveDate = Calendar.getInstance();
                    effectiveDate.setTime(sdf.parse(rs.getString(4)));
                }catch(Exception e){
                    effectiveDate = null;
                }
                
                try{
                    lastDentalVisit = Calendar.getInstance();
                    lastDentalVisit.setTime(sdf.parse(rs.getString(10)));
                }catch(Exception e){
                    lastDentalVisit = null;
                }
                
                AdditionalInfo ai = new AdditionalInfo(rs.getInt(1), rs.getInt(2), rs.getString(3), effectiveDate,rs.getString(5), rs.getString(6),
                rs.getString(7), rs.getString(8), rs.getString(9), lastDentalVisit, rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
                
                additionalInfoList.add(ai);
            }
            
        }catch(Exception e){
            System.out.println("DBAccess - getAdditionalInfoData Error: "+e);
        }
        
        return additionalInfoList;
    }
    
    public static ArrayList<MedicalCond> getMedicalConditionData(int patientID){
        String query = "SELECT * FROM MEDICAL_HISTORY WHERE patientID = "+patientID;
        
        ArrayList<MedicalCond> medicalConditionList = new ArrayList<>();
        
        try{
            rs = (JDBCResultSet) stmt.executeQuery(query);
            while(rs.next()){
                Calendar cal;
                try{
                    cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(19)));
                }catch(Exception e){
                    cal = null;
                }
                
                MedicalCond mc = new MedicalCond(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
                rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), cal);
                
                medicalConditionList.add(mc);
            }
        }catch(Exception e){
            System.out.println("DBAccess - getMedicalConditionData Error: "+e);
        }
        
        return medicalConditionList;
    }

    public static boolean validate(String usr, char[] pwd) {
        boolean isValid = false;
        PreparedStatement findCredential = null;
        
        String pass = "";
        for (int i = 0; i < pwd.length; i++) {
            pass = pass + pwd[i];
        }
        
        String query = "SELECT * FROM ACCOUNTS "
                + "where USERNAME = ? AND PASSWORD = ?";
        try {
            findCredential = conn.prepareStatement(query);
            findCredential.setString(1, usr);
            findCredential.setString(2, pass);
//            isValid = findCredential.execute();
            rs = (JDBCResultSet)findCredential.executeQuery();
//            while (rs.next()) {
            isValid = rs.next();
//            }
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isValid;
    }
    
    public static ArrayList<Treatment> getTreatmentList(int id) {
        ArrayList<Treatment> tList = new ArrayList<Treatment>();
        PreparedStatement queryTreatment = null;
        Calendar cal = Calendar.getInstance();
        
        String query = "select TREATMENTDATE, TOOTHNO, PROCEDURE from DENTAL_RECORDS "+
                "where PATIENTID = ?";
        try {
            queryTreatment = conn.prepareStatement(query);
            queryTreatment.setInt(1, id);
//            isValid = findCredential.execute();
            rs = (JDBCResultSet)queryTreatment.executeQuery();
            
            while (rs.next()) {
                cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(1)));
                Treatment temp = new Treatment(cal, rs.getString(2), rs.getString(3));
                tList.add(temp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tList;
    }
    
    public static void closeDB() {
        try {
            stmt.close();
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
