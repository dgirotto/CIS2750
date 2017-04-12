import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteListener implements ActionListener{

   public void actionPerformed(ActionEvent e){
      System.out.println("Just pressed delete");
      String command = e.getActionCommand();
      if(command=="DELETE"){
         System.out.println("Pressed DELETE");
      }

   }


}
