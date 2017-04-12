import java.io.*;

public class Test implements ToImplement{
   public String returnString(){
      return("a string");
   }
   public void takeInt(int theInt){
      System.out.println("Took " + theInt);
   }
   public static void main(String[] args){
      Test t = new Test();
      System.out.println(t.returnString());
      t.takeInt(40);
   }
}
