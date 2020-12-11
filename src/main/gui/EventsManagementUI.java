package main.gui;

import main.controllers.ProgramController;
import main.gui_interface.IEventsManagementUI;

import javax.swing.*;

public class EventsManagementUI extends JFrame implements IEventsManagementUI  {
    private JButton backButton;
    private JPanel panel1;
    private JButton createRoomButton;
    private JButton createEventButton;
    private JButton cancelEventButton;
    private JButton modifyRoomButton;
    private JButton modifyTimeButton;
    private JButton modifySpeakerButton;
    private JButton seeScheduleButton;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel Name;
    private JButton confirmButton;
    private JList EventList;
    private ProgramController programController;

    public EventsManagementUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.programController = programController;

    }
}