import java.io.*;

public class MakeDir{
   public static void main(String[] args){
      String fname = "mydirectory";
      Process p;
      try{
         p = Runtime.getRuntime().exec("mkdir " + fname);
         p = Runtime.getRuntime().exec("touch " + fname + "/afile.txt");
      }
      catch(Exception e){
         e.printStackTrace();
      }

   }


}
