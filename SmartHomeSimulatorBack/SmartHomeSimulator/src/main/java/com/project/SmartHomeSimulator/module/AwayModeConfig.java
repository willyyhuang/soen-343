package com.project.SmartHomeSimulator.module;


public class AwayModeConfig {
    private boolean awayMode;
    private int timeBeforeAuthorities;

    public boolean isAwayMode() {
        return awayMode;
    }

    public void setAwayMode(boolean awayMode) {
        this.awayMode = awayMode;
    }

    public int getTimeBeforeAuthorities() {
        return timeBeforeAuthorities;
    }

    public void setTimeBeforeAuthorities(int timeBeforeAuthorities) {
        this.timeBeforeAuthorities = timeBeforeAuthorities;
    }
}
