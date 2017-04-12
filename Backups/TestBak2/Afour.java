import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Afour extends JFrame implements ActionListener{
   private JTextArea textA;
   private JButton sendButton;
   private JButton saveButton;
   public Afour(){
      textA = new JTextArea(5,20);

      sendButton = new JButton("Send Wrk Dir");
      sendButton.addActionListener(this);
      saveButton = new JButton("Save");
      saveButton.addActionListener(this);
      this.setLayout(new GridLayout(4,1));
      this.add(textA);
      this.setSize(200,200);

      this.add(sendButton);
      this.add(saveButton);
      setDefaultCloseOperation(this.EXIT_ON_CLOSE);
      this.setVisible(true);

   }

   public void createJFile(String fname){
      System.out.println("The filename is : " + fname);
      Process p;
      try {
         p = Runtime.getRuntime().exec("mkdir " + fname);
         p = Runtime.getRuntime().exec("touch " + fname + "/" + fname + ".java");
      } 
      catch(Exception e){
         e.printStackTrace();
      }
   }

   public void actionPerformed(ActionEvent e){
      if(e.getSource() == sendButton){
         System.out.println("Pressed the send button");
         createJFile(textA.getText());
      }
      else if(e.getSource() == saveButton){
         SaveFile sf = new SaveFile();
         sf.getSaveName();
      }
   }   

   public static void main(String[] args){
      //String[] myButtons = {"DELETE","QUERY"};
      //System.out.println(myButtons[0]);
      Afour test = new Afour(); 
   }




}
