package src.IO;
import src.Board;

public class MineGameFile {
	private static int gameId = 0;
    private String seed;
	private long seedLong;
    private char difficulty;
    private int[] gameBoard;

    public MineGameFile(String seed, char difficulty)
    {
	    gameId++;
    	this.seed = seed;
    	this.difficulty = difficulty;
        int tmp = 0;
        if(difficulty == 'm')
            tmp = 1;
        else if(difficulty == 'h')
            tmp = 2;

    	Board tmpBoard = new Board(tmp, seed);
	    seedLong = tmpBoard.getRandomSeed();
	    gameBoard = tmpBoard.getField();
    }

	public MineGameFile(int gameId, String seed, long seedLong, char difficulty, int[] gameBoard)
	{
		this.gameId = gameId;
		this.seed = seed;
		this.difficulty = difficulty;
		this.gameBoard = gameBoard;
	}

    public String toString()
    {
        String out = "";

        out += "<MineOptions>";
        out += "<seed>"+seed+"</seed>";
	    out += "<seedLong>"+seedLong+"</seedLong>";
        out += "<difficulty>"+difficulty+"</difficulty>";
	    out += "<gId>"+ gameId +"</gId>";
        out += "</MineOptions>";
        out += "<MineData>"+mineDataString()+"</MineData>";

        return out;
    }

    private String mineDataString()
    {
        String out = "";
        for(int val : gameBoard)
        {
            out += val+" ";
        }

        return out;
    }
	public long getSeedLong()
	{
		return seedLong;
	}
	public String getSeed()
	{
		return seed;
	}

	public String getDifficulty()
	{
		switch (difficulty)
		{
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

	public int[] getMineData()
	{
		return gameBoard;
	}
}
