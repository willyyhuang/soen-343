package com.project.SmartHomeSimulator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartHomeSimulator.model.roomObjects.Object;
import com.project.SmartHomeSimulator.model.roomObjects.Window;
import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.Room;
import com.project.SmartHomeSimulator.model.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
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

    public boolean startSimulation(SimulationContext simulationContext) {
        if (simulationContext != null) {
            this.simulationContext.clone(simulationContext);
            this.simulationContext.setSimulationRunning(true);
            return true;
        }
        return false;
    }

    public void stopSimulation() {
        simulationContext.setSimulationRunning(false);
    }

    public SimulationContext getSimulationContext() {
        return simulationContext;
    }

    public boolean editInsideTemp(int insideTemp) {
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setInsideTemp(insideTemp);
            return true;
        }
        return false;
    }

    public boolean editOutsideTemp(int outsideTemp) {
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setOutsideTemp(outsideTemp);
            return true;
        }
        return false;
    }

    public boolean editTime(String time) {
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setTime(time);
            return true;
        }
        return false;
    }

    public boolean editDate(String date) {
        if (simulationContext.isSimulationRunning()) {
            simulationContext.setDate(date);
            return true;
        }
        return false;
    }

    public void setCurrentSimulationUser(User user) {
        simulationContext.setCurrentSimulationUser(user);
    }

    public boolean blockWindow(String roomName, String id, boolean block ) {
        if (simulationContext.isSimulationRunning()) {
            Room room = simulationContext.getHomeLayout().getRoomByName(roomName);
            UUID objectID = UUID.fromString(id);
            Object object = room.getObjectByID(objectID);
            if (room != null && object != null) {
                if (object instanceof Window) {
                    Window window = (Window) object;
                    window.setBlocked(block);
                    return true;
                }
            }
        }
        return false;
    }


    public HomeLayout loadLayout(String homeLayoutFile) {
        HomeLayout homeLayout = simulationContext.getHomeLayout();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            homeLayout = objectMapper.readValue(homeLayoutFile, HomeLayout.class);
            simulationContext.setHomeLayout(homeLayout);
            return homeLayout;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
