package src;

import java.io.File;
import src.IO.*;


public class GameGeneration {
   private String path = "games/";

   private MineGameFileIO mgfIO = new MineGameFileIO();

   public GameGeneration(boolean generateDefault)
   {
       if(generateDefault)
       {
           for(int i = 0; i<3; i++)
           {
               char d = (i == 0) ? 'e' : (i == 1) ? 'm' : (i == 2) ? 'h' : 'e';
               mgfIO.createGameFile("Default",i,i+1,d);
           }
       }
   }

   public void generateGames(String seed)
   {

       char[] chars = seed.toLowerCase().toCharArray();
       boolean found = false;
       for (int i = 0; i < chars.length; i++) {
           if (!found && Character.isLetter(chars[i])) {
               chars[i] = Character.toUpperCase(chars[i]);
               found = true;
           } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
               found = false;
           }
       }
       seed = String.valueOf(chars);


       seed = seed.replaceAll("\\s+", "");


       for(int i = 0; i<3; i++)
       {
           char d = (i == 0) ? 'e' : (i == 1) ? 'm' : (i == 2) ? 'h' : 'e';
           mgfIO.createGameFile(seed,i,i+1,d);
       }
   }
}
