package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.roomObjects.*;
import org.springframework.boot.jackson.JsonComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * HomeLayout class is used to hold information pertaining the home layout
 */
@JsonComponent
public class HomeLayout {
    private List<Room> roomList;
    private int usersInHome;
    public static int windowCount = 1;
    private static int doorCount = 1;
    private static int lightCount = 1;
    /**
     * Gets a room by name
     *
     * @param name
     * @return Room
     */
    public Room getRoomByName(String name) {
        for (Room room : this.roomList) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Gets a room by object Id
     *
     * @param objectID
     * @return String
     */
    public String getRoomNameByObjectID(String objectID) {
        for (Room room : this.roomList) {
            for (RoomObject roomObject : room.getObjects()) {
                if (roomObject.getId().toString().equals(objectID)) {
                    return room.getName();
                }
            }
        }
        return null;
    }

    /**
     * Reads home layout string and maps it to a HomeLayout object
     *
     * @param homeLayoutFile
     * @return HomeLayout
     */
    public HomeLayout readHomeLayout(String homeLayoutFile) {
        homeLayoutFile = homeLayoutFile.replace("\\", "");
        homeLayoutFile = homeLayoutFile.substring(0, 12) + homeLayoutFile.substring(13, homeLayoutFile.length() - 2) + "}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HomeLayout homeLayout = objectMapper.readValue(homeLayoutFile, HomeLayout.class);
            List<Room> rooms = createObjects(homeLayout.getRoomList());
            Room outside = new Room();
            outside.setName("outside");
            List<RoomObject> objects = new ArrayList<>();
            outside.setObjects(objects);
            rooms.add(outside);
            homeLayout.setRoomList(rooms);
            return homeLayout;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Cast roomObjects of roomObjectType window to Window type objects
     *
     * @param rooms
     * @return Room list
     */
    public List<Room> createObjects(List<Room> rooms) {
        List<RoomObject> roomObjectsJson;
        Window window;
        Door door;
        Light light;
        if (rooms != null) {
            for (Room room : rooms) {
                List<RoomObject> roomObjects = new ArrayList<>();
                roomObjectsJson = room.getObjects();
                for (RoomObject roomObject : roomObjectsJson) {
                    if (roomObject.getObjectType() == RoomObjectType.WINDOW) {
                        window = new Window(roomObject);
                        window.setName(room.getName() + "-Window" + windowCount++);
                        roomObjects.add(window);
                    }
                    else if (roomObject.getObjectType() == RoomObjectType.DOOR) {
                        door = new Door(roomObject);
                        door.setName(room.getName() + " - Door" + doorCount++);
                        roomObjects.add(door);
                    }
                    else if (roomObject.getObjectType() == RoomObjectType.LIGHT) {
                        light = new Light(roomObject);
                        light.setName(room.getName() + " - Light" + lightCount++);
                        roomObjects.add(light);
                    }
                }
                room.setObjects(roomObjects);
            }
            return rooms;
        }
        return null;
    }

    public List<RoomObject> allLights(String roomName){
        Room room = getRoomByName(roomName);
        if(room == null){
            List<RoomObject> list = new ArrayList<>();
            return list;
        }
        return room.allLights();
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public boolean isHomeEmpty() {
        return usersInHome == 0;
    }

    public void addUsersInHome(String homeLocation) {
        if (!homeLocation.equals("outside")){
            this.usersInHome++;
        }
    }

    public void removeUsersInHome(String homeLocation) {
        if (usersInHome != 0 && homeLocation.equals("outside")) {
            usersInHome--;
        }
    }

    @Override
    public String toString() {
        return "HomeLayout{" +
                "roomList=" + roomList +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomList);
    }
}
