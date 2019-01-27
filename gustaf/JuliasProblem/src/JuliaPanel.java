import javax.swing.*;
import java.awt.*;

/**
 * Created by ElevPC on 2017-02-16.
 */
public class JuliaPanel extends JPanel {
    Cirkel liten;
    Cirkel stor;

    public JuliaPanel() {
        liten = new Cirkel(50,30,10);
        stor = new Cirkel(100,100,50);
    }

    public void paint(Graphics g) {
        g.fillOval(liten.x,liten.y,liten.d,liten.d);
        g.fillOval(stor.x,stor.y,stor.d,stor.d);
    }
}
