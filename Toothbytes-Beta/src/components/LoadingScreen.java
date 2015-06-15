/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Jolas
 */
public class LoadingScreen extends JComponent {

    JLabel loading;
    JLabel text;
    
    private ImageIcon icon = new ImageIcon("res/icons/loading.gif");
    public LoadingScreen(String title) {
        this.setLayout(new BorderLayout());
        loading = new JLabel(title, icon, JLabel.CENTER);
        loading.setForeground(Color.WHITE);
        loading.setFont(new Font("Calibri", Font.PLAIN, 18));
        
        this.add(loading, BorderLayout.CENTER);
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponents(g);
    }
}
