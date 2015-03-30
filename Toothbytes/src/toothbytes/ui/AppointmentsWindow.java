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
public class AppointmentsWindow extends ModuleWindow{
    private MigLayout layout;
    public AppointmentsWindow() {
        this.add(new JLabel("Appointments"));
    }
}
