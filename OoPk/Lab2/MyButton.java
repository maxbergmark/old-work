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

public class MyButton extends JButton{ 


    public static void main(String[] args) {
        JButton button1 = new JButton();
        button1.setBackground(Color.green); //Here ground state is stated
        button1.setText("State Button");
        button1.addActionListener(new ActionListener(){
        int clicked = 0; 
    
public void actionPerformed(ActionEvent e)
            {
                //Things that happen when button is pressed
                clicked++;
                if(clicked%2>0){ //Checks if it's an odd number
                    button1.setBackground(Color.red);
                    button1.setText("Stated Button");
                    System.out.println("You clicked the button");
                }
                else{ //Double click should return to original state
                    button1.setBackground(Color.green);
                    button1.setText("State Button");
                }
                
                System.out.println(clicked);
                
            }
        });
        JOptionPane.showMessageDialog(null, button1);    
    }
       
       
       
       
    }
                
    
        
    

