package src.IO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatsFile {
	private String user;
	private Date dateStamp;
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	private String gameStatus;
	private int life;
	private String elapsedTime;


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
		out += "<elapsedtime>" + elapsedTime + "</elpasedtime>";
		out += "</stat>";
		return out;
	}
}
