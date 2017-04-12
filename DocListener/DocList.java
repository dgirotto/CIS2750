import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class DocList{

   JFrame myFrame = new JFrame();
   JTextField myField = new JTextField();
   
   public DocList(){
      myField.getDocument().addDocumentListener(new DocumentListener(){
         public void changedUpdate(DocumentEvent arg0){
        System.out.println("changed");    
         }
         public void insertUpdate(DocumentEvent arg0){
System.out.println("insert");
         }
         public void removeUpdate(DocumentEvent arg0){
System.out.println("remove");
         }
      });
      myFrame.add(myField);
      myFrame.setDefaultCloseOperation(myFrame.EXIT_ON_CLOSE);
      myFrame.setVisible(true);   

   }



   public static void main(String[] args){
      DocList test = new DocList();


   }

}
