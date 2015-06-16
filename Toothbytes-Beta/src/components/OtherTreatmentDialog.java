/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import utilities.DBAccess;
import window.TreatmentWindow;

/**
 *
 * @author Jolas
 */
public class OtherTreatmentDialog extends JDialog{
    private JComboBox treats;
    private JToggleButton fill, brush;
    private ButtonGroup toolGroup;
    private Box butBox;
    private String tool;
    private JPanel p;
    private TreatmentWindow master;
    
    public OtherTreatmentDialog(JDialog d) {
        super(d);
        master = (TreatmentWindow)d;
//        Point placement = c.getLocation();
        this.setSize(200, 100);
        this.setUndecorated(true);
        
        p = new JPanel(new BorderLayout());
        p.setBorder(new TitledBorder("Other Treatments"));
        this.setContentPane(p);
        
        treats = new JComboBox(DBAccess.getServicesOffered().toArray());
        
        toolGroup = new ButtonGroup(){
            @Override
            public void setSelected(ButtonModel model, boolean selected) {
                if(selected) {
                    super.setSelected(model, selected);
                } else {
                    clearSelection();
                }
            }
        };
        
        fill = initButtons("FL", "fill");
        brush = initButtons("MK", "marker");
        toolGroup.add(fill);
        toolGroup.add(brush);
        
        fill.setSelected(true);
        
        butBox = new Box(BoxLayout.X_AXIS);
        butBox.add(fill);
        butBox.add(brush);
        
        tool = "fill";
        
        this.getContentPane().add(treats, BorderLayout.NORTH);
        this.getContentPane().add(butBox, BorderLayout.SOUTH);
        
        this.addWindowListener(new WindowAdapter(){
            public void windowDeactivated(WindowEvent e) {
                master.refresh();
                unfocused();
            }
        });
    }
    
    public void setTool(String tool) {
       this.tool = tool;
    }
    
    public String getTool() {
        return this.tool+":"+getState();
    }
    public String getState() {
        return (String)treats.getSelectedItem();
    }
    
    public void unfocused() {
        
        this.setVisible(false);
    }
    
    public JToggleButton initButtons(String path, String tool) {
        JToggleButton temp = new JToggleButton(path);
        temp.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(tool);
                setTool(tool);
            }
        });
        return temp;
    }
    
}
