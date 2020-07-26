package ui;

import model.ShowList;
import model.TVShow;

import java.util.ArrayList;
import java.util.Scanner;

// Design your own tv show list
public class TVBucketList {
    private Scanner input;
    ShowList wholeList;

    // EFFECTS: run the tv show bucket list application
    public TVBucketList() {
        runList();
    }

    private void runList() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in).useDelimiter("\n");

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a TV show");
        System.out.println("\tr -> remove a TV show");
        System.out.println("\tm -> mark a TV show as watched");
        System.out.println("\ts -> show all TV shows of selected category");
        System.out.println("\tp -> print the current list");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addTVShow();
        } else if (command.equals("r")) {
            removeTVShow();
        } else if (command.equals("m")) {
            markAsWatched();
        } else if (command.equals("s")) {
            showByCategory();
        } else if (command.equals("p")) {
            printList();
        } else {
            System.out.println("Oops selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new show list
    private void init() {
        wholeList = new ShowList();
    }

    // MODIFIES: this
    // EFFECTS: add a tv show to the list
    private void addTVShow() {
        System.out.println("Please enter the name of the TV show: ");
        String name = input.next();
        System.out.println("Please enter the category of the TV show: ");
        String category = input.next();
        TVShow show = new TVShow(name, category);
        wholeList.addShow(show);
    }

    // MODIFIES: this
    // EFFECTS: remove a tv show from the list
    private void removeTVShow() {
        System.out.println("Please enter the name of the tv show: ");
        String name = input.next();
        for (TVShow show: wholeList.myList) {
            if (name.equals(show.getName())) {
                wholeList.removeShow(show);
            }
        }
        System.out.println("This movie cannot be found in the list.");
    }

    // MODIFIES: this
    // EFFECTS: mark a tv show as watched
    private void markAsWatched() {
        System.out.println("Please enter the name of the tv show: ");
        String name = input.next();
        for (TVShow show: wholeList.myList) {
            if (show.getName().equals(name)) {
                show.watch();
            }
        }
        System.out.println("This movie cannot be found in the list.");
    }

    // EFFECTS: show all tv shows of selected category
    private void showByCategory() {
        System.out.println("Please enter the TV show category: ");
        String category = input.next();
        ShowList selectedList = new ShowList();
        for (TVShow show: wholeList.myList) {
            if (show.getCategory().equals(category)) {
                selectedList.addShow(show);
            }
        }
        if (selectedList.getSize() == 0) {
            System.out.println("Cannot find TV shows of this category.");
        } else {
            System.out.println(category + " TV shows found in the list are");
            for (TVShow s: selectedList.myList) {
                System.out.println("Name: " + s.getName() + " - Watched? : " + s.isWatched());
            }
        }
    }

    // EFFECTS: print the tv show list
    private void printList() {
        for (TVShow show: wholeList.myList) {
            System.out.println("Name: " + show.getName() + " - Category: "
                    + show.getCategory() + " - Watched? : " + show.isWatched());
        }
    }
}
