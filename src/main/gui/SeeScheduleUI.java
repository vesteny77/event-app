package main.gui;

import main.gui_interface.ISeeScheduleUI;
import main.guilisteners.BackButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * UI class for viewing available events
 *
 * @see EventsManagementUI
 * @author Steven Yuan
 */
public class SeeScheduleUI extends JFrame implements ISeeScheduleUI {

    private JPanel jPanel;
    private JList<Object> listOfEvents;
    private JScrollPane jScrollPane;
    private JButton backButton;
    private BackButtonListener backButtonListener;
    private EventsManagementUI eventsManagementUI;

    public SeeScheduleUI(ArrayList<String> listOfEventsInfo) {
        this.jPanel = new JPanel();

        this.setTitle("Available Events");
        this.setSize(600, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        listOfEvents = new JList<>(listOfEventsInfo.toArray());
        listOfEvents.setVisibleRowCount(20);
        jScrollPane = new JScrollPane(listOfEvents, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(550, 400));
        jScrollPane.setViewportView(listOfEvents);
        jPanel.add(jScrollPane);


        backButton = new JButton("Back");
        jPanel.add(backButton);

        this.add(jPanel);
        this.setVisible(true);

        backButton.addActionListener(e -> notifyListenerBackButtonClicked());

    }

    public void notifyListenerBackButtonClicked() {
        backButtonListener.onBackButtonClicked();
    }

    public void addBackButtonListener(BackButtonListener listener) {
        backButtonListener = listener;
    }

    public EventsManagementUI goToEventsManagementUI() {
        eventsManagementUI = new EventsManagementUI();
        this.dispose();
        return eventsManagementUI;
    }
}
