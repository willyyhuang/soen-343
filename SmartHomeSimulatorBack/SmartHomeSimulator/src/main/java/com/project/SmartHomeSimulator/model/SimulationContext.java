package com.project.SmartHomeSimulator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class SimulationContext {
    private int insideTemp;
    private int outsideTemp;
    private String time;
    private String date;
    private User currentSimulationUser;
    private List<User> simulationUsers;
    private HomeLayout homeLayout;
    private boolean simulationRunning;

    public void clone(SimulationContext simulationContext)
    {
        this.insideTemp = simulationContext.insideTemp;
        this.outsideTemp = simulationContext.outsideTemp;
        this.time = simulationContext.time;
        this.date = simulationContext.date;
        this.currentSimulationUser = simulationContext.currentSimulationUser;
        this.simulationUsers = simulationContext.simulationUsers;
        this.homeLayout = simulationContext.homeLayout;
        this.simulationRunning = simulationContext.simulationRunning;
    }
}
