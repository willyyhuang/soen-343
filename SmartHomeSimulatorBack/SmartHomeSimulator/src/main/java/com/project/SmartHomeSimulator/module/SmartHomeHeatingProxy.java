package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.model.Zone;

import java.util.ArrayList;


/**
 * Proxy for the heating class, verifies permissions
 */
public class SmartHomeHeatingProxy {
    public static SmartHomeHeatingProxy smartHomeHeatingProxy = null;
    public SmartHomeHeating smartHomeHeating = SmartHomeHeating.getInstance();

    public static SmartHomeHeatingProxy getInstance(){
        if (smartHomeHeatingProxy == null){
            smartHomeHeatingProxy = new SmartHomeHeatingProxy();
        }
        return smartHomeHeatingProxy;
    }

    /**
     * Add a zone, verify permissions first
     * @param user
     * @param zone
     * @return
     */
    public boolean addZone(User user, Zone zone){
        boolean success = false;
        if(verifyPermission(user,"zone", null)){
           success =  smartHomeHeating.addZone(zone);
        }
        if (success) {
            smartHomeHeating.logSuccess(zone.getName(),"Zone",  "created", user.getName());
        } else {
            smartHomeHeating.logFail(zone.getName(),"Zone",  "created", user.getName());

            smartHomeHeating.logMessage("[Failed] " + "Creating" + " zone " + zone.getName() + ", requested by " + user.getName() + ", failed");
        }

        return success;
    }

    /**
     * Override a room temperature
     * @param user
     * @param roomName
     * @param newTemp
     * @return
     */
    public boolean changeRoomTemp(User user, String roomName,int newTemp){
        boolean success = false;
        if(verifyPermission(user,"temp", null)){
            success = smartHomeHeating.changeRoomTemp(roomName, newTemp);
        }
        if (success) {
            smartHomeHeating.logSuccess(roomName,"Temperature in room",  "changed", user.getName());
        } else {
            smartHomeHeating.logFail(roomName,"Temperature in room",  "changed", user.getName());

            smartHomeHeating.logMessage("[Failed] " + "Change in temperature in " + "room" + roomName + ", requested by " + user.getName() + ", failed");
        }
        return success;
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     * @param rooms
     * @param period
     * @return
     */
    public boolean changeZoneTemp(String zoneName,ArrayList<Room> rooms, int period){
        boolean success = false;
        if (!rooms.isEmpty()){
            success = smartHomeHeating.changeZoneTemp(rooms, period);
        }
        if (success) {
            smartHomeHeating.logSuccess(zoneName,"Temperature in zone",  "changed", "SHH");
        } else {
            smartHomeHeating.logFail(zoneName,"Temperature in zone",  "changed", "SHH");

            smartHomeHeating.logMessage("[Failed] " + "Change in temperature in " + "room" + zoneName + ", requested by " + "SHH" + ", failed");
        }
        return success;
    }

    /**
     * verify the permissions of each user Type
     * @param user
     * @param action
     * @param roomName
     * @return
     */
    public boolean verifyPermission(User user, String action, String roomName) {
        Role role = user.getRole();
        switch (role) {
            case PARENT:
                return true;
            case GUEST:
                if(roomName == null){
                    return false;
                }
                if (user.getHomeLocation().equalsIgnoreCase(roomName) &&  action.equals("temp") ) {
                    return true;
                }
            case CHILD:
            default:
                return false;
        }
    }
}