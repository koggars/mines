package gui;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;



public class SplashScreen extends JFrame
{
  private JPanel placeHolder;
  private JButton newUser, generateBtn;
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
     generateBtn = new JButton("Generate Games");
   
     add(placeHolder,BorderLayout.CENTER);
     
     try
     {
      BufferedImage myPicture = ImageIO.read(new File("images/splash.png"));
      JLabel picLabel = new JLabel(new ImageIcon(myPicture));
      placeHolder.add(picLabel,BorderLayout.CENTER);
     }
        catch(IOException e)
     {
     }
     path = "users/";
     folder = new File(path);
     File[] listOfFiles = folder.listFiles();
     String[] listOfFileNames = new String[listOfFiles.length];

     for(int i = 0; i < listOfFileNames.length; i++)
     {
         listOfFileNames[i] = listOfFiles[i].getName();
     }

     JComboBox previousUsers;

     previousUsers = new JComboBox(listOfFileNames);
     
     
     placeHolder.add(newUser, BorderLayout.WEST);
     placeHolder.add(previousUsers, BorderLayout.SOUTH);
     placeHolder.add(generateBtn, BorderLayout.SOUTH);
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

         //if user Canceled dont do anything
         if(userName == null || userName.isEmpty())
             return;
     
         
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

     generateBtn.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e)
         {

         }
     });

     previousUsers.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent f){
         JComboBox cb = (JComboBox)f.getSource();
         String user = cb.getSelectedItem().toString();
         new GameSelect("users/"+user);
         setVisible(false);
       }
     });
     
///////////////////////////////////////////////////////////////////////////////////////////////       
  
     
     
  }
}
