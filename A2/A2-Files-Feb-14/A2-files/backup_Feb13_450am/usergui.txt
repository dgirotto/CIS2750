import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class daniel extends JFrame implements ActionListener{
private JPanel uPan1;
private JPanel uPan2;
private JPanel uPan3;
private JLabel[] myLabels = new JLabel[3];
private JButton[] myBtns = new JButton[3];
private JTextField[] myFields = new JTextField[3];
public daniel(){
this.setLayout(new GridLayout(3,1));
JPanel uPan1 = new JPanel();
uPan1.setLayout(new GridLayout(3,2));
JPanel uPan2 = new JPanel();
uPan2.setLayout(new FlowLayout());
JPanel uPan3 = new JPanel();
myLabels[0] = new JLabel("Student ID");
uPan1.add(myLabels[0]);
myFields[0] = new JTextField(100);
uPan1.add(myFields[0]);
myLabels[1] = new JLabel("Test Marks");
uPan1.add(myLabels[1]);
myFields[1] = new JTextField(100);
uPan1.add(myFields[1]);
myLabels[2] = new JLabel("Final Grade");
uPan1.add(myLabels[2]);
myFields[2] = new JTextField(100);
uPan1.add(myFields[2]);
myBtns[0] = new JButton("Compile Button");
myBtns[0].addActionListener(this);
uPan2.add(myBtns[0]);
myBtns[1] = new JButton("Run Button");
myBtns[1].addActionListener(this);
uPan2.add(myBtns[1]);
this.getContentPane().add(uPan1);
this.getContentPane().add(uPan2);
this.getContentPane().add(uPan3);
this.setSize(300,300);
this.setDefaultCloseOperation(1);
}
public void actionPerformed(ActionEvent e){
}
public static void main(String[] args){
daniel myGui = new daniel();
myGui.setVisible(true);
}
}
