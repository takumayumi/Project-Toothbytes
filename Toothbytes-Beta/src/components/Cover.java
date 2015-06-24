/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
        
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm a");
        
        Calendar cal = Calendar.getInstance();
        JLabel test = new JLabel("Today is "  +dateFormat.format(cal.getTime()));
        JLabel time = new JLabel(timeFormat.format(cal.getTime()));
        
        test.setFont(new Font("Arial", Font.PLAIN, 50));
        test.setHorizontalAlignment(SwingConstants.CENTER);
        test.setForeground(Color.DARK_GRAY);
       
        time.setFont(new Font("Arial", Font.PLAIN, 50));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setForeground(Color.DARK_GRAY);
        
        this.add(time, BorderLayout.SOUTH);
        this.add(test, BorderLayout.NORTH);
        this.add(logo, BorderLayout.CENTER);
    }
    
}
