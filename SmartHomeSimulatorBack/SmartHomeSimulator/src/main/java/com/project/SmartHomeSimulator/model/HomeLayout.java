package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObjectType;
import com.project.SmartHomeSimulator.model.roomObjects.Window;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;
import java.util.Objects;

@JsonComponent
public class HomeLayout {
    private List<Room> roomList;

    public Room getRoomByName(String name) {
        for (Room room : this.roomList) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    public HomeLayout readHomeLayout(String homeLayoutFile) {
        homeLayoutFile = homeLayoutFile.replace("\\","");
        homeLayoutFile = homeLayoutFile.substring(0,12) + homeLayoutFile.substring(13,homeLayoutFile.length()-2) + "}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HomeLayout homeLayout = objectMapper.readValue(homeLayoutFile, HomeLayout.class);
            List<Room> rooms = createWindowObjects(homeLayout.getRoomList());
            homeLayout.setRoomList(rooms);
            return homeLayout;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Room> createWindowObjects(List<Room> rooms) {
        List<RoomObject> roomObjects;
        Window window;
        if (rooms != null) {
            for (Room room : rooms) {
                roomObjects = room.getObjects();
                for (RoomObject roomObject : roomObjects) {
                    if (roomObject.getObjectType() == RoomObjectType.WINDOW) {
                        window = new Window(roomObject);
                        roomObjects.remove(roomObject);
                        roomObjects.add(window);
                    }
                }
                room.setObjects(roomObjects);
            }
            return rooms;
        }
        return null;

    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
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
