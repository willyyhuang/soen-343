package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import com.project.SmartHomeSimulator.module.SimulationContext;
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
        if(awayMode) {
            if (simulationContext.getHomeLayout() != null && simulationContext.getHomeLayout().isHomeEmpty()) {
                smartHomeSecurity.getAwayModeConfig().setAwayMode(awayMode);
                smartHomeSecurity.closWindows();
                smartHomeSecurity.lockDoors();
                simulationContext.setAwayModeUser(simulationContext.getCurrentSimulationUser());
                return true;
            }
        }
        else {
            simulationContext.setAwayModeUser(null);
            smartHomeSecurity.getAwayModeConfig().setAwayMode(awayMode);
        }
        return false;
    }

    public void setTimeBeforeAuthorities(int timeBeforeAuthorities){
        smartHomeSecurity.getAwayModeConfig().setTimeBeforeAuthorities(timeBeforeAuthorities);
    }
}
