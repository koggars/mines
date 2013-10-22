package gui;

import src.IO.*;
import src.Board;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
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

	private JMenuItem mainMenu;
	private JMenuItem gameMenu;
	private JMenuItem saveGame;
	private JMenuItem quitGame;
	private JMenuItem undoLast;
	private JMenuItem redoLast;
	private JMenuItem solveGame;
	private JMenuItem highScores;
	private JMenuItem helpPopup;

	private JMenuItem aboutPopup;

	private Dimension prefDim;

	private BoardFrame gameBoard;
	private MineSaveFileIO mineSFIO;

	private String user;

	private MineGameFile currentGame;

	public MinesMain(String userName, MineGameFile gameFile)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper "+gameFile.getSeed()+" "+gameFile.getDifficulty()+" - "+userName);
		mineSFIO = new MineSaveFileIO(userName);

		currentGame = gameFile;
		user = userName;
	}

	public void newGame()
	{
		Board currentBoard = new Board(currentGame.getDifficulty(), currentGame.getSeedLong(), currentGame.getMineData());

		initComponents(currentBoard);
		setEventHandlers();
	}

	public void initComponents(Board currentBoard)
	{

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

	  mainMenu = new JMenuItem("Main Menu");
	  gameMenu = new JMenuItem("Game Menu");
	  saveGame = new JMenuItem("Save Game");
	  quitGame = new JMenuItem("Quit");
	  undoLast = new JMenuItem("Undo");
	  redoLast = new JMenuItem("Redo");
	  solveGame = new JMenuItem("Solve Current Game");
	  highScores = new JMenuItem("Scores & Stats");
	  helpPopup = new JMenuItem("Help");
	  aboutPopup = new JMenuItem("About Minesweeper");


	// Adding of components
	  add(statusBar, BorderLayout.SOUTH);
	  add(menuPlace, BorderLayout.NORTH);
	  add(placeHolder, BorderLayout.CENTER);

	  menuPlace.add(file);
	  file.add(mainMenu);
	  file.add(gameMenu);
	  file.add(saveGame);
	  file.add(quitGame);

	  menuPlace.add(edit);
	  edit.add(undoLast);
	  edit.add(redoLast);

	  menuPlace.add(actions);
	  actions.add(solveGame);
	  actions.add(highScores);

	  menuPlace.add(help);
	  help.add(helpPopup);
	  help.add(aboutPopup);

	  gameBoard = new BoardFrame(statusBar, currentBoard);
		int[] rc = currentBoard.getRowsColsMines();
		int width = rc[1]*15 + 5;
		int height = rc[0]*15 + 64;
      prefDim = new Dimension(width,height);

	  this.setPreferredSize(prefDim);
	  add(gameBoard);
	  setLocationRelativeTo(null);
	  setResizable(true);

	  this.repaint();
	  this.pack();
	  setVisible(true);
	}

	public void setEventHandlers()
	{
		saveGame.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent f){
				//mineSFIO.saveMineFile(currentFile);
			}
		});

		solveGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent f){

				if(confirmDialog("Do you Wish to Solve?"))
				{
					gameBoard.solveGame();
				}
			}
		});
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent componentEvent) {
				Dimension size = getSize();
				int[] rc = gameBoard.getBoard().getRowsColsMines();

				int width = rc[1]*15 + 2;
				int height = rc[0]*15 + 50;
				String out = "";
				out += "Cols: "+ rc[1] + "- Rows: "+ rc[0] +"\n";
				out += "G| W: "+ width + "- H: "+ height +"\n";
				out += "A| W: "+ size.getWidth() + "- H: "+ size.getHeight() +"\n";
				System.out.println(out);
			}

			@Override
			public void componentMoved(ComponentEvent componentEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void componentShown(ComponentEvent componentEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}

			@Override
			public void componentHidden(ComponentEvent componentEvent) {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});

	}

	public boolean confirmDialog(String message)
	{
		int out = JOptionPane.showConfirmDialog(this,message,"Confirm",JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);


		System.out.print(out);
		return out == 0;
	}
}
