/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import components.listener.ToothListener;
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
    private String state, preState, secondState, otherTreatment;
    private Graphics2D g2d;
    private Area markings = new Area();
    private ToothListener tl;
    private boolean modified;

    private final Image NORMAL_STATE, UNERUPTED_STATE, MISSING_STATE,
            LASER_STATE, CROWN_STATE, BRIDGE_STATE, MISSING_BRIDGED_STATE,
            EXTRACTED_STATE, DEFAULT_STATE;

    private int pointerX = -10;
    private int pointerY = -10;

    private JPopupMenu pop;
    private JMenuItem popNormal, popUnerupted, popMissing;

    public Tooth(int number, String state, boolean modified) throws IOException {
        this.number = number;
        this.state = state;
        this.secondState = null;
        this.setPreferredSize(new Dimension(55, 70));
        this.preState = null;
        this.setToolTipText(state);
        this.modified = modified;
        this.otherTreatment = null;

        NORMAL_STATE = ImageIO.read(new File("res/teeth/normal.png"));
        UNERUPTED_STATE = ImageIO.read(new File("res/teeth/unerupted.png"));
        MISSING_STATE = ImageIO.read(new File("res/teeth/missing.png"));
        LASER_STATE = ImageIO.read(new File("res/teeth/laser.png"));
        CROWN_STATE = ImageIO.read(new File("res/teeth/crown.png"));
        MISSING_BRIDGED_STATE = ImageIO.read(new File("res/teeth/bridged.png"));
        BRIDGE_STATE = ImageIO.read(new File("res/teeth/bridge.png"));
        DEFAULT_STATE = ImageIO.read(new File("res/teeth/bridged.png"));
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
        this.secondState = null;
        this.otherTreatment = null;
        this.setToolTipText(state);
        this.modified = false;

        NORMAL_STATE = ImageIO.read(new File("res/teeth/normal.png"));
        UNERUPTED_STATE = ImageIO.read(new File("res/teeth/unerupted.png"));
        MISSING_STATE = ImageIO.read(new File("res/teeth/missing.png"));
        LASER_STATE = ImageIO.read(new File("res/teeth/laser.png"));
        CROWN_STATE = ImageIO.read(new File("res/teeth/crown.png"));
        MISSING_BRIDGED_STATE = ImageIO.read(new File("res/teeth/bridged.png"));
        BRIDGE_STATE = ImageIO.read(new File("res/teeth/bridge.png"));
        DEFAULT_STATE = ImageIO.read(new File("res/teeth/bridged.png"));
        EXTRACTED_STATE = ImageIO.read(new File("res/teeth/extract.png"));

        MouseHandler mh = new MouseHandler();
        MouseMotionHandler mmh = new MouseMotionHandler();
        this.addMouseListener(mh);
        this.addMouseMotionListener(mmh);

        pop = new JPopupMenu("Tooth Popup");
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void addToothListener(ToothListener tl) {
        this.tl = tl;
    }

    public void notifyListener() {
        if (this.secondState == null) {
            tl.notify(number, state);
        } else {
            tl.notify(number, secondState);
        }

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
    }

    public String getPreState() {
        return preState;
    }

    public String getSecondState() {
        return secondState;
    }

    public void setSecondState(String secondState) {
        this.secondState = secondState;
    }

    public void setPreState(String preState) {
        this.preState = preState;
    }

    //other methods
    public void updateState() {
        if (this.preState != null) {
            if (!this.preState.matches(state)) {

                markings.reset();

                if (this.preState.matches("marker") || this.preState.matches("fill")) {
//                    this.otherTreatment = this.getSecondState();
                    this.setToolTipText(this.getSecondState());
//                    this.setState(this.getPreState());
                } else {
                    this.secondState = null;
                    this.setToolTipText(state);
                }

                this.setState(this.getPreState());

                this.setModified(true);
                if (tl != null) {
                    notifyListener();
                }

                this.repaint();
            } else if (secondState != null) {
                if (!preState.matches(secondState)) {
                    this.setToolTipText(this.getSecondState());
                    this.setState(this.getPreState());

                    this.setModified(true);
                    if (tl != null) {
                        notifyListener();
                    }
                    this.repaint();
                }
            }

            if (preState.matches("decayed") || preState.matches("filling") || preState.matches("marker")) {
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
            case "laser bleaching":
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
                g2d.setPaint(new Color(0, 200, 255));
                g2d.setComposite(AlphaComposite.getInstance(type, 0.5f));
                this.setForeground(new Color(0, 200, 255));

                g2d.draw(markings);
                g2d.fill(markings);
                break;
            case "fill":
                g2d.drawImage(DEFAULT_STATE, X_TOOTH, Y_TOOTH, null);
                if (this.secondState.length() > 9 && this.secondState.length() <= 11) {
                    g2d.drawString(this.secondState.substring(0, 4), X_TOOTH + 7, Y_TOOTH + 20);
                    g2d.drawString(this.secondState.substring(4, this.secondState.length()), X_TOOTH + 5, Y_TOOTH + 30);
                } else if (this.secondState.length() > 11) {
                    g2d.drawString(this.secondState.substring(0, 4), X_TOOTH + 7, Y_TOOTH + 20);
                    g2d.drawString(this.secondState.substring(5, 6) + "...", X_TOOTH + 5, Y_TOOTH + 30);
                } else {
                    g2d.drawString(this.secondState, X_TOOTH + 2, Y_TOOTH + 30);
                }
//                g2d.drawString(this.state.substring(0, 4) + "...", X_TOOTH + 5, Y_TOOTH + 30);
                this.setForeground(new Color(55, 100, 200));
                break;
            case "marker":
                g2d.drawImage(NORMAL_STATE, X_TOOTH, Y_TOOTH, null);
                g2d.setPaint(new Color(240, 100, 100));
                g2d.setComposite(AlphaComposite.getInstance(type, 0.5f));
                this.setForeground(new Color(240, 100, 100));

                g2d.draw(markings);
                g2d.fill(markings);
                break;
            default:
                g2d.drawImage(DEFAULT_STATE, X_TOOTH, Y_TOOTH, null);
                if (this.state.length() > 5 && this.state.length() <= 11) {
                    g2d.drawString(this.state.substring(0, 4), X_TOOTH + 7, Y_TOOTH + 20);
                    g2d.drawString(this.state.substring(5, this.state.length()), X_TOOTH + 5, Y_TOOTH + 30);
                } else if (this.state.length() > 11) {
                    g2d.drawString(this.state.substring(0, 4), X_TOOTH + 7, Y_TOOTH + 20);
                    g2d.drawString(this.state.substring(5, 6) + "...", X_TOOTH + 5, Y_TOOTH + 30);
                } else {
                    g2d.drawString(this.state, X_TOOTH+ 5, Y_TOOTH + 30);
                }
//                g2d.drawString(this.state.substring(0, 4) + "...", X_TOOTH + 5, Y_TOOTH + 30);
                this.setForeground(new Color(55, 100, 200));
                break;
        }

    }
}
