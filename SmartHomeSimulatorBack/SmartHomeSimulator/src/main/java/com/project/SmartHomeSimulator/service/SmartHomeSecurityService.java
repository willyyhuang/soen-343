package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.model.Role;
import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.module.SmartHomeSecurityProxy;
import org.springframework.stereotype.Service;

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


}
