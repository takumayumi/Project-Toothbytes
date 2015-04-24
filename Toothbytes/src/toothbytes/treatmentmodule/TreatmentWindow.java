/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.treatmentmodule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jolas
 */
public class TreatmentWindow extends JFrame {

    private JPanel p, chart, conditionPane, treatmentPane;
    private JScrollPane chartHolder;
    private JToolBar bar;
    private ToothComponent[] upper, lower;
    private Image tooth, missing, unerupted;
    private int action;
    private JToggleButton normalB, missingB, uneruptedB, decayedB;
    private ButtonGroup conditionGroup;

    public TreatmentWindow() {

        action = 1;

        //load images
        try {
            tooth = ImageIO.read(getClass().getResource("tooth.png"));
            missing = ImageIO.read(getClass().getResource("missing.png"));
            unerupted = ImageIO.read(getClass().getResource("unerupted.png"));
        } catch (IOException ex) {
            Logger.getLogger(Tooth.class.getName()).log(Level.SEVERE, null, ex);
        }

        //frame config
        this.setTitle("Treatment");
        this.setSize(1280, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        p = new JPanel(new MigLayout(
                "wrap 8",
                "[fill]push[fill]push[fill]push[fill]push[fill]push[fill]push[fill][fill]",
                "[fill]push[fill]push[fill]push[fill]"
        ));

        //chart config
        chart = new JPanel();
        chart.setLayout(new GridLayout(2, 16, 5, 10));
        chart.setBackground(Color.WHITE);

        chartHolder = new JScrollPane(chart);
        chartHolder.setBorder(new TitledBorder("Dental Chart"));

        setupChart();
        p.add(chartHolder, "span 7");

        //buttons
        conditionGroup = new ButtonGroup();
        JPanel conditionBtns = new JPanel(new GridLayout(7, 1));
        conditionBtns.setBorder(new TitledBorder("Tooth Conditions"));

        normalB = makeMeButtons("src/toothbytes/treatmentmodule/tooth.png", "Normal", 0);
        decayedB = makeMeButtons("src/toothbytes/treatmentmodule/decayed.png", "Decayed", 1);
        uneruptedB = makeMeButtons("src/toothbytes/treatmentmodule/unerupted.png", "Unerupted", 5);
        missingB = makeMeButtons("src/toothbytes/treatmentmodule/missing.png", "Missing", 6);
        
        conditionGroup.add(normalB);
        conditionGroup.add(decayedB);
        conditionGroup.add(uneruptedB);
        conditionGroup.add(missingB);
        
        conditionBtns.add(normalB);
        conditionBtns.add(decayedB);
        conditionBtns.add(missingB);
        conditionBtns.add(uneruptedB);
        p.add(conditionBtns, "span 1 2");

        this.setContentPane(p);
    }

    public void setupChart() {
        lower = new ToothComponent[16];
        upper = new ToothComponent[16];

        for (int i = 0; i < upper.length; i++) {
            upper[i] = new ToothComponent(i + 1, ToothComponent.NORMAL);
            chart.add(upper[i]);
        }

        for (int i = 15; i >= 0; i--) {
            lower[i] = new ToothComponent(i + 17, ToothComponent.NORMAL);
            chart.add(lower[i]);
        }
    }

    public void init() {
        this.setVisible(true);

    }

    public JToggleButton makeMeButtons(String iconFile, String label, final int actionNum) {
        JToggleButton theBut = new JToggleButton(label);
        Icon butIcon = new ImageIcon(iconFile);
        theBut.setIcon(butIcon);

        // Make the proper actionPerformed method execute when the
        // specific button is pressed
        theBut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                action = actionNum;

            }
        });

        return theBut;
    }

    public class ToothComponent extends JComponent {

        Graphics2D graphicSettings;
        ArrayList<Shape> shapes = new ArrayList<>();
        int state, x, y;

        int number;
        public static final int NORMAL = 0;
        public static final int DECAYED = 1;
        public static final int IMPAIRED = 2;
        public static final int ROOT_FRACTURED = 3;
        public static final int SUPERNUMERARY = 4;
        public static final int UNERUPTED = 5;
        public static final int MISSING = 6;
        public static final int CARRIES = 7;

        public ToothComponent(int number, int state) {
            this.setPreferredSize(new Dimension(50, 65));
            x = -10;
            y = -10;
            this.number = number;

            changeState(state);

            this.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    changeState(action);
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    ToothComponent temp = (ToothComponent) e.getComponent();
                    temp.setBorder(new LineBorder(Color.orange, 3));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    ToothComponent temp = (ToothComponent) e.getComponent();
                    temp.setBorder(null);
                }

            });

            this.addMouseMotionListener(new MouseMotionAdapter() {

                public void mouseDragged(MouseEvent e) {

                    // If this is a brush have shapes go on the screen quickly
                    if (action == 1) {

                        changeState(1);

                        x = e.getX();
                        y = e.getY();

                        shapes.add(drawBrush(x, y, 4, 4));
                        repaint();
                    }
                }
            });
        }

        private Ellipse2D.Float drawBrush(
                int x1, int y1, int brushStrokeWidth, int brushStrokeHeight) {
            return new Ellipse2D.Float(
                    x1, y1, brushStrokeWidth, brushStrokeHeight);
        }

        public void changeState(int s) {
            this.state = s;

            switch (state) {
                case 0:
                    this.setToolTipText("Normal");
                    break;
                case 1:
                    this.setToolTipText("Decayed");
                    break;
                case 2:
                    this.setToolTipText("Impacted");
                    break;
                case 3:
                    this.setToolTipText("Root Fragment");
                    break;
                case 4:
                    this.setToolTipText("Supernumerary");
                    break;
                case 5:
                    this.setToolTipText("Unerupted");
                    break;
                case 6:
                    this.setToolTipText("Missing");
                    break;
                case 7:
                    this.setToolTipText("Missing Caries");
                    break;
            }

            repaint();
        }

//        @Override
//        protected void paintComponent(Graphics g) {
//
//            super.paintComponent(g);
//
//            graphicSettings = (Graphics2D) g;
//
//            if (state == 1 && !shapes.isEmpty()) {
//                //graphicSettings.drawImage(tooth, 9, 15, null);
//                graphicSettings.setPaint(Color.BLACK);
//
//                for (Shape s : shapes) {
//                    graphicSettings.draw(s);
//                    graphicSettings.fill(s);
//                    System.out.println(s.getBounds());
//                }
//
//            }
//
//        }

        public void paint(Graphics g) {
            graphicSettings = (Graphics2D) g;

            graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            graphicSettings.drawString(number + "", 29, 13);

            if (state == 0) {
                graphicSettings.drawImage(tooth, 9, 15, null);

            }
            
            if (state == 1) {
                graphicSettings.drawImage(tooth, 9, 15, null);
                graphicSettings.setPaint(Color.BLACK);
                if(!shapes.isEmpty()){
                    
                    for (Shape s : shapes) {
                        graphicSettings.draw(s);
                        graphicSettings.fill(s);
                        System.out.println(s.getBounds());
                    }
                }

            }

            if (state == 5) {
                graphicSettings.drawImage(unerupted, 9, 15, null);

            }
            if (state == 6) {
                graphicSettings.drawImage(missing, 9, 15, null);

            }

        }
    }
}
