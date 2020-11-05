package com.project.SmartHomeSimulator.model.roomObjects;

/**
 * Window's class
 */
public class Window extends RoomObject {
    private boolean isBlocked;

    public Window(){
    }
    public Window(RoomObject roomObject){
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
}
