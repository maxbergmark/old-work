import javax.swing.*;
import java.awt.*;

/**
 * Created by ElevPC on 2017-02-16.
 */
public class JuliaFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jewel");
        JuliaPanel panel = new JuliaPanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
    }
}
