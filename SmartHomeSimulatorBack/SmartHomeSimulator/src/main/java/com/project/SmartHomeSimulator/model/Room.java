package com.project.SmartHomeSimulator.model;

import com.project.SmartHomeSimulator.model.roomObjects.Object;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;
import java.util.UUID;


@JsonComponent
public class Room {
    private String name;
    private List<User> users;
    private List<Object> objects;

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

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public Object getObjectByID(UUID deviceID){
        if (this.getObjects() != null){
            for (Object object : this.getObjects()) {
                if (object.getId().compareTo(deviceID) == 0) {
                    return object;
                }
            }
        }
        return null;
    }
}
