import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Graphics extends JFrame implements ActionListener{
   JButton[] buttons = new JButton[4];
   String img_names[] = {"new.png","open.png","save.png","run.png"};
   ImageIcon[] img_icons = new ImageIcon[4];
   Image[] images = new Image[4];
   public Graphics(){
      int i=0;
      setSize(300,300);
      setLayout(new BorderLayout());
      JPanel b_container = new JPanel();
      b_container.setLayout(new FlowLayout());

      for(i=0;i<4;i++){
         images[i] = new Image(img_names[i]);
         images[i].getScaledInstance(25,25,Image.SCALE_DEFAULT);
         img_icons[i] = new ImageIcon(images[i]);
         buttons[i] = new JButton(images[i]);
         buttons[i].addActionListener(this);
         buttons[i].setOpaque(false);
         buttons[i].setContentAreaFilled(false);
         buttons[i].setBorderPainted(false);
         buttons[i].setFocusPainted(false);
         b_container.add(buttons[i]);
      }


      add(b_container,BorderLayout.NORTH);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
   }

   public void actionPerformed(ActionEvent e){
      if(e.getSource()  == buttons[0]){
         System.out.println("clicked new");
      }
      else{
         System.out.println("did not click new");
      }
   }
   public static void main(String[] args){
      new Graphics();
   }



}
