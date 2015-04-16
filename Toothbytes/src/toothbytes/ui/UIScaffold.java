package toothbytes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import toothbytes.ui.toolbars.TBMenuBar;


public class UIScaffold extends JFrame {
    private JPanel mainPanel, sidePanel, statusBar, modulePanel;
    private JButton mainButton1, mainButton2, mainButton3;
    private MigLayout framework;
    private Dimension defaultSize;
    private boolean fullScreen;
    private ButtonGroup navButtons;
    private JButton appBut, recBut, payBut,testButton;
    private TBMenuBar menuBar;
    private JToolBar navBar, quickBar;
    private JButton qAddPatientBut, qSetAppointmentBut;
    private String state;
    private ModuleWindow recWindow, appWindow, payWindow;
    
    public UIScaffold(RecordsWindow rw, AppointmentsWindow aw, PaymentsWindow pw) {
        recWindow = rw;
        appWindow = aw;
        payWindow = pw;
        
        state = "firstrun";
        Font uiButtonFont = new Font("Arial", Font.PLAIN, 24);
        Color uiButtonColor = Color.WHITE;
        //frame configurations
        defaultSize = generateSize();
        defaultSize.setSize(
                defaultSize.getWidth(), defaultSize.getHeight()-40);
        this.setTitle("Toothbytes");
        this.setSize(defaultSize);
        this.setIconImage(new ImageIcon("src/toothbytes/favicon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fullScreen = false;
        
        //menu bar
        menuBar = new TBMenuBar();
        menuBar.setBackground(Color.white);
        this.setJMenuBar(menuBar);
        menuBar.bindListenerToMenu(new MenuBarHandler(), 1);
        //layout configurations
        framework = new MigLayout(
                "filly, wrap 12",   //layout constraints
                "[fill]push[fill]push[fill]push[fill]push"
                        + "[fill]push[fill]push[fill]push[fill]push"
                        + "[fill][fill]push[fill]push[fill]", //12 columns
                "[fill]push[fill][fill, 25]");//rows
        
        //mainpanel configurations
        mainPanel = new JPanel();
        mainPanel.setLayout(framework);
        mainPanel.setBackground(Color.white);
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setContentPane(mainPanel);
        
        navBar = new JToolBar("TestBar");
        navButtons = new ButtonGroup();
        
        recBut = new JButton();
        recBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/PatientRecords.png"));
        recBut.setToolTipText("Dental Records");
        navButtons.add(recBut);
        
        appBut = new JButton();
        appBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/Appointments.png"));
        appBut.setToolTipText("Appointments");
        navButtons.add(appBut);
        
        payBut = new JButton();
        payBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/Finances.png"));
        payBut.setToolTipText("Payments");
        navButtons.add(payBut);        
        
//        moduleMenu.add(recBut);
//        moduleMenu.add(appBut);
//        moduleMenu.add(payBut);
        navBar.add(recBut);
        navBar.add(appBut);
        navBar.add(payBut);
        
        NavigationHandler nh = new NavigationHandler();
        recBut.addActionListener(nh);
        appBut.addActionListener(nh);
        payBut.addActionListener(nh);
        
        navBar.setOrientation(JToolBar.VERTICAL);
        navBar.setFloatable(false);
        navBar.setBorder(BorderFactory.createLineBorder(Color.gray));
        mainPanel.add(navBar, "west");
        
        quickBar = new JToolBar("QuickBar");
        
        qAddPatientBut = new JButton(new ImageIcon("src/toothbytes/res/icons/btn/AddNewPatient.png"));
        qSetAppointmentBut = new JButton(new ImageIcon("src/toothbytes/res/icons/btn/AddNewAppointment.png"));
        
        QuickBarHandler qh = new QuickBarHandler();
        qAddPatientBut.addActionListener(qh);
        qSetAppointmentBut.addActionListener(qh);
        
        quickBar.add(qAddPatientBut);
        quickBar.add(qSetAppointmentBut);
        
        quickBar.setFloatable(false);
        mainPanel.add(quickBar, "north");
        
        //module window(left)
        modulePanel = new JPanel();
        //modulePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        modulePanel.setLayout(new MigLayout("fill"));
        modulePanel.setBackground(Color.white);
        mainPanel.add(modulePanel, "span 9 2");
        modulePanel.add(new Cover(), "grow");
        
        //side panel(right)
        sidePanel = new JPanel();
        sidePanel.setLayout(new MigLayout("fill"));
        sidePanel.setBackground(Color.white);
        mainPanel.add(sidePanel, "span 3 2");
        //status Bar(bottom)
        statusBar = new JPanel();
        statusBar.setBackground(Color.white);
        mainPanel.add(statusBar, "span 12 1");
    }
    
    public void testButtonActionPerformed(ActionEvent evt) {
        this.toggleFullScreen();
    }
       
    /**
    *this method sets the frame to be visible.
    */
    public void init(){
        menuBar.setAllFont(new Font("Tahoma", Font.PLAIN, 16));
        this.setVisible(true);
    }
    
    /*
     * This method takes the screen size of the computer for the computation of the frame size.
    */
    public Dimension generateSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    /**
    *This method toggles the full screen mode of the frame.
    */
    public void toggleFullScreen() {
        if(this.fullScreen) {
            this.dispose();
            this.setUndecorated(false);
            this.fullScreen = false;
            this.init();

        } else {
            this.dispose();
            this.setUndecorated(true);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.fullScreen = true;
            this.init();
        }
    }
    public ModuleWindow getModule(String state){
        if(state.equals("recw")){
            return recWindow;
        } else {
            if(state.equals("appw")){
                return appWindow;
            } else{
                if(state.equals("payw")) {
                    return payWindow;
                }
            }
        }
        return null;
    }
    public void loadModule(ModuleWindow c, String state) {
        this.state = state;
        modulePanel.add(c, "grow");
        SwingUtilities.updateComponentTreeUI(modulePanel);
    }
    public void addToSidePanel(JComponent c) {
        sidePanel.add(c, "grow");
    }
    public class QuickBarHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == qAddPatientBut) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        JFrame ctb = new JFrame();
                        PersonalInformation pi = new PersonalInformation(ctb);
                        System.out.println(pi.isVisible());
                        ctb.setSize(pi.getPreferredSize());
                        ctb.add(pi);
                        ctb.pack();
                        ctb.setVisible(true);
                        ctb.setForeground(Color.white);
                        ctb.setBackground(Color.white);
                        ctb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }
                    }
                );
            }
            
            
            if(e.getSource() == qSetAppointmentBut) {
                
            }
        }
        
    }
    public class NavigationHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == recBut) {
                if(!state.equals("recw")){
                    modulePanel.removeAll();
                    loadModule(getModule("recw"), "recw");
                }
            }
            if(e.getSource() == appBut) {
                if(!state.equals("appw")){
                    modulePanel.removeAll();
                    loadModule(getModule("appw"), "appw");
                }
            }
            if(e.getSource() == payBut) {
                if(!state.equals("payw")){
                    modulePanel.removeAll();
                    loadModule(getModule("payw"), "payw");
                }
            }
        }
    }
    public class MenuBarHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().matches("Full Screen")) {
                toggleFullScreen();
            }
        }
        
    }
}//end of UIScaffold