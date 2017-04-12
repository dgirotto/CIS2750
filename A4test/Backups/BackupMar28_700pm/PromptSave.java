import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PromptSave extends JFrame implements ActionListener{
   private boolean status;
   private String action;
   private JButton saveFile;
   private JButton dontSave;
   public PromptSave(String theAction,boolean theStatus){
System.out.println("inside PromptSave... call " + theAction + " after");
      action = theAction; // action to take after window is dismissed
      status = theStatus; // whether the file is new or not
      saveFile = new JButton("Save");
      saveFile.addActionListener(this);
      dontSave = new JButton("Don't Save");
      dontSave.addActionListener(this);

      this.setTitle("Save your file?");
      this.add((new JLabel("Changes were made to your file, would you like to save them?")),BorderLayout.NORTH);
      this.add(saveFile,BorderLayout.EAST);
      this.add(dontSave,BorderLayout.WEST);
      this.setSize(250,200);
      this.setDefaultCloseOperation(1);
      this.setVisible(true);
   }

   public void doAction(){
      if(action == "open"){
         Dialogc.takeAction("open");
      }
      else if(action == "new"){
         Dialogc.takeAction("new");
      }
      else if(action == "compile"){
         Dialogc.takeAction("compile");
      }

   }

   public void actionPerformed(ActionEvent e){
      this.setVisible(false);
      this.dispose();
      if(e.getSource() == saveFile){
         if(status == true){ /* file does not have a name (is new) */
            //prompt for name of file (call save as)
            SaveasFile sa = new SaveasFile(null);
         }
         else{
            Dialogc.saveFile();
         }      
      }
      else if(e.getSource() == dontSave){
         doAction();
      }
   }

}


