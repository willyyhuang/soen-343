package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.*;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SimulationContextService {
    @Autowired
    private SimulationContext simulationContext;

    @Autowired
    public SimulationContextService(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public void startSimulation() {
        simulationContext.setSimulationRunning(true);
    }

    public void stopSimulation() {
        simulationContext.setSimulationRunning(false);
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public boolean setInsideTemp(int insideTemp) {
        simulationContext.setInsideTemp(insideTemp);
        return true;
    }

    public boolean setOutsideTemp(int outsideTemp) {
        simulationContext.setOutsideTemp(outsideTemp);
        return true;
    }

    public boolean setTime(String time) {
       simulationContext.setTime(time);
       return true;
    }

    public boolean setDate(String date) {
        simulationContext.setDate(date);
        return true;
    }

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

    public boolean blockWindow(String roomName, String id, boolean block) {
        Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
        UUID objectID = UUID.fromString(id);
        RoomObject roomObject = room.getObjectByID(objectID);
         if (roomObject instanceof Window) {
                Window window = (Window) roomObject;
                window.setBlocked(block);
                return true;
            }
        return false;
    }

    public HomeLayout loadLayout(String homeLayoutFile) {
        HomeLayout homeLayout = new HomeLayout();
        homeLayout = homeLayout.readHomeLayout(homeLayoutFile);
        simulationContext.setHomeLayout(homeLayout);
        return homeLayout;
    }

}
