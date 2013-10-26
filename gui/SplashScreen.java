package gui;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;


public class SplashScreen extends JFrame {
	private JPanel placeHolder;
	private JPanel prevUserPlaceHolder;
	private JPanel buttonPlaceholder;
	private JButton newUser;
	private JButton loadUser;
	private JLabel prevUserHeader;
	private JList previousUsers;
	private JScrollPane previousUsersScroll;
	private ListSelectionModel userListModel;

	private String[] users;
	private String currentUser;
	private String path;
	private File folder;

//Constructor

//////////////////////////////////////////////////////////////////////////////////////////////////


	public SplashScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Welcome - Minesweeper v0.1");

		prevUserPlaceHolder = new JPanel();
		placeHolder = new JPanel();
		newUser = new JButton("Create User");
		loadUser = new JButton("Load User");
		prevUserHeader = new JLabel("Current Users");


		previousUsers = new JList();
		previousUsers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		previousUsers.setLayoutOrientation(JList.VERTICAL);
		previousUsers.setVisibleRowCount(-1);

		previousUsersScroll = new JScrollPane(previousUsers);
		prevUserPlaceHolder.setLayout(new BoxLayout(prevUserPlaceHolder, BoxLayout.PAGE_AXIS));
		prevUserPlaceHolder.add(prevUserHeader);
		prevUserPlaceHolder.add(Box.createRigidArea(new Dimension(0, 5)));
		prevUserPlaceHolder.add(previousUsersScroll);
		prevUserPlaceHolder.setPreferredSize(new Dimension(200, 200));

		add(placeHolder, BorderLayout.CENTER);
		updateUsers();
		try {
			BufferedImage myPicture = ImageIO.read(new File("images/splash.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			placeHolder.add(picLabel, BorderLayout.CENTER);
		} catch (IOException e) {
		}


		newUser.setPreferredSize(new Dimension(200, 45));
		loadUser.setPreferredSize(new Dimension(200, 45));
		loadUser.setEnabled(false);


		buttonPlaceholder = new JPanel();
		buttonPlaceholder.add(newUser);
		buttonPlaceholder.add(loadUser);
		buttonPlaceholder.setPreferredSize(new Dimension(200, 200));

		placeHolder.add(buttonPlaceholder, BorderLayout.CENTER);
		placeHolder.add(prevUserPlaceHolder, BorderLayout.CENTER);

		userListModel = previousUsers.getSelectionModel();


		this.pack();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);

////////////////////////////////////////////////////////////////////////////////////////////////

//Action Listeners

////////////////////////////////////////////////////////////////////////////////////////////////
		newUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String userName = JOptionPane.showInputDialog("Enter user name:", null);

				//if user Canceled dont do anything
				if (userName == null || userName.isEmpty())
					return;


				File dir = new File("users/" + userName);
				if (dir.isDirectory()) {
					JOptionPane.showMessageDialog(null, "Username already exists please select another");
				} else {
					dir.mkdir();
					updateUsers();
				}

			}
		});


		userListModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				int index = lsm.getMaxSelectionIndex();
				int index2 = lsm.getMinSelectionIndex();

				if (index == index2) {
					if (index >= 0 && index < users.length) {
						currentUser = users[index];
						loadUser.setEnabled(true);
					} else
						loadUser.setEnabled(false);

				} else
					loadUser.setEnabled(false);
			}
		});

		loadUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				openSelectGame(currentUser);
			}
		});

///////////////////////////////////////////////////////////////////////////////////////////////       


	}

	private String[] getUser() {
		path = "users/";
		folder = new File(path);
		if (!folder.isDirectory())
			folder.mkdir();

		File[] listOfFiles = folder.listFiles();
		String[] listOfFileNames = new String[listOfFiles.length];

		for (int i = 0; i < listOfFileNames.length; i++) {
			listOfFileNames[i] = listOfFiles[i].getName();
		}

		return listOfFileNames;
	}

	private void updateUsers() {
		users = getUser();
		previousUsers.setListData(users);
	}

	private void openSelectGame(String userName) {
		new GameSelect(this, "users/" + userName);
		setVisible(false);
	}
}
