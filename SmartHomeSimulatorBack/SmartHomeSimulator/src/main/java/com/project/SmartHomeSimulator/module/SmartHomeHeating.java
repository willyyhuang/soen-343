package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.api.SmartHomeHeatingController;
import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.roomObjects.*;

import java.util.List;
import java.util.UUID;

public class SmartHomeHeating extends Module{

    private static SmartHomeHeating smartHomeHeating = null;

    private static SimulationContext simulationContext = SimulationContext.getInstance();

    private SmartHomeHeating(){ setName("SmartHomeHeating");}

    public static SmartHomeHeating getInstance(){
        if (smartHomeHeating == null){
            smartHomeHeating = new SmartHomeHeating();
        }
        return smartHomeHeating;
    }

    public boolean addZone(int morningTemp, int eveningTemp, int nightTemp, String zone, List<String> roomNames){
        for (String roomName : roomNames){
            Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
            room.setZone(zone);
            room.setCurrentTemp(simulationContext.getOutsideTemp());
            room.setMorningTemp(morningTemp);
            room.setEveningTemp(eveningTemp);
            room.setNightTemp(nightTemp);
        }
        return true;
    }
    /**
     * changes the sate of the room object passed to it
     * @param roomName
     * @param id
     * @param state - block or turn on = true and unblock or turn off = false - replace the state of the object
     * @return  - true if successful false if otherwise
     */
    public boolean objectStateSwitcher(String roomName, String id, boolean state) {
        Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
        UUID objectID = UUID.fromString(id);
        RoomObject roomObject = room.getRoomObjectByID(objectID);
        if (roomObject instanceof Heater) {
            Heater heater = (Heater) roomObject;
            heater.setStatus(state);
            return true;
        }
        else if (roomObject instanceof AC){
            AC ac = (AC) roomObject;
            ac.setStatus(state);
            return true;
        }
        return false;
    }
}
