import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Afour extends JFrame implements ActionListener{
   public static boolean modF;
   public static boolean newF;

   public static JTextArea textA;
   public static String filename;

   private JButton sendButton;
   private JButton newButton;
   private JButton saveButton;
   private JButton saveasButton;
   private JButton openButton;
   private JButton helpButton;
   public static JTextField statusField;

   public Afour(){
      modF = true;
      newF = false;
      textA = new JTextArea(5,20);
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
      
      statusField = new JTextField();

      this.setLayout(new GridLayout(8,1));
      this.add(textA);
      this.setSize(200,200);

      this.add(sendButton);
      this.add(newButton);
      this.add(saveButton);
      this.add(saveasButton);
      this.add(openButton);
      this.add(helpButton);
      this.add(statusField);
      setDefaultCloseOperation(this.EXIT_ON_CLOSE);
      this.setVisible(true);

   }

   public void GenCode(){
      int fieldCtr=0,btnCtr=0,x=0;
      String noExtension = filename.replaceFirst("[.][^.]+$", "");
      while((this.getLValName("fields"))!=null){
         fieldCtr++; /* extract the number of fields */
      }
      while((this.getLValName("buttons"))!=null){
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
         output.write(noExtension+" myGui = new "+nameOfFile+"();\n");
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
      System.out.println("Saving : " + Afour.filename);
      try{
         FileWriter fw = new FileWriter(Afour.filename);
         BufferedWriter bw = new BufferedWriter(fw);
         fw.write((Afour.textA).getText());
         fw.close();
      }
      catch(Exception ex){
         new PromptErr(ex.getMessage());
      }
   }


   public static void updateStatusBar(){
      Afour.statusField.setText(filename);
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
      }
   }

   public void actionPerformed(ActionEvent e){
      if(e.getSource() == newButton){
         if(modF == true){ /* contents of current file have been modified */
            new PromptSave("new",newF);
         }
         else{
            takeAction("new");
         }
      }
      else if(e.getSource() == saveButton){
         new SaveFile(null);
      }
      else if(e.getSource() == saveasButton){
         new SaveasFile(null);
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
      Afour test = new Afour(); 
   }




}
