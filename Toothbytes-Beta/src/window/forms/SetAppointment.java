/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.forms;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import models.Appointment;
import models.Patient;
import models.PatientX;
import utilities.DBAccess;

/**
 *
 * @author Ecchi Powa
 */
public class SetAppointment extends javax.swing.JPanel {

    /**
     * Creates new form SetAppointment
     */
    
    private Appointment appointment;
    private ArrayList<PatientX> patientList = DBAccess.getPatientXData("");
    private Calendar now = Calendar.getInstance();
    private int monthMod = now.get(Calendar.MONTH);
    private int yearMod = now.get(Calendar.YEAR);
    private int initCharNo = 254;
    private int charTyped = 0;
    
    public SetAppointment() {
        initComponents();
        setMonthValues();
        setPatientValues();
    }
    
    public SetAppointment(Appointment appointment){
        initComponents();
        this.appointment = appointment;
        String[] appDate = appointment.getAppointmentDate().split("-");
        now.set(Integer.parseInt(appDate[0]), Integer.parseInt(appDate[1]), Integer.parseInt(appDate[2]));
        
        setPatientValues();
        setAppointmentValues();
    }
    
    public SetAppointment(int patientID, String reason){
        initComponents();
        setMonthValues();

        setPatientValues();
        findPatient(patientID);
        sptReason.setText(reason);
        sptReason.setEnabled(false);
    }
    
    public void setAppointmentValues(){
        String[] startHour = appointment.getAppointmentTime().split(":");
        sptStartHour.setText(startHour[0]);
        sptStartMin.setText(startHour[1]);
        
        String[] duration = appointment.getAppointmentEndTime().split(":");
        int durHour = Integer.parseInt(duration[0]);
        int durMin = Integer.parseInt(duration[1]);
        
        if(durHour > Integer.parseInt(startHour[0])){
            durHour = durHour - Integer.parseInt(startHour[0]);
        } else {
            durHour = Integer.parseInt(startHour[0]) - durHour;
        }
        
        if(durMin > Integer.parseInt(startHour[1])){
            durMin = durMin - Integer.parseInt(startHour[1]);
        } else {
            durMin = Integer.parseInt(startHour[1]) - durMin;
        }
        
        sptDurHour.setText(String.valueOf(durHour));
        sptDurMin.setText(String.valueOf(durMin));
        
        sptReason.setText(appointment.getAppointmentRemarks());
    }
        
    public void setPatientValues(){
        for(int i = 0; i < patientList.size(); i++){
            sptPatient.addItem(patientList.get(i).getFullName());
            System.out.println(patientList.get(i).getFullName());
        }
    }
    
    public void findPatient(int patientID){
        for(int i = 0; i < patientList.size(); i ++){
            if(patientList.get(i).getId() == patientID){
                sptPatient.setSelectedItem(patientList.get(i).getFullName());
            }
        }
    }
    
    public void setMonthValues(){
        if(monthMod > 11){
           yearMod = yearMod + 1;
            now.set(Calendar.YEAR, yearMod);
            now.set(Calendar.MONTH, 0);
            monthMod = 0;
        } else if (monthMod < -1){
            yearMod = yearMod - 1;
            now.set(Calendar.YEAR, yearMod);
            now.set(Calendar.MONTH, 11);
            monthMod = 11;
        } else {
            now.set(Calendar.MONTH, monthMod);
        }
        
        String month = new SimpleDateFormat("MMMM").format(now.getTime());
        sptMonth.setText(month);
        sptYear.setText(String.valueOf(now.get(Calendar.YEAR)));
        
        genCalendar();
        
        now.set(Calendar.DAY_OF_MONTH, 1);
        int currDay = now.get(Calendar.DAY_OF_WEEK);
        int weekNo = 0;
        
        try{
            for(int x = 1; x < Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)+1; x++){
                sptMiniCalendar.getModel().setValueAt(x, weekNo, currDay-1);
                currDay++;
                if((currDay) > 7){
                    currDay = currDay - 7;
                    weekNo++;
                }
            }
        }catch(Exception e){
            System.out.println("SetAppointment - setMonthValues Error: "+e);
        }
        
    }
    
    public void genCalendar(){
        sptMiniCalendar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
            
        });
        sptMiniCalendar.setAutoscrolls(false);
        sptMiniCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sptPatient = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        sptMiniCalendar = new javax.swing.JTable();
        sptNext = new javax.swing.JButton();
        sptPrevious = new javax.swing.JButton();
        sptMonth = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sptReason = new javax.swing.JTextPane();
        sptCancel = new javax.swing.JButton();
        sptSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        sptStartHour = new javax.swing.JTextField();
        sptStartMin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        sptDurHour = new javax.swing.JTextField();
        sptDurMin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        sptCharLeft = new javax.swing.JLabel();
        sptYear = new javax.swing.JLabel();

        sptPatient.setEditable(true);
        sptPatient.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Patient" }));

        sptMiniCalendar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sptMiniCalendar.setAutoscrolls(false);
        sptMiniCalendar.setCellSelectionEnabled(true);
        sptMiniCalendar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sptMiniCalendar.setRowHeight(20);
        jScrollPane1.setViewportView(sptMiniCalendar);

        sptNext.setText("Next");
        sptNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptNextActionPerformed(evt);
            }
        });

        sptPrevious.setText("Previous");
        sptPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptPreviousActionPerformed(evt);
            }
        });

        sptMonth.setText("Month");

        sptReason.setText("Reason");
        sptReason.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sptReasonMouseClicked(evt);
            }
        });
        sptReason.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sptReasonKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(sptReason);

        sptCancel.setText("Cancel");
        sptCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptCancelActionPerformed(evt);
            }
        });

        sptSave.setText("Save");
        sptSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptSaveActionPerformed(evt);
            }
        });

        jLabel1.setText("Start Time");

        sptStartHour.setText("Hour");
        sptStartHour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sptStartHourMouseClicked(evt);
            }
        });

        sptStartMin.setText("Minute");
        sptStartMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sptStartMinMouseClicked(evt);
            }
        });

        jLabel2.setText("Duration");

        sptDurHour.setText("Hour");
        sptDurHour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sptDurHourMouseClicked(evt);
            }
        });

        sptDurMin.setText("Minute");
        sptDurMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sptDurMinMouseClicked(evt);
            }
        });

        jLabel3.setText("Characters Left: ");

        sptCharLeft.setText("254");

        sptYear.setText("Year");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sptPatient, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sptCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sptSave, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sptStartHour, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sptStartMin, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sptDurHour, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sptDurMin, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(77, 77, 77))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sptPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(sptMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sptYear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sptNext, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sptCharLeft)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sptPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sptMonth)
                    .addComponent(sptNext, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sptPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sptYear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sptStartHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sptStartMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sptDurHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sptDurMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sptCharLeft))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sptCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sptSave, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sptPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sptPreviousActionPerformed
        monthMod --;
        setMonthValues();
    }//GEN-LAST:event_sptPreviousActionPerformed

    private void sptNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sptNextActionPerformed
        monthMod ++;
        setMonthValues();
    }//GEN-LAST:event_sptNextActionPerformed

    private void sptReasonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sptReasonKeyTyped
        charTyped = sptReason.getText().length();
        if(initCharNo > charTyped){            
            sptCharLeft.setText(String.valueOf(initCharNo - charTyped));
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_sptReasonKeyTyped

    private void sptReasonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sptReasonMouseClicked
        if(sptReason.getText().equals("Reason")){
            sptReason.setText("");
        }
    }//GEN-LAST:event_sptReasonMouseClicked

    private void sptStartHourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sptStartHourMouseClicked
        if(sptStartHour.getText().equals("Hour")){
            sptStartHour.setText("");
        }
    }//GEN-LAST:event_sptStartHourMouseClicked

    private void sptStartMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sptStartMinMouseClicked
        if(sptStartMin.getText().equals("Minute")){
            sptStartMin.setText("");   
        }
    }//GEN-LAST:event_sptStartMinMouseClicked

    private void sptDurHourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sptDurHourMouseClicked
        if(sptDurHour.getText().equals("Hour")){
            sptDurHour.setText("");
        }
    }//GEN-LAST:event_sptDurHourMouseClicked

    private void sptDurMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sptDurMinMouseClicked
        if(sptDurMin.getText().equals("Minute")){
            sptDurMin.setText("");   
        }
    }//GEN-LAST:event_sptDurMinMouseClicked

    private void sptCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sptCancelActionPerformed
        System.out.close();
    }//GEN-LAST:event_sptCancelActionPerformed

    private void sptSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sptSaveActionPerformed
        //Save to database
    }//GEN-LAST:event_sptSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton sptCancel;
    private javax.swing.JLabel sptCharLeft;
    private javax.swing.JTextField sptDurHour;
    private javax.swing.JTextField sptDurMin;
    private javax.swing.JTable sptMiniCalendar;
    private javax.swing.JLabel sptMonth;
    private javax.swing.JButton sptNext;
    private javax.swing.JComboBox sptPatient;
    private javax.swing.JButton sptPrevious;
    private javax.swing.JTextPane sptReason;
    private javax.swing.JButton sptSave;
    private javax.swing.JTextField sptStartHour;
    private javax.swing.JTextField sptStartMin;
    private javax.swing.JLabel sptYear;
    // End of variables declaration//GEN-END:variables
}
