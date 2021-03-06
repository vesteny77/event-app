package main.gui;

import main.gui_interface.IListOfUsersUI;
import main.guilisteners.BackButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * the file contains the screen which display the list of users
 *
 * @see UserManagementUI
 * @see AdminUserManagementUI
 * @author Ruoming Ren
 */
@SuppressWarnings("FieldCanBeLocal")

public class ListOfUsersUI extends JFrame implements IListOfUsersUI {

    private JPanel jPanel;
    private JList<Object> userList;
    private JScrollPane jScrollPane;
    private JButton backButton;
    private BackButtonListener backButtonListener;
    private UserManagementUI userManagementUI;
    private AdminUserManagementUI adminUserManagementUI;

    public ListOfUsersUI(ArrayList<String> listOfUserInfo) {
        jPanel = new JPanel();

        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // list of users
        Object[] arrayOfUserInfo = listOfUserInfo.toArray();
        userList = new JList<>(arrayOfUserInfo);

        userList.setVisibleRowCount(20);
        jScrollPane = new JScrollPane(userList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setPreferredSize(new Dimension(550, 400));
        jScrollPane.setViewportView(userList);
        jPanel.add(jScrollPane);


        // back button
        backButton = new JButton("Back");
        jPanel.add(backButton);

        backButton.addActionListener(e -> notifyListenerOnBackButtonClicked());

        add(jPanel);
        setVisible(true);
    }

    @Override
    public void notifyListenerOnBackButtonClicked() {
        backButtonListener.onBackButtonClicked();
    }

    @Override
    public void addBackButtonListener(BackButtonListener listener) {
        backButtonListener = listener;
    }

    @Override
    public UserManagementUI goToUserManagementUI() {
        userManagementUI = new UserManagementUI();
        this.dispose();
        return userManagementUI;
    }

    public AdminUserManagementUI goToAdminUserManagementUI() {
        adminUserManagementUI = new AdminUserManagementUI();
        this.dispose();
        return adminUserManagementUI;
    }
}
