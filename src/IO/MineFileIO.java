package src.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



// Performs all Operations For IO

public class MineFileIO {
    private int[] saveArray;

    private File saveFile;
    private File loadFile;

    private Path path;

    public void saveMineFile() {
        DateFormat dateFormat = new SimpleDateFormat(" ddMMyyyy hh:mm:ss");
        Date date = new Date();
        String tempDifficulty = getDifficulty();
        saveArray = gameBoard.getField();
        path = Paths.get(" ");
        path = Paths.get(user + "/" + dateFormat.format(date) + tempDifficulty + ".mines");
        try
        {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            saveFile = new File(" ");
            saveFile = new File(user + "/" + dateFormat.format(date) + tempDifficulty + ".mines");
            BufferedWriter outputWriter = null;
            outputWriter = new BufferedWriter(new FileWriter(saveFile));
            saveArray = gameBoard.getField();

            for(int i =0;i < saveArray.length; i++) {
                outputWriter.write(saveArray[i]+" ");

            }
            outputWriter.flush();
            outputWriter.close();
        }
        catch(java.io.IOException e){

        }
    }

}
