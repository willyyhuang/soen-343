package com.project.SmartHomeSimulator.model;

public class APIResponseLogin {

    public boolean success;
    public SimulationConfig simulationConfig;

    public APIResponseLogin(SimulationConfig simulationConfig){
        this.simulationConfig = simulationConfig;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
