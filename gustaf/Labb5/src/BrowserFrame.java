import javax.swing.*;
import java.awt.*;

public class BrowserFrame extends JFrame {
    public BrowserFrame () {
        super();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setPreferredSize(new Dimension(600, 600));
    }
}
