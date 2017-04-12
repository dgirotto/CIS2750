import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class QueryListener implements ActionListener{

   public void actionPerformed(ActionEvent e){
      String command = e.getActionCommand();
System.out.println("hello");
      if(command=="QUERY"){
         System.out.println("Pressed QUERY");
      }

   }


}
