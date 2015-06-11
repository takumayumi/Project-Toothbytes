/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jolas
 */
public class ChartPanel extends JPanel {

    private MigLayout layout = new MigLayout("wrap 16",
                "[55px][55px][55px][55px][55px][55px][55px][55px]15px[55px][55px][55px][55px][55px][55px][55px][55px]",
                "[][]15px[][]");;
    private Graphics2D g2d;
    
    
    public ChartPanel() {
        this.setLayout(layout);
        this.setSize(970, 300);
        this.setBackground(Color.WHITE);
        this.setBorder(new EtchedBorder());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
        g2d.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
    }

}
