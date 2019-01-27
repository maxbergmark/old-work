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

public class Button extends JButton{
    private Color state;
    private Color stated; 
    private String text;
    private String texted;
        
    //private JButton b = new JButton(text);    
    //public static JButton buttonOriginal = new JButton("State Button");
    //public JButton myButton = new JButton("hej");
    private int counter = 0;
    //public JButton myButton=new JButton("hejdå");
    
    public Button(Color col1, Color col2, String text1, String text2){
        super();
        //JButton myButton=new JButton("hejdå");
        this.state = col1; 
        this.stated = col2;
        this.text = text1;
        this.texted = text2;
        setText(text);
        setBackground(state);
        counter=0;
        System.out.println(text);       
        
    }
    
    public static Button newButton(){
        return new Button(Color.green, Color.red, "State Button","Stated Button");
    } 
    

    public void toggleState(){
        System.out.println("asdffdsaaf");
        if(counter%2==0){
            setText(texted);
            setBackground(stated);
        }
        else{
            setText(text);
            setBackground(state);
        }
        //counter++;
 
        //counter++;
        //System.out.println(counter);
        
    }
     public static void main(String[] args){
        
    }
}
