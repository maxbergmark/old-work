import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Controller implements ActionListener {
    View view;
    Model model;
    Timer timer;
    Controller(Model model, View view, int t) {
        this.model = model;
        this.view = view;
        timer = new Timer(t, this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        long startTime = System.nanoTime();
        model.updateAll();
        System.out.println((System.nanoTime() - startTime)/1000000000.0);
        view.repaint();
    }
}