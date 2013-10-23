package src.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


// Performs all Operations For IO

public class MineSaveFileIO {
	File userDirectory;

	public MineSaveFileIO(String user) {
		String path = "users/" + user;
		File folder = new File(path);
		if (folder.isDirectory()) {
			userDirectory = folder;
		} else {
			folder.mkdir();
			userDirectory = folder;
		}
	}

	public MineSaveFile loadGameFile(String path) {
		File file = new File(path);
		MineSaveFile output = null;

		if (file.exists()) {
			try {

				Scanner scanner = new Scanner(file);

				if (scanner.hasNextLine()) {
					output = deSeialize(scanner.nextLine());
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public boolean checkMineFile(MineSaveFile currentMF) {
		String filename = currentMF.getFilename() + ".save";
		File folder = new File("users/" + currentMF.getUser() + "/" + filename);
		return folder.exists();
	}

	public void createMineFile(MineSaveFile currentMF) {
		String filename = currentMF.getFilename() + ".save";
		Path path = Paths.get("users/" + currentMF.getUser() + "/" + filename);
		try {
			Files.createDirectories(path.getParent());
			Files.deleteIfExists(path);
			Files.createFile(path);
			File saveFile = new File(path.toString());
			BufferedWriter outputWriter;
			outputWriter = new BufferedWriter(new FileWriter(saveFile));

			outputWriter.write(currentMF.toString());


			outputWriter.flush();
			outputWriter.close();
		} catch (java.io.IOException e) {

		}
	}

	public String[][] getFileListData() {
		String[][] out = {{""}, {""}};
		if (userDirectory.isDirectory()) {
			File[] listOfFiles = userDirectory.listFiles();
			String[] listOfFileLocation = new String[listOfFiles.length];
			String[] listOfFileNames = new String[listOfFiles.length];


			for (int i = 0; i < listOfFileNames.length; i++) {
				String filename = listOfFiles[i].getName();
				filename = filename.replace(".save", "");

				String diff;
				switch (filename.charAt(filename.length() - 1)) {
					case 'e':
						diff = "Easy";
						break;
					case 'm':
						diff = "Medium";
						break;
					case 'h':
						diff = "Hard";
						break;
					default:
						diff = "";
				}

				listOfFileLocation[i] = userDirectory.toString() + "/" + listOfFiles[i].getName();
				MineSaveFile tmpFile = loadGameFile(listOfFileLocation[i]);
				listOfFileNames[i] = filename.substring(0, filename.length() - 1) + " | " + diff + " Created On: " + tmpFile.getCurrentDate();
				out[0] = listOfFileNames;
				out[1] = listOfFileLocation;
			}
		}
		return out;
	}

	private MineSaveFile deSeialize(String input) {
		MineSaveFile out = null;
		if (input.indexOf("<SaveOptions>") == 0) {
			String[] tags = {"User", "seed", "difficulty", "gameID", "gameFileName", "savedAt", "SaveData"};
			String[] data = new String[tags.length];
			for (int i = 0; i < tags.length; i++) {
				String start = "<" + tags[i] + ">";
				String end = "</" + tags[i] + ">";

				int startI = input.indexOf(start) + start.length();
				int endI = input.indexOf(end);


				data[i] = input.substring(startI, endI);

			}
			String user = data[0];
			String seed = data[1];
			char difficulty = data[2].charAt(0);
			int gameId = Integer.parseInt(data[3]);
			String gameFileName = data[4];
			String date = data[5];

			Scanner tmpScanner = new Scanner(data[6]);
			ArrayList<String> tmpArrayList = new ArrayList<String>();
			while (tmpScanner.hasNext()) {
				tmpArrayList.add(tmpScanner.next());
			}

			int[] mineData = new int[tmpArrayList.size()];

			for (int i = 0; i < mineData.length; i++) {
				mineData[i] = Integer.parseInt(tmpArrayList.get(i));
			}
			out = new MineSaveFile(gameId, user, difficulty, seed, mineData, gameFileName, date);

		}

		return out;
	}
}
