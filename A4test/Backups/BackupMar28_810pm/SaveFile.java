import java.io.*;

public class SaveFile{
   public SaveFile(String theAction){
      System.out.println("Saving : " + Dialogc.filename); //save the file as current filename
      try{
         FileWriter fw = new FileWriter(Dialogc.filename);
         BufferedWriter bw = new BufferedWriter(fw);
         fw.write((Dialogc.textA).getText());
         fw.close();
         Dialogc.newF=false;
         Dialogc.modF=false;
         Dialogc.updateStatusBar();
System.out.println("updated status bar");
         if(theAction!=null){
            Dialogc.takeAction(theAction);
         }
      }
      catch(Exception ex){
         new PromptErr(ex.getMessage());
      }
   }
}
