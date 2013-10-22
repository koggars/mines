package src.IO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import gui.BoardFrame;


// The MineSaveFile Template for Serialisation

public class MineSaveFile {
    private int gameID;
    private int seed;
    private int fileID;
    private String user;
    private char difficulty;
    private BoardFrame gameBoard;
    private DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy hh:mm:ss");
    private Date currentDate;

    //Dump Output
    public String toString()
    {
        String out = "";

        out += "<MineOptions>";
        out += "<User>"+user+"</User>";
        out += "<difficulty>"+difficulty+"</difficulty>";
        out += "<gID>"+gameID+"</gID>";
        out += "<fID>"+fileID+"</fID>";
        out += "<savedAt>"+dateFormat.format(currentDate)+"</savedAt>";
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
