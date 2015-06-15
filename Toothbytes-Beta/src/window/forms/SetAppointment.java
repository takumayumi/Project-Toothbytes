/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.forms;

import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import models.Appointment;
import models.PatientX;
import utilities.DBAccess;

/**
 *
 * @author Ecchi Powa
 */
public class SetAppointment extends javax.swing.JPanel {
    
    private final String BUTTON_DIR = "res/buttons/";

    /**
     * Creates new form SetAppointment
     */
    
    private int appointmentID;
    private Appointment appointment;
    private ArrayList<PatientX> patientList = DBAccess.getPatientXData("");
    private Calendar now = Calendar.getInstance();
    private int monthMod = now.get(Calendar.MONTH);
    private int yearMod = now.get(Calendar.YEAR);
    private int initCharNo = 254;
    private int charTyped = 0;
    private boolean toggle = true;
    
    public SetAppointment() {
        initComponents();
        setMonthValues();
        setPatientValues();
    }
    
    public SetAppointment(Appointment appointment){
        initComponents();
        this.appointment = appointment;
        appointmentID = appointment.getAppointmentID();
        String[] appDate = appointment.getAppointmentDate().split("-");
        monthMod = Integer.parseInt(appDate[1])-1;
        yearMod = Integer.parseInt(appDate[0]);
        toggle = false;
        
        setMonthValues();
        setPatientValues();
        findPatient(appointment.getPatientID());
        setAppointmentValues();
        now.set(Integer.parseInt(appDate[0]), Integer.parseInt(appDate[1])-1, Integer.parseInt(appDate[2]));
        sptMiniCalendar.changeSelection(now.get(Calendar.DAY_OF_WEEK_IN_MONTH)-1, now.get(Calendar.DAY_OF_WEEK)-1, true, false);
    }
    
    public SetAppointment(String date){
        initComponents();
        String[] dateArr = date.split("-");
        monthMod = Integer.parseInt(dateArr[1]);
        yearMod = Integer.parseInt(dateArr[0]);
        setMonthValues();
        setPatientValues();
        now.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
        sptMiniCalendar.changeSelection(now.get(Calendar.DAY_OF_WEEK_IN_MONTH)-1, now.get(Calendar.DAY_OF_WEEK)-1, true, false);
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
        sptStartHour.setSelectedItem(startHour[0]);
        sptStartMin.setSelectedItem(startHour[1]);
        
        String[] duration = appointment.getAppointmentEndTime().split(":");
        int durHour = Integer.parseInt(duration[0]);
        int durMin = Integer.parseInt(duration[1]);
        
        if(durHour >= Integer.parseInt(startHour[0])){
            durHour = durHour - Integer.parseInt(startHour[0]);
        } else {
            durHour = durHour + 12;
            durHour = durHour - Integer.parseInt(startHour[0]);
        }
        
        if(durMin >= Integer.parseInt(startHour[1])){
            durMin = durMin - Integer.parseInt(startHour[1]);
        } else {
            durMin = durMin + 60;
            durMin = durMin - Integer.parseInt(startHour[1]);
        }
        
        sptDurHour.setSelectedItem(String.valueOf(durHour));
        sptDurMin.setSelectedItem(String.valueOf(durMin));
        
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
    
    public Appointment getAppointmentValues(){
        Appointment appointment = new Appointment();
        
        int patientIDx = 0;
        String appointmentDate = "";
        String appointmentTime = "";
        String appointmentEndTime = "";
        String appointmentRemarks = "";
        
        for(int i = 0; i < patientList.size(); i++){
            if(patientList.get(i).getFullName().equals(sptPatient.getSelectedItem())){
                patientIDx = patientList.get(i).getId();
            }
        }
        
        int calendarX = sptMiniCalendar.getSelectedRow();
        int calendarY = sptMiniCalendar.getSelectedColumn();
        
        String appointmentDay = "";
        
        try{
            appointmentDay = sptMiniCalendar.getModel().getValueAt(calendarX, calendarY).toString();
        }catch(Exception e){
            System.out.println("SetAppointment - getAppointmentValues Error: "+ e);
        }
        
        if(Integer.parseInt(appointmentDay) < 10){
            if(now.get(Calendar.MONTH) < 10){
                appointmentDate = now.get(Calendar.YEAR) + "-0" + (now.get(Calendar.MONTH)+1) + "-0" + appointmentDay;
            } else {
                appointmentDate = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-0" + appointmentDay;
            }
        } else {
            if(now.get(Calendar.MONTH) < 10){
                appointmentDate = now.get(Calendar.YEAR) + "-0" + (now.get(Calendar.MONTH)+1) + "-" + appointmentDay;
            } else {
                appointmentDate = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + appointmentDay;
            }
        }
        
        appointmentTime = sptStartHour.getSelectedItem() + ":" + sptStartMin.getSelectedItem();
        appointmentEndTime = sptDurHour.getSelectedItem() + ":" +sptDurHour.getSelectedItem();
        /*
        int appointmentEndMinute = Integer.parseInt(sptStartMin.getSelectedItem()) + Integer.parseInt(sptDurMin.getSelectedItem());
        int appointmentEndHour = Integer.parseInt(sptStartHour.getSelectedItem()) + Integer.parseInt(sptDurHour.getText());
        
        if(appointmentEndMinute > 60){
            appointmentEndHour++;
            appointmentEndMinute = appointmentEndMinute - 60;
        }
        
        appointmentEndTime = appointmentEndHour + ":" + appointmentEndMinute;
        */
        appointmentRemarks = sptReason.getText();
        
        appointment.setPatientID(patientIDx);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setAppointmentEndTime(appointmentEndTime);
        appointment.setAppointmentRemarks(appointmentRemarks);
        
        return appointment;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sptCharLeft = new javax.swing.JLabel();
        sptYear = new javax.swing.JLabel();
        sptPatient = new javax.swing.JComboBox();
        sptStartHour = new javax.swing.JComboBox();
        sptStartMin = new javax.swing.JComboBox();
        sptDurHour = new javax.swing.JComboBox();
        sptDurMin = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(250, 255, 250));
        setMaximumSize(new java.awt.Dimension(508, 462));
        setMinimumSize(new java.awt.Dimension(508, 462));
        setPreferredSize(new java.awt.Dimension(508, 462));

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

        sptNext.setIcon(new javax.swing.ImageIcon(BUTTON_DIR+"Next.png"));
        sptNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptNextActionPerformed(evt);
            }
        });

        sptPrevious.setIcon(new javax.swing.ImageIcon(BUTTON_DIR+"Back.png"));
        sptPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptPreviousActionPerformed(evt);
            }
        });

        sptMonth.setFont(new java.awt.Font("Vijaya", 1, 18)); // NOI18N
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

        sptCancel.setIcon(new javax.swing.ImageIcon(BUTTON_DIR+"Cancel.png"));
        sptCancel.setToolTipText("Cancel");
        sptCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptCancelActionPerformed(evt);
            }
        });

        sptSave.setIcon(new javax.swing.ImageIcon(BUTTON_DIR+"Save.png"));
        sptSave.setToolTipText("Save ");
        sptSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sptSaveActionPerformed(evt);
            }
        });

        jLabel1.setText("Start Time");

        jLabel2.setText("End Time");

        jLabel3.setText("Characters Left: ");

        sptCharLeft.setText("254");

        sptYear.setFont(new java.awt.Font("Vijaya", 1, 18)); // NOI18N
        sptYear.setText("Year");

        sptPatient.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Patient" }));

        sptStartHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hour", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19" }));
        sptStartHour.setSelectedItem("Hour");

        sptStartMin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Minute", "00", "15", "30", "45" }));
        sptStartMin.setSelectedItem("Minute");

        sptDurHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hour", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19" }));
        sptDurHour.setSelectedItem("Hour");

        sptDurMin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Minute", "00", "15", "30", "45" }));
        sptDurMin.setSelectedItem("Minute");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sptPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(sptMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sptYear, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sptNext, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(sptPatient, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 4, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sptCharLeft))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(sptCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sptSave, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(sptStartHour, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sptStartMin, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(sptDurHour, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(sptDurMin, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sptPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sptMonth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sptNext, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(sptYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sptPrevious, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sptStartMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sptStartHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(sptDurHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sptDurMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(sptCharLeft))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sptCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sptSave, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
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

    private void sptCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sptCancelActionPerformed
        Window w = SwingUtilities.getWindowAncestor(this);
        w.dispose();
        
    }//GEN-LAST:event_sptCancelActionPerformed

    private void sptSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sptSaveActionPerformed
        if(sptPatient.getSelectedItem().equals("Select Patient")){
            sptPatient.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
            JOptionPane.showMessageDialog(this,"No Patient Selected.");
        } else if (sptStartHour.getSelectedItem().equals(sptDurHour.getSelectedItem())){
            sptStartHour.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
            JOptionPane.showMessageDialog(this,"Start and End Time are the same.");
        } else if (sptStartHour.getSelectedItem().equals("Hour")){
            sptStartHour.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
            JOptionPane.showMessageDialog(this,"No Time Selected.");
        } else if (sptStartMin.getSelectedItem().equals("Minute")){
            sptStartMin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
            JOptionPane.showMessageDialog(this,"No Time Selected.");
        } else if (sptDurHour.getSelectedItem().equals("Hour")){
            sptDurHour.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
            JOptionPane.showMessageDialog(this,"No Time Selected.");
        } else if (sptDurMin.getSelectedItem().equals("Minute")){
            sptDurMin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
            JOptionPane.showMessageDialog(this,"No Time Selected.");
        } else {
             appointment = getAppointmentValues();
        try{
            DBAccess.addAppointmentData(appointment);
            JOptionPane.showMessageDialog(this,"Appointment Added");
        }catch(Exception e){
            System.out.println("SetAppointment - sptSaveActionPerformed Error: " + e);
        }        
        
        Window w = SwingUtilities.getWindowAncestor(this);
        w.dispose();
        } 
    }//GEN-LAST:event_sptSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton sptCancel;
    private javax.swing.JLabel sptCharLeft;
    private javax.swing.JComboBox sptDurHour;
    private javax.swing.JComboBox sptDurMin;
    private javax.swing.JTable sptMiniCalendar;
    private javax.swing.JLabel sptMonth;
    private javax.swing.JButton sptNext;
    private javax.swing.JComboBox sptPatient;
    private javax.swing.JButton sptPrevious;
    private javax.swing.JTextPane sptReason;
    private javax.swing.JButton sptSave;
    private javax.swing.JComboBox sptStartHour;
    private javax.swing.JComboBox sptStartMin;
    private javax.swing.JLabel sptYear;
    // End of variables declaration//GEN-END:variables
}
