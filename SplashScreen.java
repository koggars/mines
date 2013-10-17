import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;



public class SplashScreen extends JFrame
{
  private JPanel placeHolder;
  private JButton newUser;
  private JComboBox previousUsers;
  String userName;
  String path;
  File folder;
  
//Constructor
  
//////////////////////////////////////////////////////////////////////////////////////////////////
  
  
public SplashScreen()
  {
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setTitle("Welcome - Minesweeper v0.1");
     
     placeHolder = new JPanel();
     newUser = new JButton("Create User");
 
   
     add(placeHolder,BorderLayout.CENTER);
     
     try
     {
      BufferedImage myPicture = ImageIO.read(new File("./splash.png"));
      JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      placeHolder.add(picLabel,BorderLayout.CENTER);
     }
        catch(IOException e)
     {
     }
     path = "users/";
     folder = new File(path);
     File[] listOfFiles = folder.listFiles();
    
     JComboBox previousUsers;
     previousUsers = new JComboBox(listOfFiles);
     
     
     placeHolder.add(newUser, BorderLayout.WEST);
     placeHolder.add(previousUsers, BorderLayout.SOUTH);
     this.pack();
     setResizable(false);
     setVisible(true);
     setLocationRelativeTo(null);

////////////////////////////////////////////////////////////////////////////////////////////////
     
//Action Listeners

////////////////////////////////////////////////////////////////////////////////////////////////
     
     newUser.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent e)
       {
            
         userName = JOptionPane.showInputDialog("Enter user name:",null);
         
     
         
         File dir = new File("users/" + userName);
         if(dir.isDirectory())
          {
           JOptionPane.showMessageDialog(null,"Username already exists please select another");
           
          }
         else
         {
          dir.mkdir();
          new GameSelect(dir.toString());
          setVisible(false);
         }
          
        }
       }); 
     previousUsers.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent f){
         JComboBox cb = (JComboBox)f.getSource();
         String user = cb.getSelectedItem().toString();
         new GameSelect(user);
         setVisible(false);
         
       
        
         
        
       }
     });
     
///////////////////////////////////////////////////////////////////////////////////////////////       
  
     
     
  }
}
