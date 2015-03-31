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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import toothbytes.ui.toolbars.TBMenuBar;


public class UIScaffold extends JFrame {
    private JPanel mainPanel, sidePanel, moduleMenu, statusBar, modulePanel;
    private JButton mainButton1, mainButton2, mainButton3;
    private MigLayout framework;
    private Dimension defaultSize;
    private boolean fullScreen;
    private ButtonGroup navButtons;
    private JButton appBut, recBut, payBut,testButton;
    private TBMenuBar menuBar;
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fullScreen = false;
        
        //menu bar
        menuBar = new TBMenuBar();
        this.setJMenuBar(menuBar);
        menuBar.bindListenerToMenu(new MenuBarHandler(), 1);
        //layout configurations
        framework = new MigLayout(
                "filly, wrap 12",   //layout constraints
                "[fill]push[fill]push[fill]push[fill]push"
                        + "[fill]push[fill]push[fill]push[fill]push"
                        + "[fill][fill]push[fill]push[fill]", //12 columns
                "[fill, 25][fill]push[fill][fill, 25]");//rows
        
        //mainpanel configurations
        mainPanel = new JPanel();
        mainPanel.setLayout(framework);
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setContentPane(mainPanel);
        //moduleMenu
        moduleMenu = new JPanel();
        moduleMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
            //buttons
        navButtons = new ButtonGroup();
        
        recBut = new JButton("Records");
        //recBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/PatientRecords.png"));
        navButtons.add(recBut);
        
        appBut = new JButton("Appointments");
        //appBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/Appointments.png"));
        navButtons.add(appBut);
        
        payBut = new JButton("Payments");
        //payBut.setIcon(new ImageIcon("src/toothbytes/res/icons/btn/Finances.png"));
        navButtons.add(payBut);        
        
        moduleMenu.add(recBut);
        moduleMenu.add(appBut);
        moduleMenu.add(payBut);
        
        NavigationHandler nh = new NavigationHandler();
        recBut.addActionListener(nh);
        appBut.addActionListener(nh);
        payBut.addActionListener(nh);
        
        mainPanel.add(moduleMenu, "span 12 1");
        
        //module window(left)
        modulePanel = new JPanel();
        //modulePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        modulePanel.setLayout(new MigLayout("fill"));
        mainPanel.add(modulePanel, "span 10 2");
        modulePanel.add(new Cover(), "grow");
        
        //side panel(right)
        sidePanel = new JPanel();
        //sidePanel.setBackground(Color.yellow);
        sidePanel.setLayout(new MigLayout("fill"));
        mainPanel.add(sidePanel, "span 2 2");
        //status Bar(bottom)
        statusBar = new JPanel();
//        statusBar.setBackground(Color.white);
        mainPanel.add(statusBar, "span 12 1");
        //test here
//        JTextField field = new JTextField();
//        field.setColumns(8);
//        statusBar.add(field);
//        testButton = new JButton("test");
//        testButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                testButtonActionPerformed(evt);
//            }
//        });
//        statusBar.add(testButton);
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
    
    /**
    *This method takes the screen size of the computer for the computation of the frame size.
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
    public class NavigationHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(e.getActionCommand());
            switch(e.getActionCommand()) {
                case "Records" :
                    if(!state.equals("recw")){
                        modulePanel.removeAll();
                        loadModule(getModule("recw"), "recw");
                    }
                    break;
                case "Appointments" :
                    if(!state.equals("appw")){
                        modulePanel.removeAll();
                        loadModule(getModule("appw"), "appw");
                    }
                    break;
                case "Payments" :
                    if(!state.equals("payw")){
                        modulePanel.removeAll();
                        loadModule(getModule("payw"), "payw");
                    }
                    break;
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