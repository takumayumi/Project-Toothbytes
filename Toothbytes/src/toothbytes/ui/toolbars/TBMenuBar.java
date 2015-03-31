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
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import toothbytes.ui.HelpContents;
import toothbytes.ui.PersonalInformation;

/**
 *
 * @author Jolas
 */
public class TBMenuBar extends JMenuBar implements ActionListener{
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
                //helpMenu - jheraldinetbugtong, integrated on 03/29/15
        helpMenu = new JMenu("Help");
        this.add(helpMenu);
        helpMenu.add(helpContents = new JMenuItem("Help Contents"));
        helpContents.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        helpMenu.add(aboutContents = new JMenuItem("About"));
        aboutContents.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));

        //add items to array
        menuItems.add(addPatientFileItem); //0
        menuItems.add(fullScreenViewItem); //1
        
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
            JOptionPane.showMessageDialog(null, "©  Project Toothbytes 2014-2015\n\n Bringas | Bugtong | Burayag | Gonzales \n\n Fabian | Maglaya | Takuma | Tandoc", "About", JOptionPane.PLAIN_MESSAGE, new ImageIcon("i/diary.gif"));
        }
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
