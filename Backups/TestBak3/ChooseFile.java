import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;


public class ChooseFile{
   private JFileChooser fileChooser;
   private BufferedReader br;
   private File file;
   int returnVal;
   String currentLine;

   public ChooseFile(){
      fileChooser = new JFileChooser();
      returnVal = fileChooser.showOpenDialog(null);

      if(returnVal == JFileChooser.APPROVE_OPTION){
         file = fileChooser.getSelectedFile();
         try{
            br = new BufferedReader(new FileReader(file));
            while((currentLine = br.readLine())!=null){
               System.out.println(currentLine);
            }
         }
         catch(Exception readEx){
            readEx.printStackTrace();
         }
      }
   }


}
