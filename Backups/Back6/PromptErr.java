import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PromptErr extends JFrame implements ActionListener{
   private JButton dismiss;
   public PromptErr(String error){
      this.setTitle("An Error Occurred!");
      this.setLayout(new BorderLayout());
      dismiss = new JButton("Dismiss");
      dismiss.addActionListener(this);
      this.add((new JLabel(error)),BorderLayout.NORTH);
      this.add(dismiss,BorderLayout.SOUTH);
      this.setSize(150,150);
      this.setDefaultCloseOperation(1);
      this.setVisible(true);
   }
   
   public void actionPerformed(ActionEvent e){
      this.setVisible(false);
      this.dispose();
   }

}
