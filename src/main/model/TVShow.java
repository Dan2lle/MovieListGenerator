package model;

// Represent a tv show having a category and a watched/not watched yet status
public class TVShow {
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
}
