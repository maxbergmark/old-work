import javax.swing.*;
import java.awt.*;

/**
 * Created by ElevPC on 2017-03-07.
 */
public class View extends JPanel {
    boolean[][] array;
    int xsize, ysize;

    public View(int xsize, int ysize) {
        this.xsize = xsize;
        this.ysize = ysize;
        setPreferredSize(new Dimension(xsize, ysize));
    }
    public void cPaint(boolean[][] array) {
        this.array = array;
        repaint();
    }

    public void paint(Graphics g) {
        int gridSize = xsize/array.length;
        g.clearRect(0,0,xsize,ysize);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if(array[i][j]) {
                    g.fillRect(i * gridSize, j* gridSize, gridSize - 1, gridSize - 1);
                }
            }
        }
    }
}
