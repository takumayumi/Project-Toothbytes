package toothbytes.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ModuleWindow extends JPanel{
    private JPanel mainPanel;
    //private JButton addPatientBut, setAppointmentBut;
    public ModuleWindow(){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        this.add(mainPanel, BorderLayout.CENTER);
    }
    public void setMainPaneLayout(LayoutManager l) {
        mainPanel.setLayout(l);
    }
    public void addToMainPane(JComponent c) {
        mainPanel.add(c);
    }
    public void addToMainPane(JComponent c, String s) {
        mainPanel.add(c, s);
    }
    public void setAllFont(Font f) {

    }
}