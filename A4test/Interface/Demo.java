import java.io.*;

public class Demo implements SomeInterface{
   public Demo(){
      System.out.println(returnString());
      System.out.println(returnInteger());
      displayInteger(45);
   }

   public String returnString(){
      String aString = "astringtoreturn";
      return aString;
   }

   public int returnInteger(){
      int anInteger = 12;
      return anInteger;
   }
   public void displayInteger(int theInt){
      System.out.println("the integer is " + theInt);

   }
   public static void main(String[] args){
      new Demo();
   }

}
