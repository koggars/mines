package src.IO;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class MineGameFileIO {

	public MineGameFile loadGameFile(String path)
	{
		File file = new File(path);
		MineGameFile output = null;

		if(file.exists())
		{
			try {

				Scanner scanner = new Scanner(file);

				if(scanner.hasNextLine()) {
					output = deSeialize(scanner.nextLine());
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	public void createGameFile(String seed, char difficulty)
	{
		MineGameFile newGame = new MineGameFile(seed,difficulty);
		String filename = seed+difficulty+".game";
		Path path = Paths.get("games/"+filename);
		try
		{
			Files.createDirectories(path.getParent());
			Files.createFile(path);
			File saveFile = new File(path.toString());
			BufferedWriter outputWriter;
			outputWriter = new BufferedWriter(new FileWriter(saveFile));

			outputWriter.write(newGame.toString());


			outputWriter.flush();
			outputWriter.close();
		}
		catch(java.io.IOException e)
		{

		}
	}

	public String[][] getFileListData()
	{
		File folder = new File("games/");
		File[] listOfFiles = folder.listFiles();
		String[] listOfFileLocation = new String[listOfFiles.length];
		String[] listOfFileNames = new String[listOfFiles.length];

		for(int i = 0; i < listOfFileNames.length; i++)
		{
			String filename = listOfFiles[i].getName();
			filename = filename.replace(".game","");

			String diff;
			switch (filename.charAt(filename.length()-1))
			{
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

			listOfFileLocation[i] = "games/"+listOfFiles[i].getName();
			listOfFileNames[i] = filename.substring(0, filename.length()-1) + " - " + diff;
		}
		String[][] out = {listOfFileNames, listOfFileLocation};
		return out;
	}

	private MineGameFile deSeialize(String input)
	{
		MineGameFile out = null;
		if(input.indexOf("<MineOptions>") == 0)
		{
			String[] tags = {"seed", "seedLong", "difficulty", "gId", "MineData"};
			String[] data = new String[tags.length];
			for(int i = 0; i<tags.length; i++)
			{
			    String start = "<"+tags[i]+">";
				String end = "</"+tags[i]+">";

				int startI = input.indexOf(start)+start.length();
				int endI = input.indexOf(end);


				data[i] = input.substring(startI,endI);

			}

			String seed = data[0];
			long seedLong = Long.parseLong(data[1]);
			char difficulty = data[2].charAt(0);
			int gameId = Integer.parseInt(data[3]);

			Scanner tmpScanner = new Scanner(data[4]);
			ArrayList<String> tmpArrayList = new ArrayList<String>();
			while(tmpScanner.hasNext())
			{
				tmpArrayList.add(tmpScanner.next());
			}

			int[] mineData = new int[tmpArrayList.size()];

			for(int i = 0; i < mineData.length; i++)
			{
				mineData[i] = Integer.parseInt(tmpArrayList.get(i));
			}
			out = new MineGameFile(gameId,seed,seedLong,difficulty,mineData);

		}

		return out;
	}
}
