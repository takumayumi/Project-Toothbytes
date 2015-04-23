/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui.components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jolas
 */
public class ToothModel extends JPanel {

    Graphics2D graphSettings;
    int xValue, yValue;
    JLabel tooth;

    public ToothModel() {

        this.setLayout(new BorderLayout());

        //tooth = new JLabel(new ImageIcon("src/toothbytes/res/icons/tooth/bluetooth.png"));
        this.addMouseMotionListener(
            new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent event) {
                    xValue = event.getX();
                    yValue = event.getY();
                    repaint();
                }
            }
        );
    }

    public void paint(Graphics g) {

        g.fillOval(xValue, yValue, 6, 6);
        // Class used to define the shapes to be drawn

        graphSettings = (Graphics2D) g;

                        // Antialiasing cleans up the jagged lines and defines rendering rules
        graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

                        // Defines the line width of the stroke
        graphSettings.setStroke(new BasicStroke(4));
        
    }

    private Ellipse2D.Float drawBrush(
            int x1, int y1, int brushStrokeWidth, int brushStrokeHeight) {

        return new Ellipse2D.Float(
                x1, y1, brushStrokeWidth, brushStrokeHeight);

    }
}
