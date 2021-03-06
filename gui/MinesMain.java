package gui;

import src.IO.*;
import src.Board;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


// Main Function for the Mines App

public class MinesMain extends JFrame {
	private GameSelect gameSelect;


	private StatusPane statsPane;
	private JLabel statusBar;
	private JLabel title;
	private JPanel placeHolder;

	private JMenuBar menuPlace;

	private JMenu menu;
	private JMenu file;
	private JMenu edit;
	private JMenu actions;
	private JMenu help;

	private JMenuItem mainMenu;
	private JMenuItem gameMenu;
	private JMenuItem saveGame;
	private JMenuItem restartGame;
	private JMenuItem quitGame;
	private JMenuItem undoLast;
	private JMenuItem redoLast;
	private JMenuItem solveGame;
	private JMenuItem helpPopup;

	private JMenuItem aboutPopup;

	private BoardFrame gameBoard;
	private MineSaveFileIO mineSFIO;

	private String user;

	private MineGameFile currentGame;
	private boolean saveStats = false;

	private StatsFileIO statsIO = new StatsFileIO();

	public MinesMain(GameSelect gameSelect, String userName, MineGameFile gameFile) {
		this.gameSelect = gameSelect;
		currentGame = gameFile;
		user = userName;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper In Game");
	}

	public MinesMain(GameSelect gameSelect, String userName, MineGameFile gameFile, MineSaveFile gameSave) {
		this.gameSelect = gameSelect;
		currentGame = gameFile;
		user = userName;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Minesweeper In Game");

		user = userName;

		loadGame(gameSave);
	}

	public void newGame() {
		Board currentBoard = new Board(currentGame.getDifficulty(), currentGame.getSeedLong(), currentGame.getMineData());
		setupGame(currentBoard);
	}

	public void loadGame(MineSaveFile gameSave) {
		Board currentBoard = new Board(currentGame.getDifficulty(), currentGame.getSeedLong(), gameSave.getGameBoard());
		setupGame(currentBoard);
	}

	public void setupGame(Board currentBoard) {
		title = new JLabel("Game: " + currentGame.getSeed() + " " + currentGame.getDifficulty() + " - " + user);

		mineSFIO = new MineSaveFileIO(user);


		int[] rc = currentBoard.getRowsColsMines();
		int width = rc[1] * 15;
		int height = rc[0] * 15;
		statsPane = new StatusPane(width);

		gameBoard = new BoardFrame(statsPane, currentBoard, this);
		gameBoard.newGame();
		gameBoard.setPreferredSize(new Dimension(width, height));

		initComponents();
		setEventHandlers();
	}

	public void initComponents() {

		menuPlace = new JMenuBar();

		placeHolder = new JPanel();

		menu = new JMenu("Menu");
		file = new JMenu("File");
		edit = new JMenu("Edit");
		actions = new JMenu("Actions");
		help = new JMenu("Help");

		mainMenu = new JMenuItem("Main Menu");
		gameMenu = new JMenuItem("Game Menu");
		saveGame = new JMenuItem("Save Game");
		restartGame = new JMenuItem("Restart Game");
		quitGame = new JMenuItem("Quit");
		undoLast = new JMenuItem("Undo");
		redoLast = new JMenuItem("Redo");
		solveGame = new JMenuItem("Solve Current Game");
		helpPopup = new JMenuItem("Help");
		aboutPopup = new JMenuItem("About Minesweeper");


		// Adding of components
		add(statsPane, BorderLayout.SOUTH);
		add(menuPlace, BorderLayout.NORTH);
		add(placeHolder, BorderLayout.CENTER);


		menuPlace.add(menu);
		menu.add(mainMenu);
		menu.add(gameMenu);
		menu.add(quitGame);

		menuPlace.add(file);
		file.add(restartGame);
		file.add(saveGame);

		menuPlace.add(edit);
		edit.add(undoLast);
		edit.add(redoLast);

		gameBoard.setMenus(undoLast, redoLast);

		redoLast.setEnabled(false);
		undoLast.setEnabled(false);

		menuPlace.add(actions);
		actions.add(solveGame);

		menuPlace.add(help);
		help.add(helpPopup);
		help.add(aboutPopup);

		placeHolder.setLayout(new BorderLayout());
		placeHolder.add(title, BorderLayout.NORTH);
		placeHolder.add(gameBoard, BorderLayout.CENTER);
		placeHolder.repaint();

		setLocationRelativeTo(null);
		//setResizable(false);

		this.repaint();
		this.pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setEventHandlers() {
		saveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent f) {
				if (gameBoard.isInGame()) {
					MineSaveFile saveFile = new MineSaveFile(currentGame, user, gameBoard.getField());
					if (mineSFIO.checkMineFile(saveFile) && confirmDialog("Save File Already exists do you wish to overwrite? \n All Saved Progress will be lost"))
						mineSFIO.createMineFile(saveFile);
					else
						mineSFIO.createMineFile(saveFile);

					gameSelect.setListData();
					gameSelect.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Error: Unable to save game \n Cannot Save a game that you have lost!");
				}
			}
		});

		restartGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				if (confirmDialog("Do You Wish to Restart Game?")) {
					gameSelect.restartGame();
				}
			}
		});
		undoLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gameBoard.undoMove();
			}
		});
		redoLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gameBoard.redoMove();
			}
		});
		solveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent f) {
				if (confirmDialog("Do you Wish to Solve?  \n All Progress will be lost!")) {
					gameBoard.solveGame();
				}
			}
		});

		mainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent f) {
				if (confirmDialog("Do You wish to go back to the Main Menu? \n All Progress will be lost!")) {
					gameSelect.showSplash();
					dispose();
				}
			}
		});
		gameMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent f) {
				if (confirmDialog("Do You wish to go back to the Game Select Menu? \n All Progress will be lost!")) {
					gameSelect.setVisible(true);
					dispose();
				}
			}
		});
		quitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent f) {
				if (confirmDialog("So You wish to quit? \n All Progress will be lost!")) {
					System.exit(0);
				}
			}
		});
	}

	public MineGameFile getGameFile() {
		return currentGame;
	}

	public boolean confirmDialog(String message) {
		int out = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
		return out == 0;
	}

	public void gameOver(boolean win) {
		if (!saveStats) {

			StatsFile stats = new StatsFile(user, win, statsPane.getCurrentLife(), statsPane.getElapsedTimeStr());

			statsIO.writeStats(currentGame.getCharDiff(), stats);
			saveStats = true;
		}
	}
}
