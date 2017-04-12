import java.io.*;

public class JFileWriter{
   void writeFile(String filename){
      /* check if file already exists in the directory */      
      try{
         File outFile = new File(filename);
         BufferedWriter output = new BufferedWriter(new FileWriter(outFile));
         if(1==0){
         output.write("JLabel myLabels[] = new JLabel[fieldCtr];\n");
	 output.write("JTextField myFields[] = new JTextField[fieldCtr];\n");
	 output.write("JButton myBtns[] = new JButton[btnCtr];\n");
	  
	 output.write("JFrame uFrame = new JFrame(\"The title\");\n"); 
	 output.write("uFrame.setLayout(new GridLayout(3,1));\n");
	
	 output.write("JPanel uPan1 = new JPanel();\n");
	 output.write("uPan1.setLayout(new GridLayout(fieldCtr,2));\n");
	  
	 output.write("JPanel uPan2 = new JPanel();\n");
	 output.write("uPan2.setLayout(new FlowLayout());\n");
	  
	 output.write("JPanel uPan3 = new JPanel();\n");
	    
	 output.write("for(x=0;x<fieldCtr;x++){\n");
         output.write("myLabels[x] = new JLabel(fNames[x]);\n");
         output.write("uPan1.add(myLabels[x]);\n");
         output.write("myFields[x] = new JTextField(100);\n");
         output.write("uPan1.add(myFields[x]);}\n");
	 output.write("for(x=0;x<btnCtr;x++){\n");
	 output.write("myBtns[x] = new JButton(bNames[x]);\n"); 
	 output.write("myBtns[x].addActionListener(this);\n");
	 output.write("uPan2.add(myBtns[x]);}\n");
	   
	 output.write("uFrame.getContentPane().add(uPan1);\n");
	 output.write("uFrame.getContentPane().add(uPan2);\n");
         output.write("uFrame.getContentPane().add(uPan3);\n");
	  
	 output.write("uFrame.setSize(300,300);\n");
         output.write("uFrame.setDefaultCloseOperation(1);\n");
	 output.write("uFrame.setVisible(true);\n");
         }
         output.write("#include <stdio.h>\n\n");
         output.write("int main(){\n");
	output.write("int x=45;\n");
	output.write("printf(\"The value of x is %d\",x);\n");
output.write("return(0);\n");
output.write("}\n");

         output.close();
      }
      catch(IOException e){
         e.printStackTrace();
      }

   }

   
   public static void main(String[] args){
      JFileWriter myfile = new JFileWriter();
      myfile.writeFile("testing.c");


   }




}
