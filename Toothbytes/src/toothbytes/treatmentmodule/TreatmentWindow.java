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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jolas
 */
public class TreatmentWindow extends JFrame {

    private JPanel p, chart, conditionPane, treatPane, treatmentPane;
    private JScrollPane chartHolder, tableHolder;
    private JToolBar bar;
    private JTable theTable;
    private ToothComponent[] upper, lower;
    private Image tooth, missing, unerupted, crown, extract;
    private int action;
    private JButton undo, finish;
    private JToggleButton normalB, missingB, uneruptedB, decayedB, amalB, jacketB, extractB;
    private ButtonGroup allButtons;
    private Stack<ToothComponent> history;
    private boolean loaded = false;
    int row = 0;
    int col = 0;
    DefaultTableModel model;

    public TreatmentWindow(String patient) {
        action = 0;
        this.setIconImage(new ImageIcon("src/toothbytes/favicon.png").getImage());
        //load images
        try {
            tooth = ImageIO.read(getClass().getResource("tooth.png"));
            missing = ImageIO.read(getClass().getResource("missing.png"));
            unerupted = ImageIO.read(getClass().getResource("unerupted.png"));
            crown = ImageIO.read(getClass().getResource("crown.png"));
            extract = ImageIO.read(getClass().getResource("extract.png"));
        } catch (IOException ex) {
            Logger.getLogger(Tooth.class.getName()).log(Level.SEVERE, null, ex);
        }

        //frame config
        this.setTitle("Treatment");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        p = new JPanel(new MigLayout(
                "wrap 8",
                "[fill]push[fill]push[fill]push[fill]push[fill]push[fill]push[fill][fill]",
                "[fill][fill][fill][fill]"
        ));

        history = new Stack<>();
        undo = new JButton(new ImageIcon("src\\toothbytes\\res\\icons\\btn\\Undo.png"));
        finish = new JButton(new ImageIcon("src\\toothbytes\\res\\icons\\btn\\Save.png"));
        finish.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save the table to database");
            }

        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!history.isEmpty()) {
                    int i = history.pop().getNumber();
                    if (i > 16) {
                        lower[i - 17].changeState(history.pop().state);
                        repaint();
                    } else {
                        upper[i - 1] = history.pop();
                        repaint();
                    }
                } else {
                    int i = history.peek().getNumber();
                    if (i > 16) {
                        history.pop();
                        lower[i - 17].changeState(0);
                        repaint();
                    } else {
                        history.pop();
                        upper[i - 17].changeState(0);
                        repaint();
                    }
                }
            }

        });
        bar = new JToolBar();
        bar.add(new JLabel("Patient: "+patient+"\t"));
        bar.add(undo);
        bar.add(finish);
        p.add(bar, "north");

        //chart config
        chart = new JPanel();
        chart.setLayout(new GridLayout(2, 16, 5, 10));
        chart.setBackground(Color.WHITE);

        chartHolder = new JScrollPane(chart);
        chartHolder.setBorder(new TitledBorder("Dental Chart"));
        
        model = new DefaultTableModel(); 
        
        model.addColumn("Date");
        model.addColumn("Tooth No.");
        model.addColumn("Condition");
        model.addColumn("Remarks");
        model.addRow(new Object[] { "", "", "", "" });
        theTable = new JTable(model);
        //setupTable();
        tableHolder = new JScrollPane(theTable);

        setupChart();
        p.add(chartHolder, "span 7 1");

        //buttons
        allButtons = new ButtonGroup();
        JPanel conditionBtns = new JPanel(new GridLayout(4, 1));
        conditionBtns.setBorder(new TitledBorder("Tooth Conditions"));

        normalB = makeMeButtons("src/toothbytes/treatmentmodule/tooth_s.png", "Normal", 0);
        decayedB = makeMeButtons("src/toothbytes/treatmentmodule/decayed.png", "Decayed", 1);
        uneruptedB = makeMeButtons("src/toothbytes/treatmentmodule/unerupted_s.png", "Unerupted", 2);
        missingB = makeMeButtons("src/toothbytes/treatmentmodule/missing_s.png", "Missing", 3);

        amalB = makeMeButtons("src/toothbytes/treatmentmodule/amal_s.png", "Amalgam", 4);
        jacketB = makeMeButtons("src/toothbytes/treatmentmodule/crown_s.png", "Jacket", 5);
        extractB = makeMeButtons("src/toothbytes/treatmentmodule/extract_s.png", "Extraction", 6);

        treatPane = new JPanel(new GridLayout(3, 1));
        treatPane.setBorder(new TitledBorder("Restoration"));

        allButtons.add(normalB);
        allButtons.add(decayedB);
        allButtons.add(uneruptedB);
        allButtons.add(missingB);

        allButtons.add(amalB);
        allButtons.add(jacketB);
        allButtons.add(extractB);

        treatPane.add(amalB);
        treatPane.add(jacketB);
        treatPane.add(extractB);

        conditionBtns.add(normalB);
        conditionBtns.add(decayedB);
        conditionBtns.add(missingB);
        conditionBtns.add(uneruptedB);
        p.add(conditionBtns, "span 1 1");

        p.add(tableHolder, "span 7 1");
        p.add(treatPane, "span 1 1");

        this.setContentPane(p);
        loaded = true;
    }

    public void setupTable() {
        JTableHeader th = theTable.getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        tcm.getColumn(0).setHeaderValue("Date");
        tcm.getColumn(1).setHeaderValue("Tooth No.");
        tcm.getColumn(1).setPreferredWidth(50);
        tcm.getColumn(2).setHeaderValue("Condition");
        tcm.getColumn(3).setHeaderValue("Remarks");

        th.repaint();
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
        public static final int UNERUPTED = 2;
        public static final int MISSING = 3;
        public static final int AMALGAM = 4;
        public static final int JACKET_CROWN = 5;
        public static final int EXTRACTION = 6;

        public ToothComponent(int number, int state) {
            this.setPreferredSize(new Dimension(50, 65));
            x = -10;
            y = -10;
            this.number = number;

            changeState(state);

            this.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    //changeState(action);
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    changeState(action);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                   
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                  
                }

            });

            this.addMouseMotionListener(new MouseMotionAdapter() {

                public void mouseDragged(MouseEvent e) {

                    // If this is a brush have shapes go on the screen quickly
                    if (action == 1) {

                        

                        x = e.getX();
                        y = e.getY();

                        shapes.add(drawBrush(x, y, 4, 4));
                        repaint();
                    }
                    if (action == 4) {

                        

                        x = e.getX();
                        y = e.getY();

                        shapes.add(drawBrush(x, y, 4, 4));
                        repaint();
                    }
                }
            });
        }

        public int getNumber() {
            return this.number;
        }

        private Ellipse2D.Float drawBrush(
                int x1, int y1, int brushStrokeWidth, int brushStrokeHeight) {
            return new Ellipse2D.Float(
                    x1, y1, brushStrokeWidth, brushStrokeHeight);
        }

        public void changeState(int s) {
            history.push(this);
            this.state = s;
            int year = LocalDateTime.now().getYear();
            int month = LocalDateTime.now().getMonthValue();
            int day = LocalDateTime.now().getDayOfMonth();
            switch (state) {
                case 0:

                    this.setToolTipText("Normal");
                    

                    if (loaded) {
                        theTable.setValueAt(month + "-" + day + "-" + year, row, 0);

                        theTable.setValueAt(this.getNumber(), row, 1);

                        theTable.setValueAt("NORMAL", row, 2);

                        model.addRow(new Object[] { "", "", "", "" });
                        row+=1;
                    }

                    break;
                case 1:

                    this.setToolTipText("Decayed");
                    if (loaded) {
                        theTable.setValueAt(month + "-" + day + "-" + year, row, 0);

                        theTable.setValueAt(this.getNumber(), row, 1);

                        theTable.setValueAt("DECAYED", row, 2);

                        model.addRow(new Object[] { "", "", "", "" });
                        row+=1;
                    }
                    break;
                case 2:

                    this.setToolTipText("Unerupted");
                    if (loaded) {
                        theTable.setValueAt(month + "-" + day + "-" + year, row, 0);

                        theTable.setValueAt(this.getNumber(), row, 1);

                        theTable.setValueAt("UNERUPTED", row, 2);

                        model.addRow(new Object[] { "", "", "", "" });
                        row+=1;
                    }
                    break;
                case 3:

                    this.setToolTipText("Missing");
                    if (loaded) {
                        theTable.setValueAt(month + "-" + day + "-" + year, row, 0);

                        theTable.setValueAt(this.getNumber(), row, 1);

                        theTable.setValueAt("MISSING", row, 2);

                        model.addRow(new Object[] { "", "", "", "" });
                        row+=1;
                    }
                    break;
                case 4:

                    this.setToolTipText("Amalagam");
                    if (loaded) {
                        theTable.setValueAt(month + "-" + day + "-" + year, row, 0);

                        theTable.setValueAt(this.getNumber(), row, 1);

                        theTable.setValueAt("AMALGAM", row, 2);

                        model.addRow(new Object[] { "", "", "", "" });
                        row+=1;
                    }
                    break;
                case 5:

                    this.setToolTipText("Jacket Crown");
                    if (loaded) {
                        theTable.setValueAt(month + "-" + day + "-" + year, row, 0);

                        theTable.setValueAt(this.getNumber(), row, 1);

                        theTable.setValueAt("JACKET", row, 2);

                        model.addRow(new Object[] { "", "", "", "" });
                        row+=1;
                    }
                    break;
                case 6:

                    this.setToolTipText("Extraction");
                    if (loaded) {
                        theTable.setValueAt(month + "-" + day + "-" + year, row, 0);

                        theTable.setValueAt(this.getNumber(), row, 1);

                        theTable.setValueAt("EXTRACTED", row, 2);

                        model.addRow(new Object[] { "", "", "", "" });
                        row+=1;
                    }
                    break;
            }

            repaint();
        }

        public void paint(Graphics g) {
            graphicSettings = (Graphics2D) g;

            graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            graphicSettings.drawString(number + "", 29, 13);

            if (state == 0) {
                graphicSettings.drawImage(tooth, 9, 15, null);

            }

            if (action == 1) {
                graphicSettings.drawImage(tooth, 9, 15, null);
                graphicSettings.setPaint(Color.BLACK);
                if (!shapes.isEmpty()) {

                    for (Shape s : shapes) {
                        graphicSettings.draw(s);
                        graphicSettings.fill(s);
                    }
                }

            }

            if (state == 2) {
                graphicSettings.drawImage(unerupted, 9, 15, null);

            }
            if (state == 3) {
                graphicSettings.drawImage(missing, 9, 15, null);

            }
            if (action == 4) {
                graphicSettings.drawImage(tooth, 9, 15, null);
                graphicSettings.setPaint(Color.CYAN);
                if (!shapes.isEmpty()) {

                    for (Shape s : shapes) {
                        graphicSettings.draw(s);
                        graphicSettings.fill(s);
                    }
                }

            }
            if (state == 5) {
                graphicSettings.drawImage(crown, 9, 15, null);

            }
            if (state == 6) {
                graphicSettings.drawImage(extract, 9, 15, null);

            }

        }
    }
}
