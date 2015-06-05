/*
 * Copyright (c) 2014, 2015, Project Toothbytes. All rights reserved.
 *
 *
*/
package window;

import components.Camera;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.Patient;
import net.miginfocom.swing.MigLayout;

public class OldTreatmentWindow extends JFrame {

    private JPanel p, chart, treatPane;
    private JScrollPane chartHolder, tableHolder;
    private JToolBar bar;
    private JTable theTable;
    private ToothComponent[] upper, lower;
    private Image tooth, missing, unerupted, crown, extract;
    private int action;
    private JButton camera, undo, redo, finish;
    private JToggleButton normalB, missingB, uneruptedB, decayedB, amalB, jacketB, extractB;
    private ButtonGroup allButtons;
    private Stack<ToothComponent> undoStack, redoStack;
    private boolean canUndo = false;
    private boolean canRedo = false;
    int row = 0;
    int col = 0;
    DefaultTableModel tableModel;
    int year = LocalDateTime.now().getYear();
    int month = LocalDateTime.now().getMonthValue();
    int day = LocalDateTime.now().getDayOfMonth();

    public OldTreatmentWindow(Patient patient) {
        action = 0;
        this.setIconImage(new ImageIcon("src/Toothbytes/favicon.png").getImage());
        //load images
        String RES_DIR = "res/teeth/";
        try {
            tooth = ImageIO.read(new File(RES_DIR+"bluetooth.png"));
            missing = ImageIO.read(new File(RES_DIR+"missing.png"));
            unerupted = ImageIO.read(new File(RES_DIR+"unerupted.png"));
            crown = ImageIO.read(new File(RES_DIR+"crown.png"));
            extract = ImageIO.read(new File(RES_DIR+"extract.png"));
        } catch (IOException ex) {
            
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

        undoStack = new Stack<>();
        redoStack = new Stack<>();
        
        String dir = "res\\buttons\\";
        camera = new JButton(new ImageIcon(dir+"Camera.png"));
        undo = new JButton(new ImageIcon(dir+"Undo.png"));
        redo = new JButton(new ImageIcon(dir+"Redo.png"));

        undo.setEnabled(canUndo);
        redo.setEnabled(canRedo);

        finish = new JButton(new ImageIcon(dir+"Save.png"));

        camera.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable(){
                    public void run(){
                        Camera camera = new Camera(patient.getId());
                    }
                });
            }

        });

        Action undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = undoStack.peek().getNumber();

                if (i > 16) {
                    lower[i - 17].revertState(undoStack.peek().state, undoStack.peek().markedArea);
                    undoStack.pop();
                } else {
                    upper[i - 1].revertState(undoStack.peek().state, undoStack.peek().markedArea);
                    undoStack.pop();
                }

                if (undoStack.empty()) {
                    undo.setEnabled(false);
                }
            }
        };
        
        Action saveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage image = new BufferedImage(chart.getWidth(), chart.getHeight(), BufferedImage.TYPE_INT_RGB);
                 Graphics2D graphics2D = image.createGraphics(); 
                File pDir = new File("saves\\"+patient.getId());
                if(!pDir.exists()) {
                    pDir.mkdir();
                }
                 chart.paint(graphics2D);
                 try{
                     File a = new File("saves\\"+patient.getId()+"\\latest.png");
                     if(a.exists()){
                         System.out.println("deleted!");
                         a.delete();
                     }
                     ImageIO.write(image,"png", new File("saves\\"+patient.getId()+"\\latest.png"));
                     ImageIO.write(image,"png", new File("saves\\"+patient.getId()+"\\"+month + "-" + day + "-" + year+".png"));
                     }
                 catch(Exception ex){
                      ex.printStackTrace();
                     }
            }
        };

        Action redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = redoStack.peek().getNumber();

                if (i > 16) {
                    lower[i - 17].changeState(redoStack.peek().state, redoStack.peek().markedArea);
                    redoStack.pop();
                } else {
                    upper[i - 1].changeState(redoStack.peek().state, redoStack.peek().markedArea);
                    redoStack.pop();
                }

                if (redoStack.empty()) {
                    redo.setEnabled(false);
                }
            }
        };

        //hotkeys settings
        undo.addActionListener(undoAction);
        redo.addActionListener(redoAction);
        finish.addActionListener(saveAction);

        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());

        undo.getInputMap().put(undoKeyStroke, "undo");
        undo.getActionMap().put("undo", undoAction);
        redo.getInputMap().put(redoKeyStroke, "redo");
        redo.getActionMap().put("redo", redoAction);

        bar = new JToolBar();
        bar.add(new JLabel("Patient: " + patient.getFullName() + "\t"));
        bar.add(camera);
        bar.addSeparator(new Dimension(15, 50));
        bar.add(undo);
        bar.add(redo);
        bar.addSeparator(new Dimension(15, 50));
        bar.add(finish);
        p.add(bar, "north");

        //chart config
        chart = new JPanel();
        chart.setLayout(new GridLayout(2, 16, 5, 10));
        chart.setBackground(Color.WHITE);

        chartHolder = new JScrollPane(chart);
        chartHolder.setBorder(new TitledBorder("Dental Chart"));

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        setupTable();
        tableHolder = new JScrollPane(theTable);

        setupChart();
        p.add(chartHolder, "span 7 1");

        //buttons
        allButtons = new ButtonGroup();
        JPanel conditionBtns = new JPanel(new GridLayout(4, 1));
        conditionBtns.setBorder(new TitledBorder("Tooth Conditions"));
        String dir2 = "res/teeth/";
        normalB = makeMeButtons(dir2+"tooth_s.png", "Normal", 0);
        decayedB = makeMeButtons(dir2+"decayed.png", "Decayed", 1);
        uneruptedB = makeMeButtons(dir2+"unerupted_s.png", "Unerupted", 2);
        missingB = makeMeButtons(dir2+"missing_s.png", "Missing", 3);

        amalB = makeMeButtons(dir2+"amal_s.png", "Amalgam", 4);
        jacketB = makeMeButtons(dir2+"crown_s.png", "Jacket", 5);
        extractB = makeMeButtons(dir2+"extract_s.png", "Extraction", 6);

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
    }

    public void setupTable() {
        tableModel.addColumn("Date");
        tableModel.addColumn("Tooth No.");
        tableModel.addColumn("Condition");
        tableModel.addColumn("Remarks");

        theTable = new JTable(tableModel);

        theTable.getColumnModel().getColumn(0).setMinWidth(100);
        theTable.getColumnModel().getColumn(1).setMinWidth(60);
        theTable.getColumnModel().getColumn(2).setMinWidth(100);

        theTable.getColumnModel().getColumn(0).setMaxWidth(200);
        theTable.getColumnModel().getColumn(1).setMaxWidth(100);
        theTable.getColumnModel().getColumn(2).setMaxWidth(300);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        theTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        theTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        theTable.getTableHeader().setReorderingAllowed(false);
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

    public class ToothComponent extends JComponent implements Serializable {

        Graphics2D graphicSettings;
        Area markedArea = new Area();
        Stack<Area> redoMarkedArea = new Stack<>();
        Stack<Area> undoMarkedArea = new Stack<>();
        Area isolateShape = new Area();

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

            this.state = state;

            this.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    changeState(action, markedArea);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    //not supported
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    //not supported
                }

            });

            this.addMouseMotionListener(new MouseMotionAdapter() {

                public void mouseDragged(MouseEvent e) {

                    // If this is a brush have shapes go on the screen quickly
                    if (action == 1) {

                        x = e.getX();
                        y = e.getY();

                        markedArea.add(new Area(drawBrush(x, y, 4, 4)));
                        repaint();
                    }
                    if (action == 4) {

                        x = e.getX();
                        y = e.getY();

                        markedArea.add(new Area(drawBrush(x, y, 4, 4)));
                        repaint();
                    }
                }
            });
        }

        public int getNumber() {
            return this.number;
        }

        public Area getMarkedArea() {
            return this.markedArea;
        }

        private Ellipse2D.Float drawBrush(
                int x1, int y1, int brushStrokeWidth, int brushStrokeHeight) {
            return new Ellipse2D.Float(
                    x1, y1, brushStrokeWidth, brushStrokeHeight);
        }

        public void revertState(int s, Area m) {
            System.out.println("state: " + s);
            
            if (s == 1 || s == 4) {
                ToothComponent temp = new ToothComponent(this.number, this.state);
                temp.markedArea = this.markedArea;
                redoStack.push(temp);
                redo.setEnabled(true);
                this.markedArea = new Area();
                this.state = s;
                paintState(s);
                revertTables();
            
            } else {
                
                ToothComponent temp = new ToothComponent(this.number, this.state);
                
                redoStack.push(temp);
                redo.setEnabled(true);
                this.state = s;
                this.markedArea = new Area();
                paintState(s);
                revertTables();
            }
        }

        public void changeState(int s, Area m) {
            if (this.state != s) {
                ToothComponent temp = new ToothComponent(this.number, this.state);
                undoStack.push(temp);
                
                undo.setEnabled(true);

                this.state = s;
                this.markedArea = m;
                paintState(s);
                updateTables();
            }
        }

        private void revertTables() {
            tableModel.removeRow(tableModel.getRowCount() - 1);
            SwingUtilities.updateComponentTreeUI(theTable);
            SwingUtilities.updateComponentTreeUI(chartHolder);
        }

        private void updateTables() {
            
            tableModel.addRow(new Object[]{"", "", "", ""});

            theTable.setValueAt(month + "-" + day + "-" + year, tableModel.getRowCount() - 1, 0);

            theTable.setValueAt(this.getNumber(), tableModel.getRowCount() - 1, 1);

            theTable.setValueAt(this.getToolTipText(), tableModel.getRowCount() - 1, 2);
        }

        private void paintState(int state) {

            switch (state) {

                case 0:
                    this.setToolTipText("Normal");
                    break;

                case 1:
                    this.setToolTipText("Decayed");
                    break;

                case 2:
                    this.setToolTipText("Unerupted");
                    break;

                case 3:
                    this.setToolTipText("Missing");
                    break;

                case 4:
                    this.setToolTipText("Amalagam");
                    break;

                case 5:
                    this.setToolTipText("Jacket Crown");
                    break;

                case 6:
                    this.setToolTipText("Extraction");
                    break;
            }
            repaint();
        }

        public void paint(Graphics g) {
            int type = AlphaComposite.SRC_OVER;
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
                graphicSettings.setComposite(AlphaComposite.getInstance(type, 0.6f));

                graphicSettings.draw(markedArea);
                graphicSettings.fill(markedArea);
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
                graphicSettings.setComposite(AlphaComposite.getInstance(type, 0.5f));

                graphicSettings.draw(markedArea);
                graphicSettings.fill(markedArea);
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
