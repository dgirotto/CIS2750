import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class GUI extends JFrame implements ActionListener{
  private FileWriter pw;
  private JTextArea textarea;
  private String myString;
  private JLabel myLabel;
  private JButton send;
  public GUI(){
    myLabel = new JLabel();
    send = new JButton("Send text");
    send.addActionListener(this);
    JPanel myPanel = new JPanel();
    myPanel.setLayout(new GridLayout(3,1));
    textarea = new JTextArea(15,25);
    JScrollPane scrollPanel = new JScrollPane(textarea);
    myPanel.add(scrollPanel);
    myPanel.add(myLabel);
    myPanel.add(send);
    this.getContentPane().add(myPanel);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400,250);
    this.setVisible(true);
  }
  public void actionPerformed(ActionEvent e){

    if(e.getSource()==send){
       try{
          BufferedWriter fileOut = new BufferedWriter(new FileWriter("test.txt")); 
          textarea.write(fileOut);
       }
       catch(IOException exc){
          System.err.println("Caught IOException: " +  exc.getMessage());
       }
       myLabel.setText(textarea.getText());
       myString=textarea.getText();
       System.out.println(myString);
    }

  }


  public static void main(String[] args){
    GUI  myGui = new GUI();
        
  }




}
