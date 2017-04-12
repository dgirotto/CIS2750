import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class testfile extends JFrame implements ActionListener{
private JPanel uPan1;
private JPanel uPan2;
private JPanel uPan3;
private JTextArea textArea;
private JLabel[] myLabels = new JLabel[2];
private JButton[] myBtns = new JButton[2];
private JTextField[] myFields = new JTextField[2];
public testfile(){
this.setTitle("daniel girotto");
this.setLayout(new GridLayout(3,1));
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
uPan1 = new JPanel();
uPan1.setLayout(new GridLayout(2,2));
uPan2 = new JPanel();
uPan2.setLayout(new FlowLayout());
uPan3 = new JPanel();
textArea = new JTextArea(15,15);
uPan3.add(textArea);
myLabels[0] = new JLabel("StudentID");
uPan1.add(myLabels[0]);
myFields[0] = new JTextField(100);
uPan1.add(myFields[0]);
myLabels[1] = new JLabel("TestMarks");
uPan1.add(myLabels[1]);
myFields[1] = new JTextField(100);
uPan1.add(myFields[1]);
myBtns[0] = new JButton("CompileButton");
myBtns[0].addActionListener(this);
uPan2.add(myBtns[0]);
myBtns[1] = new JButton("ANOTHERONE");
myBtns[1].addActionListener(this);
uPan2.add(myBtns[1]);
this.getContentPane().add(uPan1);
this.getContentPane().add(uPan2);
this.getContentPane().add(uPan3);
this.setSize(300,300);
}
public void actionPerformed(ActionEvent e){
if(e.getSource()==myBtns[0]){
textArea.setText("Pushed " + myBtns[0].getActionCommand());}
if(e.getSource()==myBtns[1]){
textArea.setText("Pushed " + myBtns[1].getActionCommand());}
}
public static void main(String[] args){
testfile myGui = new testfile();
myGui.setVisible(true);
}
}
