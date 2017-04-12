import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JNITest extends JFrame implements ActionListener{
   static{
      System.loadLibrary("JNITest");
   }
   
   //public native String returnString(String fileName);
   native int parseFile(String filename); /* call the function implemented inside Version1.c and pass a file pointer */
   
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

   public JNITest(){ /* default constructor for the GUI */
      mainPan = new JPanel(); /* embeds first, second, third and fourth panels */
      mainPan.setLayout(new GridLayout(3,1)); /* sets layout to GridLayout -- 3 rows and 1 column */
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
      this.setSize(500,400);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setVisible(true);
   }
   
   public void generateUserGUI(){
	  int fieldCtr=5,btnCtr=3,x=0;
	  String fNames[] = {"one","two","three","four","five"};
	  String bNames[] = {"btn1","btn2","btn3"};
	  // FIRST GET THE NUMBER OF FIELDS/LABELS AND BUTTONS FROM JNI FUNCTION
	  // getTitleName(), getFieldName(), and getBtnName() are all JNI functions
	  
	  JLabel myLabels[] = new JLabel[fieldCtr];
	  JTextField myFields[] = new JTextField[fieldCtr];
	  JButton myBtns[] = new JButton[btnCtr];
	  
	  //JFrame uFrame = new JFrame(getTitleName());
	  JFrame uFrame = new JFrame("The title"); 
	  uFrame.setLayout(new GridLayout(3,1));
	
	  JPanel uPan1 = new JPanel(); /* houses the labels and text fields */
	  uPan1.setLayout(new GridLayout(fieldCtr,2));
	  
	  JPanel uPan2 = new JPanel(); /* houses the buttons */
	  uPan2.setLayout(new FlowLayout());
	  
	  JPanel uPan3 = new JPanel(); /* houses the status window */
	  // CREATE JTEXTAREA AND ADD IT TO uPan3
	    
	  for(x=0;x<fieldCtr;x++){
         myLabels[x] = new JLabel(fNames[x]);
         //myLabels[x] = new JLabel(getLValName("fields"));
         uPan1.add(myLabels[x]);
         myFields[x] = new JTextField(100);
         uPan1.add(myFields[x]);
	  }
	  for(x=0;x<btnCtr;x++){
	     myBtns[x] = new JButton(bNames[x]);
	     //myBtns[x] = new JButton(getLValName("buttons")); 
	     myBtns[x].addActionListener(this); // "this" ???
	     uPan2.add(myBtns[x]);
	  }
	   
	  uFrame.getContentPane().add(uPan1);
	  uFrame.getContentPane().add(uPan2);
	  uFrame.getContentPane().add(uPan3);
	  
	  uFrame.setSize(300,300);
	  uFrame.setDefaultCloseOperation(1);
	  uFrame.setVisible(true);
   
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
         generateUserGUI();    
		 System.out.println("pressed cmpRunBtn");
	  }
	  else if(e.getSource()==stopBtn){
		  
		  
	  }
	}
   
   public static void main(String[] args){
      JNITest a2 = new JNITest(); /* invokes the no-argument constructor */
      //a2.showGUI(); /* function which displays the GUI */
      String filename = "testfile.txt";
      if(a2.parseFile(filename)==1){
         System.out.println("Returned 1");
      }
      else{
         System.out.println("Returned 0");
      }

   }


}

