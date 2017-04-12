/* 
** Dialogc.java
** Name: Daniel Girotto
** ID: 0783831
** Friday Feb 13 2015
*/

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Dialogc extends JFrame implements ActionListener{
   static{
      System.loadLibrary("JNIpm");
   }
   boolean openFlag=false;
   boolean modFlag=false;
   boolean newFlag=false;

   native String getTitleName();
   native String getLValName(String listName); /* "listName" = the parameter which requires extracting */
   native int parseFile(String filename); /* call the function implemented inside Version1.c and pass a file pointer */
   
   /* CONFUSING ... REVIEW THESE : */
   private String jCompiler;
   private String jCompPath;
   private String compOptions;
   private String jrtCommand;
   private String jvmPath;
   private String runTimeOpts;
   private String workingDir;
   /* ^^^^^^^^^^ */
   /* KEEP */
   private JFrame jCompRTFrame;
   private JTextField jCompFld;
   private JButton jCompSetBtn;
   private JTextField jCompPathFld;
   private JButton jCompPathSetBtn;
   private JButton jCompBrowseBtn;
   private JTextField jRTFld;
   private JButton jRTSetBtn;
   private JTextField jRTPathFld;
   private JButton jRTPathSetBtn;
   private JButton jRTBrowseBtn;

   private JFrame compRTOptsFrame;
   private JTextField compOptsFld;
   private JButton compOptsSendBtn;
   private JTextField rtOptsFld;
   private JButton rtOptsSendBtn;

   /* END KEEP */

 
   private JFrame wDirOptsFrame;
   
   private JLabel statusBar;

   private String nameOfFile;
   private String saveasName;

   private JButton sendString;
   private JButton dismiss;
   private JTextField inputField;
   private JFrame inputFrame;
   private JFrame errorFrame;

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
   //private JTextArea textArea = new JTextArea(25,45);
   private String jTextUpdate; /* used to detect changes in the JTextArea */	


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
      textArea.getDocument().addDocumentListener(new DocumentListener(){
         public void insertUpdate(DocumentEvent arg0){
            modFlag=true;
            updateStatus(modFlag);
         }
         public void removeUpdate(DocumentEvent arg0){
            modFlag=true;
            updateStatus(modFlag);
         }
         public void changedUpdate(DocumentEvent arg0){ 
            modFlag=true;
            updateStatus(modFlag);
            /* not fired from plain documents */
         }
      });


      JScrollPane scrollPanel = new JScrollPane(textArea);
      thirdPan.add(scrollPanel);
	  
      mainPan.add(secondPan);
      mainPan.add(thirdPan);

      statusBar = new JLabel("Status:");
      mainPan.add(statusBar);

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
         BufferedWriter fileOut = new BufferedWriter(new FileWriter(filename));
         textArea.write(fileOut);
         fileOut.close();
         if(this.parseFile(filename)==0){
            promptError("Parse failed!");
            return 0;
         }
      }
      catch(IOException e){
         
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
         promptError("Caught IOException: " +  e.getMessage());
      }
   }
   public void promptFileName(){
      inputFrame = new JFrame("Name your new file");
      inputFrame.setLayout(new GridLayout(3,1));
      inputField = new JTextField(30);
      sendString = new JButton("Create");
      sendString.addActionListener(this);
      inputFrame.add(new JLabel("Please enter a name for your saved file\n"));
      inputFrame.add(inputField);
      inputFrame.add(sendString);
      inputFrame.setSize(200,200);
      inputFrame.setDefaultCloseOperation(1);
      inputFrame.setVisible(true);
   }
   public void updateStatus(boolean modified){
      if(modified==true){
         statusBar.setText("Status of " + filename + ": Modified");
      }
      else{
         statusBar.setText("Status of " + filename + ": Unmodified");
      }
   }

   public void jComp_jRT(int type){
      jCompRTFrame = new JFrame();
      jCompRTFrame.setLayout(new GridLayout(3,3));
      if(type==1){ /* Compile options config window */
         jCompRTFrame.setTitle("Configure Java Compiler");
         jCompRTFrame.add(new JLabel("Specify Java Compiler:"));
         jCompFld = new JTextField();
         jCompRTFrame.add(jCompFld); 
         jCompSetBtn = new JButton("Set Compiler");
         jCompSetBtn.addActionListener(this);
         jCompRTFrame.add(jCompSetBtn);

         jCompRTFrame.add(new JLabel("Specify a path:"));
         jCompPathFld = new JTextField();
         jCompRTFrame.add(jCompPathFld); 
         jCompPathSetBtn = new JButton("Set Path");
         jCompPathSetBtn.addActionListener(this);
         jCompRTFrame.add(jCompPathSetBtn);

         jCompRTFrame.add(new JLabel("Browse for a Compiler:"));
         jCompBrowseBtn = new JButton();
         jCompBrowseBtn.addActionListener(this);
         jCompRTFrame.add(jCompBrowseBtn);


      }
      else{ /* RT options config window */
         jCompRTFrame.setTitle("Configure Java Runtime Command");
         jCompRTFrame.add(new JLabel("Specify external command:"));
         jRTFld = new JTextField();
         jCompRTFrame.add(jRTFld);
         jRTSetBtn = new JButton("Set Command");
         jRTSetBtn.addActionListener(this);
         jCompRTFrame.add(jRTSetBtn);

         jCompRTFrame.add(new JLabel("Specify a path:"));
         jRTPathFld = new JTextField();
         jCompRTFrame.add(jRTPathFld);
         jRTPathSetBtn = new JButton("Set Path");
         jRTPathSetBtn.addActionListener(this);
         jCompRTFrame.add(jRTPathSetBtn);

         jCompRTFrame.add(new JLabel("Browse for an external command:"));
         jRTBrowseBtn = new JButton();
         jRTBrowseBtn.addActionListener(this);
         jCompRTFrame.add(jRTBrowseBtn);
      }
      jCompRTFrame.setSize(250,250);
      jCompRTFrame.setDefaultCloseOperation(1);
      jCompRTFrame.setVisible(true);
   }



   public void compOpts_RTOpts(int type){
      compRTOptsFrame = new JFrame();
      compRTOptsFrame.setLayout(new BorderLayout());

      if(type==1){ /* compiler opts config window */
         compRTOptsFrame.setTitle("Configure Compile Options");
         compRTOptsFrame.add((new JLabel("Specify compiler options")),BorderLayout.WEST);
         compOptsFld = new JTextField();
         compRTOptsFrame.add(compOptsFld,BorderLayout.CENTER);
         compOptsSendBtn = new JButton("Send");
         compOptsSendBtn.addActionListener(this);
         compRTOptsFrame.add(compOptsSendBtn,BorderLayout.EAST);
      }
      else{ /* runtime opts config window */
         compRTOptsFrame.setTitle("Configure Runtime Options");
         compRTOptsFrame.add((new JLabel("Specify runtime options:")),BorderLayout.WEST);
         rtOptsFld = new JTextField();
         compRTOptsFrame.add(rtOptsFld,BorderLayout.CENTER);
         rtOptsSendBtn = new JButton("Send");
         rtOptsSendBtn.addActionListener(this);
         compRTOptsFrame.add(rtOptsSendBtn,BorderLayout.EAST);
      }
      compRTOptsFrame.setSize(250,250);
      compRTOptsFrame.setDefaultCloseOperation(1);
      compRTOptsFrame.setVisible(true);
   }



   public void wDirOpts(){
      wDirOptsFrame = new JFrame("Working Directory Options");
      wDirOptsFrame.setLayout(new BorderLayout());

      wDirOptsFrame.setSize(200,200);
      wDirOptsFrame.setDefaultCloseOperation(1);
      wDirOptsFrame.setVisible(true);
   }





   public void promptError(String error){
      errorFrame = new JFrame("An Error Occurred!");
      errorFrame.setLayout(new BorderLayout());
      dismiss = new JButton("Dismiss");
      dismiss.addActionListener(this);
      errorFrame.add((new JLabel(error)),BorderLayout.NORTH);
      errorFrame.add(dismiss,BorderLayout.SOUTH);
      errorFrame.setSize(200,200);
      errorFrame.setDefaultCloseOperation(1);
      errorFrame.setVisible(true);
   }
   
   public void promptHelpMenu(){
      JFrame myFrame = new JFrame("Help");
      JTextArea infoArea = new JTextArea(40,40);
      myFrame.add(infoArea);
      myFrame.setSize(200,200);
	  infoArea.setText("Welcome to the README\n\nName:Daniel Girotto\nStudent ID: 0783831\nEmail:dgirotto@uoguelph.ca");
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
   public void promptAboutMenu(){

   }
   
   public void promptSave(){
      errorFrame = new JFrame("Save your file?");
      errorFrame.setLayout(new BorderLayout());
      dismiss = new JButton("Save File");

      dismiss = new JButton("Don't Save");
      dismiss.addActionListener(this);
      errorFrame.add((new JLabel(error)),BorderLayout.NORTH);
      errorFrame.add(dismiss,BorderLayout.SOUTH);
      errorFrame.setSize(200,200);
      errorFrame.setDefaultCloseOperation(1);
      errorFrame.setVisible(true);
    

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
	     if(modFlag==true){
            if(newFlag==true){
               //promptSaveAs();  /* prompt for a name + save */
               promptFileName();
            }
            else{ /* newFlag==false; there is a project currently open; prompt for save */
               //promptSave();
            }
         }
         else{
            textArea.setText("");
         } 
         newFlag=true;
         openFlag=false;
      }


      else if(e.getSource()==openBtn || e.getSource()==openOpt){
         if(modFlag==true){
            //promptSave();
         }
		 
         frame = new JFrame("Open file");
         frame.setLayout(new FlowLayout());
         frame.setDefaultCloseOperation(1);
         openFBtn = new JButton("Select File");
         openFBtn.addActionListener(this);
         frame.add(openFBtn);
         frame.setSize(200,200);
         frame.setVisible(true);
      
      }
      else if(e.getSource()==saveBtn || e.getSource()==saveOpt || e.getSource()==saveAsBtn || e.getSource()==saveAsOpt ){
         if(e.getSource()==saveBtn || e.getSource()==saveOpt){
            modFlag=false;
            updateStatus(modFlag);
            //save the file
            System.out.println("**** save the file as " + filename);
         }
         else{ /* user selected the "save as" option */
   	        promptFileName();

         }
         if(newFlag==true){
            newFlag=false;
         }

      }
      else if(e.getSource()==cmpRunBtn || e.getSource()==compItemOpt || e.getSource()==compRunOpt){
      /* check if the current file is modified before blindly compiling */
         if(modFlag==1 && newFlag==1 && openFlag==0){

         }
         else if(modFlag==1 && newFlag==0 && openFlag==1){
            
         }
         if(compileSource()==1){
            generateGuiCode();
            try{
//CHANGE THE COMPILE OPTIONS ACCORDING TO THE USER'S SPECIFIED OPTIONS
               /* compile the generated java code */
               myProc = Runtime.getRuntime().exec("javac " + this.nameOfFile + ".java");
               if(e.getSource()==cmpRunBtn || e.getSource()==compRunOpt){
                  myProc = Runtime.getRuntime().exec("java " + this.nameOfFile);
//CHANGE THE RUN OPTIONS ACCORDING TO THE USER'S SPECIFIED OPTIONS
                  BufferedReader in = new BufferedReader(
                  new InputStreamReader(myProc.getInputStream()));
               }   
            }
            catch(IOException e2){
               e2.printStackTrace();
            }
         }
         else{
            promptError("Compile unsuccessful!\n");
         }
      }
	  else if(e.getSource()==quitOpt){
         if(modFlag==true){
            System.out.println("Prompt save");
         }
         System.exit(0);

	  }
	  else if(e.getSource()==helpItemOpt){
         promptHelpMenu();		  
	  }
	  else if(e.getSource()==aboutOpt){
		  
		  
	  }
	  else if(e.getSource()==javaCompOpt){
         jComp_jRT(1); 
	  }
	  else if(e.getSource()==javaRTOpt){
         jComp_jRT(0);
	  }
	  else if(e.getSource()==compOptsOpt){
		 compOpts_RTOpts(1);
	  }
      else if(e.getSource()==runTimeOptsOpt){
         compOpts_RTOpts(0);
      }
	  else if(e.getSource()==workDirOpt){
         wDirOpts();
	  }
      else if(e.getSource()==openFBtn){
         JFileChooser fileChooser = new JFileChooser();
         int rVal = fileChooser.showOpenDialog(null);
         if(rVal == JFileChooser.APPROVE_OPTION){ /* file has been chosen */
            openFlag=true;
            File theFile = fileChooser.getSelectedFile();
            filename = theFile.getName();
            updateStatus(modFlag); /* updates the name of the opened file in the status bar */
	        if(this.parseFile(filename)==0){
               promptError("Error! Parse failed");
            }
		    else{
		       readSource(filename);
		    }
         }
      }
      else if(e.getSource()==sendString){

         saveasName = inputField.getText();

         /* test if filename contains .config extension */
         if(!(saveasName.toLowerCase().contains(".config"))){
            // MUST END IN .CONFIG! ^^^
            saveasName+=".config";
            promptError(saveasName + " does not end in config. its new value: " + saveasName);
            filename = saveasName;
         }
         try{
            BufferedWriter fileOut = new BufferedWriter(new FileWriter(saveasName)); /* need to change the file name */
            textArea.write(fileOut);
            modFlag=false;
            updateStatus(modFlag);
            fileOut.close();
         }
         catch(IOException ex){
            promptError("Caught IOException: " +  ex.getMessage());
         }
         inputFrame.dispose();
      }
      else if(e.getSource()==dismiss){
         errorFrame.dispose();
      }
      else if(e.getSource()==jRTSetBtn){
         
      }
      else if(e.getSource()==jRTPathSetBtn){
         
      }
      else if(e.getSource()==rtOptsSendBtn){
         
      }
      else if(e.getSource()==compOptsSendBtn){
         
      }

   }
  
   
   public static void main(String[] args){
      Dialogc a2 = new Dialogc(); /* invokes the no-argument constructor */
      a2.filename="testfile.config";
      a2.showGUI(); /* function which displays the GUI */


   }

}


