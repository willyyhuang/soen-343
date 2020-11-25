package com.project.SmartHomeSimulator.model;

import com.project.SmartHomeSimulator.module.SmartHomeSecurity;

public class ResponseAPI {
    public boolean awayMode;
    public int timeBeforeAuthorities;
    public String timeToKeepLightsOn;
    public boolean success;
    public String consoleMessage;
    public boolean alertModeOn;

    public void setTimeBeforeAuthorities(int timeBeforeAuthorities) {
        this.timeBeforeAuthorities = timeBeforeAuthorities;
    }

    public void setTimeToKeepLightsOn(String timeToKeepLightsOn) {
        this.timeToKeepLightsOn = timeToKeepLightsOn;
    }

    public void setAwayMode(boolean awayMode) {
        this.awayMode = awayMode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setDefaultValues(){
        awayMode = SmartHomeSecurity.getInstance().getAwayModeConfig().isAwayMode();
        timeToKeepLightsOn = SmartHomeSecurity.getInstance().getTimeToKeepLightsOn();
        timeBeforeAuthorities = SmartHomeSecurity.getInstance().getAwayModeConfig().getTimeBeforeAuthorities();

    }
}
