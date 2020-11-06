package com.project.SmartHomeSimulator.model.roomObjects;

public class Light extends RoomObject{

    public Light(){
    }
    public Light(RoomObject roomObject){
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
