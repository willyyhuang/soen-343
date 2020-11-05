package com.project.SmartHomeSimulator.model.roomObjects;

public class Door extends RoomObject {
    private boolean isBlocked;

    public Door(){
    }
    public Door(RoomObject roomObject){
        super();
        setObjectType(roomObject.getObjectType());
        this.isBlocked = false;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "RoomObject{" +
                "name=" + getName() +
                ", id=" + getId() +
                ", roomObjectType=" + getObjectType() +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
