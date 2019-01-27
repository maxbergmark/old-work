/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christel, Max
 */

import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import testbutton.*;

public class Ramp2{

    public Ramp2(Color col1, Color col2, String text1, String text2) {
        //super(col1, col2, text1, text2);
        JFrame framen = new JFrame("MyButton Programme");
        framen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hängde sig direkt utan detta...
        framen.setPreferredSize(new Dimension(200, 200));
        framen.setLayout(null);

        MyButton myButton = MyButton.newButton(); //Måste adressera MyButton!
        //myButton.addActionListener(this);
        myButton.setBounds(10, 10, 165, 142);
        framen.getContentPane().add(myButton);
        

        framen.pack();
        framen.setVisible(true);
    }
    

 
    public static void main(String[] args) {
        Ramp2 nyramp = new Ramp2(Color.blue, Color.cyan, "State 1","State 2");

    }
}

