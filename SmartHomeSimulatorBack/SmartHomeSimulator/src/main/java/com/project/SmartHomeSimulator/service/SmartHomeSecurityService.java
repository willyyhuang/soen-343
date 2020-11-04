package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.Module.SmartHomeSecurity;
import com.project.SmartHomeSimulator.model.SimulationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartHomeSecurityService {
    private SmartHomeSecurity smartHomeSecurity = SmartHomeSecurity.getInstance();
    @Autowired
    private SimulationContext simulationContext;

    @Autowired
    public SmartHomeSecurityService(SimulationContext simulationContext) {
        this.simulationContext = simulationContext;
    }

    public boolean setAwayMode(boolean awayMode){
        if(simulationContext.getHomeLayout() != null && simulationContext.getHomeLayout().isHomeEmpty()){
            smartHomeSecurity.getAwayModeConfig().setAwayMode(awayMode);
            smartHomeSecurity.closWindows();
            smartHomeSecurity.lockDoors();
            return true;
        }
        return false;
    }
}
