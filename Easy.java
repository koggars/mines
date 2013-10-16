import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;


public class Easy extends JFrame
{
 private String user;
 private String difficulty = "e";
 
 public Easy(String user)
 {
   this.user = user;
   new MinesMain(user, difficulty);
   setVisible(false);
   
 }
}