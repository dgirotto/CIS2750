import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class A4test extends JFrame implements ActionListener{
   private JTextArea textA;
   private JButton sendButton;
   public A4test(){
      textA = new JTextArea(5,20);

      sendButton = new JButton("Send Wrk Dir");
      sendButton.addActionListener(this);

      this.setLayout(new BorderLayout());
      this.add(textA,BorderLayout.NORTH);
      this.setSize(200,200);

      this.add(sendButton,BorderLayout.SOUTH);
      setDefaultCloseOperation(this.EXIT_ON_CLOSE);
      this.setVisible(true);

      SaveFile s1 = new SaveFile();
      System.out.println(s1.getSaveName());
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
   }   

   public static void main(String[] args){
      //String[] myButtons = {"DELETE","QUERY"};
      //System.out.println(myButtons[0]);
      A4test test = new A4test(); 
   }




}
