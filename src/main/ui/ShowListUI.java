package ui;

import exceptions.ShowCannotBeFoundException;
import model.ShowList;
import model.TVShow;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

// used the TellerApp as a source for this class
// Design your own tv show list
public class ShowListUI {
    private static final String LIST_FILE = "data/realShowList";
    private Scanner input;
    ShowList wholeList;

    // EFFECTS: run the tv show bucket list application
    public ShowListUI() {
        runList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runList() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in).useDelimiter("\n");

        loadList();

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
        System.out.println("\ta -> add or remove a TV show");
        System.out.println("\tm -> mark a TV show as watched");
        System.out.println("\tc -> show all TV shows of selected category");
        System.out.println("\ts -> save changes to the file");
        System.out.println("\tp -> print the current list");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            createAndShowGUI(wholeList);
        } else if (command.equals("m")) {
            markAsWatched();
        } else if (command.equals("c")) {
            showByCategory();
        } else if (command.equals("s")) {
            saveShows();
        } else if (command.equals("p")) {
            printList();
        } else {
            System.out.println("Oops selection not valid...");
        }
    }

    // EFFECTS: create and show GUI
    private static void createAndShowGUI(ShowList wholeList) {
        //Create and set up the window.
        JFrame frame = new JFrame("Your TV Show List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ShowGUI(wholeList);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: saves new changes of the show list to file
    public void saveShows() {
        try {
            Writer writer = new Writer(new File(LIST_FILE));
            writer.write(wholeList);
            writer.close();
            System.out.println("Changes in the list saved to file " + LIST_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save the show list to " + LIST_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS: loads list from LIST_FILE, if the file exists;
    // otherwise initializes accounts with default values
    private void loadList() {
        try {
            wholeList = Reader.readShows(new File(LIST_FILE));
        } catch (IOException e) {
            init();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new show list
    private void init() {
        wholeList = new ShowList();
    }

    // MODIFIES: this
    // EFFECTS: mark a tv show as watched
    private void markAsWatched() {
        if (wholeList.myList.size() == 0) {
            System.out.println("The list is empty :(");
        } else {
            System.out.println("Please enter the name of the tv show: ");
            String name = input.next();
            if (wholeList.markShowAsWatched(name)) {
                System.out.println("You have watched " + name);
            } else {
                System.out.println("This movie cannot be found in the list.");
            }
        }
    }


    // EFFECTS: show all tv shows of selected category
    private void showByCategory() {
        System.out.println("Please enter the TV show category: ");
        String category = input.next();
        try {
            ShowList selectedList = wholeList.showByCategory(category);
            System.out.println(category + " TV shows found in the list are");
            for (TVShow s: selectedList.myList) {
                System.out.println("Name: " + s.getName() + " - Watched? : " + s.isWatched());
            }
        } catch (ShowCannotBeFoundException e) {
            System.out.println("Cannot find TV shows of this category.");
        }
    }

    // EFFECTS: print the tv show list
    private void printList() {
        for (TVShow show: wholeList.myList) {
            System.out.println(show.display());
        }
    }
}
