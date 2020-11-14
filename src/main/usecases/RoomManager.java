package main.usecases;

import main.entities.Inbox;
import main.entities.Room;

import java.time.LocalDateTime;
import java.util.*;

/**
 * this class stores all rooms which could hold events
 * @author Ruoming Ren
 */
public class RoomManager {

    private final Map<UUID, Room> rooms = new HashMap<>();

    /**
     * a new room will be added if its roomNum is not conflict with others.
     * @param roomNum the new room number
     * @param capacity the new room's capacity
     * @return true if the room has been successfully created
     */
    public boolean addRoom(int roomNum, int capacity){
        for(Room room: rooms.values()){
            if(room.getRoomNum() == roomNum){
                return false;
            }
        }
        Room newRoom = new Room(roomNum);
        newRoom.setCapacity(capacity);
        rooms.put(newRoom.getId(), newRoom);
        return true;
    }

    /**
     * the method will return the id of a room given its roomNum
     * @param roomNum the roomNum of a room
     * @return the id of the room, if there doesn't exist a room with given roomNum, return null.
     */
    public UUID getRoomIDGivenRoomNum(int roomNum){
        if(this.getRoomGivenRoomNum(roomNum) == null){
            return null;
        }else{
            return getRoomGivenRoomNum(roomNum).getId();
        }
    }

    /**
     * the method will return the room object given its roomNum
     * @param roomNum the roomNum of the room
     * @return the room object of the room, if there doesn't exist a room with given roomNum, return null.
     */
    public Room getRoomGivenRoomNum(int roomNum){
        Room target = null;
        for(Room room: rooms.values()){
            if(room.getRoomNum() == roomNum){
                target = room;
            }
        }
        if(target == null){
            return null;
        }else{
            return target;
        }
    }

    /**
     * give the schedule of a room given its roomNum
     * @param roomNum the given roomNum
     * @return the schedule of the room. Return null if the room does not exist.
     */
    public HashMap<LocalDateTime, UUID> roomSchedule(int roomNum){
        if(this.getRoomGivenRoomNum(roomNum) == null){
            return null;
        }else{
            return this.getRoomGivenRoomNum(roomNum).getSchedule();
        }
    }

    public List<UUID> getAllRooms(){
        List<UUID> allRooms = new ArrayList<UUID>();
        for(Room room: this.rooms.values()){
            allRooms.add(room.getId());
        }
        return allRooms;
    }

    public Collection<Room> getAllRoomsObject(){
        return this.rooms.values();
    }


}