package src.IO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatsFile {
	private String user;
	private Date dateStamp;
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	private String gameStatus;
	private int life;
	private String elapsedTime;


	public StatsFile(String user, String datestamp, int life, String gamestatus, String elapsedTime) {
		this.user = user;

		gameStatus = gamestatus;
		this.life = life;
		try {
			this.dateStamp = dateFormat.parse(datestamp);
		} catch (ParseException e) {
			this.dateStamp = new Date();
		}
		this.elapsedTime = elapsedTime;
	}

	public StatsFile(String user, boolean wonGame, int life, String elapsedTime) {
		dateStamp = new Date();
		this.user = user;
		gameStatus = (wonGame) ? "Win" : "Loose";
		this.life = life;
		this.elapsedTime = elapsedTime;

	}

	public String toString() {
		String out = "";
		out += "<stat>";
		out += "<user>" + user + "</user>";
		out += "<datestamp>" + dateFormat.format(dateStamp) + "</datestamp>";
		out += "<gamestatus>" + gameStatus + "</gamestatus>";
		out += "<life>" + life + "</life>";
		out += "<elapsedtime>" + elapsedTime + "</elapsedtime>";
		out += "</stat>";
		return out;
	}

	public boolean hasWon() {
		return gameStatus.equals("Win");
	}

	public String getUser() {
		return user;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public int getLife() {
		return life;
	}
}
