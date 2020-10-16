package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.roomObjects.Object;
import com.project.SmartHomeSimulator.model.roomObjects.ObjectType;
import com.project.SmartHomeSimulator.model.roomObjects.Window;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

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
        List<Object> objects;
        Window window;
        if (rooms != null) {
            for (Room room : rooms) {
                objects = room.getObjects();
                for (Object object : objects) {
                    if (object.getObjectType() == ObjectType.WINDOW) {
                        window = new Window(object);
                        objects.remove(object);
                        objects.add(window);
                    }
                }
                room.setObjects(objects);
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
}
