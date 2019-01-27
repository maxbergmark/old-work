import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ram {
    private MyButton[] button;
    public static void main(String[] args) {
        int firstArg = Integer.parseInt(args[0]);
        Ram ram = new Ram(firstArg);
    }

    public Ram(int n) {
        button = new MyButton[n];
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        ActionListener a = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < button.length; i++) {
                    if(button[i] != actionEvent.getSource()){
                        button[i].toggleState();
                    }
                }
            }
        };
        for (int i = 0; i < button.length; i++) {
            button[i] = new MyButton();
            button[i].addActionListener(a);
            panel.add(button[i]);
        }
        int heigth = (int)Math.sqrt(n);
        int width = (int)Math.sqrt(n);
        if(width*heigth+width < n){
            width++;
            heigth++;
        }
        while(width*heigth < n)
            heigth++;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(66*width,40*heigth);
        frame.setVisible(true);
    }
}

// Allt utom E5 funkar som det ska! Några saker som man kan vara petig med finns kommenterade i MyButton.
// Använd setPreferredSize istället för setSize, då setSize inte garanterar att storleken kommer förbli.
// Övrigt så är heigth inte ett ord
// Uppgift E5.* är inte klar.
