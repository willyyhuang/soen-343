package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.User;

import java.util.List;

public class SmartHomeHeatingProxy {
    public static SmartHomeHeatingProxy smartHomeHeatingProxy = null;
    public SmartHomeHeating smartHomeHeating = SmartHomeHeating.getInstance();

    public static SmartHomeHeatingProxy getInstance(){
        if (smartHomeHeatingProxy == null){
            smartHomeHeatingProxy = new SmartHomeHeatingProxy();
        }
        return smartHomeHeatingProxy;
    }

    public boolean addZone(User user, int morningTemp, int eveningTemp, int nightTemp, String zone, List<String> roomNames){
        boolean success = false;
        if(verifyPermission(user,"zone", null)){
           success =  smartHomeHeating.addZone(morningTemp, eveningTemp, nightTemp, zone, roomNames);
        }
        if (success) {
            smartHomeHeating.logSuccess("Zone", zone, "created", user.getName());
        } else {
            smartHomeHeating.logFail("Zone", zone, "created", user.getName());

            smartHomeHeating.logMessage("[Failed] " + "Creating" + " zone " + zone + ", requested by " + user.getName() + ", failed");
        }

        return success;
    }
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
