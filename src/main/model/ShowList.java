package model;

import java.util.ArrayList;

// represent a list of tv shows that added by the user
public class ShowList {
    public ArrayList<TVShow> myList;

    public ShowList() {
        myList = new ArrayList<>();
    }

    public int getSize() {
        return this.myList.size();
    }

    // MODIFIES: this
    // EFFECTS: add a show to the list
    public boolean addShow(TVShow show) {
        return this.myList.add(show);
    }

    // REQUIRED: the list is not empty
    // MODIFIES: this
    // EFFECTS: removes the specific show from the list
    public boolean removeDrama(TVShow show) {
        return this.myList.remove(show);
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
}
