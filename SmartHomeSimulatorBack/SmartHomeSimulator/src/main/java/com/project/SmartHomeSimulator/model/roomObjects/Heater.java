package com.project.SmartHomeSimulator.model.roomObjects;

public class Heater extends RoomObject {

    public Heater(RoomObject roomObject){
        super();
        setObjectType(roomObject.getObjectType());
    }

    @Override
    public String toString() {
        return "RoomObject{" +
                "name=" + getName() +
                ", id=" + getId() +
                ", roomObjectType=" + getObjectType() +
                ", status=" + isStatus() +
                '}';
    }
}
