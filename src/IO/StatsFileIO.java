package src.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
