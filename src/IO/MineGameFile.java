package src.IO;

import src.Board;

public class MineGameFile {
	private static int gameId = 0;
	private String seed;
	private long seedLong;
	private char difficulty;
	private int[] gameBoard;
	private int rows;
	private int cols;
	private int mines;

	public MineGameFile(String seed, char difficulty) {
		gameId++;
		this.seed = seed;
		this.difficulty = difficulty;
		int tmp = 0;
		if (difficulty == 'm')
			tmp = 1;
		else if (difficulty == 'h')
			tmp = 2;

		Board tmpBoard = new Board(tmp, seed);

		seedLong = tmpBoard.getRandomSeed();
		gameBoard = tmpBoard.getField();
		gameBoard = gameBoard.clone();

		int[] t = tmpBoard.getRowsColsMines();
		rows = t[0];
		cols = t[1];
		mines = t[2];
	}

	public MineGameFile(int gameId, String seed, long seedLong, char difficulty, int[] gameBoard) {
		this.gameId = gameId;
		this.seed = seed;
		this.seedLong = seedLong;
		this.difficulty = difficulty;

		String tmp = difficulty + "";
		tmp = tmp.toUpperCase();

		Board tmpBoard = new Board(tmp, seedLong, gameBoard);
		this.gameBoard = tmpBoard.getField();
		this.gameBoard = gameBoard.clone();

		int[] t = tmpBoard.getRowsColsMines();
		rows = t[0];
		cols = t[1];
		mines = t[2];

	}

	public String toString() {
		String out = "";

		out += "<MineOptions>";
		out += "<seed>" + seed + "</seed>";
		out += "<seedLong>" + seedLong + "</seedLong>";
		out += "<difficulty>" + difficulty + "</difficulty>";
		out += "<gId>" + gameId + "</gId>";
		out += "<rows>" + rows + "</rows>" + "<cols>" + cols + "</cols>" + "<mines>" + mines + "</mines>";
		out += "</MineOptions>";
		out += "<MineData>" + mineDataString() + "</MineData>";

		return out;
	}


	private String mineDataString() {
		String out = "";
		for (int val : gameBoard) {
			out += val + " ";
		}

		return out;
	}

	public long getSeedLong() {
		return seedLong;
	}

	public String getSeed() {
		return seed;
	}

	public String getDifficulty() {
		switch (difficulty) {
			case 'e':
				return "Easy";
			case 'm':
				return "Medium";
			case 'h':
				return "Hard";
			default:
				return "";
		}
	}

	public int[] getMineData() {
		return gameBoard;
	}

	public String getFileName() {
		return seed + difficulty + ".game";
	}

	public int getGameId() {
		return gameId;
	}

	public char getDiff() {
		return difficulty;
	}
}
