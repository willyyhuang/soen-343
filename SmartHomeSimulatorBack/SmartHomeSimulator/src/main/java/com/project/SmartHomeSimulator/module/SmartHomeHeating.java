package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.roomObjects.*;

import java.util.ArrayList;
import java.util.List;


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
     * @param morningTemp
     * @param eveningTemp
     * @param nightTemp
     * @param zone
     * @param roomNames
     * @return
     */
    public boolean addZone(int currentTemp, int morningTemp, int eveningTemp, int nightTemp, String zone, List<String> roomNames){
        String time = getTime();
        int switchTemp = 0;
        if(time.equals("morning") && morningTemp != 0 ){
            switchTemp = morningTemp;
        }
        else if(time.equals("evening") && eveningTemp != 0 ){
            switchTemp = eveningTemp;
        }
        else if (time.equals("night") && nightTemp != 0){
            switchTemp = nightTemp;
        }
        for (String roomName : roomNames){
            Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
            room.setZone(zone);
            room.setCurrentTemp(currentTemp);
            room.setMorningTemp(morningTemp);
            room.setEveningTemp(eveningTemp);
            room.setNightTemp(nightTemp);
            room.setOverridden(false);
            if (switchTemp != 0){
                switchStates(room,currentTemp,switchTemp);
            }
        }
        return true;
    }

    /**
     * Change room temperature and turn on or off ac or heater depending on the new temperature
     * @param roomName
     * @param newTemp
     * @return
     */
    public boolean changeTemperature(String roomName, int newTemp, int currentTemp){
        Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
        switchStates(room,currentTemp,newTemp);
        room.setCurrentTemp(newTemp);
        room.setOverridden(true);
        return true;
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     * @param rooms
     * @param currentTemp
     * @param newTemp
     * @return
     */
    public boolean changeZoneTemp(ArrayList<Room> rooms, int currentTemp, int newTemp){
        for (Room room : rooms){
            switchStates(room,currentTemp,newTemp);
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
     * Get the current time of the simulation to adapt the AC and heater states
     * @return either morning, evening or night
     */
    public String getTime(){
        String[] timeSplit = simulationContext.getTime().split(":");
        String time = timeSplit[0].substring(1);
        if(time.charAt(0) == '0'){
            time = time.substring(1);
        }
        int timeInt = Integer.parseInt(time);
        if (0 <= timeInt || timeInt < 12){
            return "morning";
        }
        else if (12 <= timeInt || timeInt < 18){
            return " evening";
        }
        else{
            return "night";
        }
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
        if (currentTemp <= desiredTemp){
            objectStateSwitcher(heater, true);
            objectStateSwitcher(ac, false);
        }
        else{
            objectStateSwitcher(heater, false);
            objectStateSwitcher(ac, true);
        }
    }
}
