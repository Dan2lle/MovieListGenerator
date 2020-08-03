package model;

import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;

// represent a list of tv shows that added by the user
public class ShowList implements Saveable {
    public ArrayList<TVShow> myList;

    public ShowList() {
        myList = new ArrayList<>();
    }

    public int getSize() {
        return this.myList.size();
    }

    // MODIFIES: this
    // EFFECTS: add a show to the list, return true if successful, false otherwise
    public boolean addShow(TVShow show) {
        return this.myList.add(show);
    }

    // REQUIRED: the list is not empty
    // MODIFIES: this
    // EFFECTS: removes the specific show from the list, return true if successful, false otherwise
    public boolean removeShow(String name) {
        for (TVShow s: this.myList) {
            if (s.getName().equals(name)) {
                return this.myList.remove(s);
            }
        }
        return false;
    }

    // EFFECTS: show all tv shows of the selected category
    public ShowList showByCategory(String category) {
        ShowList selectedList = new ShowList();
        for (TVShow s: this.myList) {
            if (category.equals(s.getCategory())) {
                selectedList.addShow(s);
            }
        }
        return selectedList;
    }


    // REQUIRES: this show has not been watched
    // MODIFIES: this
    // EFFECTS: mark the selected show as watched
    public boolean markShowAsWatched(String name) {
        for (TVShow s: this.myList) {
            if (s.getName().equals(name)) {
                s.watch();
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(PrintWriter printWriter) {
        for (TVShow s : this.myList) {
            s.save(printWriter);
            printWriter.println();
        }
    }
}
