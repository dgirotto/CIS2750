import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Afour extends JFrame implements ActionListener{
   private JButton sendButton;
   private JButton saveButton;
   private JButton openButton;
   private JButton helpButton;
   public static JTextArea textA;
 
   public Afour(){
      textA = new JTextArea(5,20);
      sendButton = new JButton("Send Wrk Dir");
      sendButton.addActionListener(this);
      saveButton = new JButton("Save");
      saveButton.addActionListener(this);
      openButton = new JButton("Open File");
      openButton.addActionListener(this);
      helpButton = new JButton("Help");
      helpButton.addActionListener(this);
      
      this.setLayout(new GridLayout(5,1));
      this.add(textA);
      this.setSize(200,200);

      this.add(sendButton);
      this.add(saveButton);
      this.add(openButton);
      this.add(helpButton);
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
      else if(e.getSource() == openButton){
         ChooseFile cf = new ChooseFile();
      }
      else if(e.getSource() == helpButton){
         HelpMenu hm = new HelpMenu();
      }
   }   

   public static void main(String[] args){
      //String[] myButtons = {"DELETE","QUERY"};
      //System.out.println(myButtons[0]);
      Afour test = new Afour(); 
   }




}
