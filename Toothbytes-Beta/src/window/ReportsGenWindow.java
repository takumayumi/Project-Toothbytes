package window;

import components.ModuleWindow;
import components.ReportsGen;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author USER
 */
public class ReportsGenWindow extends ModuleWindow{

    private ReportsGen gr;
    private JScrollPane rgsp;
    private MigLayout layout;
    
    public ReportsGenWindow(){
        layout = new MigLayout(
                "filly, wrap 12",
                "[fill][fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push"
                + "[fill]push[fill]push[fill]push[fill]push[fill]" //13 columns
        );
        super.setMainPaneLayout(layout);
        gr = new ReportsGen();
        rgsp = new JScrollPane(gr);
        super.addToMainPane(rgsp, "span 2, grow");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReportsGen repGenWin = new ReportsGen();
        repGenWin.setVisible(true);
    }
    
}
