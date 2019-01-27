import javax.swing.JFrame;

public class MyFrame extends JFrame {
    Model model;
    View view;

    public MyFrame (Model model, View view) {
        this.model = model;
        this.view = view;
        this.add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
