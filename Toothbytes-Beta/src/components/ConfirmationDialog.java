/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import net.miginfocom.swing.MigLayout;
import utilities.DataMan;

/**
 *
 * @author Jolas
 */
public class ConfirmationDialog extends JDialog{
    private JPanel panel, buttons;
    private JLabel logo, info;
    private JPasswordField pwd;
    private JButton yes, save,no;
    private boolean granted;

    /**
     * This constructor creates the login window and layouts it's components.
     */
    public ConfirmationDialog(JDialog d) {
        super(d);
        this.setTitle("Exit");
        this.setSize(250, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        panel = new JPanel(new MigLayout("fill"));
        this.setContentPane(panel);
        
        buttons = new JPanel();

        logo = new JLabel(DataMan.ResizeImage("res\\buttons\\Exit.png", 50, 50));
        info = new JLabel("Discard treatment and exit?");

        panel.add(logo, "gapx 15 0");
        panel.add(info, "gapx 0 15");
        panel.add(buttons, "south");

        yes = new JButton("Yes");
        save = new JButton("Save");
        no = new JButton("Cancel");
        buttons.add(yes);
        buttons.add(save);
        buttons.add(no);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                d.setGlassPane(new JComponent() {
                    public void paintComponent(Graphics g) {
                        g.setColor(new Color(0, 0, 0, 200));
                        g.fillRect(0, 0, getWidth(), getHeight());
                        super.paintComponents(g);
                    }
                });
                d.getGlassPane().setVisible(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                d.getGlassPane().setVisible(false);
            }
            
        });

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accepted();
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
    }
    
    public void accepted() {
        this.dispose();
        JDialog d = (JDialog) super.getOwner();
        d.dispose();
    }
    
    public void addSaveListener(ActionListener saveHandler) {
        save.addActionListener(saveHandler);
    }
}
