package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.module.SmartHomeSecurityProxy;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SmartHomeSecurityService {
    private SmartHomeSecurityProxy smartHomeSecurityProxy = new SmartHomeSecurityProxy(SmartHomeSecurity.getInstance());
    private static SimulationContext simulationContext = SimulationContext.getInstance();
    private Role currentSimulationUserRole;

    /**
     * sets Away mode to either true or false based on the current simulation user role
     *
     * @param awayMode - boolean true to turn on the away mode and false to turn it off
     * @return boolean
     */
    public boolean setAwayMode(boolean awayMode) {
        if (simulationContext.getCurrentSimulationUser() != null) {
            this.currentSimulationUserRole = simulationContext.getCurrentSimulationUser().getRole();
            if (awayMode) {
                if (simulationContext.getHomeLayout() != null && simulationContext.getHomeLayout().isHomeEmpty()) {
                    if (smartHomeSecurityProxy.setAwayMode(currentSimulationUserRole, awayMode)) {
                        simulationContext.setAwayModeUser(simulationContext.getCurrentSimulationUser());
                        smartHomeSecurityProxy.closeWindows();
                        smartHomeSecurityProxy.closeDoors();
                        return true;
                    }
                }
            } else {
                simulationContext.setAwayModeUser(null);
                smartHomeSecurityProxy.setAwayMode(currentSimulationUserRole, awayMode);
                return true;
            }
        }
        return false;
    }

    /**
     * sets how much time should pass before alerting the authorities
     *
     * @param timeBeforeAuthorities - integer value of the time that needs to pass
     * @return boolean true if setting timeBeforeAuthority was successful
     */
    public boolean setTimeBeforeAuthorities(int timeBeforeAuthorities) {
        if (simulationContext.getCurrentSimulationUser() != null) {
            this.currentSimulationUserRole = simulationContext.getCurrentSimulationUser().getRole();
            return smartHomeSecurityProxy.setTimeBeforeAuthorities(currentSimulationUserRole, timeBeforeAuthorities);
        }
        return false;
    }

    /**
     * sets the lights that should stay on for a period of time
     * before turning them off after setting away mode on
     *
     * @param lights- HasMap of room names and their corresponding lights IDs
     * @param timeToKeepLightsOn- amount of time to wait before closing the lights
     * @return- boolean
     */
    public boolean setLightsToRemainOn(HashMap<String, String> lights, String timeToKeepLightsOn) {
        if (simulationContext.getCurrentSimulationUser() != null) {
            this.currentSimulationUserRole = simulationContext.getCurrentSimulationUser().getRole();
            return smartHomeSecurityProxy.setLightsToRemainOn(currentSimulationUserRole, lights, timeToKeepLightsOn);
        }
        return false;
    }

    /**
     * Turn on/off the lights that are specified in the attribute "lights"
     *
     * @return- boolean
     */
    public boolean turnOnOffLights(boolean status) {
        return smartHomeSecurityProxy.turnOnOffLights(status);
    }
}
