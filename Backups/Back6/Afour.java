import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Afour extends JFrame implements ActionListener{
   public static boolean modF;
   public static boolean newF;
   public static String filename;
   public static JTextArea textA;

   private JButton sendButton;
   private JButton newButton;
   private JButton saveButton;
   private JButton saveasButton;
   private JButton openButton;
   private JButton helpButton;
   public static JTextField statusField;

   public Afour(){
PromptErr newerr = new PromptErr("FAIL");
      modF = true;
      newF = false;
      textA = new JTextArea(5,20);
      sendButton = new JButton("Send Wrk Dir");
      sendButton.addActionListener(this);

      newButton = new JButton("New File");
      newButton.addActionListener(this);

      saveButton = new JButton("Save");
      saveButton.addActionListener(this);

      saveasButton = new JButton("Save as");
      saveasButton.addActionListener(this);

      openButton = new JButton("Open File");
      openButton.addActionListener(this);

      helpButton = new JButton("Help");
      helpButton.addActionListener(this);
      
      statusField = new JTextField();

      this.setLayout(new GridLayout(8,1));
      this.add(textA);
      this.setSize(200,200);

      this.add(sendButton);
      this.add(newButton);
      this.add(saveButton);
      this.add(saveasButton);
      this.add(openButton);
      this.add(helpButton);
      this.add(statusField);
      setDefaultCloseOperation(this.EXIT_ON_CLOSE);
      this.setVisible(true);

   }

   public static void updateStatusBar(){
      Afour.statusField.setText(filename);
   }

   public static void saveFile(){
//save the file as current filename 
System.out.println("Saving : " + Afour.filename);

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
   public static void takeAction(String theAction){  // temporary fix???
      if(theAction == "open"){
         ChooseFile cf = new ChooseFile();
      }
      else if(theAction == "new"){
//create new file 
      }
   }

   public void actionPerformed(ActionEvent e){
      if(e.getSource() == sendButton){
         System.out.println("Pressed the send button");
         createJFile(textA.getText());
      }
      else if(e.getSource() == saveButton){
         SaveFile sf = new SaveFile();
      }
      else if(e.getSource() == saveasButton){
         SaveasFile saf = new SaveasFile();
      }
      else if(e.getSource() == openButton){
         if(modF == true){
System.out.println("changes were made... prompting save\n");
            PromptSave pf = new PromptSave("open",newF);
         }
         else{
            Afour.takeAction("open");
         }
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
