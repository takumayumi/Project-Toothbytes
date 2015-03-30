/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import toothbytes.ui.PersonalInformation;

/**
 *
 * @author Jolas
 */
public class TBMenuBar extends JMenuBar{
    JMenu viewMenu, fileMenu, settingsMenu, helpMenu;
    JMenuItem addPatientFileItem;
    JCheckBoxMenuItem fullScreenViewItem;
    ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
    
    public TBMenuBar() {
        //File Menu
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Add patient, Set Appointment, exit program, etc.");
        this.add(fileMenu);
        
        addPatientFileItem = new JMenuItem("New Patient", new ImageIcon("images/middle.gif"));
        addPatientFileItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK));
        addPatientFileItem.addActionListener(new java.awt.event.ActionListener(){
            
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
                    }
                );
            }
        });
        
        fileMenu.add(addPatientFileItem);
        //View Menu
        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        viewMenu.getAccessibleContext().setAccessibleDescription(
                "Change Toothbyte's display mode");
        this.add(viewMenu);
            //items in View Menu
        fullScreenViewItem = new JCheckBoxMenuItem("Full Screen");
        fullScreenViewItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, ActionEvent.ALT_MASK));
        viewMenu.add(fullScreenViewItem);
        //Settings menu
        settingsMenu = new JMenu("Settings");
        this.add(settingsMenu);
        //Help menu
        helpMenu = new JMenu("Help");
        this.add(helpMenu);
        //add items to array
        menuItems.add(addPatientFileItem); //0
        menuItems.add(fullScreenViewItem); //1
    }
    
    public void bindListenerToMenu(ActionListener al, int menuIndex) {
        menuItems.get(menuIndex).addActionListener(al);
    }
    public void setAllFont(Font f){
        viewMenu.setFont(f);
        fileMenu.setFont(f);
        helpMenu.setFont(f);
        settingsMenu.setFont(f);
        for(JMenuItem i : menuItems) {
            i.setFont(f);
        }
    }
}
