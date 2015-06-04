/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import models.Appointment;
import utilities.DBAccess;
import window.forms.SetAppointment;

/**
 *
 * @author Ecchi Powa
 */
public class CalendarWindow extends ModuleWindow {

    /**
     * Creates new form Calendar
     */
    
    
    private Calendar now = Calendar.getInstance();
    private int monthMod = now.get(Calendar.MONTH);
    private int yearMod = now.get(Calendar.YEAR);
    private ArrayList<Appointment> appointmentList = DBAccess.getAppointmentData("");
    private ImageIcon teeth = new ImageIcon("res/images/logo_smaller.png");
    private JPopupMenu selectionPopup;
    
    public CalendarWindow() {
        initComponents();
        
        calendarSetUp();
        popupMenuGen();
    }
    
    public void calendarSetUp(){
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
        cdwMonth.setText(month);
        cdwYear.setText(String.valueOf(now.get(Calendar.YEAR)));
        
        genCalendar();
        
        now.set(Calendar.DAY_OF_MONTH, 1);
        int currDay = now.get(Calendar.DAY_OF_WEEK);
        int weekNo = 0;
        
        try{
            for(int i = 1; i < now.getActualMaximum(Calendar.DAY_OF_MONTH)+1; i++){
                int appNo;
                if(now.get(Calendar.MONTH) < 10){
                    if(i < 10){
                        appNo = calendarInput(now.get(Calendar.YEAR)+"-0"+(now.get(Calendar.MONTH)+1)+"-0"+i);
                    } else {
                        appNo = calendarInput(now.get(Calendar.YEAR)+"-0"+(now.get(Calendar.MONTH)+1)+"-"+i);
                    }
                } else {
                    if(i < 10){
                        appNo = calendarInput(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-0"+i);
                    } else {
                        appNo = calendarInput(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+i);
                    }
                }
                
                if(appNo > 0){
                    cdwCalendar.getModel().setValueAt(i + "," +appNo, weekNo, currDay-1);
                } else {
                    cdwCalendar.getModel().setValueAt(i, weekNo, currDay-1);
                }
                currDay++;
                if(currDay > 7){
                    currDay = currDay - 7;
                    weekNo++;
                }
            }
        }catch(Exception e){
            System.out.println("CalendarWindow - calendarSetUp Error: " + e);
        }
    }
    
    public void genCalendar(){
        cdwCalendar.setModel(new javax.swing.table.DefaultTableModel(
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
        
//        for(int i = 0; i < 6; i++){
//            cdwCalendar.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
//        }
        
        cdwCalendar.setRowHeight(408/6);
        cdwCalendar.setAutoscrolls(false);
        cdwCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    class ImageRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected,boolean hasFocus, int row, int column){
            JLabel label = new JLabel();
            JLabel image = new JLabel(teeth);
            try{
                String[] input = String.valueOf(value).split(",");
                teeth.setDescription(input[1]);
                label = new JLabel(input[0] ,JLabel.LEFT);
                label.add(image);
            }catch(Exception e){
                label.setText(String.valueOf(value));
            }
            
            return label;
        }
    }
    
    public void popupMenuGen(){
        
        selectionPopup = new JPopupMenu("Appointment");
        
        JMenuItem addNewAppointment = new JMenuItem("Set Appointment");
        
        addNewAppointment.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mousePressed(java.awt.event.MouseListener evt){
                java.awt.EventQueue.invokeLater(new Runnable(){
                    public void run(){
                        JDialog nA = new JDialog();
                        nA.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        SetAppointment sA = new SetAppointment();
                        nA.setSize(sA.getPreferredSize());
                        nA.add(sA);
                        nA.pack();
                        nA.setVisible(true);
                        nA.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    }
                });
            }
        });
        
        selectionPopup.add(addNewAppointment);
    }
    
    public int calendarInput(String date){
        int appointmentCount = 0;
        
        for(int i = 0; i < appointmentList.size(); i++){
            if(date.equals(appointmentList.get(i).getAppointmentDate())){
                appointmentCount++;
                System.out.println(appointmentCount);
            }
        }
        
        return appointmentCount;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cdwPrevious = new javax.swing.JButton();
        cdwNext = new javax.swing.JButton();
        cdwMonth = new javax.swing.JLabel();
        cdwYear = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cdwCalendar = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(686, 546));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Appointment Viewer"));

        cdwPrevious.setText("Previous");
        cdwPrevious.setMaximumSize(new java.awt.Dimension(75, 23));
        cdwPrevious.setMinimumSize(new java.awt.Dimension(75, 23));
        cdwPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdwPreviousActionPerformed(evt);
            }
        });

        cdwNext.setText("Next");
        cdwNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdwNextActionPerformed(evt);
            }
        });

        cdwMonth.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cdwMonth.setText("Month");

        cdwYear.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cdwYear.setText("Year");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cdwPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(cdwYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cdwMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                .addComponent(cdwNext, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cdwPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cdwNext)
                    .addComponent(cdwMonth)
                    .addComponent(cdwYear))
                .addContainerGap())
        );

        cdwCalendar.setModel(new javax.swing.table.DefaultTableModel(
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
        cdwCalendar.setAutoscrolls(false);
        cdwCalendar.setCellSelectionEnabled(true);
        cdwCalendar.setDebugGraphicsOptions(javax.swing.DebugGraphics.BUFFERED_OPTION);
        cdwCalendar.setRowHeight(68);
        cdwCalendar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cdwCalendarMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cdwCalendarMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(cdwCalendar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cdwPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdwPreviousActionPerformed
        monthMod --;
        calendarSetUp();
    }//GEN-LAST:event_cdwPreviousActionPerformed

    private void cdwNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdwNextActionPerformed
        monthMod ++;
        calendarSetUp();
    }//GEN-LAST:event_cdwNextActionPerformed

    private void cdwCalendarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cdwCalendarMouseClicked
        
    }//GEN-LAST:event_cdwCalendarMouseClicked

    public void setMenuMode(java.awt.event.MouseEvent evt){
        if(evt.getButton() == MouseEvent.BUTTON1){
            popupMenuGen();
            int x = cdwCalendar.getSelectedRow();
            int y = cdwCalendar.getSelectedColumn();
                      
            Object value = cdwCalendar.getModel().getValueAt(x, y);
            String date = "";
            try{
                String[] z = String.valueOf(value).split(",");
                if(now.get(Calendar.MONTH) < 10){
                    if(Integer.parseInt(z[0]) < 10){
                        date = yearMod+"-0"+(now.get(Calendar.MONTH)+1)+"-0"+z[0];
                    } else {
                        date = yearMod+"-0"+(now.get(Calendar.MONTH)+1)+"-"+z[0];
                    }
                } else {
                    if(Integer.parseInt(z[0]) < 10){
                        date = yearMod+"-"+(now.get(Calendar.MONTH)+1)+"-0"+z[0];
                    } else {
                        date = yearMod+"-"+(now.get(Calendar.MONTH)+1)+"-"+z[0];
                    }
                }
                               
                try{
                    int appointmentNo = Integer.parseInt(z[1]);
                    ArrayList<Appointment> dateAppointments = getAppointment(date);
                    
                    for(int i = 0; i < dateAppointments.size(); i++){
                        selectionPopup.add(new JMenuItem(dateAppointments.get(i).getAppointmentTime()){
                            public void ActionPerformed(ActionEvent e){
                                java.awt.EventQueue.invokeLater(new Runnable(){
                                    public void run(){                                        
                                        JDialog nA = new JDialog();
                                        SetAppointment sA = new SetAppointment();
                                        nA.setSize(sA.getPreferredSize());
                                        nA.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                                        nA.add(sA);
                                        nA.pack();
                                        nA.setVisible(true);
                                        nA.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                    }
                                });
                            }
                        });
                    }
                    
                    
                }catch(Exception ex){
                    System.out.println("CalendarWindow - cdwCalendarMouseClick Error: "+ex);
                }
                
            }catch(Exception e){
                System.out.println("CalendarWindow - cdwCalendarMouseClick Error: "+e);
            }
        }
    }
    
    private void cdwCalendarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cdwCalendarMouseReleased
        int x = cdwCalendar.getSelectedRow();
        int y = cdwCalendar.getSelectedColumn();
        
        boolean check = false;
        try{
            String value = cdwCalendar.getModel().getValueAt(x, y).toString();
            check = true;
        }catch(Exception e){
            System.out.println(e);
        }
        
        if(evt.getButton()== 3 && check){
            selectionPopup.show(evt.getComponent(), evt.getX(), evt.getY());
            selectionPopup.setVisible(true);
        }
        
    }//GEN-LAST:event_cdwCalendarMouseReleased
    
    private ArrayList<Appointment> getAppointment(String date){
        ArrayList<Appointment> dateAppointments = new ArrayList<>();
        
        for(int i = 0; i < appointmentList.size(); i++){
            if(date.equals(appointmentList.get(i).getAppointmentDate())){
                dateAppointments.add(appointmentList.get(i));
            }
        }
        
        return dateAppointments;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable cdwCalendar;
    private javax.swing.JLabel cdwMonth;
    private javax.swing.JButton cdwNext;
    private javax.swing.JButton cdwPrevious;
    private javax.swing.JLabel cdwYear;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
