package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.*;
import com.project.SmartHomeSimulator.model.Zones.CompleteZones;
import com.project.SmartHomeSimulator.model.Zones.Zone;
import com.project.SmartHomeSimulator.model.Zones.ZoneObject;
import com.project.SmartHomeSimulator.model.roomObjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Module about heating the house
 */
public class SmartHomeHeating extends Module implements AwayModeMonitor, Monitor {

    private static SmartHomeHeating smartHomeHeating = null;
    private boolean awayMode = false;
    private boolean isSummer = false;
    private List<User> simulationUsers = new ArrayList<>();

    private SmartHomeHeating() {
        setName("SmartHomeHeating");
    }

    public static SmartHomeHeating getInstance() {
        if (smartHomeHeating == null) {
            smartHomeHeating = new SmartHomeHeating();
        }
        return smartHomeHeating;
    }

    /**
     * Add a zone with assigned rooms and temperatures - initial temperature of a
     * room is outside temperature
     *
     * @param zone
     * @return
     */
    public boolean addZone(Zone zone) {
        ZoneObject zoneObject = new ZoneObject();
        for (String roomName : zone.getRoomsInZone()) {
            if (roomName.equalsIgnoreCase("backyard") || roomName.equalsIgnoreCase("outside")) {
                break;
            }
            double desiredTemp = SimulationContext.getInstance().getEmptyRoomTemp();
            Room room = SimulationContext.getInstance().getHomeLayout().getRoomByName(roomName);
            if (room.getUsersInRoom() > 0) {
                desiredTemp = zone.getDesiredTemp();
            }
            room.setZone(zone.getName());
            switchStates(room, SimulationContext.getInstance().getOutsideTemp(), desiredTemp);
            room.setDesiredTemp(zone.getDesiredTemp());
            room.setCurrentTemp(SimulationContext.getInstance().getOutsideTemp());
            room.setPeriod1(zone.getPeriod1());
            room.setPeriod1Temp(zone.getPeriod1Temp());
            room.setPeriod2(zone.getPeriod2());
            room.setPeriod2Temp(zone.getPeriod2Temp());
            room.setPeriod3(zone.getPeriod3());
            room.setPeriod3Temp(zone.getPeriod3Temp());
            room.setOverridden(false);
            HomeLayout.usersInHome--;
            zoneObject.getRooms().add(roomName);
            HomeLayout.roomsNotInZone--;
        }
        zoneObject.setZoneName(zone.getName());
        CompleteZones.zones.add(zoneObject);
        return true;
    }

    /**
     * Change room temperature and turn on or off ac or heater depending on the new
     * temperature
     *
     * @param roomName
     * @param newTemp
     * @return
     */
    public boolean changeRoomTemp(String roomName, double newTemp) {
        Room room = SimulationContext.getInstance().getHomeLayout().getRoomByName(roomName);
        switchStates(room, room.getCurrentTemp(), newTemp);
        room.setOverridden(true);
        return true;
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     *
     * @param rooms
     * @param period
     * @return
     */
    public boolean changeZoneTemp(ArrayList<Room> rooms, int period) {
        int newTemp = getPeriodTemp(rooms.get(0), period);
        for (Room room : rooms) {
            switchStates(room, room.getCurrentTemp(), newTemp);
            room.setOverridden(false);
        }
        return true;
    }

    /**
     * changes the sate of the room object passed to it
     *
     * @param roomObject
     * @param state      - block or turn on = true and unblock or turn off = false - replace the state of the object
     * @return - true if successful false if otherwise
     */
    public boolean objectStateSwitcher(RoomObject roomObject, boolean state) {
        if (roomObject instanceof Heater) {
            Heater heater = (Heater) roomObject;
            heater.setStatus(state);
            return true;
        } else if (roomObject instanceof AC) {
            AC ac = (AC) roomObject;
            ac.setStatus(state);
            return true;
        }
        return false;
    }

    /**
     * Switches AC and Heater state depending on the current temp and the desired
     * temp
     *
     * @param room
     * @param currentTemp
     * @param desiredTemp
     */
    public void switchStates(Room room, double currentTemp, double desiredTemp) {
        Heater heater = (Heater) room.getRoomObjectByType(RoomObjectType.HEATER);
        AC ac = (AC) room.getRoomObjectByType(RoomObjectType.AC);

        if (currentTemp < desiredTemp) {
            objectStateSwitcher(heater, true);
            objectStateSwitcher(ac, false);
        } else if (currentTemp > desiredTemp) {
            if (currentTemp <= desiredTemp) {
                objectStateSwitcher(heater, true);
                objectStateSwitcher(ac, false);
            } else {
                objectStateSwitcher(heater, false);
                objectStateSwitcher(ac, true);
            }
        } else {
            objectStateSwitcher(heater, false);
            objectStateSwitcher(ac, false);
        }
    }

    /**
     * Get the temp for that period
     *
     * @param room
     * @param period
     * @return
     */
    public int getPeriodTemp(Room room, int period) {
        switch (period) {
            case 1:
                return room.getPeriod1Temp();
            case 2:
                return room.getPeriod2Temp();
            case 3:
                return room.getPeriod3Temp();
        }
        return 0;
    }

    /**
     * open or close windows
     *
     * @param state
     */
    public boolean openCloseWindows(String windowId, boolean state) {
        HomeLayout homeLayout = SimulationContext.getInstance().getHomeLayout();
        String roomName = homeLayout.getRoomNameByObjectID(windowId);
        if (roomName != null) {
            if (SmartHomeCoreFunctionality.getInstance().objectStateSwitcher(roomName, windowId, state)) {
                logSuccess("Window", roomName, state ? "opened" : "closed", "SHH module");
                return true;
            } else {
                String windowName = homeLayout.getRoomByName(roomName).getRoomObjectByID(UUID.fromString(windowId))
                        .getName();
                logFail(windowName, roomName, state ? "opening" : "closing", "SHH module");
                return false;
            }
        }
        return false;
    }

    @Override
    public void updateAwayModeMonitor(boolean awayMode) {
        this.awayMode = awayMode;
        if (awayMode){
            setAwayModeTemp();
        }
    }

    /**
     * Adjust rooms temperature when on away mode if its winter or summer
     */
    private void setAwayModeTemp() {
        List<Room> rooms = SimulationContext.getInstance().getHomeLayout().getRoomList();
        double newTemp;
        if (isSummer) {
            newTemp = SimulationContext.getInstance().getSummerTemp();
        } else {
            newTemp = SimulationContext.getInstance().getWinterTemp();
        }
        for (Room room : rooms) {
            switchStates(room, room.getCurrentTemp(), newTemp);
            room.setOverridden(false);
        }
    }

    @Override
    public void update(String awayModeUser, User user) {
        if (user != null) {
            User oldUserObject = findUserByName(user.getName());
            if (oldUserObject != null){
                Room oldRoom = SimulationContext.getInstance().getHomeLayout().getRoomByName(oldUserObject.getHomeLocation());
                if (!oldRoom.getName().equals(user.getHomeLocation())){
                    if (oldRoom.roomEmpty()){
                        switchStates(oldRoom, oldRoom.getCurrentTemp(), SimulationContext.getInstance().getEmptyRoomTemp());
                    }
                    Room room = SimulationContext.getInstance().getHomeLayout().getRoomByName(user.getHomeLocation());
                    switchStates(room, room.getCurrentTemp(), room.getDesiredTemp());
                }
            }
        }
        cloneSimulationUsers(SimulationContext.getInstance().getSimulationUsers());
    }

    /**
     * finds user by name and returns it
     *
     * @param name
     * @return - true if successful false if otherwise
     */
    public User findUserByName(String name) {
        for (User user : simulationUsers) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * clones the users list and saves it in the simulationUsers field
     *
     * @param users
     */
    public void cloneSimulationUsers(List<User> users) {
        if (users != null) {
            for (User user : users) {
                simulationUsers.add(user.clone());
            }
        }
    }


    public boolean isAwayMode() {
        return awayMode;
    }

    public void setAwayMode(boolean awayMode) {
        this.awayMode = awayMode;
    }

    public boolean isSummer() {
        return isSummer;
    }

    public void setSummer(boolean summer) {
        isSummer = summer;
    }
}
