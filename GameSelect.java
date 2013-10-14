import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;

public class GameSelect extends JFrame {
 
  private JPanel placeHolder;
  private JButton easy;
  private JButton medium;
  private JButton hard;
  String user;
  
//Constructor

/////////////////////////////////////////////////////////////////////////////////////////////////////

public GameSelect(String user)
  {
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setTitle("Select Game- Minesweeper");
     
     this.user = user;

     
     placeHolder = new JPanel();
     easy = new JButton("Easy");
     medium = new JButton("Medium");
     hard = new JButton("Hard");
     
     add(placeHolder,BorderLayout.CENTER);
     placeHolder.add(easy, BorderLayout.CENTER);
     placeHolder.add(medium, BorderLayout.CENTER);
     placeHolder.add(hard, BorderLayout.CENTER);
     
     this.pack();
     setResizable(false);
     setVisible(true);
     setLocationRelativeTo(null);
     
/////////////////////////////////////////////////////////////////////////////////////////////////////
    
// Action Listeners
     
/////////////////////////////////////////////////////////////////////////////////////////////
     
     easy.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e)
     {
       
        new Easy(GameSelect.this.user);
        setVisible(false);
          
     }
     });
     medium.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e)
     {

        new Medium(GameSelect.this.user);
        setVisible(false);    
       
          
     }
     });
     hard.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent e)
     {

        new Hard(GameSelect.this.user);
        setVisible(false);   
       
          
     }
     });
//////////////////////////////////////////////////////////////////////////////////////////////
  }
}