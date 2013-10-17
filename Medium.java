import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.filechooser.FileView;


public class Medium extends JFrame
{
 private String user;
 private String difficulty = "m";
 private String location = "games/medium/";

 private File loadFile;
 
 final JFileChooser fc = new JFileChooser(location);
 public Medium(String user)
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
}
}