import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MinesMain extends JFrame
{
   private JLabel statusBar;
  
   private JPanel placeHolder;
  
   private JMenuBar menuPlace;
  
   private JMenu file;
   private JMenu edit;
   private JMenu actions;
   private JMenu help;
  
   private JMenuItem newGame;
   private JMenuItem saveGame;
   private JMenuItem loadGame;
   private JMenuItem undoLast;
   private JMenuItem redoLast;
   private JMenuItem solveGame;
   private JMenuItem highScores;
   private JMenuItem helpPopup;
 
   private JMenuItem aboutPopup;
 
   private JLabel StatusBar;
 
   private Dimension prefDim;
   
   private Board gameBoard;
   
   private int[] saveArray;
 
   private File saveFile;
   
   private String user;
   private String difficulty;
   
   private BufferedWriter saveOut;
   DateFormat dateFormat = new SimpleDateFormat(" ddMMyyyy hh:mm:ss");
   Date date = new Date();
 
  public MinesMain(String userName, String difficulty)
  {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Minesweeper v0.1");
    user = userName;
    this.difficulty = difficulty;
    System.out.print(userName);
    
// Object Instantiation


    statusBar = new JLabel("");


    menuPlace = new JMenuBar();
 
    placeHolder = new JPanel();
   
    file = new JMenu("File");
    edit = new JMenu("Edit");
    actions = new JMenu("Actions");
    help = new JMenu("Help");
    
    newGame = new JMenuItem("New Game");
    saveGame = new JMenuItem("Save Game");
    loadGame = new JMenuItem("Load Game");
    undoLast = new JMenuItem("Undo");
    redoLast = new JMenuItem("Redo");
    solveGame = new JMenuItem("Solve Current Game");
    highScores = new JMenuItem("High Scores");
    helpPopup = new JMenuItem("Help");
    aboutPopup = new JMenuItem("About Minesweeper");

    prefDim = new Dimension(255,310);
    
    
// Adding of components
    add(statusBar, BorderLayout.SOUTH);
    add(menuPlace, BorderLayout.NORTH);
    add(placeHolder, BorderLayout.CENTER);
  
    menuPlace.add(file);
    file.add(newGame);
    file.add(saveGame);
    file.add(loadGame);

    menuPlace.add(edit);
    edit.add(undoLast);
    edit.add(redoLast);
 
    menuPlace.add(actions);
    actions.add(solveGame);
    actions.add(highScores);
  
    menuPlace.add(help);
    help.add(helpPopup);
    help.add(aboutPopup);
    
    
 
    this.setPreferredSize(prefDim);
    
     this.pack();
     gameBoard = new Board(statusBar);
     add(gameBoard);
     setLocationRelativeTo(null);
     setResizable(true);
     setVisible(true);
     repaint();

     
     
     saveGame.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent f){
       String tempDifficulty = getDifficulty() ;
       saveArray = gameBoard.getField();
       Path path = Paths.get(user + "/" + dateFormat.format(date) + tempDifficulty + ".mines");
        try
       {
       Files.createDirectories(path.getParent());
       Files.createFile(path);
       }
       catch(java.io.IOException e){
         
       }
       
      
       }
     
     
     });
    
 }
  public String getDifficulty()
  {
    return difficulty;
  }
 
}
