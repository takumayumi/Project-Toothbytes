package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * <h1>ModuleWindow</h1>
 * The {@code ModuleWindow} class constructs the Module Window to be able to 
 * manage the modules of program Toothbytes.
 */
public class ModuleWindow extends JPanel{

    //private JButton addPatientBut, setAppointmentBut;

    private JPanel toolBar, mainPanel;
    
    /**
     * This constructor creates the Module Window interface and layouts it's 
     * components.
     */

    public ModuleWindow(){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        this.add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * This method allows LayoutManager to be used in constructing the main 
     * panel.
     * @param   l 
     *          Object representation of LayoutManager.
     */
    public void setMainPaneLayout(LayoutManager l) {
        mainPanel.setLayout(l);
    }
    
    /**
     * This method allows a JComponent in object representation to be added in 
     * main panel.
     * @param   c 
     *          Object representation of JComponent.
     */
    public void addToMainPane(JComponent c) {
        mainPanel.add(c);
    }
    
    /**
     * This method allows a JComponent in object representation and a String to 
     * be added in the main panel.
     * @param   c
     *          JComponent object.
     * @param   s 
     *          A String represenation.
     */
    public void addToMainPane(JComponent c, String s) {
        mainPanel.add(c, s);
    }
    
    public void addPLV(PatientListViewer plv) {
        this.add(plv, BorderLayout.WEST);
    }
    
    public void removeMainComponent(int i) {
        mainPanel.remove(i);
    }
    public void setAllFont(Font f) {

    }
}