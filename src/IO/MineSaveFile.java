package src.IO;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


// The MineSaveFile Template for Serialisation

public class MineSaveFile {
	private int gameID;
	private String user;
	private char difficulty;
	private String seed;
	private int[] gameBoard;
	private String gameFile;
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	private Date currentDate;

	public MineSaveFile(MineGameFile currentGame, String user, int[] gameBoard) {
		if (checkValid(currentGame.getFileName())) {
			gameID = currentGame.getGameId();
			this.user = user;
			seed = currentGame.getSeed();
			difficulty = currentGame.getDiff();
			this.gameBoard = gameBoard.clone();
			gameFile = currentGame.getFileName();

			currentDate = new Date();
		}
	}

	public MineSaveFile(int gameID, String user, char difficulty, String seed, int[] gameBoard, String gameFile, String date) {
		this.gameID = gameID;
		this.user = user;
		this.difficulty = difficulty;
		this.seed = seed;
		this.gameBoard = gameBoard.clone();
		this.gameFile = gameFile;

		try {
			this.currentDate = dateFormat.parse(date);
		} catch (ParseException e) {
			this.currentDate = new Date();
		}

	}

	public String getFilename() {
		return gameFile.replace(".game", "");
	}

	//Dump Output
	public String toString() {
		String out = "";

		out += "<SaveOptions>";
		out += "<User>" + user + "</User>";
		out += "<seed>" + seed + "</seed>";
		out += "<difficulty>" + difficulty + "</difficulty>";
		out += "<gameID>" + gameID + "</gameID>";
		out += "<gameFileName>" + gameFile + "</gameFileName>";
		out += "<savedAt>" + dateFormat.format(currentDate) + "</savedAt>";
		out += "</SaveOptions>";
		out += "<SaveData>" + getMineData() + "</SaveData>";

		return out;
	}

	private String getMineData() {
		String out = "";
		for (int val : gameBoard) {
			out += val + " ";
		}

		return out;
	}

	public int[] getGameBoard() {
		return gameBoard.clone();
	}

	public boolean checkValid(String gameFileLoc) {
		File folder = new File("games/" + gameFileLoc);
		return folder.exists();
	}

	public String getUser() {
		return user;
	}

	public String getCurrentDate() {
		return dateFormat.format(currentDate);
	}
}
