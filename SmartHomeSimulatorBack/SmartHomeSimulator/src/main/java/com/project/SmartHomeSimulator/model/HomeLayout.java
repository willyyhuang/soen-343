package com.project.SmartHomeSimulator.model;

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

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
