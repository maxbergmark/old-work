import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller extends JFrame implements ActionListener, KeyListener {
    Timer timer;
    SnakePanel panel;
    int time, counter;
    Window window;
    Bot bot;
    Snek snek;
    boolean isPaused, speedUp, fixtime;
    long nanoTime;

    public Controller (int snakeLength, int gridSize, int gridLength, int time, boolean fade) {
        isPaused = false;
        speedUp = false;
        fixtime = false;
        counter = 0;
        window = new Window(snakeLength, gridSize, this);
        bot = new Bot(window);
        snek = new Snek(window);
        panel = new SnakePanel(window, gridLength, fade, this);
        this.time = time;
        timer = new Timer(time, this);
        add(panel);
        addKeyListener(this);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        timer.start();
        nanoTime = System.nanoTime();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //bot.updateBot();
        snek.updateBot();
        if(counter % 10000 == 0 && speedUp) {
            fixtime = true;
        } else if (counter % 10000 == 0 && !speedUp) {
            fixtime = false;
        }
        if(window.snake.checkSnake(window.snake.get(0))) {
            counter++;
            window.snake.updateSnake();
            if( (fixtime && counter % 10000 == 0) || window.score > window.grid.length * window.grid[0].length ) {
                panel.repaint();
            } else if (!fixtime) {
                panel.repaint();
            }
        } else {
            panel.repaint();
            System.out.println(window.snake.get(0).x + "    " + window.snake.get(0).y + "    " +
                    window.snake.direction + "    " + snek.why + "    " + window.goal.x + "    " + window.goal.y);
            timer.stop();
            if(window.score > window.grid.length * window.grid[0].length) {
                System.out.println("SUCCESS!   COUNTER: " + counter + "    TIME: " + (System.nanoTime() - nanoTime)/1e9);
            } else {
                System.out.println("FAILURE!   COUNTER: " + counter);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP && window.snake.get(0).y - 1 != window.snake.get(1).y) {
            window.snake.direction = 0;
        }
        if (key == KeyEvent.VK_RIGHT && window.snake.get(0).x + 1 != window.snake.get(1).x) {
            window.snake.direction = 1;
        }
        if (key == KeyEvent.VK_DOWN && window.snake.get(0).y + 1 != window.snake.get(1).y) {
            window.snake.direction = 2;
        }
        if (key == KeyEvent.VK_LEFT && window.snake.get(0).x - 1 != window.snake.get(1).x) {
            window.snake.direction = 3;
        }
        if (key == KeyEvent.VK_SPACE) {
            if(isPaused) {
                timer.start();
                isPaused = false;
            } else {
                timer.stop();
                isPaused = true;
            }
        }
        if (key == KeyEvent.VK_S) {
            speedUp = !speedUp;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
