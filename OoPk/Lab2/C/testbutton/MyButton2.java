/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christel, Max
 */

package testbutton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MyButton2 extends JButton{
    private Color[] states = new Color[2];
    private String[] texts = new String[2];
        
    //private JButton b = new JButton(text);    
    //public static JButton buttonOriginal = new JButton("State Button");
    //public JButton myButton = new JButton("hej");
    private int counter;
    //public JButton myButton=new JButton("hejdå");
    
    public MyButton2(Color col1, Color col2, String text1, String text2){
        super();
        this.states[0] = col1;
        this.states[1] = col2;
        this.texts[0] = text1;
        this.texts[1] = text2;
        setText(texts[0]);
        setBackground(states[0]);
        setBorder(new LineBorder(Color.red, 0));
        counter = 0; 
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
    
    public static MyButton2 newButton() {
        return new MyButton2(Color.green, Color.red, "State Button", "Stated Button");
    } 

    public void toggleState(){ //Allt fungerar men denna gör inte som jag vill... Vet inte vad jag sätter färgen på.
        addCount();
        setText(getTexts()[getCount()%2]);
        setBackground(getColors()[getCount()%2]);

         
    }


     public static void main(String[] args){
        
    }
}
