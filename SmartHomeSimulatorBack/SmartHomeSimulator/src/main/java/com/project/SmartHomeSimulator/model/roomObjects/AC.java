package com.project.SmartHomeSimulator.model.roomObjects;

public class AC extends RoomObject{

    public AC(RoomObject roomObject){
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
