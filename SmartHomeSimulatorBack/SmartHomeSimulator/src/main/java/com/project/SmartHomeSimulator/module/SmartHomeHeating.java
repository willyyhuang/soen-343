package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.Zone;
import com.project.SmartHomeSimulator.model.roomObjects.*;

import java.util.ArrayList;

/**
 * Module about heating the house
 */
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

    /**
     * Add a zone with assigned rooms and temperatures - initial temperature of a room is outside temperature
     * @param zone
     * @return
     */
    public boolean addZone(Zone zone){
        for (String roomName : zone.getRoomsInZone()){
            Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
            room.setZone(zone.getName());
            switchStates(room, simulationContext.getOutsideTemp(), zone.getCurrentTemp());
            room.setCurrentTemp(zone.getCurrentTemp());
            room.setPeriod1(zone.getPeriod1());
            room.setPeriod1Temp(zone.getPeriod1Temp());
            room.setPeriod2(zone.getPeriod2());
            room.setPeriod2Temp(zone.getPeriod2Temp());
            room.setPeriod3(zone.getPeriod3());
            room.setPeriod3Temp(zone.getPeriod3Temp());
            room.setOverridden(false);
        }
        return true;
    }

    /**
     * Change room temperature and turn on or off ac or heater depending on the new temperature
     * @param roomName
     * @param newTemp
     * @return
     */
    public boolean changeRoomTemp(String roomName, int newTemp){
        Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
        switchStates(room,room.getCurrentTemp(),newTemp);
        room.setCurrentTemp(newTemp);
        room.setOverridden(true);
        return true;
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     * @param rooms
     * @param period
     * @return
     */
    public boolean changeZoneTemp(ArrayList<Room> rooms, int period){
        int newTemp = getPeriodTemp(rooms.get(0), period);
        for (Room room : rooms){
            switchStates(room,room.getCurrentTemp(),newTemp);
            room.setCurrentTemp(newTemp);
            room.setOverridden(false);
        }
        return true;
    }
    /**
     * changes the sate of the room object passed to it
     * @param roomObject
     * @param state - block or turn on = true and unblock or turn off = false - replace the state of the object
     * @return  - true if successful false if otherwise
     */
    public boolean objectStateSwitcher(RoomObject roomObject, boolean state) {
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

    /**
     * Switches AC and Heater state depending on the current temp and the desired temp
     * @param room
     * @param currentTemp
     * @param desiredTemp
     */
    public void switchStates(Room room, int currentTemp, int desiredTemp){
        Heater heater = (Heater) room.getRoomObjectByType(RoomObjectType.HEATER);
        AC ac = (AC) room.getRoomObjectByType(RoomObjectType.AC);
        if (currentTemp < desiredTemp){
            objectStateSwitcher(heater, true);
            objectStateSwitcher(ac, false);
        }
        else if(currentTemp > desiredTemp) {
            objectStateSwitcher(heater, false);
            objectStateSwitcher(ac, true);
        }
        else{
            objectStateSwitcher(heater, false);
            objectStateSwitcher(ac, false);
        }
    }

    /**
     * Get the temp for that period
     * @param room
     * @param period
     * @return
     */
    public int getPeriodTemp(Room room, int period){
        switch(period){
            case 1:
                return room.getPeriod1Temp();
            case 2:
                return room.getPeriod2Temp();
            case 3:
                return room.getPeriod3Temp();
        }
        return 0;
    }
}
