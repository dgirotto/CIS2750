import java.io.*;
import java.util.*;

public class TestExec{
  public static void main(String[] args){
    String commands[] = {"ls -l","ls -al","mkdir testDir"};
    int i;
    String line;

    try {
      Process p;

      for(i=0;i<commands.length;i++){
        p = Runtime.getRuntime().exec(commands[i]);
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        line = null;
        while((line = in.readLine()) != null){
          System.out.println(line);
        } 
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }
   
  } 
}
