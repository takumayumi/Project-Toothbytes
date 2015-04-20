package toothbytes.ui;

import java.awt.Color;
import static java.awt.Color.WHITE;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import toothbytes.ui.components.AppointmentsWindow;
import toothbytes.ui.components.Cover;
import toothbytes.ui.components.ModuleWindow;
import toothbytes.ui.components.PaymentsWindow;
import toothbytes.ui.components.SidePanel;
import toothbytes.ui.forms.PersonalInformation;
import toothbytes.ui.toolbars.TBMenuBar;

public class MainScreen extends JFrame {

    private JPanel mainPanel, sidePanel, modulePanel;
    private JButton mainButton1, mainButton2, mainButton3;
    private MigLayout framework;
    private Dimension defaultSize;
    private boolean fullScreen;
    private ButtonGroup navButtons;
    private JButton appBut, recBut, payBut, testButton;
    private TBMenuBar menuBar;
    private JToolBar navBar, quickBar, statusBar;
    private JButton qAddPatientBut, qSetAppointmentBut;
    private String state;
    private ModuleWindow recWindow, appWindow, payWindow;
    private SidePanel sp;
    public static String time;
    
    public MainScreen(RecordsWindow rw, AppointmentsWindow aw, PaymentsWindow pw) {
        
        recWindow = rw;
        appWindow = aw;
        payWindow = pw;

        state = "firstrun";
        
        Font uiButtonFont = new Font("Arial", Font.PLAIN, 24);
        Color uiButtonColor = Color.WHITE;
        
        //frame configurations
        defaultSize = generateSize();
        defaultSize.setSize(
                defaultSize.getWidth(), defaultSize.getHeight() - 40);
        
        this.setTitle("Toothbytes");
        this.setSize(defaultSize);
        this.setIconImage(new ImageIcon("src/toothbytes/favicon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        fullScreen = false;

        //menu bar
        menuBar = new TBMenuBar();
        menuBar.setBackground(Color.white);
        this.setJMenuBar(menuBar);
        menuBar.bindListenerToMenu(new MenuBarHandler(), 1);
        
        //layout configurations
        framework = new MigLayout(
                "filly, wrap 12", //layout constraints
                "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill][fill]push[fill]push[fill]", //12 columns
                "[fill]push[fill]");//rows

        //mainpanel configurations
        mainPanel = new JPanel();
        mainPanel.setLayout(framework);
        mainPanel.setBackground(Color.white);
        this.setContentPane(mainPanel);
             
        //status Bar(bottom)
        statusBar = new JToolBar();
        statusBar.setBackground(Color.darkGray);
        
        mainPanel.add(statusBar, "south");
        
        JLabel test = new JLabel(time);
        
            Timer setTime = new Timer();
            setTime.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    int hour = LocalDateTime.now().getHour();
                    int minute = LocalDateTime.now().getMinute();
                    
                    int year = LocalDateTime.now().getYear();
                    int month = LocalDateTime.now().getMonthValue();
                    int day = LocalDateTime.now().getDayOfMonth();
                    
                    String sHour = String.valueOf(hour);
                    String sMin = String.valueOf(minute);
                    String hourFormat = "AM";
                    
                    String date = year + "/" + month + "/" + day;
                    
                    if(hour > 12){
                        hour = hour - 12;
                        hourFormat = "PM";
                        sHour = String.valueOf(hour);
                    } else if (hour == 0){
                        hour = 12;
                        sHour = "12";
                    }
                    
                    if(hour < 10){
                        sHour = "0"+sHour;
                    }
                    
                    if(minute < 10){
                        sMin = "0"+sMin;
                    }
                    
                    time = sHour + " : " + sMin + " " + hourFormat;
                    
                    
                    test.setText("<html>"+time +"<br>"+ date + "/<html>");
                }
            }, 1000, 1000);
        
        
        
        
        test.setForeground(Color.white);
        test.setFont(new Font("Tahoma", Font.PLAIN, 18));
        statusBar.add(test);
        
        //nav bar
        navBar = new JToolBar("TestBar");
        //navButtons = new ButtonGroup();

        recBut = new JButton();
        recBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/PatientRecords.png"));
        recBut.setToolTipText("Dental Records");
        recBut.setBackground(WHITE);
        //navButtons.add(recBut);

        appBut = new JButton();
        appBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/Appointments.png"));
        appBut.setToolTipText("Appointments");
        appBut.setBackground(WHITE);
        //navButtons.add(appBut);

        payBut = new JButton();
        payBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/Finances.png"));
        payBut.setToolTipText("Payments");
        payBut.setBackground(WHITE);
        //navButtons.add(payBut);
        
        navBar.add(recBut);
        navBar.add(appBut);
        navBar.add(payBut);
        navBar.setBackground(WHITE);

        NavigationHandler nh = new NavigationHandler();
        recBut.addActionListener(nh);
        appBut.addActionListener(nh);
        payBut.addActionListener(nh);

        navBar.setOrientation(JToolBar.VERTICAL);
        navBar.setFloatable(false);
        navBar.setBorder(BorderFactory.createLineBorder(Color.gray));
        mainPanel.add(navBar, "west");

        //module window(left)
        modulePanel = new JPanel();
        modulePanel.setLayout(new MigLayout("fill"));
        modulePanel.setBackground(Color.white);
        mainPanel.add(modulePanel, "span 12 2");
        modulePanel.add(new Cover(), "grow");

        //side panel(right)
        sp = new SidePanel();
        sp.setBackground(WHITE);
        mainPanel.add(sp, "east");
        
        //quick bar
        quickBar = new JToolBar("QuickBar");
        quickBar.setBackground(WHITE);

        qAddPatientBut = new JButton(new ImageIcon("src/toothbytes/res/icons/btn/AddNewPatient.png"));
        qAddPatientBut.setBackground(WHITE);
        qAddPatientBut.setToolTipText("Add Patient");
        qSetAppointmentBut = new JButton(new ImageIcon("src/toothbytes/res/icons/btn/AddNewAppointment.png"));
        qSetAppointmentBut.setBackground(WHITE);
        qSetAppointmentBut.setToolTipText("Set Appointment");

        QuickBarHandler qh = new QuickBarHandler();
        qAddPatientBut.addActionListener(qh);
        qSetAppointmentBut.addActionListener(qh);

        quickBar.add(qAddPatientBut);
        quickBar.add(qSetAppointmentBut);

        //quickBar.setFloatable(false);
        mainPanel.add(quickBar, "north");
    }

    public void testButtonActionPerformed(ActionEvent evt) {
        this.toggleFullScreen();
    }

    /**
     * this method sets the frame to be visible.
     */
    public void init() {
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
     * This method toggles the full screen mode of the frame.
     */
    public void toggleFullScreen() {
        if (this.fullScreen) {
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

    public ModuleWindow getModule(String state) {
        if (state.equals("recw")) {
            return recWindow;
        } else {
            if (state.equals("appw")) {
                return appWindow;
            } else {
                if (state.equals("payw")) {
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
            if (e.getSource() == qAddPatientBut) {
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

            if (e.getSource() == qSetAppointmentBut) {

            }
        }

    }

    public class NavigationHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == recBut) {
                if (!state.equals("recw")) {
                    modulePanel.removeAll();
                    loadModule(getModule("recw"), "recw");
                }
            }
            if (e.getSource() == appBut) {
                if (!state.equals("appw")) {
                    modulePanel.removeAll();
                    loadModule(getModule("appw"), "appw");
                }
            }
            if (e.getSource() == payBut) {
                if (!state.equals("payw")) {
                    modulePanel.removeAll();
                    loadModule(getModule("payw"), "payw");
                }
            }
        }
    }

    public class MenuBarHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().matches("Full Screen")) {
                toggleFullScreen();
            }
        }

    }   
}//end of MainScreen
