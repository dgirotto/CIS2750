import java.io.*;

public class TestEx{

   public void process(String age){
      int ageNo = Integer.parseInt(age);
      if(ageNo<0){
         throw new InvalidAgeException();
      }

   } 

}
