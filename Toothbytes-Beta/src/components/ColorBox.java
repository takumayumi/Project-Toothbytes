/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import utilities.Configuration;

/**
 *
 * @author Jolas
 */
public class ColorBox extends JPanel {

    private Color fill;
    JPanel box;

    public ColorBox(Component parent) {
        this.setLayout(null);
        
        fill = Color.RED;
        
        JLabel text = new JLabel("Color");
        text.setFont(Configuration.TB_FONT_HEADER);
        text.setBounds(5, 5, 40, 25);
        
        box = new JPanel();
        box.setSize(80, 80);
        box.setBounds(50, 5, 30, 30);
        
        box.setBackground(fill);
        box.setOpaque(true);
        box.setBorder(new EtchedBorder());
        box.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setFill(JColorChooser.showDialog(parent, "Choose tooth marker color", Color.RED));
            }
        });
        
        this.add(text);
        this.add(box);
    }

    public Color getFill() {
        return fill;
    }

    public void setFill(Color fill) {
        this.fill = fill;
        box.setBackground(fill);
    }

}
