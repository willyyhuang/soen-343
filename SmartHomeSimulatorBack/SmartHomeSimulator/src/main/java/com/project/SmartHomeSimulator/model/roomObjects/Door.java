package com.project.SmartHomeSimulator.model.roomObjects;

public class Door {
    private boolean isOpen;
    private boolean isBlocked;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
