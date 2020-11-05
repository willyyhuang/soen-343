package com.project.SmartHomeSimulator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.model.User;

@Service
public class SimulationContextService {
    @Autowired
    private SimulationContext simulationContext;

    @Autowired
    public SimulationContextService(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    /**
     * Start the simulation
     */
    public void startSimulation() {
        simulationContext.setSimulationRunning(true);
    }

    /**
     * Stop the simulation
     */
    public void stopSimulation() {
        simulationContext.setSimulationRunning(false);
    }

    /**
     * Get the simulation Context
     * @return the simulation context
     */
    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    /**
     * Set the inside temp
     * @param insideTemp
     * @return - true if successful false if otherwise
     */
    public boolean setInsideTemp(int insideTemp) {
        simulationContext.setInsideTemp(insideTemp);
        return true;
    }

    /**
     * Set Outside Temp
     * @param outsideTemp
     * @return  - true if successful false if otherwise
     */
    public boolean setOutsideTemp(int outsideTemp) {
        simulationContext.setOutsideTemp(outsideTemp);
        return true;
    }

    /**
     * Set time
     * @param time
     * @return - true if successful false if otherwise
     */
    public boolean setTime(String time) {
       simulationContext.setTime(time);
       return true;
    }

    /**
     * Set date
     * @param date
     * @return  - true if successful false if otherwise
     */
    public boolean setDate(String date) {
        simulationContext.setDate(date);
        return true;
    }

    /**
     * Set the current user in charge of the simulation
     * @param name
     * @return - true if successful false if otherwise
     */
    public boolean setCurrentSimulationUser(String name) {
        List<User> users = simulationContext.getSimulationUsers();
        for(User user: users) {
            if (user.getName().equals(name)) {
                simulationContext.setCurrentSimulationUser(user);
                return true;
            }
        }
        return false;
    }

    /**
     * loads the layout of the house with the JSON file as a stirng
     * @param homeLayoutFile
     * @return the home Layout
     */
    public HomeLayout loadLayout(String homeLayoutFile) {
        HomeLayout homeLayout = new HomeLayout();
        homeLayout = homeLayout.readHomeLayout(homeLayoutFile);
        simulationContext.setHomeLayout(homeLayout);
        return homeLayout;
    }

}
