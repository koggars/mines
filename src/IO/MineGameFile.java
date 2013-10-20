package src.IO;
import gui.Board;

public class MineGameFile {
    private int gameID;
    private String seed;
    private int fileID;
    private char difficulty;
    private Board gameBoard;

    public MineGameFile(String seed, int gameID,int fileID, char difficulty)
    {

    	this.seed = seed;
    	this.gameID = gameID;
    	this.fileID = fileID;
    	this.difficulty = difficulty;
        int tmp = 0;
        if(difficulty == 'm')
            tmp = 1;
        else if(difficulty == 'h')
            tmp = 2;

    	gameBoard = new Board(tmp, seed);
    }

    public String toString()
    {
        String out = "";

        out += "<MineOptions>";
        out += "<seed>"+seed+"</seed>";
        out += "<difficulty>"+difficulty+"</difficulty>";
        out += "<gID>"+gameID+"</gID>";
        out += "<fID>"+fileID+"</fID>";
        out += "</MineOptions>";
        out += "<MineData>"+getMineData()+"</MineData>";

        return out;
    }

    private String getMineData()
    {
        String out = "";
        for(int val : gameBoard.getField())
        {
            out += val+" ";
        }

        return out;
    }
}
