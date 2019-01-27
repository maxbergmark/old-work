import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paint extends JPanel implements MouseListener, ActionListener{
    int gridSize, xcells, ycells;
    boolean[][] array;
    JFrame frame;

    public Paint (int xcells, int ycells) {
        addMouseListener(this);
        this.xcells = xcells;
        this.ycells = ycells;
        gridSize = 600/xcells;
        JPanel buttonpanel = new JPanel();
        JButton button = new JButton("GOOOOO");
        button.addActionListener(this);
        buttonpanel.add(button);
        JPanel container = new JPanel();
        container.add(this);
        container.add(buttonpanel);
        setPreferredSize(new Dimension(600, gridSize * ycells));
        array = new boolean[xcells][ycells];
        frame = new JFrame();
        frame.add(container);
        frame.pack();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        int xcell = (int)(point.getX() / gridSize);
        int ycell = (int)(point.getY() / gridSize);
        array[xcell][ycell] = !array[xcell][ycell];
        repaint();
    }

    public void paint(Graphics g){
        g.clearRect(0,0,600,gridSize*ycells);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if(array[i][j]) {
                    g.fillRect(i * gridSize, j* gridSize, gridSize - 1, gridSize - 1);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Controller(300, array, 30, 0);
    }
}
