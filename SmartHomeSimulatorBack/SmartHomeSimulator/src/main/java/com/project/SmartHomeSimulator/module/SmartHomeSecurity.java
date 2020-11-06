package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.roomObjects.Door;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.Window;

import java.util.HashMap;
import java.util.List;

public class SmartHomeSecurity implements Monitor {

    private static SmartHomeSecurity instance;
    private static SmartHomeCoreFunctionality smartHomeCoreFunctionality;
    public static SimulationContext simulationContext = SimulationContext.getInstance();
    private AwayModeConfig awayModeConfig;
    private boolean alertModeOn;
    private HashMap<String,String> lights;
    private int timeToKeepLightsOn;

    //this class cannot be instantiated
    private SmartHomeSecurity() {
        this.awayModeConfig = new AwayModeConfig();
        this.lights = new HashMap<String, String>();
    }

    public static SmartHomeSecurity getInstance() {
        if (instance == null) {
            instance = new SmartHomeSecurity();
        }
        return instance;
    }

    @Override
    public void update(String awayModeUser,User user) {
        if ( awayModeConfig.isAwayMode() && !awayModeUser.equals(user.getName()) ) {
            if (!user.getHomeLocation().equals("outside")){
                //todo print in console
                this.alertModeOn = true;
            }
        }
    }

    public void closWindows() {
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        simulationContext = SimulationContext.getInstance();
        List<Room> rooms = simulationContext.getHomeLayout().getRoomList();
        List<RoomObject> roomObjects;
        for (Room room : rooms) {
            roomObjects = room.getObjects();
            for (RoomObject roomObject : roomObjects) {
                if (roomObject instanceof Window) {
                    smartHomeCoreFunctionality.objectStateSwitcher(room.getName(), roomObject.getId().toString(), true );
                }
            }
        }
        //todo log the action in console and corresponding file
    }

    public void lockDoors() {
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        simulationContext = SimulationContext.getInstance();
        List<Room> rooms = simulationContext.getHomeLayout().getRoomList();
        List<RoomObject> roomObjects;
        for (Room room : rooms) {
            roomObjects = room.getObjects();
            for (RoomObject roomObject : roomObjects) {
                if (roomObject instanceof Door) {
                    //todo wait for door lock/unlock implementation
                    smartHomeCoreFunctionality.objectStateSwitcher(room.getName(), roomObject.getId().toString(), true );
                }
            }
        }
        //todo log the action in console and corresponding file
    }

    public void setLightsToRemainOn(HashMap<String, String> lights, int timeToKeepLightsOn){
        this.lights = lights;
        this.timeToKeepLightsOn = timeToKeepLightsOn;
    }

    public void turnOffLights(){
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        this.lights.forEach((roomName, lightID) -> smartHomeCoreFunctionality.objectStateSwitcher(roomName,lightID, false));
    }

    public int getTimeToKeepLightsOn() {
        return timeToKeepLightsOn;
    }

    public void setTimeToKeepLightsOn(int timeToKeepLightsOn) {
        this.timeToKeepLightsOn = timeToKeepLightsOn;
    }

    public boolean isAlertModeOn() {
        return alertModeOn;
    }

    public void setAlertModeOn(boolean alertModeOn) {
        this.alertModeOn = alertModeOn;
    }

    public AwayModeConfig getAwayModeConfig() {
        return awayModeConfig;
    }

    public void setAwayModeConfig(AwayModeConfig awayModeConfig) {
        this.awayModeConfig = awayModeConfig;
    }

}
