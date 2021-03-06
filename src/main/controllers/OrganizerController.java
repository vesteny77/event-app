package main.controllers;

import main.entities.Event;
import main.entities.Room;
import main.entities.User;
import main.usecases.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Apart from the responsibilities listed in the UserController, the OrganizerController handles advance actions
 * which could be done by an organizer: checking and managing events, creating speaker accounts, and creating
 * rooms.
 *
 * @author Ruoming Ren
 * @version 2.1
 * @since 2020-11-10
 */

public class OrganizerController extends AttendeeController {

    UsersManager usersManager;
    RoomManager roomManager;
    EventsManager eventsManager;


    /**
     * Constructor of OrganizerController for a logged in user.
     *
     * @param programController pre-defined programController
     */
    public OrganizerController(ProgramController programController) {
        super(programController);
        this.usersManager = programController.getUsersManager();
        this.roomManager = programController.getRoomManager();
        this.eventsManager = programController.getEventsManager();
    }

    /**
     * create a new room given roomNum and capacity
     *
     * @param roomNum  the roomNum of the room
     * @param capacity the capacity of the room
     * @return true if the room have been successfully created
     */
    public boolean createRoom(int roomNum, int capacity) {
        return roomManager.addRoom(roomNum, capacity);
    }

    public boolean hasTech(int roomNum) {
        return roomManager.getRoomGivenRoomNum(roomNum).getHasTech();
    }

    public boolean hasTable(int roomNum) {
        return roomManager.getRoomGivenRoomNum(roomNum).getIsTable();
    }

    public boolean hasStage(int roomNum) {
        return roomManager.getRoomGivenRoomNum(roomNum).getHasStage();
    }

    public int getRoomCapacity(int roomNum) {
        return roomManager.getRoomCapacityGivenRoomNum(roomNum);
    }

    /**
     * Build an event by giving the title, time, room ID, and speaker ID. The room must already exist.
     *
     * @param title    the title of the event
     * @param time     the time of the event
     * @param roomNum  the roomNum of the event
     * @param duration the duration of the event
     * @param capacity the capacity of the event
     * @param type     the type of the event
     * @return true if the event be created successfully.
     */
    public boolean createEvent(String title, LocalDateTime time, int roomNum, int duration, int capacity, String type) {
        if (roomManager.getRoomGivenRoomNum(roomNum) == null) {
            return false;
        }
        EventBuilder newEvent = new EventBuilder();
        newEvent.setTitle(title);
        newEvent.setTime(time);
        newEvent.setRoom(roomManager.getRoomIDGivenRoomNum(roomNum));
        newEvent.setDuration(duration);
        newEvent.setCapacity(capacity);
        return eventController.createEvent(newEvent, type);
    }

    /**
     * Remove the event from the schedule and return true if the event have been successfully removed
     *
     * @param event the uuid of the event
     * @return true if the event have been successfully removed
     */
    public boolean removeEvent(String event) {
        return eventController.removeEvent(event);
    }

    /**
     * Update the time of the event
     *
     * @param eventId the uuid of the event
     * @param time    the new time of the event
     * @return true if the event's time have been successfully update or the new time has no difference with the
     * old time. Return false if the new time is conflict with other event and the time haven't been successfully
     * updated.
     */
    public boolean updateTime(String eventId, LocalDateTime time) {
        Map<String, Event> schedule = eventsManager.getSchedule();
        EventInfoManager eventInfoManager = new EventInfoManager(eventId, schedule, roomManager, usersManager);
        String roomId = eventInfoManager.getRoomId(eventId);
        int duration = eventInfoManager.getDuration(eventId);
        int capacity = eventInfoManager.getCapacity();
        return eventController.updateEventInfo(eventId, time, roomId, duration, capacity);
    }

    /**
     * Update the capacity of the event
     *
     * @param eventId     the uuid of the event
     * @param newCapacity the new capacity of the event
     * @return true if the event's time have been successfully update or the new time has no difference with the
     * old time. Return false if the new time is conflict with other event and the time haven't been successfully
     * updated.
     */
    public boolean updateCapacity(String eventId, int newCapacity) {
        Map<String, Event> schedule = eventsManager.getSchedule();
        EventInfoManager eventInfoManager = new EventInfoManager(eventId, schedule, roomManager, usersManager);
        String roomId = eventInfoManager.getRoomId(eventId);
        LocalDateTime time = eventInfoManager.getTime(eventId);
        int duration = eventInfoManager.getDuration(eventId);
        int roomNum = roomManager.getRoomNumGivenId(roomId);
        if (checkCapacityInBound(roomNum, newCapacity)){
            return eventController.updateEventInfo(eventId, time, roomId, duration, newCapacity);
        }else{
            int oldCapacity = eventInfoManager.getCapacity();
            eventController.updateEventInfo(eventId, time, roomId, duration, oldCapacity);
            return false;
        }

    }

    public boolean checkCapacityInBound(int roomNum, int eventCap) {
        return roomManager.getRoomCapacityGivenRoomNum(roomNum) >= eventCap;
    }


    /**
     * Return true iff the speaker is successfully updated
     *
     * @param eventId   of the single speaker event
     * @param speakerId of the speaker
     * @return true if the update has successfully execute
     */
    public boolean updateSingleEventSpeaker(String eventId, String speakerId) {
        ArrayList<String> currentSpeaker = eventController.getEventSpeakers(eventId);
        if (currentSpeaker != null) {
            if (currentSpeaker.size() == 1){
                String previousSpeaker = currentSpeaker.get(0);
                eventController.removeSpeaker(eventId, previousSpeaker);
                return eventController.addSpeaker(eventId, speakerId);
            }
        }
        return eventController.addSpeaker(eventId, speakerId);
    }

    /**
     * Return true iff the speaker is successfully removed from the speaker list
     *
     * @param eventId   of the multi speaker event
     * @param speakerId of the speaker
     * @return true iff the speaker is in the speaker list of the event and is removed successfully
     */
    public boolean removeSpeakerMultiEvent(String eventId, String speakerId) {
        ArrayList<String> currentSpeaker = eventController.getEventSpeakers(eventId);
        if (currentSpeaker != null) {
            return eventController.removeSpeaker(eventId, speakerId);
        }
        return false;
    }

    /**
     * Return true iff the speaker is successfully added
     *
     * @param eventId   of the multi speaker event
     * @param speakerId of the speaker
     * @return true iff the speaker is not in the speaker list previously and is added successfully
     */
    public boolean addSpeakerMultiEvent(String eventId, String speakerId) {
        return eventController.addSpeaker(eventId, speakerId);
    }

    /**
     * update the room of the event
     *
     * @param eventId the uuid of the event
     * @param roomNum the new room of the event
     * @return true if room of the event have been successfully update of the new room has no difference with the
     * old room. Return false if the room has been occupied at that time
     */
    public boolean updateRoom(String eventId, int roomNum) {
        Map<String, Event> schedule = eventsManager.getSchedule();
        EventInfoManager eventInfoManager = new EventInfoManager(eventId, schedule, roomManager, usersManager);
        LocalDateTime time = eventInfoManager.getTime(eventId);
        String roomId = roomManager.getRoomIDGivenRoomNum(roomNum);
        int duration = eventInfoManager.getDuration(eventId);
        int capacity = eventInfoManager.getCapacity();
        return eventController.updateEventInfo(eventId, time, roomId, duration, capacity);
    }

    /**
     * create a new speaker account
     *
     * @param userName the user name of the speaker
     * @param password the password of the speaker
     * @return true if the account have been successfully created
     */
    public boolean createSpeaker(String userName, String password) {
        return usersManager.addUser(userName, password, "Speaker");
    }

    /**
     * get all speakers of the program
     *
     * @return a list of speakers which inside the program
     */
    public List<String> getAllSpeakers() {
        List<String> speakers = new ArrayList<>();
        for (String user : usersManager.getAllUsers()) {
            // check if he is a speaker
            if (this.usersManager.fetchType(user).equals("Speaker")) {
                speakers.add(user);
            }
        }
        return speakers;
    }

    public List<String> getAllSpeakerUsernames(){
        List<String> usernames = new ArrayList<>();
        List<String> speakerIds = getAllSpeakers();
        for (String speakerId: speakerIds){
            usernames.add(usersManager.getSpeakerUsername(speakerId));
        }
        return usernames;
    }

    /**
     * get all speakers in string format
     *
     * @return a string represent all speakers inside the program
     */
    public String speakerToString() {
        String ret = "";
        int count = 1;
        for (String speakerId : this.getAllSpeakers()) {
            User speaker = usersManager.fetchUser(speakerId);
            ret = ret + count + ". " + speaker.getUsername() + "\n";
            count++;
        }
        return ret;
    }

    /**
     * get all rooms' numbers
     *
     * @return a list of rooms' numbers
     */
    public List<Integer> getAllRooms() {
        return this.roomManager.getAllRooms();
    }

    /**
     * add constraints to room base on list of constraints
     *
     * @param roomNum  room number
     * @param category room constraints
     * @return verification if there is any room constaints are modified
     */
    public boolean addConstraintToRoom(int roomNum, ArrayList<String> category) {
        return roomManager.addConstraintToRoom(roomNum, category);
    }

    /**
     * get all rooms in string format
     *
     * @return a string represent all rooms inside the program
     */
    public String roomToString(List<Integer> suggestedRooms) {
        String ret = "";
        int count = 1;
        for (int roomNum : suggestedRooms) {
            Room room = roomManager.getRoomGivenRoomNum(roomNum);
            ret = ret + count + ". Room #" + room.getRoomNum() + "\n";
            count++;
        }
        return ret;
    }

    public ArrayList<Integer> roomToList(List<Integer> suggestedRooms) {
        ArrayList<Integer> listOfRooms = new ArrayList<>();
        for (int roomNum : suggestedRooms) {
            Room room = roomManager.getRoomGivenRoomNum(roomNum);
            listOfRooms.add(room.getRoomNum());
        }
        return listOfRooms;
    }

    /**
     * get the event controller of the program
     *
     * @return the event controller
     */
    public EventController getEventController() {
        return eventController;
    }


}
