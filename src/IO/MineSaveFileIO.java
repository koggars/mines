package src.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



// Performs all Operations For IO

public class MineSaveFileIO {
	File userDirectory;

	public MineSaveFileIO(String user)
	{
		String path = "users/"+user;
		File folder = new File(path);
		if(folder.isDirectory())
		{
			userDirectory = folder;
		}
	}
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

	public String[][] getFileListData()
	{
		String[][] out = {{""}, {""}};
		if(userDirectory.isDirectory())
		{
			File[] listOfFiles = userDirectory.listFiles();
			String[] listOfFileLocation = new String[listOfFiles.length];
			String[] listOfFileNames = new String[listOfFiles.length];

			for(int i = 0; i < listOfFileNames.length; i++)
			{
				String filename = listOfFiles[i].getName();
				filename = filename.replace(".game","");

				String diff;
				switch (filename.charAt(filename.length()-1))
				{
					case 'e':
						diff = "Easy";
						break;
					case 'm':
						diff = "Medium";
						break;
					case 'h':
						diff = "Hard";
						break;
					default:
						diff = "";
				}

				listOfFileLocation[i] = "games/"+listOfFiles[i].getName();
				listOfFileNames[i] = filename.substring(0, filename.length()-1) + " - " + diff;
				out[0] = listOfFileNames;
				out[1] = listOfFileLocation;
			}
		}
		return out;
	}
}
