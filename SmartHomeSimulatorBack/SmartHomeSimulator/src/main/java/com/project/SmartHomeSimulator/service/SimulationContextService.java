package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.*;
import com.project.SmartHomeSimulator.model.roomObjects.RoomObject;
import com.project.SmartHomeSimulator.model.roomObjects.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setInsideTemp(insideTemp);
            return true;
        }
        return false;
    }

    public boolean setOutsideTemp(int outsideTemp) {
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setOutsideTemp(outsideTemp);
            return true;
        }
        return false;
    }

    public boolean setTime(String time) {
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setTime(time);
            return true;
        }
        return false;
    }

    public boolean setDate(String date) {
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setDate(date);
            return true;
        }
        return false;
    }

    public void setCurrentSimulationUser(User user) {
        simulationContext.setCurrentSimulationUser(user);
    }

    public boolean blockWindow(String roomName, String id, boolean block) {
        if (simulationContext.isSimulationRunning() && simulationContext.getCurrentSimulationUser().getRole() == Role.STRANGER) {
            Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
            UUID objectID = UUID.fromString(id);
            RoomObject roomObject = room.getObjectByID(objectID);
            if (room != null && roomObject != null) {
                if (roomObject instanceof Window) {
                    Window window = (Window) roomObject;
                    window.setBlocked(block);
                    return true;
                }
            }
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
