package src.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;


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

	private ArrayList<StatsFile> loadFile(File file) {
		ArrayList<StatsFile> output = new ArrayList<StatsFile>();

		if (file.exists()) {
			try {

				Scanner scanner = new Scanner(file);

				while (scanner.hasNextLine()) {
					output.add(deSeialize(scanner.nextLine()));
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public StatsFile deSeialize(String input) {
		StatsFile out = null;
		if (input.indexOf("<stat>") == 0) {
			String[] tags = {"user", "datestamp", "life", "gamestatus", "elapsedtime"};
			String[] data = new String[tags.length];
			for (int i = 0; i < tags.length; i++) {
				String start = "<" + tags[i] + ">";
				String end = "</" + tags[i] + ">";

				int startI = input.indexOf(start) + start.length();
				int endI = input.indexOf(end);


				if (startI < 0 || endI < 0) {
					System.out.print(tags[i]);
					return null;
				} else
					data[i] = input.substring(startI, endI);

			}

			out = new StatsFile(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4]);
		}
		return out;
	}

	public ArrayList<StatsData> getStatsData(char diff) {
		ArrayList<StatsFile> stats = loadFile(getFile(diff));
		ArrayList<StatsData> output = new ArrayList<StatsData>();
		for (StatsFile aStat : stats) {
			if (aStat != null) {
				StatsData sd = new StatsData(aStat);
				int index = output.indexOf(sd);
				if (index != -1) {
					StatsData newSd = output.get(index);

					newSd.addRatio(aStat.hasWon());

				} else {
					output.add(sd);
				}
			}
		}

		Collections.sort(output, new Comparator<StatsData>() {
			public int compare(StatsData o1, StatsData o2) {
				Double d1 = o1.getWinLossRatio();
				Double d2 = o2.getWinLossRatio();
				return d2.compareTo(d1);
			}
		});

		return output;
	}
}
