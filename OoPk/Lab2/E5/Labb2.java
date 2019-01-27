/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import testbutton.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author eleonorkollberg
 */
public class Labb2 extends JFrame implements ActionListener{
    public MyButton knappen; 

    
    public Labb2(){
        knappen = MyButton.newButton();
        knappen.setPreferredSize(new Dimension(400,300));
        knappen.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(knappen);
        pack();
        setVisible(true);         
    }
 
    public void actionPerformed(ActionEvent e){
        System.out.println("asdf");
        knappen.toggleState();
        repaint();
       
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    Labb2 k = new Labb2();
    }
    
}