package com.project.SmartHomeSimulator.service;

import java.util.List;

import com.project.SmartHomeSimulator.model.ResponseParameters;
import org.springframework.stereotype.Service;

import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.model.User;

@Service
public class SimulationContextService {

    private SimulationContext simulationContext = SimulationContext.getInstance();

    /**
     * Start the simulation
     */
    public ResponseParameters startSimulation() {
        ResponseParameters response = verifyParameters();
        if(response.allowed) {
            simulationContext.setSimulationRunning(true);
        }
        return response;
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
     * Set Outside Temp
     * @param outsideTemp
     * @return  - true if successful false if otherwise
     */
    public boolean setOutsideTemp(double outsideTemp) {
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

    /**
     * Set autoMode
     * @param autoMode
     * @return - true if successful false if otherwise
     */
    public boolean setAutoMode(boolean autoMode) {
        simulationContext.setAutoMode(autoMode);
        return true;
    }

    public ResponseParameters verifyParameters(){
        ResponseParameters response = new ResponseParameters();
        response.setAllowed(true);
        response.setConsoleMessage("[Success] The simulation started!");
        if (HomeLayout.roomsNotInZone != 0){
            response.setAllowed(false);
            response.setConsoleMessage("[Failed] All rooms should be in zones before starting the simulation.");
        }
        if (simulationContext.getTime() == null || simulationContext.getDate() == null)
        {
            response.setAllowed(false);
            response.setConsoleMessage("[Failed] Date and Time should be set in simulation parameters before starting the simulation.");
        }
        if(simulationContext.getCurrentSimulationUser() == null){
            response.setAllowed(false);
            response.setConsoleMessage("[Failed] A current profile should be chosen before starting the simulation.");
        }
        if(simulationContext.getHomeLayout() == null){
            response.setAllowed(false);
            response.setConsoleMessage("[Failed] A home layout should be uploaded before starting the simulation.");
        }

        return response;
    }

}
