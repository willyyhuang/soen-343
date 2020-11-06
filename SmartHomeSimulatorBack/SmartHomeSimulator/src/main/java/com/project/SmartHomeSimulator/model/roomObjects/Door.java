package com.project.SmartHomeSimulator.model.roomObjects;

public class Door extends RoomObject {

    public Door(){
    }
    public Door(RoomObject roomObject){
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
