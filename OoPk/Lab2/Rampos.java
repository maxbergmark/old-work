/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christel
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rampos extends Button {
    private static void createAndShowGUI()  {
 
        JFrame frame1 = new JFrame("JAVA");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        Button myButton = newButton();
        myButton.addActionListener(new ActionListener() {
            
            
            int clicked = 0; //Counts how many times we've clicked
            public void actionPerformed(ActionEvent e)
            {
                //Things that happen when button is pressed
                //toggleState();
                
                System.out.println(clicked);
                
            }
        });      
 
        frame1.getContentPane().add(myButton);
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

