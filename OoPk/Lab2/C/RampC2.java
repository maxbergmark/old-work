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

public class RampC2 implements ActionListener{
    private MyButton2[] myButtons;
    private int n;
    private int columns;
    public RampC2(Color col1, Color col2, String text1, String text2, String[] args){

        try {
        n = Integer.parseInt(args[0]);
        }
        catch (IndexOutOfBoundsException e) {
            n = 5;
        }
        int width = 10;
        int height = 10;
        columns = (int)(Math.floor(1400/width));
        int rows = (int)(n/columns + 1);

        JFrame framen = new JFrame("MyButton Programme");
        framen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Hängde sig direkt utan detta...
        framen.setPreferredSize(new Dimension((int)(Math.min(width*n, 1400)) + 30, 50 + (int)(height*(1+Math.floor((n-1)/columns)))));
        framen.setLayout(null);

        myButtons = new MyButton2[n];
        for (int i = 0; i < n; i++) {
            int x = (width*i)%1400;
            int y = (int)(height*Math.floor(i/columns));
            float col = (float)(x)/1400;

            float row = (float)(y)/(float)(rows*height);

            Color a = Color.getHSBColor(col, (float)(Math.pow(row, .3)), 1f-(float)(Math.pow(row, 3)));
            Color b = Color.getHSBColor(1f-col, 1f-(float)(Math.pow(row, 3)), (float)(Math.pow(row, .3)));
            String t1 = "";
            String t2 = "";


            //Color a = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
            //Color b = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
            //String t1 = UUID.randomUUID().toString().substring(0, 5);
            //String t2 = UUID.randomUUID().toString().substring(0, 5);
            myButtons[i] = new MyButton2(a, b, t1, t2);
            myButtons[i].setBounds(x, y, width, height);
            framen.getContentPane().add(myButtons[i]);
            myButtons[i].addActionListener(this);
        }
        


        framen.pack();
        framen.setVisible(true);
    }
    
     public void actionPerformed(ActionEvent e)
            {   
                MyButton2 source = (MyButton2) e.getSource();
                for (int i = 0; i < n; i++) {
                    //if (myButtons[i] != source) {
                    if ((i + (int)(i/columns))%2 == 0) {
                        myButtons[i].toggleState();
                    }
                }
            }
 
    public static void main(String[] args) {
        RampC2 nyramp = new RampC2(Color.blue, Color.cyan, "State 1","State 2", args);

    }
}

