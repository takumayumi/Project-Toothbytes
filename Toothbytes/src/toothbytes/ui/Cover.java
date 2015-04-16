/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jolas
 */
public class Cover extends JPanel{
    
    public Cover() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        JLabel logo = new JLabel(new ImageIcon("src/toothbytes/res/icons/main_logo.png"));
        this.add(logo, BorderLayout.CENTER);
    }
    
}
