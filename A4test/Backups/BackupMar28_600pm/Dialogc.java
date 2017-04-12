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
 
   private JButton sendButton;
   private JButton newButton;
   private JButton saveButton;
   private JButton saveasButton;
   private JButton openButton;
   private JButton helpButton;
   private JButton compButton;
   private JButton runButton;
   public static JTextField statusField;

   public Dialogc(){
      modF = false;
      newF = true;

      filename = "untitled";
      textA = new JTextArea(5,20);

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

      sendButton = new JButton("Send Wrk Dir");
      sendButton.addActionListener(this);

      newButton = new JButton("New File");
      newButton.addActionListener(this);

      saveButton = new JButton("Save");
      saveButton.addActionListener(this);

      saveasButton = new JButton("Save as");
      saveasButton.addActionListener(this);

      openButton = new JButton("Open File");
      openButton.addActionListener(this);

      helpButton = new JButton("Help");
      helpButton.addActionListener(this);
      
      compButton = new JButton("Compile");
      compButton.addActionListener(this);

      runButton = new JButton("Run");
      runButton.addActionListener(this);

      statusField = new JTextField();

      this.setLayout(new GridLayout(10,1));
      this.add(textA);
      this.setSize(200,200);

      this.add(sendButton);
      this.add(newButton);
      this.add(saveButton);
      this.add(saveasButton);
      this.add(openButton);
      this.add(helpButton);
      this.add(compButton);
      this.add(runButton);
      this.add(statusField);
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
         Dialogc.statusField.setText(filename + " : [MODIFIED]");
      }
      else{
         Dialogc.statusField.setText(filename + " : [UNMODIFIED]");
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
      if(theAction == "open"){
         ChooseFile cf = new ChooseFile();
      }
      else if(theAction == "new"){
         textA.setText("");
         filename="untitled";
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
               myProc = Runtime.getRuntime().exec("javac " + noExtension + ".java");
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
      if(e.getSource() == sendButton){
         System.out.println("Pressed the send button");
         createJFile(textA.getText());
      }
      else if(e.getSource() == newButton){
         if(modF == true){ /* contents of current file have been modified */
            new PromptSave("new",newF);
         }
         else{
            takeAction("new");
         }
      }
      else if(e.getSource() == compButton){
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
      else if(e.getSource() == saveButton){
         new SaveFile(null);
      }
      else if(e.getSource() == saveasButton){
         new SaveasFile(null); // null = no action taken after save
      }
      else if(e.getSource() == openButton){
         if(modF == true){
            System.out.println("changes were made... prompting save\n");
            new PromptSave("open",newF);
         }
         else{
            takeAction("open");
         }
      }
      else if(e.getSource() == helpButton){
         new HelpMenu();
      }
   }   

   public static void main(String[] args){
      //String[] myButtons = {"DELETE","QUERY"};
      //System.out.println(myButtons[0]);
      Dialogc test = new Dialogc(); 
      updateStatusBar();
   }




}

