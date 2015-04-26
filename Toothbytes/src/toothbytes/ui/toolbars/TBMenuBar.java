package toothbytes.ui.toolbars;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import toothbytes.ui.About;
import toothbytes.ui.forms.PersonalInformation;

public class TBMenuBar extends JMenuBar implements ActionListener {

    JMenu viewMenu, fileMenu, settingsMenu, helpMenu;
    JMenuItem addPatientFileItem, helpContents, aboutContents;
    JCheckBoxMenuItem fullScreenViewItem;
    ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();

    public TBMenuBar() {
        //File Menu
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Add patient, Set Appointment, exit program, etc.");
        
        this.add(fileMenu);

        addPatientFileItem = new JMenuItem("New Patient", new ImageIcon(
                "src/toothbytes/res/icons/btn/AddNewPatient_s.png"));
        
        addPatientFileItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK));
        
        addPatientFileItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {

                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        JDialog ctb = new JDialog();
                        PersonalInformation pi = new PersonalInformation(ctb);
                        ctb.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        System.out.println(pi.isVisible());
                        ctb.setSize(pi.getPreferredSize());
                        ctb.add(pi);
                        ctb.pack();
                        ctb.setVisible(true);
                        ctb.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    }
                });
            }
        });

        fileMenu.add(addPatientFileItem);
        
        // View Menu
        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        viewMenu.getAccessibleContext().setAccessibleDescription(
                "Change Toothbyte's display mode");
        this.add(viewMenu);
        
        // Items in View Menu
        fullScreenViewItem = new JCheckBoxMenuItem("Full Screen");
        fullScreenViewItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, ActionEvent.ALT_MASK));
        viewMenu.add(fullScreenViewItem);
        
        // Settings menu
        settingsMenu = new JMenu("Settings");
        this.add(settingsMenu);
        
        // Help menu
        helpMenu = new JMenu("Help");
        this.add(helpMenu);
        helpMenu.add(helpContents = new JMenuItem("Help Contents"));
        helpContents.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        helpMenu.add(aboutContents = new JMenuItem("About"));
        aboutContents.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));

        // Add items to array
        menuItems.add(addPatientFileItem); // 0 
        menuItems.add(fullScreenViewItem); // 1
        menuItems.add(helpContents);
        menuItems.add(aboutContents);

        helpContents.addActionListener(this);
        aboutContents.addActionListener(this);
    }

    public void bindListenerToMenu(ActionListener al, int menuIndex) {
        menuItems.get(menuIndex).addActionListener(al);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == helpContents) {
            help();
        } else if (e.getSource() == aboutContents) {
            About ab = new About();
        }
    }

    public void setAllFont(Font f) {
        viewMenu.setFont(f);
        fileMenu.setFont(f);
        helpMenu.setFont(f);
        settingsMenu.setFont(f);
        for (JMenuItem i : menuItems) {
            i.setFont(f);
        }
    }
    
    public void help() {
        String fileName = "src\\toothbytes\\help\\Toothbytes.chm";
        String[] commands = {"cmd", "/c", fileName};
        try {
            Runtime.getRuntime().exec(commands);
            //Runtime.getRuntime().exec("C:\\Users\\Riyasam\\Documents\\NetBeansProjects\\SwingTest\\src\\Test\\RealWorld.chm");
        } catch (IOException ex) {
            Logger.getLogger(TBMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String s;
//        Process p;
//        try {
//            p = Runtime.getRuntime().exec("src\\toothbytes\\help\\Toothbytes.chm");
//            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            p.waitFor();
//            System.out.println("exit: " + p.exitValue());
//            p.destroy();
//        } catch (IOException | InterruptedException e) {
//            Logger.getLogger(TBMenuBar.class.getName()).log(Level.SEVERE, null, e);
//        }
    }
    
}
