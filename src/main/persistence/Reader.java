package persistence;

import model.ShowList;
import model.TVShow;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// used the TellerApp as a source of code for this class
// represents a reader that can read data of a TV show list file
public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns TV show list parsed from file; throws IOException
    // if an exception is raised when opening / reading from file
    public static ShowList readShows(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of TV shows parsed from list of strings
    // where each string contains data for one TV show
    private static ShowList parseContent(List<String> fileContent) {
        ShowList showList = new ShowList();
        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            showList.addShow(parseTVShow(lineComponents));
        }
        return showList;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // EFFECTS: components has size 2 where element 0 represents the name of the TV show, and element
    // 1 represents the category of the tv show
    private static TVShow parseTVShow(List<String> components) {
        String name = components.get(0);
        String category = components.get(1);
        boolean isWatched = Boolean.parseBoolean(components.get(2));
        TVShow newShow = new TVShow(name, category);
        if (isWatched) {
            newShow.watch();
        }
        return newShow;
    }
}
