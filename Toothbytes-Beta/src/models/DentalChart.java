/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import components.TreatmentWindow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jolas
 */
public class DentalChart extends JPanel {

    private Graphics2D g2d;
    private ArrayList<Tooth> allTeeth, l1, l2, l3, l4, l5, l6, l7, l8;
    private MigLayout layout;
    private Tooth passMe;
    private boolean enabled = false;

    public DentalChart() {
        layout = new MigLayout("wrap 16",
                "[55px][55px][55px][55px][55px][55px][55px][55px]15px[55px][55px][55px][55px][55px][55px][55px][55px]",
                "[][]15px[][]");

        this.setSize(970, 300);
        this.setLayout(layout);
        this.setBorder(new LineBorder(Color.BLACK));

        allTeeth = new ArrayList<Tooth>();
        l1 = new ArrayList<Tooth>();
        l2 = new ArrayList<Tooth>();
        l3 = new ArrayList<Tooth>();
        l4 = new ArrayList<Tooth>();
        l5 = new ArrayList<Tooth>();
        l6 = new ArrayList<Tooth>();
        l7 = new ArrayList<Tooth>();
        l8 = new ArrayList<Tooth>();

        initTeeth();

        //5th quadrant
        this.add(l5.get(4), "skip 3,span 1");
        for (int i = 3; i >= 0; i--) {
            this.add(l5.get(i), "span 1");
        }
        //6th quadrant
        for (int i = 0; i < 5; i++) {
            this.add(l6.get(i), "span 1");
        }
        //1st quadrant
        this.add(l1.get(7), "skip 3, span 1");
        for (int i = 6; i >= 0; i--) {
            this.add(l1.get(i), "span 1");
        }
        //2nd quadrant
        for (int i = 0; i < 8; i++) {
            this.add(l2.get(i), "span 1");
        }
        //4th quadrant
        for (int i = 7; i >= 0; i--) {
            this.add(l4.get(i), "span 1");
        }
        //3rd quadrant
        for (int i = 0; i < 8; i++) {
            this.add(l3.get(i), "span 1");
        }

        //8th quadrant
        this.add(l8.get(4), "skip 3,span 1");
        for (int i = 3; i >= 0; i--) {
            this.add(l8.get(i), "span 1");
        }
        //7th quadrant
        for (int i = 0; i < 5; i++) {
            this.add(l7.get(i), "span 1");
        }

    }

    private void initTeeth() {

        int tens = 10; // increment
        int max = 1; // maximum number

        for (int i = 1; i <= 52; i++) {
            try {
                //premanent teeth
                if (i <= 32) {
                    Tooth temp = new Tooth(tens + max, "normal");

                    temp.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("foreground")) {
                                Tooth t = (Tooth) evt.getSource();
                                if(enabled){
                                    TreatmentWindow.updateTable(t.getNumber(), t.getState());
                                }
                            }
                        }
                    });
                    allTeeth.add(temp);
                    max++;

                    if (max > 8) {
                        max = 1;
                        tens += 10;
                    }

                    //prime teeth
                } else {
                    Tooth temp = new Tooth(tens + max, "normal");

                    temp.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("foreground")) {
                                Tooth t = (Tooth) evt.getSource();
                                if(enabled){
                                    TreatmentWindow.updateTable(t.getNumber(), t.getState());
                                }
                            }
                        }
                    });
                    allTeeth.add(temp);
                    max++;

                    if (max > 5) {
                        max = 1;
                        tens += 10;
                    }
                }

            } catch (IOException e) {
                //todo
            }

        }

        for (int i = 0; i < 8; i++) {
            l1.add(allTeeth.get(i));
            l2.add(allTeeth.get(i + 8));
            l3.add(allTeeth.get(i + 16));
            l4.add(allTeeth.get(i + 24));
        }

        for (int i = 32; i < 37; i++) {
            l5.add(allTeeth.get(i));
            l6.add(allTeeth.get(i + 5));
            l7.add(allTeeth.get(i + 10));
            l8.add(allTeeth.get(i + 15));
        }
    }

    public void updatePreState(String s) {
        for (int i = 0; i < 52; i++) {
            allTeeth.get(i).setPreState(s);
        }
    }

    public void updateTooth(int n, String state) {
        for (int i = 0; i < 52; i++) {
            if (allTeeth.get(i).getNumber() == n) {
                allTeeth.get(i).setState(state);
                System.out.println("state" + allTeeth.get(i).getState());
            }
        }
    }
    
    public void setEnabled(boolean b) {
        this.enabled = b;
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
