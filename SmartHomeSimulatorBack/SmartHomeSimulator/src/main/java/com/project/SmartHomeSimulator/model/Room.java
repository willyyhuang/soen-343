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
    private double currentTemp;
    private double desiredTemp;
    private String period1;
    private int period1Temp;
    private String period2;
    private int period2Temp;
    private String period3;
    private int period3Temp;
    private int usersInRoom;

    public double getDesiredTemp() {
        return desiredTemp;
    }

    public void setDesiredTemp(double desiredTemp) {
        this.desiredTemp = desiredTemp;
    }

    public String getPeriod1() {
        return period1;
    }

    public void setPeriod1(String period1) {
        this.period1 = period1;
    }

    public int getPeriod1Temp() {
        return period1Temp;
    }

    public void setPeriod1Temp(int period1Temp) {
        this.period1Temp = period1Temp;
    }

    public String getPeriod2() {
        return period2;
    }

    public void setPeriod2(String period2) {
        this.period2 = period2;
    }

    public int getPeriod2Temp() {
        return period2Temp;
    }

    public void setPeriod2Temp(int period2Temp) {
        this.period2Temp = period2Temp;
    }

    public String getPeriod3() {
        return period3;
    }

    public void setPeriod3(String period3) {
        this.period3 = period3;
    }

    public int getPeriod3Temp() {
        return period3Temp;
    }

    public void setPeriod3Temp(int period3Temp) {
        this.period3Temp = period3Temp;
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

    public void incrementUsersInRoom() {
        usersInRoom++;
    }

    public void decrementUsersInRoom() {
        usersInRoom--;
    }

    public boolean roomEmpty() {
        return usersInRoom == 0;
    }

    public int getUsersInRoom() {
        return usersInRoom;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    /**
     * Gets a room object by id
     *
     * @param deviceID
     * @return room object
     */
    public RoomObject getRoomObjectByID(UUID deviceID) {
        if (this.getObjects() != null) {
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
     *
     * @param type
     * @return room object
     */
    public RoomObject getRoomObjectByType(RoomObjectType type) {
        if (this.getObjects() != null) {
            for (RoomObject roomObject : this.getObjects()) {
                if (roomObject.getObjectType() == type) {
                    return roomObject;
                }
            }
        }
        return null;
    }

    public List<RoomObject> allLights() {
        List<RoomObject> lights = new ArrayList<>();
        for (RoomObject object : this.getObjects()) {
            if (object.getObjectType() == RoomObjectType.LIGHT) {
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
