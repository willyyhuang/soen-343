package com.project.SmartHomeSimulator.module;

import com.project.SmartHomeSimulator.model.Role;

public class SmartHomeSecurityProxy {
    private SmartHomeSecurity smartHomeSecurity;

    public SmartHomeSecurityProxy(SmartHomeSecurity smartHomeSecurity){
        this.smartHomeSecurity = smartHomeSecurity;
    }

    public boolean setAwayMode(Role role, boolean awayMode){
        if(verifyPermission(role)){
            smartHomeSecurity.getAwayModeConfig().setAwayMode(awayMode);
            return true;
        }
        return false;
    }

    public void closeWindows(){
        smartHomeSecurity.closWindows();
    }

    public void closeDoors(){
        smartHomeSecurity.lockDoors();
    }

    public boolean setTimeBeforeAuthorities(Role role,int timeBeforeAuthorities){
        if ( verifyPermission(role)){
            smartHomeSecurity.getAwayModeConfig().setTimeBeforeAuthorities(timeBeforeAuthorities);
            return true;
        }
        return false;
    }

    private boolean verifyPermission(Role role){
        switch (role){
            case PARENT:CHILD: return true;
            default: return false;
        }
    }
}
