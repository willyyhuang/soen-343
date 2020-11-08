package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Role;

import java.util.List;

public class SmartHomeSecurityProxy {
    private SmartHomeSecurity smartHomeSecurity;

    public SmartHomeSecurityProxy(SmartHomeSecurity smartHomeSecurity) {
        this.smartHomeSecurity = smartHomeSecurity;
    }

    public boolean setAwayMode(Role role, boolean awayMode) {
        if (verifyPermission(role)) {
            smartHomeSecurity.getAwayModeConfig().setAwayMode(awayMode);
            return true;
        }
        return false;
    }

    public void closeWindows() {
        smartHomeSecurity.closWindows();
    }

    public void closeDoors() {
        smartHomeSecurity.lockDoors();
    }

    public boolean setTimeBeforeAuthorities(Role role, int timeBeforeAuthorities) {
        if (verifyPermission(role)) {
            smartHomeSecurity.getAwayModeConfig().setTimeBeforeAuthorities(timeBeforeAuthorities);
            return true;
        }
        return false;
    }

    public boolean setLightIDs(Role role, List<String> lightIDs) {
        if (verifyPermission(role)) {
            smartHomeSecurity.setLightIDs(lightIDs);
            return true;
        }
        return false;
    }

    public boolean setTimeToKeepLightsOn(Role role,String timeToKeepLightsOn) {
        if (verifyPermission(role)) {
            smartHomeSecurity.setTimeToKeepLightsOn(timeToKeepLightsOn);
            return true;
        }
        return false;
    }

    public boolean turnOnOffLights(boolean status) {
        return smartHomeSecurity.turnOnOffLights(status);
    }

    private boolean verifyPermission(Role role) {
        switch (role) {
            case PARENT:
                CHILD:
                return true;
            default:
                return false;
        }
    }
}
