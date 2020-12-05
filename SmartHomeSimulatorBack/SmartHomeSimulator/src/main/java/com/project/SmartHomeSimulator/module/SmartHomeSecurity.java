package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.roomObjects.Door;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SmartHomeSecurity extends Module implements Monitor {

    private static SmartHomeSecurity instance;
    private SmartHomeCoreFunctionality smartHomeCoreFunctionality;
    public SimulationContext simulationContext;
    private AwayModeConfig awayModeConfig;
    private boolean alertModeOn;
    private List<String> lightIDs;
    private String timeToKeepLightsOn;
    private List<AwayModeMonitor> awayModeMonitors;

    //this class cannot be instantiated
    private SmartHomeSecurity() {
        this.setName("SmartHomeSecurity");
        this.awayModeConfig = new AwayModeConfig();
        this.lightIDs = new ArrayList<String>();
        this.awayModeMonitors = new ArrayList<>();
    }

    public static SmartHomeSecurity getInstance() {
        if (instance == null) {
            instance = new SmartHomeSecurity();
        }
        return instance;
    }

    @Override
    public void update(String awayModeUser, User user) {
        if (awayModeConfig.isAwayMode()) {
            if (!awayModeUser.equals(user.getName()) && !user.getHomeLocation().equals("outside")) {
                this.alertModeOn = true;
                logMessage("[Alert] " + user.getName() + " is detected in the house during away mode");
                return;
            }
        }
        this.alertModeOn = false;

    }

    public void notifyAwayModeMonitors(boolean awayMode) {
        for (AwayModeMonitor monitor : this.awayModeMonitors) {
            monitor.updateAwayModeMonitor(awayMode);
        }
    }

    public void addAwayModeMonitor(AwayModeMonitor awayModeMonitor){
        this.awayModeMonitors.add(awayModeMonitor);
    }

    public void removeAwayModeMonitor(AwayModeMonitor awayModeMonitor){
        this.awayModeMonitors.remove(awayModeMonitor);
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
                    success = smartHomeCoreFunctionality.objectStateSwitcher(room.getName(), roomObject.getId().toString(), false);
                    if (success) {
                        logSuccess("Window", room.getName(), "close", "SHP module");
                    } else {
                        logFail(roomObject.getName(),room.getName(),"closing","SHP module");
                    }
                }
            }
        }
    }

    public void lockDoors() {
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        simulationContext = SimulationContext.getInstance();
        List<Room> rooms = simulationContext.getHomeLayout().getRoomList();
        boolean success;
        List<RoomObject> roomObjects;
        for (Room room : rooms) {
            if (Stream.of("building entrance", "backyard", "garage").anyMatch(room.getName()::equalsIgnoreCase)) {
                roomObjects = room.getObjects();
                for (RoomObject roomObject : roomObjects) {
                    if (roomObject instanceof Door) {
                        success = smartHomeCoreFunctionality.objectStateSwitcher(room.getName(), roomObject.getId().toString(), false);
                        if (success) {
                            logSuccess("Door", room.getName(), "lock", "SHP module");
                        } else {
                            logMessage("[Failed] locking door in room " + room.getName() + ", requested by SHP, failed");
                        }
                    }
                }
            }
        }
    }

    public void setLightIDs(List<String> lightIDs) {
        this.lightIDs = lightIDs;
    }

    public void setTimeToKeepLightsOn(String timeToKeepLightsOn) {
        this.timeToKeepLightsOn = timeToKeepLightsOn;
    }

    public boolean turnOnOffLights(boolean status) {
        smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();
        HomeLayout homeLayout = SimulationContext.getInstance().getHomeLayout();
        String roomName;
        for(String lightID: this.lightIDs){
            roomName = homeLayout.getRoomNameByObjectID(lightID);
            if(roomName != null){
                if (smartHomeCoreFunctionality.objectStateSwitcher(roomName, lightID, status)) {
                    logSuccess("Light", roomName, status ? "turned on" : "turned off", "SHP module");
                } else {
                    logMessage("[Failed] Turning off lights in " + roomName + ", requested by SHP, failed.");
                }
            }
        }
        return true;
    }

    public String getTimeToKeepLightsOn() {
        return timeToKeepLightsOn;
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

    public void setAwayMode(boolean awayMode){
        this.awayModeConfig.setAwayMode(awayMode);
        notifyAwayModeMonitors(awayMode);
    }
}
