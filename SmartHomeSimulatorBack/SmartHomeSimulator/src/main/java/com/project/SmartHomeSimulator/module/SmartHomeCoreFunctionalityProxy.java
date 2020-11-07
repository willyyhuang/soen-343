package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.model.User;

public class SmartHomeCoreFunctionalityProxy {

    public static SmartHomeCoreFunctionalityProxy smartHomeCoreFunctionalityProxy = null;
    public SmartHomeCoreFunctionality smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();

    public static SmartHomeCoreFunctionalityProxy getInstance() {
        if (smartHomeCoreFunctionalityProxy == null) {
            smartHomeCoreFunctionalityProxy = new SmartHomeCoreFunctionalityProxy();
        }
        return smartHomeCoreFunctionalityProxy;
    }

    public boolean openCloseWindow(User user, String roomName, String windowID, boolean state) {
        boolean success = false;
        if (verifyPermission(user, "window", roomName)) {
            success = smartHomeCoreFunctionality.objectStateSwitcher(roomName, windowID, state);
            if (success) {
                smartHomeCoreFunctionality.logSuccess("Window", roomName, state ? "opened" : "closed", user.getName());
            } else {
                smartHomeCoreFunctionality.logFail("Window", roomName, (state ? "opening" : "closing"), user.getName());

                smartHomeCoreFunctionality.logMessage("[Failed] " + (state ? "opening" : "closing") + " window in " + roomName + ", requested by " + user.getName() + ", failed");
            }
        }
        return success;
    }

    public boolean blockUnblockWindow(User user, String roomName, String windowID, boolean state) {
        boolean success = false;
        if (verifyPermission(user, "window", roomName)) {
            success = smartHomeCoreFunctionality.blockUnblockWindow(roomName, windowID, state);
            if (success) {
                smartHomeCoreFunctionality.logSuccess("Window", roomName, state ? "blocked" : "unblocked", user.getName());
            } else {
                smartHomeCoreFunctionality.logFail("window", roomName, state ? "Blocking" : "Unblocking", user.getName());
            }
        }
        return success;
    }


    public boolean onOffLights(User user, String roomName, String lightID, boolean state) {
        boolean success = false;
        if (verifyPermission(user, "light", roomName)) {
            success = smartHomeCoreFunctionality.objectStateSwitcher(roomName, lightID, state);
            if (success) {
                smartHomeCoreFunctionality.logSuccess("Light", roomName, state ? "turned on" : "turned off", user.getName());
            } else {
                smartHomeCoreFunctionality.logFail("light", roomName, (state ? "turning on" : "turning off"), user.getName());
            }
        }
        return success;
    }

    public boolean openCloseDoors(User user, String roomName, String doorID, boolean state) {
        boolean success = false;
        if (verifyPermission(user, "door", roomName)) {
            success = smartHomeCoreFunctionality.objectStateSwitcher(roomName, doorID, state);
            if (success) {
                smartHomeCoreFunctionality.logSuccess("Door", roomName, state ? "opened" : "closed", user.getName());
            } else {
                smartHomeCoreFunctionality.logFail("Door", roomName, (state ? "opening" : "closing"), user.getName());
            }
        }
        return success;
    }

    public boolean verifyPermission(User user, String action, String roomName) {
        Role role = user.getRole();
        switch (role) {
            case PARENT:
                return true;
            case GUEST:
            case CHILD:
                if (user.getHomeLocation().equalsIgnoreCase(roomName) && (action.equals("door") || action.equals("window") || action.equals("light"))) {
                    return true;
                }
            default:
                return false;
        }
    }
}
