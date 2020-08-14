package ui;

import model.ShowList;
import model.TVShow;
import persistence.Writer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class MyActionListener implements java.awt.event.ActionListener, DocumentListener {
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    public JList<String> jlist = new JList<>(listModel);
    private static final String LIST_FILE = "data/realShowList";
    private boolean alreadyEnabled = false;
    private static final String addString = "Add a show";
    private static final String saveString = "Save the shows";
    private static final String removeString = "Remove this show";
    public JButton addButton = new JButton(addString);
    public JButton saveButton = new JButton(saveString);
    public JButton removeButton = new JButton(removeString);
    private ShowGUI showGUI;
    private static final String SOUND_FILE = "data/Mouse Click Fast.wav";

    public MyActionListener(ShowGUI showGUI) {
        this.showGUI = showGUI;
        createAddButton(this, addButton);
        createSaveButton(this, saveButton);
        createRemoveButton(this, removeButton);
        addListModel(showGUI.wholeList);
        createList();
    }

    // EFFECTS: initiate the JList
    private void addListModel(ShowList showList) {
        for (int i = 0; i < showList.myList.size(); i++) {
            addElementAsString(showList.myList.get(i), listModel);
        }
    }

    // EFFECTS: present tv shows on the panel as readable strings
    public void addElementAsString(TVShow tvShow, DefaultListModel<String> listModel) {
        listModel.addElement(tvShow.display());
    }

    // EFFECTS: create the list and put it in a scroll pane
    private void createList() {
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setSelectedIndex(0);
        jlist.setVisibleRowCount(5);
    }

    // EFFECTS: create the add button to add a show
    private void createAddButton(MyActionListener myActionListener, JButton addButton) {
        addButton.setActionCommand(addString);
        addButton.addActionListener(myActionListener);
        addButton.setEnabled(false);
    }

    // EFFECTS: create the save button to add a show
    private void createSaveButton(MyActionListener myActionListener, JButton saveButton) {
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(myActionListener);
    }

    // EFFECTS: create the remove button to add a show
    private void createRemoveButton(MyActionListener myActionListener, JButton removeButton) {
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(myActionListener);
    }

    //Required by MyActionListener.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            playSound();
            TVShow tvShow = getTVShow();
            checkIfUniqueName(tvShow);
            showGUI.wholeList.addShow(tvShow);
            addElementAsString(tvShow, listModel);
            resetTextField();
            selectAndVisible();
            removeButton.setEnabled(true);
        } else if (e.getSource() == saveButton) {
            playSound();
            saveShowIntoFile();
            selectAndVisible();
        } else if (e.getSource() == removeButton) {
            playSound();
            removeShow();
            selectAndVisible();
        }
    }

    // EFFECTS: select the new item and make it visible
    private void selectAndVisible() {
        int index = jlist.getSelectedIndex();
        jlist.setSelectedIndex(index);
        jlist.ensureIndexIsVisible(index);
    }

    // EFFECTS: remove the selected show
    private void removeShow() {
        int index = jlist.getSelectedIndex();
        listModel.remove(index);
        showGUI.wholeList.myList.remove(index);
        int size = listModel.getSize();
        if (size == 0) { //Nobody's left, disable firing.
            removeButton.setEnabled(false);
        } else { //Select an index.
            if (index == listModel.getSize()) {
                index--; //removed item in last position
            }
            jlist.setSelectedIndex(index);
            jlist.ensureIndexIsVisible(index);
        }
    }


    // EFFECTS: generate the input tv show
    public TVShow getTVShow() {
        String name = showGUI.showName.getText();
        String category = showGUI.showCategory.getText();
        return new TVShow(name, category);
    }

    // EFFECTS: check if the user didn't type in a unique name
    public void checkIfUniqueName(TVShow tvShow) {
        if (tvShow.getName().equals("") || alreadyInList(tvShow.getName())) {
            Toolkit.getDefaultToolkit().beep();
            showGUI.showName.requestFocusInWindow();
            showGUI.showName.selectAll();
        }
    }

    // MODIFIES: this
    // EFFECTS: save the newly added tv show into the file
    public void saveShowIntoFile() {
        try {
            Writer writer = new Writer(new File(LIST_FILE));
            writer.write(showGUI.wholeList);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save the show list to " + LIST_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS: reset the text fields
    public void resetTextField() {
        showGUI.showName.requestFocusInWindow();
        showGUI.showName.setText("");
        showGUI.showCategory.requestFocusInWindow();
        showGUI.showCategory.setText("");
    }

    // EFFECTS: check if the input name is already contained in the list
    private boolean alreadyInList(String name) {
        return listModel.contains(name);
    }

    // EFFECTS: enable the button for add a show
    private void enableButton() {
        if (!alreadyEnabled) {
            addButton.setEnabled(true);
        }
    }

    // EFFECTS: handle empty text field
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            addButton.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }

    //Required by DocumentListener.
    @Override
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    //Required by DocumentListener.
    @Override
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    //Required by DocumentListener.
    @Override
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // EFFECTS: make a sound when a button is clicked
    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new File(SOUND_FILE));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}



