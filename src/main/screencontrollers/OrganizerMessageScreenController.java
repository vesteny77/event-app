package main.screencontrollers;

import main.controllers.ProgramController;
import main.presenters.OrganizerMessageScreen;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * OrganizerMessageScreenController is the screen where the organizer can choose who to message.
 *
 * @author Yi Tao Li
 * @version 1.0
 * @since 2020-11-13
 */
@Deprecated
public class OrganizerMessageScreenController extends AttendeeMessageScreenController {

    private final OrganizerMessageScreen organizerMessageScreen;

    /**
     * Constructor of OrganizerMessageScreenController
     *
     * @param programController pre-defined ProgramController
     */
    public OrganizerMessageScreenController(ProgramController programController) {
        super(programController);
        this.organizerMessageScreen = new OrganizerMessageScreen(this.usersManager, this.recipients);
    }

    /**
     * Asks OrganizerMessageScreen to display the appropriate prompts, receives input from the organizer, and processes
     * them accordingly to create and send messages.
     */
    public void start() {
        this.organizerMessageScreen.prompt();
        String next = scanner.nextLine();
        while (!next.equals("0")) {
            if (next.equals("all") || next.equals("attendees") || next.equals("speakers")) {
                sendMessage(next);
            } else {
                ArrayList<String> inputs = new ArrayList<>(Arrays.asList(next.split(",")));
                while (!isValid(inputs) && !next.equals("0")) {
                    this.organizerMessageScreen.prompt2(next);
                    next = this.scanner.nextLine();
                    inputs = new ArrayList<>(Arrays.asList(next.split(",")));
                }
                if (next.equals("0")) {
                    break;
                }
                sendMessage(inputs);
            }
            this.organizerMessageScreen.prompt();
            next = this.scanner.nextLine();
        }
        this.goToPreviousScreenController();
        this.end();
    }

    private void sendMessage(ArrayList<String> inputs) {
        this.organizerMessageScreen.messagePrompt();
        String next = scanner.nextLine();
        if (next.equals("0")) {
            return;
        }
        ArrayList<String> recipients = new ArrayList<>();
        for (String i : inputs) {
            recipients.add(this.recipients.get(Integer.parseInt(i) - 1));
        }
        this.messageController.broadCast(this.loggedInUser, recipients, next);
        this.organizerMessageScreen.successMessage();
    }

    private void sendMessage(String type) {
        this.organizerMessageScreen.messagePrompt();
        String next = scanner.nextLine();
        if (next.equals("0")) {
            return;
        }
        System.out.println(next);
        switch (type) {
            case "all":
                this.messageController.broadCastToAll(this.loggedInUser, next);
                this.organizerMessageScreen.successMessage();
                break;
            case "attendees":
                this.messageController.broadCastToAttendees(this.loggedInUser, next);
                this.organizerMessageScreen.successMessage();
                break;
            case "speakers":
                this.messageController.broadCastToSpeakers(this.loggedInUser, next);
                this.organizerMessageScreen.successMessage();
        }
    }

    private boolean isValid(ArrayList<String> inputs) {
        for (String input : inputs) {
            if (!isNumeric(input)) {
                return false;
            }
            if (!(0 < Integer.parseInt(input) && Integer.parseInt(input) <= recipients.size())) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
