import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ChooseFile{
   private JFileChooser fileChooser;
   private BufferedReader br;
   private File file;
   private File workdir;
   int returnVal;
   String toReturn;
   String currentLine;

   public ChooseFile(){
      fileChooser = new JFileChooser();
      workdir = new File(System.getProperty("user.dir"));
      fileChooser.setCurrentDirectory(workdir);
      returnVal = fileChooser.showOpenDialog(null);
      
      if(returnVal == JFileChooser.APPROVE_OPTION){
         file = fileChooser.getSelectedFile();
         Dialogc.filename = file.getName();
         try{
            br = new BufferedReader(new FileReader(file));
            while((currentLine = br.readLine())!=null){
               if(toReturn != null){
                 toReturn = toReturn + "\n" + currentLine;
               }
               else{ /* toReturn is null */
                  toReturn = currentLine;
               }
            }
            Dialogc.textA.setText(toReturn);
            Dialogc.newF=false;
            Dialogc.modF=false;
            Dialogc.updateStatusBar();
         }
         catch(Exception readEx){
            readEx.printStackTrace();
         }
      }
   }
}

