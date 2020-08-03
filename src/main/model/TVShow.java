package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;

// Represent a tv show having a category and a watched/not watched yet status
public class TVShow implements Saveable {
    private String name;
    private String category;
    private boolean isWatched;

    // EFFECTS: constructs a tv show with a category and is not watched yet
    public TVShow(String name, String category) {
        this.name = name;
        this.category = category;
        this.isWatched = false;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    // EFFECTS: returns true if watched, false otherwise
    public boolean isWatched() {
        return isWatched;
    }

    // REQUIRES: this show is not watched yet
    // MODIFIES: this
    // EFFECTS: mark this as watched
    public void watch() {
        isWatched = true;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(category);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(isWatched);
    }
}
