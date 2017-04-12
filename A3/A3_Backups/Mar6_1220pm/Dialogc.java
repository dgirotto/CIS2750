/* 
** Dialogc.java
** Name: Daniel Girotto
** ID: 0783831
** Friday Feb 13 2015
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Dialogc extends JFrame implements ActionListener{
   static{
      System.loadLibrary("JNIpm");
   }
   native String getTitleName();
   native String getLValName(String listName); /* "listName" = the parameter which requires extracting */
   native int parseFile(String filename); /* call the function implemented inside Version1.c and pass a file pointer */
   
   private String nameOfFile;
   private String newFilename;
   private JButton sendString;
   private JTextField inputField;
   private JFrame inputFrame;

   private JFrame frame;
   private JButton openFBtn;
   
   private String filename;
   private String fieldName;
   private Process myProc;

   private JPanel mainPan;
   private JPanel secondPan;
   private JPanel thirdPan;

   private JButton newBtn;
   private JButton openBtn;
   private JButton saveBtn;
   private JButton saveAsBtn;
   private JButton cmpRunBtn;
 
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

   public Dialogc(){ /* default constructor for the GUI */
      mainPan = new JPanel(); /* embeds first, second, third and fourth panels */
      secondPan = new JPanel();
      secondPan.setLayout(new FlowLayout());
      thirdPan = new JPanel();

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

      secondPan.add(newBtn);
      secondPan.add(openBtn);
      secondPan.add(saveBtn);
      secondPan.add(saveAsBtn);
      secondPan.add(cmpRunBtn);

      textArea = new JTextArea(25,45);
      JScrollPane scrollPanel = new JScrollPane(textArea);
      thirdPan.add(scrollPanel);
	  
      mainPan.add(secondPan);
      mainPan.add(thirdPan);
      
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
      helpItemOpt = new JMenuItem("Help");    
      helpItemOpt.addActionListener(this);
      helpOpt.add(helpItemOpt);
      aboutOpt = new JMenuItem("About");
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
      this.setTitle("Dialogc");
      this.setSize(550,450);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setVisible(true);
   }
   
   public int compileSource(){ /* reads the input from JTextArea and writes it to a file */
      try{
         BufferedWriter fileOut = new BufferedWriter(new FileWriter(filename)); /* need to change the file name */
         textArea.write(fileOut);
         fileOut.close();
         if(this.parseFile(filename)==0){
            System.err.println("Error! Parse failed");
            return 0;
         }
      }
      catch(IOException e){
         System.err.println("Caught IOException: " +  e.getMessage());
      }
      return 1;	  
   }
   
   public void readSource(String filename){
      FileReader reader = null;
      try{
         reader = new FileReader(filename);
         textArea.read(reader,filename);
         reader.close();
      }
      catch(IOException e){
         System.err.println("Caught IOException: " +  e.getMessage());
      }
   }
   public void promptFileName(){
      inputFrame = new JFrame();
      inputFrame.setLayout(new GridLayout(3,1));
      inputField = new JTextField(20);
      sendString = new JButton("Create");
      sendString.addActionListener(this);
      inputFrame.add(new JLabel("Please enter a name for your saved file\n"));
      inputFrame.add(inputField);
      inputFrame.add(sendString);
      inputFrame.setSize(100,100);
      inputFrame.setDefaultCloseOperation(1);
      inputFrame.setVisible(true);
   }
   
   public void promptHelpMenu(){
      JFrame myFrame = new JFrame("Help");
      JTextArea infoArea = new JTextArea(40,40);
      myFrame.add(infoArea);
      myFrame.setSize(100,100);
	  infoArea.setText("Welcome to the README\n");
	  infoArea.append("NOTE: The command - 'LD_LIBRARY_PATH=.' has been omitted from the Makefile\n");
	  infoArea.append("in order for the program to run properly you must explicitly type the command out\n");
	  infoArea.append("after running 'Make'. Then you must run 'Make' again in order for the changes to take\n");
	  infoArea.append("effect. The default name for a text file (unless explicitly changed by the user through\n");
	  infoArea.append("a 'Save' or 'Save As' is 'testfile.config'. Therefore as a result once compiled, you will\n");
	  infoArea.append("be left with the resulting files: testfile.java and testfile.class provided your parameters\n");
	  infoArea.append("compiled successfully.\n\nALSO NOTE: You MUST compile from the option menu before clicking the 'Run' button!");
	  infoArea.setEditable(false);
      myFrame.setDefaultCloseOperation(1);
      myFrame.setVisible(true);
   }
   
   public void generateGuiCode(){
      int fieldCtr=0,btnCtr=0,x=0;
      nameOfFile = filename.replaceFirst("[.][^.]+$", "");
      while((this.getLValName("fields"))!=null){
         fieldCtr++; /* extract the number of fields */
      }
      while((this.getLValName("buttons"))!=null){
         btnCtr++; /* extract the number of buttons */
      }
      try{
      FileWriter fw = new FileWriter(nameOfFile+".java");
      BufferedWriter output = new BufferedWriter(fw);

      output.write("import javax.swing.*;\n");
      output.write("import java.awt.event.*;\n");
      output.write("import java.awt.*;\n");

      output.write("public class " + nameOfFile + " extends JFrame implements ActionListener{\n");
      output.write("private JPanel uPan1;\n");
      output.write("private JPanel uPan2;\n");
      output.write("private JPanel uPan3;\n");

      output.write("private JTextArea textArea;\n");
      output.write("private JLabel[] myLabels = new JLabel["+fieldCtr+"];\n");
      output.write("private JButton[] myBtns = new JButton["+btnCtr+"];\n");
      output.write("private JTextField[] myFields = new JTextField["+fieldCtr+"];\n");

      output.write("public "+nameOfFile+"(){\n"); /* beginning of no argument constructor */
      output.write("this.setTitle(\""+this.getTitleName()+"\");\n");
      output.write("this.setLayout(new GridLayout(3,1));\n");
      output.write("this.setDefaultCloseOperation(EXIT_ON_CLOSE);\n");	

      output.write("uPan1 = new JPanel();\n");
      output.write("uPan1.setLayout(new GridLayout("+fieldCtr+",2));\n");
	  
      output.write("uPan2 = new JPanel();\n");
      output.write("uPan2.setLayout(new FlowLayout());\n");
	  
      output.write("uPan3 = new JPanel();\n");
      output.write("textArea = new JTextArea(15,15);\n");
      output.write("uPan3.add(textArea);\n");

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
      output.write("}\n"); /* closes no argument constructor */   

      output.write("public void actionPerformed(ActionEvent e){\n");
      for(x=0;x<btnCtr;x++){
         output.write("if(e.getSource()==myBtns["+x+"]){\n");
         output.write("textArea.setText(\"Pushed \" + myBtns["+x+"].getActionCommand());}\n");
      }
      output.write("}\n"); /*closes action listener */
      output.write("public static void main(String[] args){\n");
      output.write(nameOfFile+" myGui = new "+nameOfFile+"();\n");
      output.write("myGui.setVisible(true);\n");
      output.write("}\n"); /* closes main function */
      output.write("}\n"); /* closes program */
      output.close();
   }
   catch(IOException e){
      e.printStackTrace();
   } 

  }
   
   
   
   public void actionPerformed(ActionEvent e) {
      if(e.getSource()==newBtn || e.getSource()==newOpt){
	     
      }
      else if(e.getSource()==openBtn || e.getSource()==openOpt){
         frame = new JFrame("Open file");
         frame.setLayout(new FlowLayout());
         frame.setDefaultCloseOperation(1);
         openFBtn = new JButton("Select File");
         openFBtn.addActionListener(this);
         frame.add(openFBtn);
         frame.setSize(100,100);
         frame.setVisible(true);
      
      }
      else if(e.getSource()==saveBtn || e.getSource()==saveAsBtn || e.getSource()==saveAsOpt || e.getSource()==saveOpt){
		 promptFileName();
      }
      else if(e.getSource()==cmpRunBtn || e.getSource()==compItemOpt || e.getSource()==compRunOpt){
         if(compileSource()==1){
            generateGuiCode();
            try{
               myProc = Runtime.getRuntime().exec("javac " + this.nameOfFile + ".java");
               if(e.getSource()==cmpRunBtn || e.getSource()==compRunOpt){
                  myProc = Runtime.getRuntime().exec("java " + this.nameOfFile);
                  BufferedReader in = new BufferedReader(
                  new InputStreamReader(myProc.getInputStream()));
               }   
            }
            catch(IOException e2){
               e2.printStackTrace();
            }
         }
         else{
            System.err.println("Compile unsuccessful!\n");
         }
      }
	  else if(e.getSource()==quitOpt){
             System.exit(0);	  
	  }
	  else if(e.getSource()==helpItemOpt){
         promptHelpMenu();		  
	  }
	  else if(e.getSource()==aboutOpt){
		  
		  
	  }
	  else if(e.getSource()==javaCompOpt){
		  
		  
	  }
	  else if(e.getSource()==compOptsOpt){
		  
		  
	  }
	  else if(e.getSource()==javaRTOpt || e.getSource()==runTimeOptsOpt){
		  
		  
	  }
	  else if(e.getSource()==workDirOpt){
	  
	  }
	  else if(e.getSource()==openFBtn){
         JFileChooser fileChooser = new JFileChooser();
         int rVal = fileChooser.showOpenDialog(null);
         if(rVal == JFileChooser.APPROVE_OPTION){
            File theFile = fileChooser.getSelectedFile();
            filename = theFile.getName();
         }
	     if(this.parseFile(filename)==0){
            System.err.println("Error! Parse failed");
         }
		 else{
		    readSource(filename);
		 } 
      }
	  else if(e.getSource()==sendString){
             
	     newFilename = inputField.getText();
         try{
            BufferedWriter fileOut = new BufferedWriter(new FileWriter(newFilename)); /* need to change the file name */

            textArea.write(fileOut);
            fileOut.close();
         }
         catch(IOException ex){
            System.err.println("Caught IOException: " +  ex.getMessage());
         }
         inputFrame.dispose();	  
	  }
	  
   }
   
   public static void main(String[] args){
      Dialogc a2 = new Dialogc(); /* invokes the no-argument constructor */
      a2.filename="testfile.config";
      a2.showGUI(); /* function which displays the GUI */

   }

}












