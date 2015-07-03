/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.PatientX;
import models.PaymentX;
import models.RecordsX;
import utilities.DBAccess;
import window.forms.MiniBilling;
import window.forms.SetAppointment;

public class PaymentViewer extends JPanel{
    PatientX px;
    private int patientID;
    private int dentalRecordID;
    private ArrayList<RecordsX> recordsX;
    private PaymentX paymentX;
    
    private String PATIENTS_DIR = "res/patients/";
    
    
    public PaymentViewer(int patientID) {
        initComponents();
        this.patientID = patientID;
        setRightAlign();
        setPatientInfo();
        setTransactionsTable();
        paymentX = new PaymentX();
        recordsX = DBAccess.getRecordsData(patientID);
        setOtherInfo();
    }
    
    private void setRightAlign(){
        DefaultTableCellRenderer rightRenderer;
        rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //Transactions Table
        transactionsTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        transactionsTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        transactionsTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        transactionsTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        
        transactionsTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(10);
        transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(7);
        transactionsTable.getColumnModel().getColumn(5).setPreferredWidth(7);
        transactionsTable.getColumnModel().getColumn(6).setPreferredWidth(7);
        
        //transactionsTable.getColumn(1).setPreferredWidth(50);
    }
    private void setOtherInfo(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        totalBalance.setText(String.valueOf(df.format(getTotalBalance())));
        totalAmountChargedLabel.setText(String.valueOf(df.format(getTotalAmountCharged())));
        totalAmountPaidLabel.setText(String.valueOf(df.format(getTotalAmountPaid())));
    }
    
    private void setPatientInfo(){
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy");
        dateToday.setText(sdf.format(now.getTime()));
        receivedFrom.setText(DBAccess.getPatientName(patientID));
        px = DBAccess.getData(patientID);
        patientNameLabel.setText(px.getFullName());
        
        File f = new File("res/patients/" + px.getId() + ".jpg");
        String path = PATIENTS_DIR + px.getId() + ".jpg";
        ImageIcon croppedImg = ResizeImage(path);
        
        if (f.exists()) {
            patientImage.setIcon(croppedImg);
        } else {
            patientImage.setIcon(new ImageIcon("res/images/patient.png"));
        }
        
        
        try{
            if(!px.getCellNo().equalsIgnoreCase("NULL")){
                cellphoneNo.setText(px.getCellNo());
            } else {
                cellphoneNo.setText("");
            }
        }catch(Exception e){
            cellphoneNo.setText("");
        }
        
        if(px.getGender() == 'M'){
            genderLabel.setText("Male");
        } else {
            genderLabel.setText("Female");
        }
        

        int age = 0;
        if(px.getBdate().isSet(Calendar.YEAR)){
            age = now.get(Calendar.YEAR)-px.getBdate().get(Calendar.YEAR);
        }
        
        ageLabel.setText(String.valueOf(age));
        
        try{
            if(px.getHomeAddress().isEmpty()){
                addressLabel.setText("");
            } else {
                addressLabel.setText(px.getHomeAddress());
            }
        }catch(Exception e){
            addressLabel.setText("");
        }
        
        try{
            if(!px.getEmailAdd().equalsIgnoreCase("NULL")){
                emailAddressLabel.setText(px.getEmailAdd());
            } else {
                emailAddressLabel.setText("");
            }
        }catch(Exception e){
            emailAddressLabel.setText("");
        }
        
        try{
            if(!px.getOccupation().equalsIgnoreCase("NULL")){
                occupationLabel.setText(px.getOccupation());
            } else {
                occupationLabel.setText("");
            }
        }catch(Exception e){
            occupationLabel.setText("");
        }
        
        try{
            if(!px.getHomeNo().equalsIgnoreCase("NULL")){
                homeNoLabel.setText(px.getHomeNo());
            } else {
                homeNoLabel.setText("");
            }
        }catch(Exception e){
            homeNoLabel.setText("");
        }
        
        try{
            if(!px.getCellNo().equalsIgnoreCase("NULL")){
                cellphoneNo.setText(px.getCellNo());
            } else {
                cellphoneNo.setText("");
            }
        }catch(Exception e){
            cellphoneNo.setText("");
        }
    }
    
    public void refreshContentTable(){
        recordsX = DBAccess.getRecordsData(patientID);        
        DecimalFormat df = new DecimalFormat("#,###.00");
        //DecimalFormat df = new DecimalFormat("#,###.00");        
    }
    
    public ImageIcon ResizeImage(String imagePath){
        ImageIcon MyImage = new ImageIcon(imagePath);
        Image img = MyImage.getImage();        
        Image newImage = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
    
    private void setTransactionsTable(){
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        
        recordsX = DBAccess.getRecordsData(patientID);
        DecimalFormat df = new DecimalFormat("#,###.00");
        DefaultTableModel dtm = ((DefaultTableModel)transactionsTable.getModel());
        Object[] rows;
        
        for(int x = 0; x < recordsX.size(); x++){
            RecordsX rx = new RecordsX();
            rx.setTreatmentDate(recordsX.get(x).getTreatmentDate());
            rx.setAmountCharged(recordsX.get(x).getAmountCharged());
            rx.setProcedure(recordsX.get(x).getProcedure());
            rx.setToothNo(recordsX.get(x).getToothNo());
            rx.setBalance(recordsX.get(x).getBalance());
            rx.setRecordsID(recordsX.get(x).getRecordsID());
            
            for(int y = 0; y < recordsX.size(); y++){
                if(String.valueOf(rx.getPatientID()).equals(recordsX.get(y).getPatientID())){
                    rx.setAmountCharged(recordsX.get(y).getAmountCharged() + rx.getAmountCharged());
            }
            }
          
            double totalAmountPaid = rx.getAmountCharged() - rx.getBalance();

            transactionsTable.getModel().setValueAt(rx.getTreatmentDate(), x, 0);
            transactionsTable.getModel().setValueAt(rx.getRecordsID(), x, 1);
            transactionsTable.getModel().setValueAt(rx.getProcedure(), x, 2);
            transactionsTable.getModel().setValueAt(rx.getToothNo(), x, 3);
            transactionsTable.getModel().setValueAt(df.format(rx.getAmountCharged()), x, 4);
            transactionsTable.getModel().setValueAt(df.format(totalAmountPaid), x, 5);
            transactionsTable.getModel().setValueAt(rx.getBalance(), x, 6);
            
            if(rx.getBalance() != 0){
            } else {
            }
        }
        transactionsTable.addMouseListener(tableClickListener);
    }
    
     private void setUp(){
        
         Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        paymentX.setDentalRecordID(dentalRecordID);
            paymentX.setPaymentDate(sdf.format(now.getTime()));
        try {
            //String rcptNumber = receiptNumber.getText();
        //paymentX.setPaymentID(Integer.parseInt(rcptNumber));
        //paymentX.setDentalRecordID(dentalRecordID);
            
            paymentX.setAmountPaid(Double.parseDouble(amountReceived.getText()));
            double balance = Double.parseDouble(balanceLabel.getText()) - Double.parseDouble(amountReceived.getText());
            System.out.println(balance);
            DBAccess.billingUpdate(paymentX, balance);
            System.out.println(paymentX.getDentalRecordID());
             JOptionPane.showMessageDialog(this, "Payment Updated.");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Incorrect amount entered.","Error", JOptionPane.ERROR_MESSAGE);
        }
     }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BillingPanel = new javax.swing.JPanel();
        personalInformationPanel = new javax.swing.JPanel();
        patientNameLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        genderLabel = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        occupationLabel = new javax.swing.JLabel();
        emailAddressLabel = new javax.swing.JLabel();
        cellphoneNo = new javax.swing.JLabel();
        homeNoLabel = new javax.swing.JLabel();
        patientImage = new javax.swing.JLabel();
        transactionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();
        totalBalanceLabel = new javax.swing.JLabel();
        totalBalance = new javax.swing.JLabel();
        setScheduleButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        totalAmountChargedLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalAmountPaidLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        dateToday = new javax.swing.JLabel();
        recordIDLabel = new javax.swing.JLabel();
        receivedFrom = new javax.swing.JLabel();
        treatmentLabel = new javax.swing.JLabel();
        amountReceived = new javax.swing.JTextField();
        payButton = new javax.swing.JButton();
        receiptNumber = new javax.swing.JTextField();
        balanceLabel = new javax.swing.JTextField();

        BillingPanel.setBackground(new java.awt.Color(250, 255, 250));

        personalInformationPanel.setBackground(new java.awt.Color(250, 255, 250));
        personalInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Patient Information"));

        patientNameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        patientNameLabel.setText("Last, First MI.");

        addressLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addressLabel.setText("Address");

        genderLabel.setText("Gender");

        ageLabel.setText("00 years old");

        occupationLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        occupationLabel.setText("Occupation");

        emailAddressLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailAddressLabel.setText("EmailAddress");

        cellphoneNo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cellphoneNo.setText("CellphoneNo");

        homeNoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        homeNoLabel.setText("HomeNo");

        patientImage.setBackground(new java.awt.Color(250, 250, 250));
        patientImage.setOpaque(true);

        javax.swing.GroupLayout personalInformationPanelLayout = new javax.swing.GroupLayout(personalInformationPanel);
        personalInformationPanel.setLayout(personalInformationPanelLayout);
        personalInformationPanelLayout.setHorizontalGroup(
            personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(patientImage, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(personalInformationPanelLayout.createSequentialGroup()
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(emailAddressLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addressLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cellphoneNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(homeNoLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(occupationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(personalInformationPanelLayout.createSequentialGroup()
                        .addComponent(ageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(genderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        personalInformationPanelLayout.setVerticalGroup(
            personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(personalInformationPanelLayout.createSequentialGroup()
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(occupationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ageLabel)
                            .addComponent(genderLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addressLabel)
                            .addComponent(homeNoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cellphoneNo)
                            .addComponent(emailAddressLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(patientImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        transactionPanel.setBackground(new java.awt.Color(250, 255, 250));
        transactionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction"));

        transactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Record ID", "Treatment Received", "Tooth No", "Amount Charged", "Amount Paid", "Outstanding Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        transactionsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        transactionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transactionsTableMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                transactionsTableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(transactionsTable);

        javax.swing.GroupLayout transactionPanelLayout = new javax.swing.GroupLayout(transactionPanel);
        transactionPanel.setLayout(transactionPanelLayout);
        transactionPanelLayout.setHorizontalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        transactionPanelLayout.setVerticalGroup(
            transactionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        );

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        totalBalanceLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        totalBalanceLabel.setText("Total Balance:");

        totalBalance.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        totalBalance.setForeground(new java.awt.Color(255, 0, 0));

        setScheduleButton.setText("Set Schedule");
        setScheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setScheduleButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Total Amount Charged:");

        totalAmountChargedLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        totalAmountChargedLabel.setText(" ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Total Amount Paid:");

        totalAmountPaidLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        totalAmountPaidLabel.setText(" ");

        jLabel3.setText("jLabel3");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ruben T. Pascual Jr.");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("General Dentistry");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("#9 Kayang Street, Baguio City");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NON-VAT Reg. TIN 167-248-607-000");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Make Payment");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Date:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Receipt Number:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Received From:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Payment For:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Amount Received:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Balance:");

        dateToday.setText(" ");

        receivedFrom.setText(" ");

        payButton.setText("Pay");
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        receiptNumber.setEditable(false);
        receiptNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiptNumberActionPerformed(evt);
            }
        });

        balanceLabel.setEditable(false);

        javax.swing.GroupLayout BillingPanelLayout = new javax.swing.GroupLayout(BillingPanel);
        BillingPanel.setLayout(BillingPanelLayout);
        BillingPanelLayout.setHorizontalGroup(
            BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BillingPanelLayout.createSequentialGroup()
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BillingPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(personalInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(transactionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BillingPanelLayout.createSequentialGroup()
                                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(BillingPanelLayout.createSequentialGroup()
                                        .addGap(426, 426, 426)
                                        .addComponent(jLabel3))
                                    .addGroup(BillingPanelLayout.createSequentialGroup()
                                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(totalBalanceLabel)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLabel4))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(totalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(totalAmountPaidLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(totalAmountChargedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addComponent(setScheduleButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshButton)))
                                        .addGap(41, 41, 41)
                                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(treatmentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dateToday, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(recordIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(receivedFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)))
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(amountReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(payButton))
                                            .addComponent(jLabel8)
                                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(receiptNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE))))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BillingPanelLayout.setVerticalGroup(
            BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BillingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(5, 5, 5)
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BillingPanelLayout.createSequentialGroup()
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(totalAmountChargedLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(totalAmountPaidLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalBalanceLabel)
                            .addComponent(totalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BillingPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(dateToday))
                        .addGap(2, 2, 2)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(receiptNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(recordIDLabel)
                            .addComponent(receivedFrom))
                        .addGap(19, 19, 19)))
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(treatmentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(setScheduleButton)
                    .addComponent(refreshButton)
                    .addComponent(amountReceived, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(payButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BillingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BillingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setScheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setScheduleButtonActionPerformed
        // TODO add your handling code here:
        JDialog mb = new JDialog();
        SetAppointment sA = new SetAppointment(patientID, "Payment");
        mb.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        mb.setSize(sA.getPreferredSize());
        mb.add(sA);
        mb.pack();
        mb.setVisible(true);
        mb.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_setScheduleButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        setTransactionsTable();
        transactionsTable.clearSelection();
        totalAmountChargedLabel.setText(String.valueOf(getTotalAmountCharged()));
        totalAmountPaidLabel.setText(String.valueOf(getTotalAmountPaid()));
        totalBalance.setText(String.valueOf(getTotalBalance()));
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void transactionsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionsTableMouseClicked

    }//GEN-LAST:event_transactionsTableMouseClicked

    private void transactionsTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionsTableMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_transactionsTableMouseReleased

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        // TODO add your handling code here:
        if(balanceLabel.getText().equalsIgnoreCase(".00")){
            JOptionPane.showMessageDialog(this, "You've already paid your outstanding balance.");
        } else {
            setUp();
        }
    }//GEN-LAST:event_payButtonActionPerformed

    private void receiptNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiptNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_receiptNumberActionPerformed
            
    private double getTotalBalance(){
        double total = 0;
        
        for (RecordsX recordsX1 : recordsX) {
            total = total + recordsX1.getBalance();
            if(recordsX1.getBalance() > 0.0){
                System.out.println(recordsX1.getRecordsID());
                dentalRecordID = recordsX1.getRecordsID();
            }
        }
        
        return total;
    }
    
    private double getTotalAmountCharged(){
        double total = 0;
        
        for (RecordsX recordsX1 : recordsX) {
            total = total + recordsX1.getAmountCharged();
            if(recordsX1.getAmountCharged() > 0.0){
                System.out.println(recordsX1.getRecordsID());
                dentalRecordID = recordsX1.getRecordsID();
            }
        }
        
        return total;
    }
    
    private double getTotalAmountPaid(){
        double total = 0;
        for (RecordsX recordsX1 : recordsX) {
            total = getTotalAmountCharged() - getTotalBalance();
            if(recordsX1.getAmountCharged() > 0.0){
                System.out.println(recordsX1.getRecordsID());
                dentalRecordID = recordsX1.getRecordsID();
            }
        }
        
        return total;
    }
    
    private MouseListener tableClickListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
           int row = transactionsTable.getSelectedRow();
           //paymentX = DBAccess.getPaymentData(patientID);
           String balanceLeft = String.valueOf(transactionsTable.getModel().getValueAt(row, 6));
           balanceLabel.setText(balanceLeft);
           String treatmentReceived = String.valueOf(transactionsTable.getModel().getValueAt(row, 1));
           treatmentLabel.setText(treatmentReceived);
           
        }
        
    };
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BillingPanel;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JTextField amountReceived;
    private javax.swing.JTextField balanceLabel;
    private javax.swing.JLabel cellphoneNo;
    private javax.swing.JLabel dateToday;
    private javax.swing.JLabel emailAddressLabel;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JLabel homeNoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel occupationLabel;
    private javax.swing.JLabel patientImage;
    private javax.swing.JLabel patientNameLabel;
    private javax.swing.JButton payButton;
    private javax.swing.JPanel personalInformationPanel;
    private javax.swing.JTextField receiptNumber;
    private javax.swing.JLabel receivedFrom;
    private javax.swing.JLabel recordIDLabel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton setScheduleButton;
    private javax.swing.JLabel totalAmountChargedLabel;
    private javax.swing.JLabel totalAmountPaidLabel;
    private javax.swing.JLabel totalBalance;
    private javax.swing.JLabel totalBalanceLabel;
    private javax.swing.JPanel transactionPanel;
    private javax.swing.JTable transactionsTable;
    private javax.swing.JLabel treatmentLabel;
    // End of variables declaration//GEN-END:variables

}
