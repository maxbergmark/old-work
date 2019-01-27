import javax.swing.*;
import java.awt.*;

public class SnakePanel extends JPanel {
    Window window;
    int gridLength;
    boolean fade;
    Controller controller;

    public SnakePanel(Window window, int gridLength, boolean fade, Controller controller) {
        this.window = window;
        this.fade = fade;
        this.controller = controller;
        this.gridLength = gridLength;
        setPreferredSize(new Dimension(window.grid.length * gridLength, window.grid.length * gridLength));
    }

    public void paint (Graphics g) {
        long time = System.nanoTime();
        if(fade || controller.fixtime) {
            double colorDiff = 200.0 / window.snake.size();
            g.clearRect(0, 0, gridLength * window.grid.length, gridLength * window.grid[0].length);
            g.setColor(Color.red);
            g.fillRect(window.goal.x * gridLength, window.goal.y * gridLength, gridLength - 1, gridLength - 1);
            for (int i = 0; i < window.snake.size(); i++) {
                g.setColor(new Color((int) (i * colorDiff), (int) (i * colorDiff), (int) (i * colorDiff)));
                g.fillRect(window.snake.get(i).x * gridLength, window.snake.get(i).y * gridLength, gridLength - 1, gridLength - 1);
            }
        } else {
            g.clearRect(0,0,60,15);
            g.setColor(Color.red);
            g.fillRect(window.goal.x * gridLength, window.goal.y * gridLength, gridLength - 1, gridLength - 1);
            g.clearRect(window.last.x * gridLength, window.last.y * gridLength, gridLength - 1, gridLength - 1);
            g.setColor(Color.black);
            g.fillRect(window.snake.get(0).x * gridLength, window.snake.get(0).y * gridLength, gridLength - 1, gridLength - 1);
        }
        g.setColor(Color.red);
        g.drawString(Integer.toString(window.score), 10, 10);
    }
}
