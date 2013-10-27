package src.IO;

class StatsData {
	private String user;
	private String shortstElapsedTime;
	private int wins;
	private int looses;


	public double getWinLossRatio()
	{
		if(looses != 0)
		{
			return (double)wins/(double)looses;
		}
		else
		{
			return (double)wins/1.0;
		}
	}
}