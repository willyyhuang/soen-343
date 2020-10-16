package com.project.SmartHomeSimulator.model.roomObjects;

public class Window extends Object {
    private boolean isBlocked;

    public Window(){
    }
    public Window(Object object){
        super();
        setIs_On_Open(object.isIs_On_Open());
        setObjectType(object.getObjectType());
        this.isBlocked = false;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
