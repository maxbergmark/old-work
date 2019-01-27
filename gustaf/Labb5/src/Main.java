import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        BrowserFrame frame = new BrowserFrame();
        BrowserPanel panel = new BrowserPanel();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
