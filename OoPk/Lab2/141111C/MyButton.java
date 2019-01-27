/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christel, Max
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class MyButton extends JButton implements ActionListener{
    private Color[] states = new Color[2];
    private String[] texts = new String[2];
        
    //private JButton b = new JButton(text);    
    //public static JButton buttonOriginal = new JButton("State Button");
    //public JButton myButton = new JButton("hej");
    private int counter;
    //public JButton myButton=new JButton("hejdå");
    
    public MyButton(Color col1, Color col2, String text1, String text2){
        super();
        this.states[0] = col1;
        this.states[1] = col2;
        this.texts[0] = text1;
        this.texts[1] = text2;
        setText(texts[0]);
        setBackground(states[0]);
        counter = 0; 
        addActionListener(this);
    }

    public Color[] getColors() {
        return this.states;
    }
    public String[] getTexts() {
        return this.texts;
    }
    public int getCount() {
        return this.counter;
    }
    public void addCount() {
        this.counter++;
    }
    
    public static MyButton newButton() {
        return new MyButton(Color.green, Color.red, "State Button", "Stated Button");
    } 

    public void actionPerformed(ActionEvent e)
        {
            //toggleState(this.myButton);
            toggleState(((MyButton) e.getSource()));
        }

    public void toggleState(MyButton tempbutton){ //Allt fungerar men denna gör inte som jag vill... Vet inte vad jag sätter färgen på.
        tempbutton.addCount();
        tempbutton.setText(tempbutton.getTexts()[tempbutton.getCount()%2]);
        tempbutton.setBackground(tempbutton.getColors()[tempbutton.getCount()%2]);

         
    }

     public static void main(String[] args){
        
    }
}
