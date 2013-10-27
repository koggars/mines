package src.IO;

public class StatsData {
	private String user;
	private String shortstElapsedTime;
	private int wins;
	private int looses;
	private int life;


	public StatsData(StatsFile sf) {
		this.user = sf.getUser();
		this.shortstElapsedTime = sf.getElapsedTime();
		wins = 0;
		looses = 0;
		life = sf.getLife();
	}

	public double getWinLossRatio() {
		if (looses != 0) {
			return (double) wins / (double) looses;
		} else {
			return (double) wins / 1.0;
		}
	}

	public boolean equals(Object object) {
		boolean sameSame = false;

		if (object != null && object instanceof StatsData) {
			StatsData obj = (StatsData) object;
			sameSame = this.user.equals(obj.getUser());
		}

		return sameSame;
	}

	public String getUser() {
		return user;
	}

	public void addRatio(boolean win) {
		if (win)
			this.wins++;
		else
			this.looses++;
	}
}