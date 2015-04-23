package toothbytes.ui.toolbars;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import toothbytes.ui.About;
import toothbytes.ui.HelpContents;
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
                        JFrame ctb = new JFrame();
                        PersonalInformation pi = new PersonalInformation(ctb);
                        System.out.println(pi.isVisible());
                        ctb.setSize(pi.getPreferredSize());
                        ctb.add(pi);
                        ctb.pack();
                        ctb.setVisible(true);
                        ctb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        helpContents.addActionListener(this);
        aboutContents.addActionListener(this);
    }

    public void bindListenerToMenu(ActionListener al, int menuIndex) {
        menuItems.get(menuIndex).addActionListener(al);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == helpContents) {
            HelpContents hc = new HelpContents();
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
}
