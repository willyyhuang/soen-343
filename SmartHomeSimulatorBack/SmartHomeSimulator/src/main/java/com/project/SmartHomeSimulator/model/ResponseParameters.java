package com.project.SmartHomeSimulator.model;

public class ResponseParameters {
    public boolean allowed;
    public String consoleMessage;

    public boolean isAllowed() {
        return allowed;
    }

    public String getConsoleMessage() {
        return consoleMessage;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public void setConsoleMessage(String consoleMessage) {
        this.consoleMessage = consoleMessage;
    }
}
