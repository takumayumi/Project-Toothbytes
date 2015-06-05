/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
 */
package window;

import components.CalendarWindow;
import components.Cover;
import components.ExitDialog;
import components.LoginDialog;
import components.ModuleWindow;
import components.PaymentWindow;
import components.RecordsWindow;
import components.ReportsGenWindow;
import components.SidePanel;
import components.TBMenuBar;
import java.awt.Color;
import static java.awt.Color.WHITE;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import window.AppointmentsWindow;
import window.forms.PersonalInformation;
import window.forms.SetAppointment;
/**
 * <h1>MainScreen</h1>
 * The {@code MainScreen} class constructs the main window of Toothbytes. It
 * layouts the panels, buttons, menu bar, toolbars, and module windows of 
 * the program.
 */
public class MainScreen extends JFrame {

    private JPanel mainPanel, sidePanel, modulePanel;
    private JButton mainButton1, mainButton2, mainButton3;
    private MigLayout framework;
    private Dimension defaultSize;
    private boolean fullScreen;
    private ButtonGroup navButtons;
    private JToggleButton homeBut, appBut, recBut, payBut, repgenBut, testButton;
    private TBMenuBar menuBar;
    private JToolBar navBar, quickBar, statusBar;
    private JButton qAddPatientBut, qSetAppointmentBut;
    private String state;
    private ModuleWindow recWindow, appWindow, payWindow,repWindow;
    private SidePanel sp;
    public static String time;
    private Cover c;
    private LoginDialog ld;
    private ExitDialog ed;

    public MainScreen(RecordsWindow rw, CalendarWindow aw, PaymentWindow pw, ReportsGenWindow rgw) {
        //Dialogs
        ld = new LoginDialog(this);
        ed = new ExitDialog(this);
        
        recWindow = rw;
        appWindow = aw;
        payWindow = pw;
        repWindow = rgw;

        state = "cover";

        Font uiButtonFont = new Font("Walkway SemiBold", Font.PLAIN, 24);
        Color uiButtonColor = Color.WHITE;

        // Frame configurations.
        defaultSize = generateSize();
        defaultSize.setSize(
                defaultSize.getWidth(), defaultSize.getHeight() - 40);

        this.setTitle("Toothbytes");
        this.setSize(defaultSize);
        this.setIconImage(new ImageIcon("src/Toothbytes/favicon.png").getImage());

        fullScreen = false;

        // Menu bar.
        menuBar = new TBMenuBar();
        menuBar.setBackground(Color.white);
        this.setJMenuBar(menuBar);
        menuBar.bindListenerToMenu(new MenuBarHandler(), 1);

        // Layout configurations.
        framework = new MigLayout(
                "filly, wrap 12", // Layout constraints.
                "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill][fill]push[fill]push[fill]", // 12 columns.
                "[fill]push[fill]"); // Rows.

        // Mainpanel configurations.
        mainPanel = new JPanel();
        mainPanel.setLayout(framework);
        mainPanel.setBackground(Color.white);
        this.setContentPane(mainPanel);

        // Status Bar (bottom).
        statusBar = new JToolBar();
        statusBar.setBackground(Color.darkGray);

        mainPanel.add(statusBar, "south");

        JLabel test = new JLabel(time);

        Timer setTime = new Timer();
        setTime.scheduleAtFixedRate(new TimerTask() {

            /**
             * This method calls the time depending on the user's device and
             * displays it on the status bar.
             */
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

                String date = month + " / " + day + " / " + year;

                if (hour > 12) {
                    hour = hour - 12;
                    hourFormat = "PM";
                    sHour = String.valueOf(hour);
                } else if (hour == 0) {
                    hour = 12;
                    sHour = "12";
                }

                if (hour < 10) {
                    sHour = "0" + sHour;
                }

                if (minute < 10) {
                    sMin = "0" + sMin;
                }

                time = sHour + " : " + sMin + " " + hourFormat;

                test.setText("<html> " + time + "<br>" + date + "</html>");
            }
        }, 1000, 1000);

        test.setForeground(Color.white);
        test.setFont(new Font("Tahoma", Font.PLAIN, 18));
        statusBar.add(test);

        // Nav bar.
        final String BUTTON_DIR = "res/buttons/";

        navBar = new JToolBar("TestBar");
        navButtons = new ButtonGroup();

        homeBut = new JToggleButton();
        homeBut.setIcon(new ImageIcon(BUTTON_DIR + "Home.png"));
        homeBut.setToolTipText("Home");
        homeBut.setBackground(WHITE);

        recBut = new JToggleButton();
        recBut.setIcon(new ImageIcon(BUTTON_DIR + "PatientRecords.png"));
        recBut.setToolTipText("Dental Records");
        recBut.setBackground(WHITE);

        appBut = new JToggleButton();
        appBut.setIcon(new ImageIcon(BUTTON_DIR + "Appointments.png"));
        appBut.setToolTipText("Appointments");
        appBut.setBackground(WHITE);

        payBut = new JToggleButton();
        payBut.setIcon(new ImageIcon(BUTTON_DIR + "Finances.png"));
        payBut.setToolTipText("Payments");
        payBut.setBackground(WHITE);

        repgenBut = new JToggleButton();
        repgenBut.setIcon(new ImageIcon(BUTTON_DIR+"GenerateReport.png"));
        repgenBut.setToolTipText("Generate Reports");
        repgenBut.setBackground(WHITE);
        
        navButtons.add(homeBut);
        navButtons.add(recBut);
        navButtons.add(appBut);
        navButtons.add(payBut);
        navButtons.add(repgenBut);

        navBar.add(homeBut);
        navBar.add(recBut);
        navBar.add(appBut);
        navBar.add(payBut);
        navBar.add(repgenBut);
        navBar.setBackground(WHITE);

        NavigationHandler nh = new NavigationHandler();
        homeBut.addActionListener(nh);
        recBut.addActionListener(nh);
        appBut.addActionListener(nh);
        payBut.addActionListener(nh);
        repgenBut.addActionListener(nh);

        navBar.setOrientation(JToolBar.VERTICAL);
        navBar.setFloatable(false);
        navBar.setBorder(BorderFactory.createLineBorder(Color.gray));
        mainPanel.add(navBar, "west");

        // Module window (left).
        modulePanel = new JPanel();
        modulePanel.setLayout(new MigLayout("fill"));
        modulePanel.setBackground(Color.white);
        mainPanel.add(modulePanel, "span 12 2");
        c = new Cover();
        modulePanel.add(c, "grow");

        // Side panel (right).
        sp = new SidePanel();
        sp.setBackground(WHITE);
        mainPanel.add(sp, "east");

        // Quick bar.
        quickBar = new JToolBar("QuickBar");
        quickBar.setBackground(WHITE);

        qAddPatientBut = new JButton(new ImageIcon(BUTTON_DIR+"AddNewPatient.png"));
        qAddPatientBut.setBackground(WHITE);
        qAddPatientBut.setToolTipText("Add Patient");
        qSetAppointmentBut = new JButton(new ImageIcon(BUTTON_DIR+"AddNewAppointment.png"));
        qSetAppointmentBut.setBackground(WHITE);
        qSetAppointmentBut.setToolTipText("Set Appointment");

        QuickBarHandler qh = new QuickBarHandler();
        qAddPatientBut.addActionListener(qh);
        qSetAppointmentBut.addActionListener(qh);

        quickBar.add(qAddPatientBut);
        quickBar.add(qSetAppointmentBut);

        mainPanel.add(quickBar, "north");
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {

            public void windowOpened(WindowEvent e) {
                
                ld.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                ld.setVisible(true);
                ld.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                
            }

            public void windowClosing(WindowEvent e) {
                
                ed.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                ed.setVisible(true);
                ed.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            }
        });
    }

    /**
     * This method tests the button action performed.
     *
     * @param evt ActionEvent object.
     */
    public void testButtonActionPerformed(ActionEvent evt) {
        this.toggleFullScreen();
    }

    /**
     * This method sets the frame to be visible.
     */
    public void init() {
        menuBar.setAllFont(new Font("Walkway SemiBold", Font.PLAIN, 16));
        this.setVisible(true);
    }

    /**
     * This method takes the screen size of the computer for the computation of
     * the frame size.
     *
     * @return Screen size of the user's device.
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

    /**
     * This method returns a specific module window depending on the state
     * chosen by the user.
     *
     * @param state A String representation.
     * @return Module window.
     */
    public ModuleWindow getModule(String state) {
        if (state.equals("recw")) {
            return recWindow;
        } else if (state.equals("appw")) {
            return appWindow;
        } else if (state.equals("payw")) {
            return payWindow;
        } else if (state.equals("repw")){
            return repWindow;
        } else {
            return null;
        }
    }

    /**
     * This method allows the program to load the module return by the getModule
     * method.
     *
     * @param c Object representation of ModuleWindow.
     * @param state A String representation.
     * @see getModule
     */
    public void loadModule(ModuleWindow c, String state) {
        this.state = state;
        modulePanel.add(c, "grow");
        SwingUtilities.updateComponentTreeUI(modulePanel);
    }

    /**
     * This method allows a JComponent to be added into the side panel.
     *
     * @param c Object representation of JComponent.
     */
    public void addToSidePanel(JComponent c) {
        sidePanel.add(c, "grow");
    }

    /**
     * <h1>QuickBarHandler</h1>
     * The {@code QuickBarHandler} class handles quick bar from the JFrame.
     */
    public class QuickBarHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == qAddPatientBut) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        JDialog ctb = new JDialog();
                        ctb.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        PersonalInformation pi = new PersonalInformation(ctb);
                        System.out.println(pi.isVisible());
                        ctb.setSize(pi.getPreferredSize());
                        ctb.add(pi);
                        ctb.pack();
                        ctb.setVisible(true);
                        ctb.setForeground(Color.white);
                        ctb.setBackground(Color.white);
                        ctb.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    }
                }
                );
            }

            if (e.getSource() == qSetAppointmentBut) {
                java.awt.EventQueue.invokeLater(new Runnable(){
                    public void run(){
                        JDialog sA = new JDialog();
                        SetAppointment  nA = new SetAppointment();
                        sA.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        sA.setSize(nA.getPreferredSize());
                        sA.add(nA);
                        sA.pack();
                        sA.setVisible(true);
                        sA.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    }
                });
            }
        }

    }

    /**
     * <h1>NavigationHandler</h1>
     * The {@code NavigationHandler} class manages the navigation of the module
     * panel.
     */
    public class NavigationHandler implements ActionListener {

        /**
         * This method handles the action performed by the user within the
         * module panel.
         *
         * @param e Object representation of ActionEvent.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == homeBut) {
                if (!state.equals("cover")) {
                    state = "cover";
                    modulePanel.removeAll();
                    modulePanel.add(c, "grow");
                    SwingUtilities.updateComponentTreeUI(modulePanel);
                }
            }
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
            if (e.getSource() == repgenBut) {
                if (!state.equals("repw")) {
                    modulePanel.removeAll();
                    loadModule(getModule("repw"), "repw");
        }
    }
        }
    }

    /**
     * <h1>MenuBarHandler</h1>
     * The {@code MenuBarHandler} class manages the menu bar of the program.
     */
    public class MenuBarHandler implements ActionListener {

        /**
         * The method handles the action performed by the user within the menu
         * bar.
         *
         * @param e Object representation of ActionEvent.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().matches("Full Screen")) {
                toggleFullScreen();
            }
        }
    }

}
//end of MainScreen
