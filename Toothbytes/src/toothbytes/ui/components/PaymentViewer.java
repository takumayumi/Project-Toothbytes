package toothbytes.ui.components;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import toothbytes.database.DBAccess;
import toothbytes.model.PatientX;
import toothbytes.model.PaymentX;
import toothbytes.model.RecordsX;
import toothbytes.ui.forms.SetPaymentSchedule;

public class PaymentViewer extends JPanel{
    PatientX px;
    private int patientID;
    private ArrayList<RecordsX> recordsX;
    private ArrayList<PaymentX> paymentX;
    
    public PaymentViewer(int patientID) {
        initComponents();
        this.patientID = patientID;
        setPatientInfo();
        setTransactionsTable();
        
    }
    
    private void setPatientInfo(){
        px = DBAccess.getData(patientID);
        patientNameLabel.setText(px.getFullName());
        
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
    
    private void setTransactionsTable(){
        recordsX = DBAccess.getRecordsData(patientID);
        
        DecimalFormat df = new DecimalFormat("#.00");
        
        for(int x = 0; x < recordsX.size(); x++){
            RecordsX rx = new RecordsX();
            rx.setTreatmentDate(recordsX.get(x).getTreatmentDate());
            for(int y = 0; y < recordsX.size(); y++){
                if(rx.getTreatmentDate().equals(recordsX.get(y).getTreatmentDate())){
                    rx.setAmountCharged(recordsX.get(y).getAmountCharged() + rx.getAmountCharged());
                    rx.setBalance(recordsX.get(y).getBalance() + rx.getBalance());
                }
            }
            double totalAmountPaid = rx.getAmountCharged() - rx.getBalance();
            transactionsTable.getModel().setValueAt(rx.getTreatmentDate(), x, 0);
            transactionsTable.getModel().setValueAt(df.format(rx.getAmountCharged()), x, 1);
            transactionsTable.getModel().setValueAt(df.format(totalAmountPaid), x, 2);
            
            if(rx.getBalance() != 0){
                transactionsTable.getModel().setValueAt(df.format(rx.getBalance()), x, 3);
            } else {
                transactionsTable.getModel().setValueAt("-----", x, 3);
            }
            
            if(totalAmountPaid == rx.getAmountCharged()){
                transactionsTable.getModel().setValueAt("Paid", x, 4);
            } else {
                transactionsTable.getModel().setValueAt("Existing Balance", x, 4);
            }
        }
        transactionsTable.addMouseListener(tableClickListener);
    }
    
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BillingPanel = new javax.swing.JPanel();
        personalInformationPanel = new javax.swing.JPanel();
        patientPhotoPanel = new javax.swing.JPanel();
        patientNameLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        genderLabel = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        occupationLabel = new javax.swing.JLabel();
        emailAddressLabel = new javax.swing.JLabel();
        cellphoneNo = new javax.swing.JLabel();
        homeNoLabel = new javax.swing.JLabel();
        detailsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        detailsTable = new javax.swing.JTable();
        transactionPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();

        personalInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Patient Information"));

        patientPhotoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout patientPhotoPanelLayout = new javax.swing.GroupLayout(patientPhotoPanel);
        patientPhotoPanel.setLayout(patientPhotoPanelLayout);
        patientPhotoPanelLayout.setHorizontalGroup(
            patientPhotoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );
        patientPhotoPanelLayout.setVerticalGroup(
            patientPhotoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );

        patientNameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        patientNameLabel.setText("Last, First MI.");

        addressLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addressLabel.setText("Address...");

        genderLabel.setText("Gender...");

        ageLabel.setText("00 years old...");

        occupationLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        occupationLabel.setText("Occupation...");

        emailAddressLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailAddressLabel.setText("EmailAddress...");

        cellphoneNo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cellphoneNo.setText("CellphoneNo...");

        homeNoLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        homeNoLabel.setText("HomeNo...");

        javax.swing.GroupLayout personalInformationPanelLayout = new javax.swing.GroupLayout(personalInformationPanel);
        personalInformationPanel.setLayout(personalInformationPanelLayout);
        personalInformationPanelLayout.setHorizontalGroup(
            personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(patientPhotoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(personalInformationPanelLayout.createSequentialGroup()
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(emailAddressLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addressLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(personalInformationPanelLayout.createSequentialGroup()
                                .addGap(135, 135, 135)
                                .addComponent(occupationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalInformationPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cellphoneNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(homeNoLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
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
                .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addGroup(personalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emailAddressLabel)
                            .addComponent(cellphoneNo))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addComponent(patientPhotoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        detailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));

        detailsTable.setModel(new javax.swing.table.DefaultTableModel(
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
                "Treatment Done", "Tooth No.", "Amount Charged", "Amount Paid", "Outstanding Balance"
            }
        ));
        detailsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(detailsTable);

        javax.swing.GroupLayout detailsPanelLayout = new javax.swing.GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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
                "Date", "Total Amount Charged", "Total Amount Paid", "Total Balance", "Status"
            }
        ));
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

        javax.swing.GroupLayout BillingPanelLayout = new javax.swing.GroupLayout(BillingPanel);
        BillingPanel.setLayout(BillingPanelLayout);
        BillingPanelLayout.setHorizontalGroup(
            BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BillingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalInformationPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BillingPanelLayout.createSequentialGroup()
                        .addComponent(transactionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0))
                    .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BillingPanelLayout.setVerticalGroup(
            BillingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BillingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(personalInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BillingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BillingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void transactionsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transactionsTableMouseClicked
        evt.getButton();
        JPopupMenu menu = new JPopupMenu("Popup");
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            }
        });
        JMenuItem menuItem1 = new JMenuItem("Set schedule of payment");
        menuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JFrame paymentSchedContainer = new JFrame();
                paymentSchedContainer.setSize(440, 480);
                paymentSchedContainer.setLocationRelativeTo(null);
                paymentSchedContainer.setResizable(false);
                SetPaymentSchedule paymentSched = new SetPaymentSchedule();
                paymentSchedContainer.add(paymentSched);
                paymentSchedContainer.setVisible(true);
                paymentSched.setVisible(true);
            }
        });
        menu.add(menuItem1);
        if(evt.getButton() == 3){
            menu.show(evt.getComponent(), evt.getX(), evt.getY());
            menu.setVisible(true);
        }
        evt.consume();
    }//GEN-LAST:event_transactionsTableMouseClicked
    
    private MouseListener tableClickListener = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            DecimalFormat df = new DecimalFormat("#.00");
            int row = detailsTable.getSelectedRow();
            String date = String.valueOf(detailsTable.getModel().getValueAt(1, 1));
            System.out.println(row);
            paymentX = DBAccess.getPaymentData(patientID);
            
            for(int i = 0; i < paymentX.size(); i++){
                if(paymentX.get(i).getPaymentDate().equals(date)){
                    double amountPaid = recordsX.get(i).getAmountCharged() - recordsX.get(i).getBalance();
                        
                    detailsTable.getModel().setValueAt(recordsX.get(i).getProcedure(), i, 0);
                    detailsTable.getModel().setValueAt(recordsX.get(i).getToothNo(), i, 1);
                    detailsTable.getModel().setValueAt(df.format(recordsX.get(i).getAmountCharged()), i, 2);
                    detailsTable.getModel().setValueAt(df.format(amountPaid), i, 3);
                    detailsTable.getModel().setValueAt(df.format(recordsX.get(i).getBalance()), i, 4);
                }
            }
        }
    };
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BillingPanel;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JLabel cellphoneNo;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JTable detailsTable;
    private javax.swing.JLabel emailAddressLabel;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JLabel homeNoLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel occupationLabel;
    private javax.swing.JLabel patientNameLabel;
    private javax.swing.JPanel patientPhotoPanel;
    private javax.swing.JPanel personalInformationPanel;
    private javax.swing.JPanel transactionPanel;
    private javax.swing.JTable transactionsTable;
    // End of variables declaration//GEN-END:variables
}
