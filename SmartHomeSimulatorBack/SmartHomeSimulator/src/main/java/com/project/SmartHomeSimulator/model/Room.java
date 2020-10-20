package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;
import java.util.UUID;

/**
 * Room class is used to hold information pertaining the rooms in the home layout
 */
@JsonComponent
@JsonIgnoreProperties("users")
public class Room {
    private String name;
    private List<RoomObject> roomObjects;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoomObject> getObjects() {
        return roomObjects;
    }

    public void setObjects(List<RoomObject> roomObjects) {
        this.roomObjects = roomObjects;
    }

    /**
     * Gets a room object by id
     * @param deviceID
     * @return room object
     */
    public RoomObject getRoomObjectByID(UUID deviceID){
        if (this.getObjects() != null){
            for (RoomObject roomObject : this.getObjects()) {
                if (roomObject.getId().compareTo(deviceID) == 0) {
                    return roomObject;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", roomObjects=" + roomObjects +
                '}';
    }
}
