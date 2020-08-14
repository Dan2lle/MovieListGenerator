package ui;

import model.ShowList;
import model.TVShow;
import persistence.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// used the ListDemo project as a source of code for this class
// Represents a GUI for the user story of adding a show to the list
public class ShowGUI extends JPanel  {
    private MyActionListener myActionListener;
    private JLabel nameLabel = new JLabel("Please enter the name below:");
    private JLabel categoryLabel = new JLabel("Please enter the category below:");
    public JTextField showName;
    public JTextField showCategory;
    public ShowList wholeList;

    public ShowGUI(ShowList wholeList) {
        super(new BorderLayout());
        this.wholeList = wholeList;
        this.myActionListener = new MyActionListener(this);
        createTextFields(this.myActionListener);
    }

    // EFFECTS: create the text fields for name and category
    private void createTextFields(MyActionListener myActionListener) {
        showName = new JTextField(15);
        showName.addActionListener(myActionListener);
        showName.getDocument().addDocumentListener(myActionListener);
        showCategory = new JTextField(15);
        showCategory.addActionListener(myActionListener);
        showCategory.getDocument().addDocumentListener(myActionListener);
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
        JScrollPane listScrollPane = new JScrollPane(this.myActionListener.jlist);
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
                        .addComponent(this.myActionListener.removeButton)
                        .addComponent(this.myActionListener.addButton)
                        .addComponent(this.myActionListener.saveButton)));
    }

    // EFFECTS: organizes the vertical layouts for components
    private void verticalGroup(GroupLayout layout) {
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel).addComponent(categoryLabel)
                        .addComponent(this.myActionListener.removeButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(showName).addComponent(showCategory)
                        .addComponent(this.myActionListener.addButton))
                .addComponent(this.myActionListener.saveButton));
    }

}
