package components;

import components.ModuleWindow;
import components.ReportsGen;

/**
 *
 * @author USER
 */
public class ReportsGenWindow extends ModuleWindow{

    private ReportsGen gr;
    
    public ReportsGenWindow(){
        gr = new ReportsGen();
        
        super.addToMainPane(gr, "span 2, grow");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReportsGen repGenWin = new ReportsGen();
        repGenWin.setVisible(true);
    }
    
}
