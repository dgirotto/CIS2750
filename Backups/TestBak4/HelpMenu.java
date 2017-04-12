import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class HelpMenu extends JFrame{
   private JTextArea infoArea;
   public HelpMenu(){
      infoArea = new JTextArea(40,40);
      infoArea.setText("Welcome to the README\n\nName:Daniel Girotto\nStudent ID: 0783831\nEmail:dgirotto@uoguelph.ca");
      infoArea.append("NOTE: The command - 'LD_LIBRARY_PATH=.' has been omitted from the Makefile\n");
      infoArea.append("in order for the program to run properly you must explicitly type the command out\n");
      infoArea.append("after running 'Make'. Then you must run 'Make' again in order for the changes to take\n");
      infoArea.append("effect. The default name for a text file (unless explicitly changed by the user through\n");
      infoArea.append("a 'Save' or 'Save As' is 'testfile.config'. Therefore as a result once compiled, you will\n");
      infoArea.append("be left with the resulting files: testfile.java and testfile.class provided your parameters\n");
      infoArea.append("compiled successfully.\n\nALSO NOTE: You MUST compile from the option menu before clicking the 'Run' button!");
      infoArea.setEditable(false);

      this.setTitle("Help");
      this.add(infoArea);
      this.setSize(250,200);
      this.setDefaultCloseOperation(1);
      this.setVisible(true);
   }

}


