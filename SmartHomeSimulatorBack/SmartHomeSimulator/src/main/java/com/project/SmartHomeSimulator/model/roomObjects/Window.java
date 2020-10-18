package com.project.SmartHomeSimulator.model.roomObjects;

public class Window extends RoomObject {
    private boolean isBlocked;

    public Window(){
    }
    public Window(RoomObject roomObject){
        super();
        setStatus(roomObject.isStatus());
        setObjectType(roomObject.getObjectType());
        this.isBlocked = false;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
