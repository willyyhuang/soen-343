package com.project.SmartHomeSimulator.model.roomObjects;

public class Door extends RoomObject {
    private boolean isBlocked;
    private boolean isOpen;



    public Door(){
    }
    public Door(RoomObject roomObject){
        super();
        setObjectType(roomObject.getObjectType());
        this.isBlocked = false;
    }

    public boolean isOpen(){return  isOpen;}
    public void setOpen(boolean open){
        isOpen=open;
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
