//get compile, run, and doclistener to work
//working directory problems
//modified on new file
//write custom exception (A3)

import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Dialogc extends JFrame implements ActionListener{
   static{
      System.loadLibrary("JNIpm");
   }

   native static String getTitleName();
   native static String getLValName(String listName); /* "listName" = the parameter which requires extracting */
   native static int parseFile(String filename); /* call the function implemented inside Version1.c and pass a file pointer */

   public static boolean modF;
   public static boolean newF;

   public static JTextArea textA;
   public static String filename;
   private static Process myProc;
   private String jTextUpdate; // detects updates in the JTextArea
   public static String noExtension;
 
   private JButton newButton;
   private JButton saveButton;
   private JButton saveasButton;
   private JButton openButton;
   private JButton compButton;
   private JButton runButton;
   public static JTextField statusField;

   private JPanel mainPan;
   private JPanel secondPan;
   private JPanel thirdPan; 
   public static JLabel statusBar;  // FIX THIS

   private JMenu fileOpt;
   private JMenuItem newOpt;
   private JMenuItem openOpt;
   private JMenuItem saveOpt;
   private JMenuItem saveAsOpt;
   private JMenuItem quitOpt;

   private JMenu compOpt;
   private JMenuItem compItemOpt;
   private JMenuItem compRunOpt;
   
   private JMenu confOpt;
   private JMenuItem javaCompOpt;
   private JMenuItem compOptsOpt;
   private JMenuItem javaRTOpt;
   private JMenuItem runTimeOptsOpt;
   private JMenuItem workDirOpt;
   private JMenuItem compileMode;

   private JMenu helpOpt;
   private JMenuItem helpItemOpt;
   private JMenuItem aboutOpt;
   private JMenuBar menuBar;

   public Dialogc(){
      modF = false;
      newF = true;
      filename = "untitled";

      mainPan = new JPanel(); /* embeds first, second, third and fourth panels */
      secondPan = new JPanel();
      secondPan.setLayout(new FlowLayout());
      thirdPan = new JPanel();

      newButton = new JButton("New File");
      newButton.addActionListener(this);

      saveButton = new JButton("Save");
      saveButton.addActionListener(this);

      saveasButton = new JButton("Save as");
      saveasButton.addActionListener(this);

      openButton = new JButton("Open File");
      openButton.addActionListener(this);
      
      compButton = new JButton("Compile");
      compButton.addActionListener(this);

      runButton = new JButton("Run");
      runButton.addActionListener(this);

      secondPan.add(newButton);
      secondPan.add(openButton);
      secondPan.add(saveButton);
      secondPan.add(saveasButton);
      secondPan.add(compButton);
      secondPan.add(runButton);

      textA = new JTextArea(25,45);
      textA.getDocument().addDocumentListener(new DocumentListener(){
         public void insertUpdate(DocumentEvent arg){
            modF=true;
            updateStatusBar();
         }
         public void removeUpdate(DocumentEvent arg){
            modF=true;
            updateStatusBar();
         }
         public void changedUpdate(DocumentEvent arg){ 
            modF=true;
            updateStatusBar();
         }
      });
      JScrollPane scrollPanel = new JScrollPane(textA);
      thirdPan.add(scrollPanel);
      mainPan.add(secondPan);
      mainPan.add(thirdPan);

      statusBar = new JLabel();
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

      compileMode = new JMenuItem("Compile Mode");
      compileMode.addActionListener(this);
      confOpt.add(compileMode);
      
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
 
      this.getContentPane().add(mainPan);
      setDefaultCloseOperation(this.EXIT_ON_CLOSE);
      this.setVisible(true);

   }

   public static void GenCode(){
      int fieldCtr=0,btnCtr=0,x=0;
      noExtension = filename.replaceFirst("[.][^.]+$", "");
      while((getLValName("fields"))!=null){
         fieldCtr++; /* extract the number of fields */
      }
      while((getLValName("buttons"))!=null){
         btnCtr++; /* extract the number of buttons */
      }
      try{
         FileWriter fw = new FileWriter(noExtension + ".java");
         BufferedWriter output = new BufferedWriter(fw);

         output.write("import javax.swing.*;\n");
         output.write("import java.awt.event.*;\n");
         output.write("import java.awt.*;\n");

         output.write("public class " + noExtension + " extends JFrame implements ActionListener{\n");
         output.write("private JPanel uPan1;\n");
         output.write("private JPanel uPan2;\n");
         output.write("private JPanel uPan3;\n");

         output.write("private JTextArea textArea;\n");
         output.write("private JLabel[] myLabels = new JLabel["+fieldCtr+"];\n");
         output.write("private JButton[] myBtns = new JButton["+btnCtr+"];\n");
         output.write("private JTextField[] myFields = new JTextField["+fieldCtr+"];\n");

         output.write("public "+noExtension+"(){\n"); /* beginning of no argument constructor */
         output.write("setTitle(\""+getTitleName()+"\");\n");
         output.write("setLayout(new GridLayout(3,1));\n");
         output.write("setDefaultCloseOperation(EXIT_ON_CLOSE);\n");	

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
         output.write("getContentPane().add(uPan1);\n");
         output.write("getContentPane().add(uPan2);\n");
         output.write("getContentPane().add(uPan3);\n");
	  
         output.write("setSize(300,300);\n");
         output.write("}\n"); /* closes no argument constructor */   

         output.write("public void actionPerformed(ActionEvent e){\n");
  
         for(x=0;x<btnCtr;x++){
            output.write("if(e.getSource()==myBtns["+x+"]){\n");
            output.write("textArea.setText(\"Pushed \" + myBtns["+x+"].getActionCommand());}\n");
         }
         output.write("}\n"); /*closes action listener */
         
         getLValName("fields");  
         for(x=0;x<fieldCtr;x++){
            output.write("public String getDC" + getLValName("fields") +"(){\n");
            output.write("String temp = myFields["+x+"].getText();\n");
            output.write("return temp;}\n");
         }

         getLValName("fields");
         for(x=0;x<fieldCtr;x++){
            output.write("public void setDC" + getLValName("fields") +"(String theString){\n");
            output.write("myFields["+x+"].setText(theString);}\n");
         }

         output.write("public void appendToStatusArea(String message){\n");
         output.write("textArea.append(message + \"\\n\");}\n");

         output.write("public static void main(String[] args){\n");
         output.write(noExtension+" myGui = new "+noExtension+"();\n");
         output.write("myGui.setVisible(true);\n");
         output.write("}\n"); /* closes main function */
         output.write("}\n"); /* closes program */
         output.close();
      }
      catch(IOException e){
         e.printStackTrace();
      } 
   }

   public static void saveFile(){
      //save the file as current filename 
      System.out.println("Saving : " + Dialogc.filename);

      noExtension = filename.replaceFirst("[.][^.]+$", "");
      try{
         FileWriter fw = new FileWriter(Dialogc.filename);
         BufferedWriter bw = new BufferedWriter(fw);
         fw.write((Dialogc.textA).getText());
         fw.close();
      }
      catch(Exception ex){
         new PromptErr(ex.getMessage());
      }
   }

   public static void updateStatusBar(){
      if(Dialogc.modF==true){
         Dialogc.statusBar.setText(filename + " : [MODIFIED]");
      }
      else{
         Dialogc.statusBar.setText(filename + " : [UNMODIFIED]");
      }
   }

   public void createJFile(String fname){
      System.out.println("The filename is : " + fname);
      Process p;
      try {
         p = Runtime.getRuntime().exec("mkdir " + fname);
         p = Runtime.getRuntime().exec("touch " + fname + "/" + fname + ".java");
      } 
      catch(Exception e){
         e.printStackTrace();
      }
   }

   public static void takeAction(String theAction){  // temporary fix???
      noExtension = filename.replaceFirst("[.][^.]+$", ""); /* update the extension */

      if(theAction == "open"){
         ChooseFile cf = new ChooseFile();
      }
      else if(theAction == "new"){
         textA.setText("");
         filename="untitled";
         noExtension="untitled";
         newF = true;
         modF = false;
      }
      else if(theAction == "quit"){
         //Dialogc.dispose();
         System.exit(0);
      }
      else if(theAction == "compile"){
         if(parseFile(filename)==1){
            GenCode();
            try{ /* compile the generated java code */
               //if(compilerOpts==null){
               //   compilerOpts="";
               //}
               //myProc = Runtime.getRuntime().exec(javaCompiler + " " + compilerOpts + " " + this.nameOfFile + ".java");
//create directory, compile each of the actionlistener classes individually (for each of the buttons)
               myProc = Runtime.getRuntime().exec("javac " + noExtension + ".java"); // compile the code
//need to compile each of the actionlistener classes (for the buttons) as well
               myProc = Runtime.getRuntime().exec("mkdir " + noExtension); // create directory

//move all generated actionlisteners (.java files) into the new (noExtension) directory as well
               myProc = Runtime.getRuntime().exec("mv " + noExtension + "* " + noExtension + "/"); //move all project files into new directory

            }
            catch(Exception e){
               new PromptErr(e.getMessage());
            }
         }
         else{
            new PromptErr("There was an error parsing the file!");
         }
      }
      else if(theAction == "run"){
         //if(runtimeOpts==null){
            //runtimeOpts="";
         //}
         try{ /* run the generated java code */
            //myProc = Runtime.getRuntime().exec(runtimeCommand + " " + runtimeOpts + " " + this.nameOfFile);
            myProc = Runtime.getRuntime().exec("java " + noExtension);
            //BufferedReader in = new BufferedReader(new InputStreamReader(myProc.getInputStream()));
         }
         catch(Exception e){
            new PromptErr(e.getMessage());
         }
      }
      updateStatusBar();
   }

   public void actionPerformed(ActionEvent e){
      if(e.getSource() == newButton || e.getSource() == newOpt){
         if(modF == true){ /* contents of current file have been modified */
            new PromptSave("new",newF);
         }
         else{
            takeAction("new");
         }
      }
      else if(e.getSource() == compButton || e.getSource() == compItemOpt || e.getSource() == compRunOpt){
//FIX COMPRUNOPT
         if(modF==true){
            new PromptErr("You must save your changes before compiling!");
            if(newF==true){
               new SaveasFile("compile"); 
            }
            else{ //not a new file
               new SaveFile("compile");
            }
         }
         else{
            if(newF==false){ // not modified, but new file (empty)
               takeAction("compile");
            }
            else{
               new PromptErr("Cannot compile an empty file!");
            }
         }
      }
      else if(e.getSource() == runButton){
         if(new File(("./"+noExtension+".class")).isFile()){ // check if .class file exists before running
            System.out.println(noExtension+".class exists!");
            takeAction("run");
         }
         else{
            System.out.println(noExtension +  " does not exist... prompt to compile first");
            new PromptErr(noExtension + ".class does not exist.\nCompiling first...");
            takeAction("compile");
         }
      }
      else if(e.getSource() == saveButton || e.getSource() == saveOpt){
         new SaveFile(null);
      }
      else if(e.getSource() == saveasButton || e.getSource() == saveAsOpt){
         new SaveasFile(null); // null = no action taken after save
      }
      else if(e.getSource() == openButton || e.getSource() == openOpt){
         if(modF == true){
            System.out.println("changes were made... prompting save\n");
            new PromptSave("open",newF);
         }
         else{
            takeAction("open");
         }
      }
      else if(e.getSource() == quitOpt){
         if(modF == true){
            new PromptSave("quit",newF);
         }
         else{
            takeAction("quit");
         }

      }
      else if(e.getSource() == helpItemOpt){
         new HelpMenu();
      }
      else if(e.getSource() == aboutOpt){
         new AboutMenu();
      }

   }   

   public static void main(String[] args){
      //String[] myButtons = {"DELETE","QUERY"};
      //System.out.println(myButtons[0]);
      Dialogc test = new Dialogc(); 
      updateStatusBar();
   }

}


