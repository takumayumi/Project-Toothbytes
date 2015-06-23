/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Appointment;
import models.PatientX;
import utilities.DBAccess;
import window.forms.UpdateAppointment;

/**
 *
 * @author Ecchi Powa
 */
public class CalendarDay extends javax.swing.JPanel {

    /**
     * Creates new form CalendarDay
     */
    
    private Calendar now;
    private ArrayList<PatientX> patientList;
    
    public CalendarDay(Calendar now) {
        initComponents();
        this.now = now;
        patientList = DBAccess.getPatientXData("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        now.set(Calendar.MONTH, now.get(Calendar.MONTH)+1);
        String date = sdf.format(now.getTime());
        System.out.println(date);
        ArrayList<Appointment> appointmentList = DBAccess.getAppointmentData( " WHERE APPOINTMENTDATE LIKE '"+date+"';");
        
        setValues();
        setTable(appointmentList);
        addListeners();
    }
    
    private void setValues(){
        //Set Patient Values
        for(int i = 0; i < patientList.size(); i++){
            patientBox.addItem(patientList.get(i).getFullName());
        }
        
        //Set Year
        yearField.setText(String.valueOf(now.get(Calendar.YEAR)));
        
        //Set Month Values
        SimpleDateFormat month = new SimpleDateFormat("MMMM");
        Calendar setMonth = Calendar.getInstance();
        for(int i = 0; i < now.getMaximum(Calendar.MONTH); i++){
            setMonth.set(Calendar.MONTH, i);
            monthBox.addItem(month.format(setMonth.getTime()));
        }
        
        monthBox.setSelectedIndex(now.get(Calendar.MONTH)+1);
        
        //Set Day Values
        for(int i = 1; i < 31; i++){
            dayBox.addItem(i);
        }
        dayBox.setSelectedItem(now.get(Calendar.DAY_OF_MONTH));
    }
    
    private void setTable(ArrayList<Appointment> appointmentList){
        resetTable();
        
        DefaultTableModel editModel = new DefaultTableModel();
        editModel = (DefaultTableModel) dayTable.getModel();
        
        editModel.addColumn("AppointmentID");
        editModel.addColumn("Date");
        editModel.addColumn("Start Time");
        editModel.addColumn("End Time");
        editModel.addColumn("Reason");
        editModel.addColumn("Patient");
        
        dayTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        for(int i = 0; i < appointmentList.size(); i++){
            editModel.addRow(new Object[]{appointmentList.get(i).getAppointmentID(), appointmentList.get(i).getAppointmentDate(), appointmentList.get(i).getAppointmentTime(), appointmentList.get(i).getAppointmentEndTime(), appointmentList.get(i).getAppointmentRemarks(), patientList.get(appointmentList.get(i).getPatientID()).getFullName()});
        }
        
        dayTable.removeColumn(dayTable.getColumnModel().getColumn(0));
    }
    
    private void resetTable(){
        dayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        dayTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        dayTable.setCellSelectionEnabled(true);
        dayTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        dayTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(dayTable);
        dayTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void addListeners(){
        //PatientListener
        patientBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                try{
                    int patientID = patientBox.getSelectedIndex()-1;
                    ArrayList<Appointment> appointmentList = DBAccess.getAppointmentData(" WHERE PATIENTID = "+patientID+";");
                    setTable(appointmentList);
                }catch(Exception e){}
            }
        });
        
        //Day Listener
        dayBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                try{
                    String date;
                    int year = Integer.parseInt(yearField.getText());
                    int month = monthBox.getSelectedIndex();
                    int day = dayBox.getSelectedIndex();
                    
                    if(month > 10){
                        if(day > 10){
                            date = year + "-" + month + "-" + day;
                        } else {
                            date = year + "-" + month + "-0" + day;
                        }
                    } else {
                        if(day > 10){
                            date = year + "-0" + month + "-" + day;
                        } else {
                            date = year + "-0" + month + "-0" + day;
                        }
                    }
                    
                    ArrayList<Appointment> appointmentList = DBAccess.getAppointmentData(" WHERE APPOINTMENTDATE LIKE '"+date+"';");
                    setTable(appointmentList);
                }catch(Exception e){}
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        patientBox = new javax.swing.JComboBox();
        monthBox = new javax.swing.JComboBox();
        dayBox = new javax.swing.JComboBox();
        yearField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dayTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        patientBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Patient" }));

        monthBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Month" }));

        dayBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Day" }));

        yearField.setText("Year");

        dayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AppointmentID", "Date", "Start Time", "End Time", "Reason", "Patient Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(dayTable);

        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(monthBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dayBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(patientBox, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(182, 182, 182))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(patientBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(monthBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dayBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            java.awt.EventQueue.invokeLater(new Runnable(){
                public void run(){
                    int id = dayTable.getSelectedRow();
                    id = Integer.parseInt(String.valueOf(dayTable.getModel().getValueAt(id, 0)));
                    
                    ArrayList<Appointment> appointmentList = DBAccess.getAppointmentData(" WHERE APPOINTMENTID = "+id+";");
                    
                    JDialog ua = new JDialog();
                    ua.add(new UpdateAppointment(appointmentList.get(0)));
                    ua.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    ua.pack();
                    ua.setVisible(true);
                    ua.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                }
            });
        }catch(Exception E){}
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox dayBox;
    private javax.swing.JTable dayTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox monthBox;
    private javax.swing.JComboBox patientBox;
    private javax.swing.JTextField yearField;
    // End of variables declaration//GEN-END:variables
}
