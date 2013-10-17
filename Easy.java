import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.filechooser.FileView;


public class Easy extends JFrame
{
 private String user;
 private String difficulty = "e";
 private String location = "games/easy/";
 private File loadFile;

         
 final JFileChooser fc = new JFileChooser(location);
 
 public Easy(String user)
 {
   this.user = user;
   fc.setFileView(new FileView() {
    @Override
    public Boolean isTraversable(File f) {
         return location.equals(f);
    }
 });
   
   int returnVal = fc.showOpenDialog(this);
   loadFile = fc.getSelectedFile();
   new MinesMain(user, difficulty);
   setVisible(false);
   
 }
}