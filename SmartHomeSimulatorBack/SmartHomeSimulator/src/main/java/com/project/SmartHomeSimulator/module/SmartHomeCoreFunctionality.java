package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.roomObjects.Light;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class SmartHomeCoreFunctionality {

    private static SmartHomeCoreFunctionality smartHomeCoreFunctionality = null;
    @Autowired
    private SimulationContext simulationContext;

    public static SmartHomeCoreFunctionality getInstance(){
        if(smartHomeCoreFunctionality == null){
            smartHomeCoreFunctionality = new SmartHomeCoreFunctionality();
        }
        return smartHomeCoreFunctionality;
    }

    /**
     * block a window or unblock it
     * @param roomName
     * @param id
     * @param state - block or turn on = true and unblock or turn off = false - replace the state of the object
     * @return  - true if successful false if otherwise
     */
    public boolean objectStateSwitcher(String roomName, String id, boolean state) {
        Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
        UUID objectID = UUID.fromString(id);
        RoomObject roomObject = room.getRoomObjectByID(objectID);
        if (roomObject instanceof Window) {
            Window window = (Window) roomObject;
            window.setBlocked(state);
            return true;
        }
        else if (roomObject instanceof Light){
            Light light = (Light) roomObject;
            light.setIsOn(state);
            return true;
        }
        return false;
    }
}