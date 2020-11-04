package com.project.SmartHomeSimulator.Module;

import com.project.SmartHomeSimulator.model.Role;
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
    public void update(User user) {
        if ( awayModeConfig.isAwayMode() && (user.getRole() == Role.STRANGER || user.getRole() == Role.STRANGER) ) {
            System.out.println("if user homelocation is inside the house then send notification");
        }
    }

    public AwayModeConfig getAwayModeConfig() {
        return awayModeConfig;
    }

    public void setAwayModeConfig(AwayModeConfig awayModeConfig) {
        this.awayModeConfig = awayModeConfig;
    }
}
