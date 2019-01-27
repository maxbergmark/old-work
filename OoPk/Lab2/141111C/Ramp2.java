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

public class Ramp2 extends MyButton{

    public Ramp2(Color col1, Color col2, String text1, String text2){ 
        super(col1, col2, text1, text2);
        JFrame framen = new JFrame("MyButton Programme");
        framen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hängde sig direkt utan detta... E2.3
        framen.setPreferredSize(new Dimension(200, 200));
        framen.setLayout(null);
        //framen.getContentPane().add(myButton);


        MyButton myButton = newButton();
        //myButton.addActionListener(this);
        myButton.setBounds(10, 10, 165, 142);
        framen.getContentPane().add(myButton);
        /*
        Button myButton2 = newButton();
        myButton2.addActionListener(this);
        myButton2.setBounds(210, 10, 165, 142);
        framen.getContentPane().add(myButton2);
*/
        framen.pack(); //framen sätts in i fönstret
        framen.setVisible(true);
    }


 
    public static void main(String[] args) {
        Ramp2 nyramp = new Ramp2(Color.blue, Color.cyan, "State 1","State 2");

    }
}

