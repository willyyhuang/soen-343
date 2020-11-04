package com.project.SmartHomeSimulator.Module;

import com.project.SmartHomeSimulator.model.User;

public class SmartHomeSecurity implements Monitor {

    private static SmartHomeSecurity instance = new SmartHomeSecurity();
    private AwayModeConfig awayModeConfig = new AwayModeConfig();

    //this class cannot be instantiated
    private SmartHomeSecurity() {
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
                //to do
                System.out.println("send notification to awayModeUser and print in console");
            }
        }
    }

    public AwayModeConfig getAwayModeConfig() {
        return awayModeConfig;
    }

    public void setAwayModeConfig(AwayModeConfig awayModeConfig) {
        this.awayModeConfig = awayModeConfig;
    }

    public void closWindows() {
        //to do
        // should send a command to SHC and log the action in console and corresponding file
    }

    public void lockDoors() {
        //to do
        // should send a command to SHC and log the action in console and corresponding file
    }
}
