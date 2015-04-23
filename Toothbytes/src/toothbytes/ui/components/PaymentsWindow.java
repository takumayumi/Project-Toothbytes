package toothbytes.ui.components;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

/**
 * <h1>PaymentsWindow</h1>
 * The {@code PaymentsWindow} class constructs the Payments Window to be able 
 * to see the list of patients and their respective payments attributes.
 */
public class PaymentsWindow extends ModuleWindow{
    private MigLayout layout;
    
    /**
     * This constructor creates the Payments Windows and it's components.
     */
    public PaymentsWindow() {
        this.add(new JLabel("Payments"));
    }
    
}
