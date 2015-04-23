/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui.components;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Jolas
 */
public class DentalChart extends JFrame{
    
    private ToothModel tm;
    
    public DentalChart() {
    
        this.setSize(100, 100);
        
        tm = new ToothModel();
        
        this.setContentPane(tm);
        
    }
    public void init() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
