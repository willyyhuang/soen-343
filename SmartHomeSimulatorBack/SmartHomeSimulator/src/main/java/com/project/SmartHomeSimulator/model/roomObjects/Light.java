package com.project.SmartHomeSimulator.model.roomObjects;

public class Light extends RoomObject{
    // true means it's on, false means it's off
    private boolean isOn;

    public Light(){
    }
    public Light(RoomObject roomObject){
        super();
        setObjectType(roomObject.getObjectType());
        this.isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setIsOn(boolean blocked) {
        isOn = blocked;
    }
}
