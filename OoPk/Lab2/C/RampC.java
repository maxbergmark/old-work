/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christel, Max
 */

import java.util.UUID;
import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import testbutton.*;

public class RampC{
    private MyButton[] myButtons;
    public RampC(Color col1, Color col2, String text1, String text2, String[] args){
        //super(col1, col2, text1, text2);
        int n = Integer.parseInt(args[0]);
        JFrame framen = new JFrame("MyButton Programme");
        framen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hängde sig direkt utan detta...
        framen.setPreferredSize(new Dimension(100*n + 30, 160));
        framen.setLayout(null);

        
        System.out.println(n);
        myButtons = new MyButton[n];
        for (int i = 0; i < n; i++) {
            Color a = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
            Color b = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
            String t1 = UUID.randomUUID().toString().substring(0, 5);
            String t2 = UUID.randomUUID().toString().substring(0, 5);
            myButtons[i] = new MyButton(a, b, t1, t2);
            myButtons[i].setBounds(10+100*i, 10, 90, 100);
            framen.getContentPane().add(myButtons[i]);
        }
        

        //MyButton myButton = MyButton.newButton(); //Måste adressera MyButton!
        //myButton.addActionListener(this);
        //myButton.setBounds(10, 10, 165, 142);
        //framen.getContentPane().add(myButton);
        
        /*
        Button myButton2 = newButton();
        myButton2.addActionListener(this);
        myButton2.setBounds(210, 10, 165, 142);
        framen.getContentPane().add(myButton2);
*/
        framen.pack();
        framen.setVisible(true);
    }
    
     public void actionPerformeasdfd(ActionEvent e)
            {
                //toggleState(this.myButton);
                //toggleState(((Button) e.getSource()));
            }
 
    public static void main(String[] args) {
        RampC nyramp = new RampC(Color.blue, Color.cyan, "State 1","State 2", args);

    }
}

