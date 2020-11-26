package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObjectType;
import org.springframework.boot.jackson.JsonComponent;

import java.util.ArrayList;
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
    private String zone;
    private boolean overridden = false;
    private int currentTemp;
    private int morningTemp;
    private int eveningTemp;
    private int nightTemp;

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getMorningTemp() {
        return morningTemp;
    }

    public void setMorningTemp(int morningTemp) {
        this.morningTemp = morningTemp;
    }

    public int getEveningTemp() {
        return eveningTemp;
    }

    public void setEveningTemp(int eveningTemp) {
        this.eveningTemp = eveningTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(int nightTemp) {
        this.nightTemp = nightTemp;
    }

    public boolean isOverridden() {
        return overridden;
    }

    public void setOverridden(boolean overridden) {
        this.overridden = overridden;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

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

    /**
     * Gets a room object by type
     * @param type
     * @return room object
     */
    public RoomObject getRoomObjectByType( RoomObjectType type){
        if (this.getObjects() != null){
            for (RoomObject roomObject : this.getObjects()) {
                if (roomObject.getObjectType() == type) {
                    return roomObject;
                }
            }
        }
        return null;
    }

    public List<RoomObject> allLights(){
        List<RoomObject> lights = new ArrayList<>();
        for(RoomObject object : this.getObjects()){
            if(object.getObjectType() == RoomObjectType.LIGHT){
                lights.add(object);
            }
        }
        return lights;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", roomObjects=" + roomObjects +
                '}';
    }
}
