package src.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StatsFileIO {
	File[] statsFiles = {new File("stats/easy.stats"), new File("stats/medium.stats"), new File("stats/hard.stats")};

	public StatsFileIO() {
		try {
			for (File stat : statsFiles) {
				if (!stat.exists()) {
					stat.getParentFile().mkdir();
					stat.createNewFile();
				}
			}
		} catch (IOException e) {

		}
	}

	public void writeStats(char difficulty, StatsFile stats) {

		try {
			FileWriter fw = new FileWriter(getFile(difficulty), true);
			fw.write(stats + "\n");
			fw.close();
		} catch (IOException ioe) {
			System.out.print("Cannot Print Stats! " + ioe.getMessage());
		}
	}

	private File getFile(char diff) {
		switch (diff) {
			case 'e':
				return statsFiles[0];
			case 'm':
				return statsFiles[1];
			case 'h':
				return statsFiles[2];
			default:
				return statsFiles[0];
		}
	}

	private ArrayList<StatsFile> loadFile(char diff)
	{
		File file = getFile(diff);
		ArrayList<StatsFile> output = new ArrayList<StatsFile>();

		if (file.exists()) {
			try {

				Scanner scanner = new Scanner(file);

				while(scanner.hasNextLine()) {
					output.add(deSeialize(scanner.nextLine()))
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public StatsFile deSeialize(String input)
	{
		MineSaveFile out = null;
		if (input.indexOf("<stat>") == 0) {
			String[] tags = {"user", "datestamp", "gamestatus", "elapsedtime"};
			String[] data = new String[tags.length];
			for (int i = 0; i < tags.length; i++) {
				String start = "<" + tags[i] + ">";
				String end = "</" + tags[i] + ">";

				int startI = input.indexOf(start) + start.length();
				int endI = input.indexOf(end);


				data[i] = input.substring(startI, endI);

			}
			String user = data[0];

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

	private StatsData[] getStatsData()
	{

	}
}
