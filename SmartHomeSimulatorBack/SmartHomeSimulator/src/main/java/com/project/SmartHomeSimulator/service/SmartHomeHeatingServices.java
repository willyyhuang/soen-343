package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.module.SmartHomeCoreFunctionality;
import com.project.SmartHomeSimulator.module.SmartHomeHeatingProxy;
import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class SmartHomeHeatingServices {
    private static SimulationContext simulationContext = SimulationContext.getInstance();
    private SmartHomeHeatingProxy smartHomeHeatingProxy = SmartHomeHeatingProxy.getInstance();

    /**
     * Add a zone
     * @param morningTemp
     * @param eveningTemp
     * @param nightTemp
     * @param zone
     * @param roomNames
     * @return
     */
    public boolean addZone(int currentTemp, int morningTemp, int eveningTemp, int nightTemp, String zone, List<String> roomNames) {
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeHeatingProxy.addZone(user, currentTemp,morningTemp, eveningTemp, nightTemp, zone, roomNames);
    }

    /**
     * change temperature of a room, override
     * @param roomName
     * @param newTemp
     * @return
     */
    public boolean changeTemperature(String roomName, int newTemp){
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeHeatingProxy.changeTemperature(user, roomName, newTemp);
    }
}
