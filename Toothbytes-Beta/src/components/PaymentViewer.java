/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;
import java.awt.Dialog;
import java.awt.Image;
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
import javax.swing.JPanel;
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
    private ArrayList<RecordsX> recordsX;
    private int dentalRecordID = 0;
    private ArrayList<PaymentX> paymentX;
    private String PATIENTS_DIR = "res/patients/";
    
    public PaymentViewer(int patientID) {
        initComponents();
        this.patientID = patientID;
        setRightAlign();
        setPatientInfo();
        setTransactionsTable();
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
        
    }
    private void setOtherInfo(){
        DecimalFormat df = new DecimalFormat("#,###.00");
        totalBalance.setText(String.valueOf(df.format(getTotalBalance())));
    }
    
    private void setPatientInfo(){
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
        
        Calendar now = Calendar.getInstance();
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
        totalBalance.setText(String.valueOf(df.format(getTotalBalance())));
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
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

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
            transactionsTable.getModel().setValueAt(rx.getAmountCharged(), x, 4);

            
            if(rx.getBalance() != 0){
            } else {
            }
        }
        transactionsTable.addMouseListener(tableClickListener);
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
        makePaymentButton = new javax.swing.JButton();
        totalBalanceLabel = new javax.swing.JLabel();
        totalBalance = new javax.swing.JLabel();
        setScheduleButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        totalAmountChargedLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalAmountPaidLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dateOfTransaction = new javax.swing.JLabel();
        treatmentReceived = new javax.swing.JLabel();
        toothNumberLabel = new javax.swing.JLabel();
        amountChargedPerTooth = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        balanceTreatment = new javax.swing.JLabel();

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
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
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addComponent(patientImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        transactionPanel.setBackground(new java.awt.Color(250, 255, 250));
        transactionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaction"));

        transactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Record ID", "Treatment Received", "Tooth No", "Amount Charged"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        transactionsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        transactionsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transactionsTableMouseClicked(evt);
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

        makePaymentButton.setText("Make Payment");
        makePaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makePaymentButtonActionPerformed(evt);
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

        jLabel6.setText("Amount Charged:");

        jLabel7.setText("Tooth No.:");

        jLabel8.setText("Treatment Received:");

        jLabel9.setText("Date of Transaction:");

        dateOfTransaction.setText(" ");

        treatmentReceived.setText(" ");

        toothNumberLabel.setText(" ");

        amountChargedPerTooth.setText(" ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Total Balance:");

        balanceTreatment.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        balanceTreatment.setText(" ");

        javax.swing.GroupLayout BillingPanelLayout = new javax.swing.GroupLayout(BillingPanel);
        BillingPanel.setLayout(BillingPanelLayout);
        BillingPanelLayout.setHorizontalGroup(
            BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BillingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalInformationPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transactionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BillingPanelLayout.createSequentialGroup()
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalBalanceLabel)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(totalAmountChargedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                    .addComponent(totalAmountPaidLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(balanceTreatment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(BillingPanelLayout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(jLabel3))
                                    .addGroup(BillingPanelLayout.createSequentialGroup()
                                        .addGap(78, 78, 78)
                                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(dateOfTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(treatmentReceived, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(toothNumberLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(amountChargedPerTooth, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))))
                            .addGroup(BillingPanelLayout.createSequentialGroup()
                                .addComponent(totalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(setScheduleButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(makePaymentButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refreshButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        BillingPanelLayout.setVerticalGroup(
            BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BillingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BillingPanelLayout.createSequentialGroup()
                        .addComponent(personalInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transactionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(totalAmountChargedLabel)))
                    .addGroup(BillingPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(dateOfTransaction))))
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BillingPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(treatmentReceived))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(toothNumberLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(amountChargedPerTooth)))
                    .addGroup(BillingPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalAmountPaidLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(balanceTreatment))))
                .addGap(25, 25, 25)
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(setScheduleButton)
                        .addComponent(makePaymentButton)
                        .addComponent(refreshButton)
                        .addComponent(totalBalanceLabel))
                    .addComponent(totalBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(BillingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void makePaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makePaymentButtonActionPerformed
        // TODO add your handling code here:
        JDialog mb = new JDialog();
        MiniBilling miniBill = new MiniBilling(mb, patientID);
        mb.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        mb.setSize(miniBill.getPreferredSize());
        mb.add(miniBill);
        mb.pack();
        mb.setVisible(true);
        mb.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_makePaymentButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        setTransactionsTable();
        transactionsTable.clearSelection();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void transactionsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionsTableMouseClicked

    }//GEN-LAST:event_transactionsTableMouseClicked
    
    private MouseListener tableClickListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            DecimalFormat df = new DecimalFormat("#,###.00");
            int row = transactionsTable.getSelectedRow();
            paymentX = DBAccess.getPaymentData(patientID);
            
            String date = String.valueOf(transactionsTable.getModel().getValueAt(row, 0));
            dateOfTransaction.setText(date);
            String recordid = String.valueOf(transactionsTable.getModel().getValueAt(row, 1));
            String patientid = String.valueOf(patientID);
            
            for(int i = 0; i < paymentX.size(); i++){
                if(paymentX.get(i).getPaymentDate().equals(date)){
                double amountPaid = recordsX.get(i).getAmountCharged() - recordsX.get(i).getBalance();
                totalAmountPaidLabel.setText(String.valueOf(amountPaid));                    
                }
        }
            
  
            for(int x = 0; x < recordsX.size(); x++){
                RecordsX rx = new RecordsX();
                rx.setTreatmentDate(recordsX.get(x).getTreatmentDate());
                rx.setAmountCharged(recordsX.get(x).getAmountCharged());
                rx.setProcedure(recordsX.get(x).getProcedure());
                rx.setToothNo(recordsX.get(x).getToothNo());
                rx.setBalance(recordsX.get(x).getBalance());
                rx.setRecordsID(recordsX.get(x).getRecordsID());
                
                double totalAmountPaid = rx.getAmountCharged() - rx.getBalance();
                
            if(String.valueOf(recordsX.get(x).getRecordsID()).equals(recordid)){
                treatmentReceived.setText(String.valueOf(recordsX.get(x).getProcedure()));
                toothNumberLabel.setText(String.valueOf(recordsX.get(x).getRecordsID()));
                amountChargedPerTooth.setText(String.valueOf(recordsX.get(x).getAmountCharged()));
                totalAmountChargedLabel.setText(String.valueOf(recordsX.get(x).getAmountCharged()));
                //double totalBalanceLabel = rx.getAmountCharged() - rx.getBalance();
                //balanceTreatment.setText(String.valueOf(totalBalanceLabel));
                balanceTreatment.setText(String.valueOf(recordsX.get(x).getBalance()));
                totalAmountPaidLabel.setText(String.valueOf(totalAmountPaid));
                //double totalAmountPaid = (String.valueOf(recordsX.get(x).getBalance()));
            }
            /*
            for(int y = 0; y < recordsX.size(); y++){
                if(patientid.equals(String.valueOf(recordsX.get(y).getPatientID()))){
                    rx.setAmountCharged(recordsX.get(y).getAmountCharged());
                    rx.setBalance(recordsX.get(y).getBalance() + rx.getAmountCharged());                   
                }
                
            }
                    */
            
            //double balanceTreat = rx.getAmountCharged() - getTotalBalance();
            //balanceTreatment.setText(String.valueOf(balanceTreat));
            
            //totalAmountPaidLabel.setText(String.valueOf(totalAmountPaid));
            //totalAmountChargedLabel.setText(String.valueOf(rx.getAmountCharged()));
            }
        }
    };
   
            
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
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BillingPanel;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JLabel amountChargedPerTooth;
    private javax.swing.JLabel balanceTreatment;
    private javax.swing.JLabel cellphoneNo;
    private javax.swing.JLabel dateOfTransaction;
    private javax.swing.JLabel emailAddressLabel;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JLabel homeNoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton makePaymentButton;
    private javax.swing.JLabel occupationLabel;
    private javax.swing.JLabel patientImage;
    private javax.swing.JLabel patientNameLabel;
    private javax.swing.JPanel personalInformationPanel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton setScheduleButton;
    private javax.swing.JLabel toothNumberLabel;
    private javax.swing.JLabel totalAmountChargedLabel;
    private javax.swing.JLabel totalAmountPaidLabel;
    private javax.swing.JLabel totalBalance;
    private javax.swing.JLabel totalBalanceLabel;
    private javax.swing.JPanel transactionPanel;
    private javax.swing.JTable transactionsTable;
    private javax.swing.JLabel treatmentReceived;
    // End of variables declaration//GEN-END:variables
}
