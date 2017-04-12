import java.io.*;

public class GenCode{
   public GenCode(){
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
}
