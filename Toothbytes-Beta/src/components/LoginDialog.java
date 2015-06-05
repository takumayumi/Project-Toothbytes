/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utilities.DBAccess;

/**
 * <h1>LoginDialog</h1>
 * The {@code LoginDialog} class implements a program that will show a Login 
 * Window for authentication and authorization purposes of the program 
 * Toothbytes.
 */
public class LoginDialog extends JDialog {
    private JLabel logo, usrLabel, pwdLabel;
    private JPanel form, buttons;
    private JTextField usr;
    private JPasswordField pwd;
    private JButton login, exit;
    private boolean granted;
    
    /**
     * This constructor creates the login window and layouts it's components.
     */
    public LoginDialog(JFrame f) {
        super(f);
        this.setTitle("Toothbytes");
        this.setIconImage(new ImageIcon("src/toothbytes/favicon.png").getImage());
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);
        
        buttons = new JPanel(new GridLayout(1,2));
        this.getContentPane().add(buttons, BorderLayout.SOUTH);
        
        form = new JPanel(new MigLayout("fillx, wrap 6", "[][fill]push[fill]push[fill]push[fill]push[fill]", "[]10px[]"));
        form.setBackground(Color.DARK_GRAY);
        this.getContentPane().add(form, BorderLayout.CENTER);
        
        
        logo = new JLabel(new ImageIcon("res\\icons\\main_logo_w.png"));
        form.add(logo, "span 6");
        
        usrLabel = new JLabel("Username");
        usrLabel.setForeground(Color.white);
        pwdLabel = new JLabel("Password");
        pwdLabel.setForeground(Color.white);
        
        usr = new JTextField();
        pwd = new JPasswordField();
        
        
        form.add(usrLabel, "span 1");
        form.add(usr, "span 5");
        form.add(pwdLabel, "span 1");
        form.add(pwd, "span 5");
        
        login = new JButton("LOGIN");
        exit = new JButton("EXIT");
        buttons.add(login);
        buttons.add(exit);
        
        this.getRootPane().setDefaultButton(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = DBAccess.validate(usr.getText(), pwd.getPassword());
                
                if(isValid) {
                    unlock();
                } else {
                    lock();
                }
            }
        });
        
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Closing database");
                DBAccess.closeDB();
                System.out.println("bye!");
                System.exit(0);
            }
        });
        
        this.setUndecorated(true);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing database");
                DBAccess.closeDB();
                System.out.println("bye!");
                System.exit(0);
            }

            @Override
            public void windowActivated(WindowEvent e) {
                f.setGlassPane(new JComponent(){
                    public void paintComponent(Graphics g) {
                        g.setColor(new Color(0, 0, 0, 200));
                        g.fillRect(0, 0, getWidth(), getHeight());
                        super.paintComponents(g);
                    }
                });
                f.getGlassPane().setVisible(true);
            }
        });
    }
    
    public void unlock() {
        this.dispose();
        JFrame f = (JFrame)super.getOwner();
        f.getGlassPane().setVisible(false);
        System.out.println("Login Successful");
    }
    public void lock() {
        JOptionPane.showMessageDialog(rootPane, "Your username/password is incorrect", "Login Failed", JOptionPane.WARNING_MESSAGE);
    }
}
