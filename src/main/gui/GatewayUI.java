package main.gui;

import main.controllers.ProgramController;
import main.gui_interface.IGatewayUI;

import javax.swing.*;

@Deprecated
public class GatewayUI extends JFrame implements IGatewayUI {
    private ProgramController programController;
    private JButton button1;
    private JPanel panel1;
    private JButton saveAllButton;
    private JButton saveUsersButton;
    private JButton saveRoomsButton;
    private JButton saveEventsButton;
    private JButton saveMessagesButton;
    private JButton saveInboxesButton;

    public GatewayUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.programController = programController;
    }
}
