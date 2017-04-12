import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SaveFile extends JFrame implements ActionListener{
   private String savename;
   private JTextField savefield;
   private JButton saveBtn;
   public SaveFile(){
      savefield = new JTextField(20);
      saveBtn = new JButton("Save!");
      saveBtn.addActionListener(this);
      this.setSize(150,150);
      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(1);
      this.add(savefield,BorderLayout.EAST);
      this.add(saveBtn,BorderLayout.WEST);
      this.setVisible(true); 
   }
   public String getSaveName(){
      return savename;
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==saveBtn){
         

      }
   }


}
