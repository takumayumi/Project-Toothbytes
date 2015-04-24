package toothbytes.treatmentmodule;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Tooth extends JComponent {

    Graphics2D graphicSettings;
    ArrayList<Shape> shapes;
    byte state;
    Image tooth;
    int number;
    public static final byte NORMAL = 0;
    public static final byte DECAYED = 1;
    public static final byte IMPAIRED = 2;
    public static final byte ROOT_FRACTURED = 3;
    public static final byte SUPERNUMERARY = 4;
    public static final byte UNERUPTED = 5;
    public static final byte MISSING = 6;
    public static final byte CARRIES = 7;

    public Tooth(int n) {
        this.setPreferredSize(new Dimension(50, 65));
        try {

            tooth = ImageIO.read(getClass().getResource("tooth.png"));

        } catch (IOException ex) {

            Logger.getLogger(Tooth.class.getName()).log(Level.SEVERE, null, ex);

        }

        this.number = n;
        this.state = 0;
        this.setToolTipText("Normal");
    }

    public void changeState(byte s) {
        this.state = s;

        switch (state) {
            case 0:
                this.setToolTipText("Normal");
                break;
            case 1:
                this.setToolTipText("Decayed");
                break;
            case 2:
                this.setToolTipText("Impacted");
                break;
            case 3:
                this.setToolTipText("Root Fragment");
                break;
            case 4:
                this.setToolTipText("Supernumerary");
                break;
            case 5:
                this.setToolTipText("Unerupted");
                break;
            case 6:
                this.setToolTipText("Missing");
                break;
            case 7:
                this.setToolTipText("Missing Caries");
                break;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        graphicSettings = (Graphics2D) g;

        graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (state == 0) {
            graphicSettings.drawImage(tooth, 0, 15, null);
            graphicSettings.drawString(number + "", 20, 10);
        }
        if (state == 1) {
            graphicSettings.drawImage(tooth, 0, 15, null);
            graphicSettings.drawString(number + "", 20, 10);
        }

    }

}
