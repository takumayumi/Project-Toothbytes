/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <h1>Cover</h1>
 * The {@code Cover} manages the opening screen of the program Toothbytes.
 */
public class Cover extends JPanel{
    
    /**
     * This constructor layouts the opening screen of the program.
     */
    public Cover() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        JLabel logo = new JLabel(new ImageIcon("res/icons/main_logo.png"));
        this.add(logo, BorderLayout.CENTER);
    }
    
}
