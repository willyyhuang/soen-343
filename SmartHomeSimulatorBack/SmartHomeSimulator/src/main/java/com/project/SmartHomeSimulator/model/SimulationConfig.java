package com.project.SmartHomeSimulator.model;

import com.project.SmartHomeSimulator.dao.SimulationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class SimulationConfig {

    private String username;

    private boolean success = true;
    private ArrayList<SimulationUser> simulationUsers ;
    private SimulationUser currentSimulationUser;

    public SimulationConfig (ArrayList<SimulationUser> simulationUsers){

        this.simulationUsers = simulationUsers;
    }

    public SimulationUser getCurrentSimulationUser() {
        return currentSimulationUser;
    }

    public void setCurrentSimulationUser(SimulationUser currentSimulationUser) {
        this.currentSimulationUser = currentSimulationUser;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
