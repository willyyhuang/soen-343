package com.project.SmartHomeSimulator.model;


import java.util.ArrayList;

public class SimulationConfig {

    private ArrayList<SimulationProfile> simulationProfiles ;
    private SimulationProfile currentSimulationProfile;

    public SimulationConfig (ArrayList<SimulationProfile> simulationProfiles, SimulationProfile simulationProfile){

        this.simulationProfiles = simulationProfiles;
        this.currentSimulationProfile = simulationProfile;
    }

    public ArrayList<SimulationProfile> getSimulationProfiles() {
        return simulationProfiles;
    }

    public SimulationProfile getCurrentSimulationProfile() {
        return currentSimulationProfile;
    }
}
