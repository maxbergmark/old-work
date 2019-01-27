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

public class MyButton2 extends JButton{ 
    private Color state;
    private Color stated; 
    private String text;
    private String texted;
    
    public JButton MyButton(Color col1, Color col2, String text1, String text2){
        this.state = col1; 
        this.stated = col2;
        this.text = text1;
        this.texted = text2;
        JButton buttonMy = new JButton(text);
        buttonMy.setBackground(state);
        return buttonMy;
                
    } 
    int clicked = 0;
    public JButton createButton(){
        return MyButton(Color.green, Color.red, "State Button", "Stated Button");
    }
    public void ToggleState(){
        clicked++;
    }

    public static void main(String[] args) {
        JButton button1 = new JButton();
        JButton newButton= createButton();
        button1.setBackground(Color.green); //Here ground state is stated
        button1.setText("State Button");
        button1.addActionListener(new ActionListener(){
         
    
        public void actionPerformed(ActionEvent e){ //Things that happen when button is pressed
                clicked++; //Counter to keep track of state
                if(clicked%2>0){ //Checks if it's an odd number
                    button1.setBackground(stated);
                    button1.setText(texted);
                    System.out.println("You clicked the button");
                }
                else{ //Double click should return to original state
                    button1.setBackground(state);
                    button1.setText(text);
                }
                
                System.out.println(clicked);
                
            }
        });
        JOptionPane.showMessageDialog(null, newButton);
        
       
    }
       
       
       
       
    }
                
    
        
    

