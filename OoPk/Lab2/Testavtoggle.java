/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christel
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Testavtoggle {
   public static void main(String[] args) {
      JToggleButton toggleBtn = new JToggleButton("Toggle Me!");
      toggleBtn.addActionListener(new ActionListener() {

         public void actionPerformed(ActionEvent e) {
            JToggleButton tBtn = (JToggleButton)e.getSource();
            if (tBtn.isSelected()) {
               System.out.println("button selected");
               tBtn.setBackground(Color.red);
            } else {
               System.out.println("button not selected");
            }
         }
      });

      JOptionPane.showMessageDialog(null, toggleBtn);
   }
}
