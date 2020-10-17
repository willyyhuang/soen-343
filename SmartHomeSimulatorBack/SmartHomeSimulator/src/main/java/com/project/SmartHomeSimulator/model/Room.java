package com.project.SmartHomeSimulator.model;

import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;
import java.util.UUID;


@JsonComponent
public class Room {
    private String name;
    private List<User> users;
    private List<RoomObject> roomObjects;

    public boolean addUser(User user){
        return this.users.add(user);
    }
    public boolean removeUser(User user){
        return this.users.remove(user);
    }
    public boolean isEmpty(){
        return users.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<RoomObject> getObjects() {
        return roomObjects;
    }

    public void setObjects(List<RoomObject> roomObjects) {
        this.roomObjects = roomObjects;
    }

    public RoomObject getObjectByID(UUID deviceID){
        if (this.getObjects() != null){
            for (RoomObject roomObject : this.getObjects()) {
                if (roomObject.getId().compareTo(deviceID) == 0) {
                    return roomObject;
                }
            }
        }
        return null;
    }
}
