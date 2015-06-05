/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
 */
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import net.miginfocom.swing.MigLayout;
import utilities.DBAccess;

/**
 * <h1>LoginDialog</h1>
 * The {@code LoginDialog} class implements a program that will show a Login
 * Window for authentication and authorization purposes of the program
 * Toothbytes.
 */
public class ExitDialog extends JDialog {

    private JPanel panel, buttons;
    private JLabel logo, info;
    private JPasswordField pwd;
    private JButton yes, no;
    private boolean granted;

    /**
     * This constructor creates the login window and layouts it's components.
     */
    public ExitDialog(JFrame f) {
        super(f);
        this.setTitle("Closing Time");
        this.setSize(250, 150);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        buttons = new JPanel();
        this.getContentPane().add(buttons, BorderLayout.SOUTH);

        panel = new JPanel(new MigLayout("fillx, wrap 6", "[][fill]push[fill]push[fill]push[fill]push[fill]", "[]10px[]"));

        this.getContentPane().add(panel, BorderLayout.CENTER);

        logo = new JLabel(new ImageIcon("res\\buttons\\Cancel.png"));
        info = new JLabel("Are you sure you want to exit?");

        panel.add(logo, "span 1");
        panel.add(info, "span 5");

        yes = new JButton("Yes");
        no = new JButton("No");
        buttons.add(yes);
        buttons.add(no);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                f.setGlassPane(new JComponent() {
                    public void paintComponent(Graphics g) {
                        g.setColor(new Color(0, 0, 0, 200));
                        g.fillRect(0, 0, getWidth(), getHeight());
                        super.paintComponents(g);
                    }
                });
                f.getGlassPane().setVisible(true);
            }
        });

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Closing database");
                DBAccess.closeDB();
                System.out.println("bye!");
                System.exit(0);
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelled();
            }
        });
    }

    public void cancelled() {
        this.dispose();
        JFrame f = (JFrame) super.getOwner();
        f.getGlassPane().setVisible(false);
    }

}
