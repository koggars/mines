package src.IO;

import java.io.File;
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
}
