/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toothbytes.ui;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Jolas
 */
public class PaymentsWindow extends ModuleWindow{
    private MigLayout layout;
    public PaymentsWindow() {
        this.add(new JLabel("Payments"));
    }
}
