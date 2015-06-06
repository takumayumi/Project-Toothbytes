/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;

/**
 *
 * @author Jolas
 */
public class Tooth extends JComponent {

    private int number;
    private String state;
    private String preState;
    private Graphics2D g2d;
    private Area markings = new Area();

    private final Image NORMAL_STATE, UNERUPTED_STATE, MISSING_STATE,
            LASER_STATE, CROWN_STATE, BRIDGE_STATE, MISSING_BRIDGED_STATE,
            EXTRACTED_STATE;

    private int pointerX = -10;
    private int pointerY = -10;

    private JPopupMenu pop;
    private JMenuItem popNormal, popUnerupted, popMissing;

    public Tooth(int number, String state) throws IOException {
        this.number = number;
        this.state = state;
        this.setPreferredSize(new Dimension(55, 70));
        this.preState = null;
        this.setToolTipText(state);

        NORMAL_STATE = ImageIO.read(new File("res/teeth/normal.png"));
        UNERUPTED_STATE = ImageIO.read(new File("res/teeth/unerupted.png"));
        MISSING_STATE = ImageIO.read(new File("res/teeth/missing.png"));
        LASER_STATE = ImageIO.read(new File("res/teeth/laser.png"));
        CROWN_STATE = ImageIO.read(new File("res/teeth/crown.png"));
        MISSING_BRIDGED_STATE = ImageIO.read(new File("res/teeth/bridged.png"));
        BRIDGE_STATE = ImageIO.read(new File("res/teeth/bridge.png"));
        EXTRACTED_STATE = ImageIO.read(new File("res/teeth/extract.png"));

        MouseHandler mh = new MouseHandler();
        MouseMotionHandler mmh = new MouseMotionHandler();
        this.addMouseListener(mh);
        this.addMouseMotionListener(mmh);

        pop = new JPopupMenu("Tooth Popup");
    }

    public Tooth() throws IOException {
        this.number = 1;
        this.state = "normal";
        this.setPreferredSize(new Dimension(55, 70));
        this.preState = null;
        this.setToolTipText(state);

        NORMAL_STATE = ImageIO.read(new File("res/teeth/normal.png"));
        UNERUPTED_STATE = ImageIO.read(new File("res/teeth/unerupted.png"));
        MISSING_STATE = ImageIO.read(new File("res/teeth/missing.png"));
        LASER_STATE = ImageIO.read(new File("res/teeth/laser.png"));
        CROWN_STATE = ImageIO.read(new File("res/teeth/crown.png"));
        MISSING_BRIDGED_STATE = ImageIO.read(new File("res/teeth/bridged.png"));
        BRIDGE_STATE = ImageIO.read(new File("res/teeth/bridge.png"));
        EXTRACTED_STATE = ImageIO.read(new File("res/teeth/extract.png"));

        MouseHandler mh = new MouseHandler();
        MouseMotionHandler mmh = new MouseMotionHandler();
        this.addMouseListener(mh);
        this.addMouseMotionListener(mmh);

        pop = new JPopupMenu("Tooth Popup");
    }

    // Accessor & Mutators
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.setToolTipText(state);
    }

    public String getPreState() {
        return preState;
    }

    public void setPreState(String preState) {
        this.preState = preState;
    }

    //other methods
    public void updateState() {
        if (this.preState != null) {
            if (!this.preState.matches(state)) {
                this.setState(this.getPreState());
                this.repaint();
            }

            if (preState.matches("decayed") || preState.matches("filling")) {
                markings.add(new Area(drawBrush(pointerX, pointerY, 4, 4)));
                repaint();
            }
        }
    }

    private Ellipse2D.Float drawBrush(
            int x1, int y1, int brushStrokeWidth, int brushStrokeHeight) {
        return new Ellipse2D.Float(
                x1, y1, brushStrokeWidth, brushStrokeHeight);
    }

    //Mouse Handling
    public void hover() {
        this.setBorder(new LineBorder(new Color(0, 51, 102)));
    }

    public void active() {
        this.setBorder(new LineBorder(new Color(0, 153, 204)));
    }

    public void reset() {
        this.setBorder(null);
    }

    public class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            pointerX = e.getX();
            pointerY = e.getY();
            updateState();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

    }

    public class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            active();
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            pointerX = e.getX();
            pointerY = e.getY();
            updateState();
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            hover();
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            reset();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setFont(new Font("Calibri", Font.BOLD, 16));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int type = AlphaComposite.SRC_OVER;
//        settings
        final int X_TOOTH = 3;
        final int Y_TOOTH = 13;

//        experimental
//        experimental
        if (number <= 9) {
            g2d.drawString(String.valueOf(number), this.getWidth() / 2 - 3, 12);
        } else {
            g2d.drawString(String.valueOf(number), this.getWidth() / 2 - 8, 12);
        }

        switch (state) {
            case "normal":
                g2d.drawImage(NORMAL_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(new Color(50, 200, 90));
                break;
            case "missing":
                g2d.drawImage(MISSING_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(Color.BLACK);
                break;
            case "unerupted":
                g2d.drawImage(UNERUPTED_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(Color.PINK);
                break;
            case "crown":
                g2d.drawImage(CROWN_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(Color.darkGray);
                break;
            case "laser":
                g2d.drawImage(LASER_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(Color.GRAY);
                break;
            case "extraction":
                g2d.drawImage(EXTRACTED_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(Color.MAGENTA);
                break;
            case "bridge":
                g2d.drawImage(BRIDGE_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(Color.ORANGE);
                break;
            case "missing_bridged":
                g2d.drawImage(MISSING_BRIDGED_STATE, X_TOOTH, Y_TOOTH, null);
                this.setForeground(Color.cyan);
                break;
            case "decayed":
                g2d.drawImage(NORMAL_STATE, X_TOOTH, Y_TOOTH, null);
                g2d.setPaint(new Color(10, 10, 10));
                //g2d.setComposite(AlphaComposite.getInstance(type, 0.5f));
                this.setForeground(new Color(204, 0, 0));

                g2d.draw(markings);
                g2d.fill(markings);
                break;
            case "filling":
                g2d.drawImage(NORMAL_STATE, X_TOOTH, Y_TOOTH, null);
                g2d.setPaint(new Color(0, 10, 60));
                g2d.setComposite(AlphaComposite.getInstance(type, 0.5f));
                this.setForeground(new Color(20, 30, 60));

                g2d.draw(markings);
                g2d.fill(markings);
                break;
        }

    }
}
