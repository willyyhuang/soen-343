package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.module.SimulationContext;
import org.springframework.stereotype.Service;

@Service
public class SmartHomeHeatingServices {
    private static SimulationContext simulationContext = SimulationContext.getInstance();

    /**
     * sets how much time should pass before alerting the authorities
     *
     * @param timeBeforeAuthorities - integer value of the time that needs to pass
     * @return boolean true if setting timeBeforeAuthority was successful
     */
    public boolean setTimeBeforeAuthorities(int timeBeforeAuthorities) {

        return false;
    }
}
