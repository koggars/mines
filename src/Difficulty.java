package src;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import java.io.File;

public class Difficulty extends JFrame {
    private String user;
    private String difficulty = "e";
    private String location;
    private File loadFile;


    final JFileChooser fc = new JFileChooser(location);

    public Difficulty(char diff, String user)
    {
        this.difficulty = ""+diff;
        this.location = setLocation(diff);


        this.user = user;
        fc.setFileView(new FileView() {
            @Override
            public Boolean isTraversable(File f) {
                return location.equals(f);
            }
        });

        int returnVal = fc.showOpenDialog(this);
        loadFile = fc.getSelectedFile();
        new MinesMain(user, this.difficulty);
        setVisible(false);

    }


    private String setLocation(char def)
    {
        String location = "games/";
        switch (def)
        {
            case 'e':
                location += "easy/";
                break;
            case 'm':
                location += "medium/";
                break;
            case 'h':
                location += "hard/";
                break;
        }
        return location;
    }
}