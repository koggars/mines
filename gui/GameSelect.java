package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

import src.IO.*;
import src.GameGeneration;

public class GameSelect extends JFrame {
	private SplashScreen splashScreen;


	private JPanel leftPlaceholder = new JPanel();
	private JPanel rightPlaceholder = new JPanel();
	private JPanel buttonPlaceholder = new JPanel();

	private JPanel centerPlaceholder = new JPanel();

	private JLabel leftHeaderLbl = new JLabel("Minesweeper Levels");
	private JLabel rightHeaderLbl = new JLabel("Your Current Saves");

	private JList leftList = new JList();
	private ListSelectionModel leftListModel;
	private JScrollPane leftListScroller;

	private JList rightList = new JList();
	private ListSelectionModel rightListModel;
	private JScrollPane rightListScroller;

	private MinesMain mainWindow;

	private JButton generateBtn = new JButton("Generate Game");
	private JButton loadGameBtn = new JButton("Load Save");
	private JButton newGameBtn = new JButton("New Game");
	private JButton viewStatsBtn = new JButton("View Leaderboard");

	private GameGeneration gameGenerator = new GameGeneration();
	private MineGameFileIO gameIO = new MineGameFileIO();
	private MineSaveFileIO saveIO;

	private String[] gameFileLocations;
	private String[] saveFileLocations;

	private String username;

	private int selectedGameIndex = -1;
	private int selectedSaveIndex = -1;


	//Constructor

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	public GameSelect(SplashScreen splashScreen, String user) {
		this.splashScreen = splashScreen;
		gameGenerator.generateDefault();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		username = user.replace("users/", "");
		setTitle("Select Game - " + username);
		saveIO = new MineSaveFileIO(username);


		loadGameBtn.setEnabled(false);
		newGameBtn.setEnabled(false);
		viewStatsBtn.setEnabled(false);


		initComponents();

		setListData();

		leftListModel = leftList.getSelectionModel();
		rightListModel = rightList.getSelectionModel();

		setPreferredSize(new Dimension(850, 250));
		this.pack();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);

		/////////////////////////////////////////////////////////////////////////////////////////////////////

		// Action Listeners

		/////////////////////////////////////////////////////////////////////////////////////////////
		newGameBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MineGameFile mgf = gameIO.loadGameFile(gameFileLocations[selectedGameIndex]);
						openMainWindow(mgf, null);
					}
				}
		);

		generateBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String seed = JOptionPane.showInputDialog("Enter A Seed for Randomization:", null);

						//if user Canceled dont do anything
						if (seed == null || seed.isEmpty())
							return;
						else {
							gameGenerator.generateGames(seed);

							setListData();
						}
					}
				});

		leftListModel.addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {

						ListSelectionModel lsm = (ListSelectionModel) e.getSource();
						int index = lsm.getMaxSelectionIndex();
						int index2 = lsm.getMinSelectionIndex();


						if (index == index2) {
							selectedGameIndex = index;

							newGameBtn.setEnabled(index >= 0 && index < gameFileLocations.length);
							viewStatsBtn.setEnabled(index >= 0 && index < gameFileLocations.length);

						}
					}
				}
		);
		rightListModel.addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {

						ListSelectionModel lsm = (ListSelectionModel) e.getSource();
						int index = lsm.getMaxSelectionIndex();
						int index2 = lsm.getMinSelectionIndex();


						if (index == index2) {
							selectedSaveIndex = index;

							newGameBtn.setEnabled(index >= 0 && index < saveFileLocations.length);
							viewStatsBtn.setEnabled(index >= 0 && index < saveFileLocations.length);

						}
					}
				}
		);

		//////////////////////////////////////////////////////////////////////////////////////////////
	}

	private void initComponents() {

		leftList = new JList();
		leftList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		leftList.setLayoutOrientation(JList.VERTICAL);
		leftList.setVisibleRowCount(-1);

		leftListScroller = new JScrollPane(leftList);

		rightList = new JList();
		rightList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		rightList.setLayoutOrientation(JList.VERTICAL);
		rightList.setVisibleRowCount(-1);

		rightListScroller = new JScrollPane(rightList);

		leftPlaceholder.setLayout(new BoxLayout(leftPlaceholder, BoxLayout.PAGE_AXIS));
		leftPlaceholder.add(leftHeaderLbl);
		leftPlaceholder.add(Box.createRigidArea(new Dimension(0, 5)));
		leftPlaceholder.add(leftListScroller);
		leftPlaceholder.setPreferredSize(new Dimension(300, 200));

		rightPlaceholder.setLayout(new BoxLayout(rightPlaceholder, BoxLayout.PAGE_AXIS));
		rightPlaceholder.add(rightHeaderLbl);
		rightPlaceholder.add(Box.createRigidArea(new Dimension(0, 5)));
		rightPlaceholder.add(rightListScroller);
		rightPlaceholder.setPreferredSize(new Dimension(300, 200));

		generateBtn.setPreferredSize(new Dimension(200, 45));
		loadGameBtn.setPreferredSize(new Dimension(200, 45));
		newGameBtn.setPreferredSize(new Dimension(200, 45));
		viewStatsBtn.setPreferredSize(new Dimension(200, 45));

		buttonPlaceholder.add(generateBtn);
		buttonPlaceholder.add(loadGameBtn);
		buttonPlaceholder.add(newGameBtn);
		buttonPlaceholder.add(viewStatsBtn);
		buttonPlaceholder.setPreferredSize(new Dimension(200, 200));

		centerPlaceholder.add(leftPlaceholder);
		centerPlaceholder.add(buttonPlaceholder);
		centerPlaceholder.add(rightPlaceholder);

		add(centerPlaceholder, BorderLayout.CENTER);
	}

	private void setListData() {
		String[][] gameListData = gameIO.getFileListData();
		leftList.setListData(gameListData[0]);
		gameFileLocations = gameListData[1];

		String[][] saveListData = saveIO.getFileListData();

	}

	private void openMainWindow(MineGameFile file, MineSaveFile save) {
		if (file != null && save == null) {
			mainWindow = new MinesMain(this, username, file);
			mainWindow.newGame();
		} else if (save != null && file == null) {
			mainWindow = new MinesMain(this, username, save);
			mainWindow.loadGame();
		}
		setVisible(false);
	}

	public void showSplash() {
		splashScreen.setVisible(true);
		dispose();
	}

}