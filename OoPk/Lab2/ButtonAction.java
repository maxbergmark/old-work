import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
 
public class ButtonAction {
    
 
    private static void createAndShowGUI()  {
 
        JFrame frame1 = new JFrame("JAVA");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JButton button = new JButton(" >> JavaProgrammingForums.com <<");
        button.setBackground(Color.green);
        button.setText("State Button");
        button.addActionListener(new ActionListener() {
            
            
            int clicked = 0; //Counts how many times we've clicked
            public void actionPerformed(ActionEvent e)
            {
                //Things that happen when button is pressed
                clicked++;
                if(clicked%2>0){ //Checks if it's an odd number
                    button.setBackground(Color.red);
                    button.setText("Stated Button");
                    System.out.println("You clicked the button");
                }
                else{ //Double click should return to original state
                    button.setBackground(Color.green);
                    button.setText("State Button");
                }
                
                System.out.println(clicked);
                
            }
        });      
 
        frame1.getContentPane().add(button);
        frame1.pack();
        frame1.setVisible(true);
    }
 
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}