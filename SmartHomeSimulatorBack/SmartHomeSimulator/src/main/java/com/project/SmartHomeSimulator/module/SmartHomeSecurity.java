package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.roomObjects.Door;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.Window;

import java.util.HashMap;
import java.util.List;

public class SmartHomeSecurity extends Module implements Monitor {

    private static SmartHomeSecurity instance;
    private static SmartHomeCoreFunctionality smartHomeCoreFunctionality;
    public static SimulationContext simulationContext = SimulationContext.getInstance();
    private AwayModeConfig awayModeConfig;
    private boolean alertModeOn;
    private HashMap<String, String> lights;
    private int timeToKeepLightsOn;

    //this class cannot be instantiated
    private SmartHomeSecurity() {
        this.setName("SmartHomeSecurity");
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
    public void update(String awayModeUser, User user) {
        if (awayModeConfig.isAwayMode() && !awayModeUser.equals(user.getName())) {
            if (!user.getHomeLocation().equals("outside")) {
                this.alertModeOn = true;
                logMessage("[Alert] " + user.getName() + " is detected in the house during away mode");
            }
        }
    }

    public void closWindows() {
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        simulationContext = SimulationContext.getInstance();
        boolean success;
        List<Room> rooms = simulationContext.getHomeLayout().getRoomList();
        List<RoomObject> roomObjects;
        for (Room room : rooms) {
            roomObjects = room.getObjects();
            for (RoomObject roomObject : roomObjects) {
                if (roomObject instanceof Window) {
                    success = smartHomeCoreFunctionality.objectStateSwitcher(room.getName(), roomObject.getId().toString(), true);
                    if (success) {
                        logSuccess("Window", room.getName(), "close", "SHP module");
                    } else {
                        logMessage("[Failed] closing window in room " + room.getName() + ", requested by SHP, failed");
                    }
                }
            }
        }
    }

    //todo lock doors in backyard and front door
    public void lockDoors() {
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        simulationContext = SimulationContext.getInstance();
        boolean success;
        List<Room> rooms = simulationContext.getHomeLayout().getRoomList();
        List<RoomObject> roomObjects;
        for (Room room : rooms) {
            roomObjects = room.getObjects();
            for (RoomObject roomObject : roomObjects) {
                if (roomObject instanceof Door) {
                    //todo wait for door lock/unlock implementation
                    success = smartHomeCoreFunctionality.objectStateSwitcher(room.getName(), roomObject.getId().toString(), true);
                    if (success) {
                        logSuccess("Door", room.getName(), "lock", "SHP module");
                    } else {
                        logMessage("[Failed] locking door in room " + room.getName() + ", requested by SHP, failed");
                    }

                }
            }
        }
    }

    public void setLightsToRemainOn(HashMap<String, String> lights, int timeToKeepLightsOn) {
        this.lights = lights;
        this.timeToKeepLightsOn = timeToKeepLightsOn;
    }

    public boolean turnOffLights() {
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        this.lights.forEach((roomName, lightID) -> {
                    if (smartHomeCoreFunctionality.objectStateSwitcher(roomName, lightID, false)) {
                        logSuccess("Light", roomName, "turn off", "SHP module");
                    } else {
                        logMessage("[Failed] Turning off lights in " + roomName + ", requested by SHP, failed.");
                    }
                }
        );
        return true;
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
