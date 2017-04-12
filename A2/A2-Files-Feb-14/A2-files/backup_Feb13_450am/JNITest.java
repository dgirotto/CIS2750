import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class JNITest extends JFrame implements ActionListener{
   static{
      System.loadLibrary("JNITest");
   }
   native String getTitleName();
   native String getLValName(String listName); /* "listName" = the parameter which requires extracting */
   native int parseFile(String filename); /* call the function implemented inside Version1.c and pass a file pointer */
   
   private String filename;
   private String fieldName;
   
   private JPanel mainPan;
   private JPanel secondPan;
   private JPanel thirdPan;
   private JPanel fourthPan;

   private JButton newBtn;
   private JButton openBtn;
   private JButton saveBtn;
   private JButton saveAsBtn;
   private JButton cmpRunBtn;
   private JButton stopBtn;
 
   private JMenuBar menuBar;
   private JMenu fileOpt;
   private JMenu compOpt;
   private JMenu confOpt;
   private JMenu helpOpt;
   
   /* menu items for "FILE" */
   private JMenuItem newOpt;
   private JMenuItem openOpt;
   private JMenuItem saveOpt;
   private JMenuItem saveAsOpt;
   private JMenuItem quitOpt;
   
   /* menu items for "COMPILE" */
   private JMenuItem compItemOpt;
   private JMenuItem compRunOpt;
   
   /* menu items for "CONFIG" */
   private JMenuItem javaCompOpt;
   private JMenuItem compOptsOpt;
   private JMenuItem javaRTOpt;
   private JMenuItem runTimeOptsOpt;
   private JMenuItem workDirOpt;
   
   /* menu items for "HELP" */
   private JMenuItem helpItemOpt;
   private JMenuItem aboutOpt;
   
   private JTextArea textArea;

   public JNITest(){ /* default constructor for the GUI */
      mainPan = new JPanel(); /* embeds first, second, third and fourth panels */
      //mainPan.setLayout(new GridLayout(3,1)); /* sets layout to GridLayout -- 3 rows and 1 column */
      secondPan = new JPanel();
      secondPan.setLayout(new FlowLayout());
      thirdPan = new JPanel(); /* layout ?? */
      fourthPan = new JPanel(); /* layout ?? */

      newBtn = new JButton("New"); /* the following buttons are embedded inside "secondPan" */
      newBtn.addActionListener(this);
      openBtn = new JButton("Open");
      openBtn.addActionListener(this);
      saveBtn = new JButton("Save");
      saveBtn.addActionListener(this);
      saveAsBtn = new JButton("Save As");
      saveAsBtn.addActionListener(this);
      cmpRunBtn = new JButton("Run");
      cmpRunBtn.addActionListener(this);
      stopBtn = new JButton("Stop");
      stopBtn.addActionListener(this);

      secondPan.add(newBtn);
      secondPan.add(openBtn);
      secondPan.add(saveBtn);
      secondPan.add(saveAsBtn);
      secondPan.add(cmpRunBtn);
      secondPan.add(stopBtn);

	  textArea = new JTextArea(25,45);
      JScrollPane scrollPanel = new JScrollPane(textArea);
	  thirdPan.add(scrollPanel);
	  
      mainPan.add(secondPan);
      mainPan.add(thirdPan);
      mainPan.add(fourthPan);
      
      fileOpt = new JMenu("File");
      newOpt = new JMenuItem("New");
      newOpt.addActionListener(this);
      fileOpt.add(newOpt);
      openOpt = new JMenuItem("Open");
      openOpt.addActionListener(this);
      fileOpt.add(openOpt);
      saveOpt = new JMenuItem("Save");
      saveOpt.addActionListener(this);
      fileOpt.add(saveOpt);
      saveAsOpt = new JMenuItem("Save As");
      saveAsOpt.addActionListener(this);
      fileOpt.add(saveAsOpt);
      quitOpt = new JMenuItem("Quit");
      quitOpt.addActionListener(this);
      fileOpt.add(quitOpt);
      
      compOpt = new JMenu("Compile");
      compItemOpt = new JMenuItem("Compile");
      compItemOpt.addActionListener(this);
      compOpt.add(compItemOpt);
      compRunOpt = new JMenuItem("Compile and Run");
      compRunOpt.addActionListener(this);
      compOpt.add(compRunOpt);
      
      confOpt = new JMenu("Config");
      javaCompOpt = new JMenuItem("Java Compiler Options");
      javaCompOpt.addActionListener(this);
      confOpt.add(javaCompOpt);
      compOptsOpt = new JMenuItem("Compiler Options");
      compOptsOpt.addActionListener(this);
      confOpt.add(compOptsOpt);
      javaRTOpt = new JMenuItem("Java Run Time");
      javaRTOpt.addActionListener(this);
      confOpt.add(javaRTOpt);
      runTimeOptsOpt = new JMenuItem("Run Time Options");
      runTimeOptsOpt.addActionListener(this);
      confOpt.add(runTimeOptsOpt);
      workDirOpt = new JMenuItem("Working Directory Options");
      workDirOpt.addActionListener(this);
      confOpt.add(workDirOpt);
      
      helpOpt = new JMenu("Help");
      helpItemOpt = new JMenu("Help");      
      helpItemOpt.addActionListener(this);
      helpOpt.add(helpItemOpt);
      aboutOpt = new JMenu("About");
      aboutOpt.addActionListener(this);
      helpOpt.add(aboutOpt);
      
      menuBar = new JMenuBar();
      menuBar.add(fileOpt);
      menuBar.add(compOpt);
      menuBar.add(confOpt);
      menuBar.add(helpOpt);
      setJMenuBar(menuBar);
 
      this.getContentPane().add(mainPan); /* adds the main panel to the JFrame object instanciated by the user */
      
   }

   public void showGUI(){
      this.setTitle("an example");
      this.setSize(550,650);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setVisible(true);
   }
   
   public void compileSource(){ /* reads the input from JTextArea and writes it to a file */
      try{
         System.out.println("reading " + filename);
         BufferedWriter fileOut = new BufferedWriter(new FileWriter(filename)); /* need to change the file name */
         textArea.write(fileOut);
         fileOut.close();
         if(this.parseFile(filename)==1){
            System.out.println("Returned 1");
         }
         else{
            System.out.println("Returned 0. Parse failed");
         }
         //else{
         //   System.out.println("Parse step failed");
         //}
         //System.out.println("the name of the file is " + filename);
      }
      catch(IOException e){
         System.err.println("Caught IOException: " +  e.getMessage());
      }
      
            //while((fieldName=getLValName("fields"))!=null){
      //    System.out.println(fieldName);
      //}
	  
   }
   
   
   public void generateGuiCode(){
   
   
int fieldCtr=0,btnCtr=0,x=0;
while((this.getLValName("fields"))!=null){
   fieldCtr++; /* extract the number of fields */
}
while((this.getLValName("buttons"))!=null){
   btnCtr++; /* extract the number of buttons */
}
try{

FileWriter fw = new FileWriter("usergui.txt");
BufferedWriter output = new BufferedWriter(fw);

output.write("import javax.swing.*;\n");
output.write("import java.awt.event.*;\n");
output.write("import java.awt.*;\n");
output.write("public class " + this.getTitleName() + " extends JFrame implements ActionListener{\n");
output.write("private JPanel uPan1;\n");
output.write("private JPanel uPan2;\n");
output.write("private JPanel uPan3;\n");
output.write("private JLabel[] myLabels = new JLabel["+fieldCtr+"];\n");
output.write("private JButton[] myBtns = new JButton["+fieldCtr+"];\n");
output.write("private JTextField[] myFields = new JTextField["+fieldCtr+"];\n");

output.write("public "+this.getTitleName()+"(){\n"); //beginning of no argument constructor
output.write("this.setLayout(new GridLayout(3,1));\n");
	
output.write("JPanel uPan1 = new JPanel();\n");
output.write("uPan1.setLayout(new GridLayout("+fieldCtr+",2));\n");
	  
output.write("JPanel uPan2 = new JPanel();\n");
output.write("uPan2.setLayout(new FlowLayout());\n");
	  
output.write("JPanel uPan3 = new JPanel();\n");

for(x=0;x<fieldCtr;x++){
   output.write("myLabels["+x+"] = new JLabel(\""+getLValName("fields")+"\");\n");
   output.write("uPan1.add(myLabels["+x+"]);\n");
   output.write("myFields["+x+"] = new JTextField(100);\n");
   output.write("uPan1.add(myFields["+x+"]);\n");
}
for(x=0;x<btnCtr;x++){
   output.write("myBtns["+x+"] = new JButton(\""+getLValName("buttons")+"\");\n");
   output.write("myBtns["+x+"].addActionListener(this);\n");
   output.write("uPan2.add(myBtns["+x+"]);\n");
}
output.write("this.getContentPane().add(uPan1);\n");
output.write("this.getContentPane().add(uPan2);\n");
output.write("this.getContentPane().add(uPan3);\n");
	  
output.write("this.setSize(300,300);\n");
output.write("this.setDefaultCloseOperation(1);\n");
output.write("}\n"); //closes no argument constructor	   
output.write("public void actionPerformed(ActionEvent e){\n");
output.write("}\n"); //closes action listener
output.write("public static void main(String[] args){\n");
output.write(this.getTitleName()+" myGui = new "+this.getTitleName()+"();\n");
output.write("myGui.setVisible(true);\n");
output.write("}\n"); // closes main function
output.write("}\n"); // closes program
output.close();
} catch(IOException e){
  e.printStackTrace();
} 

  }
   
   
   
   public void actionPerformed(ActionEvent e) {
	  if(e.getSource()==newBtn){
	     
	  }
	  else if(e.getSource()==openBtn){
		  
		  
	  }
	  else if(e.getSource()==saveBtn){
		  
		  
	  }
	  else if(e.getSource()==saveAsBtn){
		  
		  
	  }
	  else if(e.getSource()==cmpRunBtn){
	     compileSource();
	  
         //generateGuiCode();    
		 System.out.println("pressed cmpRunBtn");
	  }
	  else if(e.getSource()==stopBtn){
		  
		  
	  }
	}
   
   public static void main(String[] args){
      JNITest a2 = new JNITest(); /* invokes the no-argument constructor */
      a2.filename="config.txt";
      //a2.showGUI(); /* function which displays the GUI */

      if(a2.parseFile(a2.filename)==1){
         System.out.println("Returned 1");
      }
      else{
         System.out.println("Returned 0");
      }
      //a2.compileSource();
      a2.generateGuiCode();
   }


}






