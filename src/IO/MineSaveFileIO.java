package src.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



// Performs all Operations For IO

public class MineSaveFileIO {

    public void saveMineFile(MineSaveFile currentMF) {
        Path path = createSaveFileName("");//currentMF.getUser());
        try
        {
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            Files.createFile(path);
            File saveFile = new File(path.toString());
            BufferedWriter outputWriter;
            outputWriter = new BufferedWriter(new FileWriter(saveFile));

            outputWriter.write(currentMF.toString());


            outputWriter.flush();
            outputWriter.close();
        }
        catch(java.io.IOException e){

        }
    }
    public MineSaveFile loadMineFile(File inFile)
    {
        return new MineSaveFile();
    }

    public boolean checkMineFile(File inFile)
    {
       return false;
    }


    private Path createSaveFileName(String user)
    {
        Path out = Paths.get("");

//        DateFormat dateFormat = new SimpleDateFormat(" ddMMyyyy hh:mm:ss");
//        Date date = new Date();
//        String strPath = user + "/" + dateFormat.format(date) + ".mines";
//        out = Paths.get(strPath);

        return out;
    }
}
