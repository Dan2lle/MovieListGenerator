package ui;

import model.ShowList;
import model.TVShow;
import persistence.Writer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

// used the ListDemo project as a source of code for this class
// Represents a GUI for the user story of adding a show to the list
public class AddShowGUI extends JPanel  {
    private static final String LIST_FILE = "data/realShowList";
    private JList jlist;
    public DefaultListModel listModel;

    private static final String addString = "Add a show";
    private static final String saveString = "Save the shows";
    private JButton addButton;
    private JButton saveButton;
    private JLabel nameLabel;
    private JLabel categoryLabel;
    public JTextField showName;
    public JTextField showCategory;
    public ShowList wholeList;

    public AddShowGUI(ShowList wholeList) {
        super(new BorderLayout());
        this.wholeList = wholeList;
        listModel = new DefaultListModel();
        addListModel(wholeList);
        jlist = new JList(listModel);
        addButton = new JButton(addString);
        saveButton = new JButton(saveString);
        nameLabel = new JLabel("Please enter the name below:");
        categoryLabel = new JLabel("Please enter the category below:");
        AddListener addListener = new AddListener(addButton, saveButton);
        createList(jlist);
        createAddButton(addListener, addButton);
        createSaveButton(addListener, saveButton);
        createTextFields(addListener);
    }

    // EFFECTS: initiate the JList
    private void addListModel(ShowList showList) {
        for (int i = 0; i < showList.myList.size(); i++) {
            addElementAsString(showList.myList.get(i), listModel);
        }
    }

    // EFFECTS: present tv shows on the panel as readable strings
    public void addElementAsString(TVShow tvShow, DefaultListModel listModel) {
        String addString = "Name: " + tvShow.getName() + " - Category: "
                + tvShow.getCategory() + " - Is Watched? : " + tvShow.isWatched();
        listModel.addElement(addString);
    }

    // EFFECTS: create the list and put it in a scroll pane
    private void createList(JList jlist) {
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setSelectedIndex(0);
        jlist.setVisibleRowCount(5);
    }

    // EFFECTS: create the add button to add a show
    private void createAddButton(AddListener addListener, JButton addButton) {
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
    }

    // EFFECTS: create the save button to add a show
    private void createSaveButton(AddListener addListener, JButton saveButton) {
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(addListener);
    }

    // EFFECTS: create the text fields for name and category
    private void createTextFields(AddListener addListener) {
        showName = new JTextField(15);
        showName.addActionListener(addListener);
        showName.getDocument().addDocumentListener(addListener);
        showCategory = new JTextField(15);
        showCategory.addActionListener(addListener);
        showCategory.getDocument().addDocumentListener(addListener);
        createLayout();
    }

    // EFFECTS: create a panel that uses BoxLayout
    private void createLayout() {
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        horizontalGroup(layout);
        verticalGroup(layout);
        JScrollPane listScrollPane = new JScrollPane(jlist);
        add(listScrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.PAGE_END);
    }

    // EFFECTS: organizes the horizontal layouts for components
    private void horizontalGroup(GroupLayout layout) {
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nameLabel).addComponent(showName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(categoryLabel).addComponent(showCategory))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(addButton).addComponent(saveButton)));
    }

    // EFFECTS: organizes the vertical layouts for components
    private void verticalGroup(GroupLayout layout) {
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel).addComponent(categoryLabel).addComponent(addButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(showName).addComponent(showCategory).addComponent(saveButton)));
    }

    // This listener is shared by the text field and the add button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;
        private JButton saveButton;
        private static final String SOUND_FILE = "data/Mouse Click Fast.wav";

        public AddListener(JButton button, JButton saveButton) {
            this.button = button;
            this.saveButton = saveButton;
        }

        //Required by ActionListener.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button) {
                playSound();
                TVShow tvShow = getTVShow();
                checkIfUniqueName(tvShow);
                wholeList.addShow(tvShow);
                addElementAsString(tvShow, listModel);
                resetTextField();

                //Select the new item and make it visible.
                int index = jlist.getSelectedIndex();
                jlist.setSelectedIndex(index);
                jlist.ensureIndexIsVisible(index);
            } else if (e.getSource() == saveButton) {
                playSound();
                saveShowIntoFile();
            }
        }

        // EFFECTS: generate the input tv show
        public TVShow getTVShow() {
            String name = showName.getText();
            String category = showCategory.getText();
            TVShow tvShow = new TVShow(name, category);
            return tvShow;
        }

        // EFFECTS: check if the user didn't type in a unique name
        public void checkIfUniqueName(TVShow tvShow) {
            if (tvShow.getName().equals("") || alreadyInList(tvShow.getName())) {
                Toolkit.getDefaultToolkit().beep();
                showName.requestFocusInWindow();
                showName.selectAll();
                return;
            }
        }

        // MODIFIES: this
        // EFFECTS: save the newly added tv show into the file
        public void saveShowIntoFile() {
            try {
                Writer writer = new Writer(new File(LIST_FILE));
                writer.write(wholeList);
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
            showName.requestFocusInWindow();
            showName.setText("");
            showCategory.requestFocusInWindow();
            showCategory.setText("");
        }

        // EFFECTS: check if the input name is already contained in the list
        private boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        // EFFECTS: enable the button for add a show
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // EFFECTS:
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
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

}
