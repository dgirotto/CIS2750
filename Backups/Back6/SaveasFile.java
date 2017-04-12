import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SaveasFile extends JFrame implements ActionListener{
   private JTextField saveasField;
   private JButton saveasBtn;
   private BufferedWriter bw;
   private FileWriter fw;

   public SaveasFile(){
      saveasField = new JTextField(20);
      saveasBtn = new JButton("Save As");
      saveasBtn.addActionListener(this);
      this.setSize(150,150);
      this.setLayout(new GridLayout(3,1));
      this.setDefaultCloseOperation(1);
      this.add(new JLabel("Enter a name for the file:"));
      this.add(saveasField);
      this.add(saveasBtn);
      this.setVisible(true); 
   }
   public void actionPerformed(ActionEvent e){
      if(e.getSource()==saveasBtn){
         Afour.filename = saveasField.getText();
         Afour.updateStatusBar();
         try{
            fw = new FileWriter(Afour.filename);
            bw = new BufferedWriter(fw);
            bw.write((Afour.textA).getText());
            bw.close();
         }
         catch(Exception ex){
            new PromptErr(ex.getMessage());
         }
         this.dispose(); 
      }
   }


}
