import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SaveasFile extends JFrame implements ActionListener{

   private String savename;
   private JTextField saveasField;
   private JButton saveasBtn;
   public SaveasFile(){
      saveasField = new JTextField(20);
      saveasBtn = new JButton("Save As");
      saveasBtn.addActionListener(this);
      this.setSize(150,150);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(1);
      this.add((new JLabel("Enter a name for the file:")),BorderLayout.WEST);
      this.add(saveasField,BorderLayout.EAST);
      this.add(saveasBtn,BorderLayout.SOUTH);
      this.setVisible(true); 
   }
   public String getSaveName(){
      return savename;
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==saveasBtn){
         Afour.filename = saveasField.getText();
         Afour.updateStatusBar();
         saveasField.setText("You tried to save " + saveasField.getText());
      }
   }


}
