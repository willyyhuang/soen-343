package com.project.SmartHomeSimulator.Module;


public class AwayModeConfig {
    private boolean awayMode;
    private String duration;

    public boolean isAwayMode() {
        return awayMode;
    }

    public void setAwayMode(boolean awayMode) {
        this.awayMode = awayMode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
