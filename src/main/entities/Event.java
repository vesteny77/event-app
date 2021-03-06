package main.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A class that represents an event at the conference.
 *
 * @author Yile Xie, Zewen Ma, Haoze Huang
 * @version 2.0
 * @since 2020-11-27
 */

public class Event {

    private final ArrayList<String> attendeesID;
    private final ArrayList<String> speakersID;
    private String id;
    private String title;
    private LocalDateTime time;
    private String roomID;
    private String type;
    private int duration;
    private int capacity;

    /**
     * No-arg constructor for deserialization
     */
    public Event() {
        this.attendeesID = new ArrayList<>();
        this.speakersID = new ArrayList<>();
    }

    /**
     * No speaker event constructor.
     * A title, time, room number, duration, and capacity
     * are required to create an instance of Event that has no speakers.
     *
     * @param title    of the Event
     * @param time     of the Event
     * @param roomID   of the Event
     * @param duration of the Event (in minute)
     * @param capacity of the Event
     */
    public Event(String title, LocalDateTime time, String roomID, int duration, int capacity) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.time = time;
        this.roomID = roomID;
        this.type = "NoSpeakerEvent";
        this.attendeesID = new ArrayList<>();
        this.speakersID = new ArrayList<>();
        this.duration = duration;
        this.capacity = capacity;
    }

    /**
     * Multiple and Single speakers event constructor.
     * A title, time, room number, duration, and capacity
     * are required to create an instance of Event with multiple speakers.
     *
     * @param title    of the Event
     * @param time     of the Event
     * @param roomID   of the Event
     * @param type     of the Event
     * @param duration of the Event (in minute)
     * @param capacity of the Event
     */
    public Event(String title, LocalDateTime time, String roomID, String type, int duration, int capacity) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.time = time;
        this.roomID = roomID;
        this.speakersID = new ArrayList<>();
        this.attendeesID = new ArrayList<>();
        this.type = type;
        this.duration = duration;
        this.capacity = capacity;
    }

    /**
     * Get the unique String of this event.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets ID from string
     *
     * @param id String as string
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Returns a list of IDs of attendees who signed up for this event.
     *
     * @return attendeesID
     */
    public ArrayList<String> getAttendeesID() {
        return attendeesID;
    }

    /**
     * A given id of an attendee is added to the list of id
     *
     * @param id to be added
     */
    public void addAttendees(String id) {
        attendeesID.add(id);
    }

    /**
     * A given id of an attendee is removed from the list of id
     *
     * @param id to be removed
     */
    public void removeAttendees(String id) {
        attendeesID.remove(id);
    }

    /**
     * Returns the title of this event.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for the event.
     *
     * @param title to be changed to
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the time of this event.
     *
     * @return time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Reschedule the event.
     *
     * @param time to be changed to
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Returns the room ID in which this event occurs.
     *
     * @return roomNum
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Reassign a room for the event to take place.
     *
     * @param roomID to be changed to
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    /**
     * Get type of the event (We are not going to use this method in Phase 2) See "addSpeaker" method
     *
     * @return the type of the event
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type of the event
     *
     * @param type sets type of event
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get a list of speakers in the event
     *
     * @return an array list of speakerID
     */
    public ArrayList<String> getSpeakers() {
        return this.speakersID;
    }

    /**
     * Add a speaker to the speaker list.
     *
     * @param speakerID of the speaker
     */
    public void addSpeaker(String speakerID) {
        this.speakersID.add(speakerID);
    }

    /**
     * Remove a speaker from the speaker list.
     *
     * @param speakerID of the speaker
     */
    public void removeSpeaker(String speakerID) {
        this.speakersID.remove(speakerID);
    }

    /**
     * Get the duration of the event
     *
     * @return the duration of the event
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Set(Change) the duration of the event(in minute)
     *
     * @param duration of the event
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get the capacity of the event
     *
     * @return the capacity of the event
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Change the capacity of the event
     *
     * @param capacity of the event
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


}
