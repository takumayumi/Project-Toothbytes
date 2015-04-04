package toothbytes.ui;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class PaymentsWindow extends ModuleWindow{
    private MigLayout layout;
    public PaymentsWindow() {
        this.add(new JLabel("Payments"));
    }
}
