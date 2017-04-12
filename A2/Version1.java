import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Version1 extends JFrame{
  
   private JPanel mainPan;
   private JPanel firstPan;
   private JPanel secondPan;
   private JPanel thirdPan;
   private JPanel fourthPan;

   private JButton newBtn;
   private JButton openBtn;
   private JButton saveBtn;
   private JButton saveAsBtn;
   private JButton cmpRunBtn;
   private JButton stopBtn;
   
   public Version1(){ /* default constructor for the GUI */
      mainPan = new JPanel(); /* embeds first, second, third and fourth panels */
      mainPan.setLayout(new GridLayout(4,1)); /* sets layout to GridLayout -- 4 rows and 1 column */  
      firstPan = new JPanel(); /* layout ?? */
      secondPan = new JPanel();
      secondPan.setLayout(new FlowLayout());
      thirdPan = new JPanel(); /* layout ?? */
      fourthPan = new JPanel(); /* layout ?? */

      newBtn = new JButton("New"); /* the following buttons are embedded inside "secondPan" */
      openBtn = new JButton("Open");
      saveBtn = new JButton("Save");
      saveAsBtn = new JButton("Save As");
      cmpRunBtn = new JButton("Run");
      stopBtn = new JButton("Stop");   
      
      secondPan.add(newBtn);
      secondPan.add(openBtn);
      secondPan.add(saveBtn);
      secondPan.add(saveAsBtn);
      secondPan.add(cmpRunBtn);
      secondPan.add(stopBtn);
      
      mainPan.add(firstPan);
      mainPan.add(secondPan);
      mainPan.add(thirdPan);
      mainPan.add(fourthPan);
      this.getContentPane().add(mainPan); /* adds the main panel to the JFrame object instanciated by the user */
      

   }   


   public static void main(String[] args){
      Version1 theGui = new Version1();
      theGui.setTitle("an example");
      theGui.setSize(500,400);
      theGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      theGui.setVisible(true);

  }


}
