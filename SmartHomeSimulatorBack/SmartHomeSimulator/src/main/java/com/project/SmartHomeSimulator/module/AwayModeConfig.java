package com.project.SmartHomeSimulator.module;


public class AwayModeConfig {
    private boolean awayMode;
    private int duration;

    public boolean isAwayMode() {
        return awayMode;
    }

    public void setAwayMode(boolean awayMode) {
        this.awayMode = awayMode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
