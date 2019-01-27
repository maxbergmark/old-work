import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller extends JFrame implements ActionListener{
    Model model;
    View best, answer;
    Timer timer;
    int generationCount;

    public Controller(int numOfChildren, boolean[][] answer, int elitism, int timer) {
        model = new Model(numOfChildren, answer, elitism);
        best = new View(300,300);
        generationCount = 0;
        this.answer = new View(300,300);
        this.timer = new Timer(timer, this);
        JPanel container = new JPanel();
        container.add(best);
        container.add(this.answer);
        add(container);
        pack();
        //setSize(new Dimension(700,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        model.nextGen();
        generationCount++;
        best.cPaint(model.children[0].array);
        answer.cPaint(model.answer);
        System.out.println("GENERATION: " + generationCount + " FITNESS: " + model.children[0].fitness);
        if(model.children[0].fitness == model.children[0].array.length * model.children[0].array[0].length) {
            timer.stop();
            System.out.println("SUCCESS: GENERATION: " + generationCount);
        }
    }
}
