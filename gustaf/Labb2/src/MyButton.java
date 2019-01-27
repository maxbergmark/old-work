import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton /*implements ActionListener*/ {
    private int state;
    private Color color1;
    private Color color2;
    private String text1;
    private String text2;

    public MyButton(Color color1, Color color2, String text1, String text2) {
        this.color1 = color1;
        this.color2 = color2;
        this.text1 = text1;
        this.text2 = text2;
        this.state = 1;
        setText(text1);
        setBackground(color1);
        //addActionListener(this);
    }

    public MyButton() {
        this(Color.GREEN, Color.RED, "uno", "dos"); //gör samma sak som de sju raderna under gör.
        //addActionListener(this);
    }

    public void toggleState() {
        /*
        setText((++state % 2 == 0) ? text2 : text1);
        setBackground((state % 2 == 0) ? color2 : color1);
        // många logiska operationer kan utföras på enklare sätt
        // om du hade sparat dina färger/texter i en lista så hade det blivit ännu snyggare:
        // setText(texts[state % 2]); // går att modifiera för större listor, eller generalisera.
        // setBackground(colors[state++ % 2]);
        // sista pyttelilla grej: använd 0/1 som state istället för 1/2 då java är 0-indexerat. Behöver inte ändras nu.
        */


        if(state == 1) {
            setText(text2);
            setBackground(color2);
            state = 2;
        }
        else {
            setText(text1);
            setBackground(color1);
            state = 1;
        }

    }

    /*public void actionPerformed(ActionEvent action) {
        toggleState();
    }*/

}
