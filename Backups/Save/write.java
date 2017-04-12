import java.io.*;

public class write{
   private BufferedWriter bw;
   private File fname;
   private String toFile;
   public write(String name){
      toFile = "hello";
      fname = new File(name);
      try{
         bw = new BufferedWriter(new FileWriter(fname));
         bw.write(toFile);
         bw.close();
      }
      catch(IOException ex){
         ex.printStackTrace();
      }


   }

   public static void main(String[] args){
      write w = new write(args[0]);
   }

}
