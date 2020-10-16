package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.SmartHomeSimulator.model.roomObjects.*;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;


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

    public AC getAc() {
        return ac;
    }

    public void setAc(AC ac) {
        this.ac = ac;
    }

    public Door getDoors() {
        return doors;
    }

    public void setDoors(Door doors) {
        this.doors = doors;
    }

    public Heater getHeater() {
        return heater;
    }

    public void setHeater(Heater heater) {
        this.heater = heater;
    }

    public Light getLights() {
        return lights;
    }

    public void setLights(Light lights) {
        this.lights = lights;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}
