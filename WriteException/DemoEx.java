import java.io.*;

public class DemoEx{
   public static void main(String[] args){
      String age = new String();

      System.out.print("Enter your age: ");
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      try{
         age = br.readLine();
      }
      catch(TextEx e){
         e.printStackTrace();
      }

      TestEx test = new TestEx();
      test.process(age);
   }
}
