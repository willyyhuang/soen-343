package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.SmartHomeSimulator.model.roomObjects.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@Getter
@Setter
@JsonComponent
@JsonIgnoreProperties
public class Room {
    private String name;
    private List<User> users;
    private AC ac;
    private Door doors;
    private Heater heater;
    private Light lights;
    private Sensor sensor;
    private Window window;

    public boolean addUser(User user){
        return this.users.add(user);
    }
    public boolean removeUser(User user){
        return this.users.remove(user);
    }
    public boolean isEmpty(){
        return users.isEmpty();
    }

}
