package src.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MineGameFileIO {

   public void createGameFile(String seed, int gameID,int fileID, char difficulty)
   {
       MineGameFile newGame = new MineGameFile(seed,gameID,fileID,difficulty);
       String filename = seed+gameID+fileID+difficulty+".game";
       Path path = Paths.get("games/"+filename);
       try
       {
           Files.createDirectories(path.getParent());
           Files.createFile(path);
           File saveFile = new File(path.toString());
           BufferedWriter outputWriter;
           outputWriter = new BufferedWriter(new FileWriter(saveFile));

           outputWriter.write(newGame.toString());


           outputWriter.flush();
           outputWriter.close();
       }
       catch(java.io.IOException e)
       {

       }
   }
}
