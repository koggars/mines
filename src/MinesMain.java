package src;

import src.IO.*;
import gui.Board;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.*;


// Main Function for the Mines App

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
   private MineSaveFileIO mineSFIO = new MineSaveFileIO();
   
   private String user;
   private String difficulty;

   private MineSaveFile currentFile;
   
   private BufferedWriter saveOut;
  

 
  public MinesMain(String userName, String difficulty)
  {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Minesweeper v0.1");
    user = userName;
    this.difficulty = difficulty;
     
   final JFileChooser fc = new JFileChooser(user);
   FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Mines Files" , "mines");
   fc.setFileFilter(filter);
    
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

    prefDim = new Dimension(242,290);
    
    
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
    
   
     gameBoard = new Board(statusBar);
     add(gameBoard);
     setLocationRelativeTo(null);
     setResizable(false);
     this.repaint();
     gameBoard.repaint();
     this.pack();
     setVisible(true);
     
     saveGame.addActionListener(new ActionListener(){
       
     public void actionPerformed(ActionEvent f){
         mineSFIO.saveMineFile(currentFile);
     }
    });
    loadGame.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent f){
         fc.showOpenDialog(gameBoard);
         currentFile = mineSFIO.loadMineFile(fc.getSelectedFile());
       }
     });
    
     newGame.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent f){
         
       gameBoard.newGame();
       gameBoard.repaint();
       }
     });
     
     solveGame.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent f){
         
   gameBoard.solveGame();
       }
     });
    
  }
  
  
public String getDifficulty()
{
    return difficulty;  
}
 
}
