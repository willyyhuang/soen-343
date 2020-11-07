package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.module.SmartHomeCoreFunctionalityProxy;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.module.SmartHomeCoreFunctionality;
import org.springframework.stereotype.Service;

@Service
public class SmartHomeCoreFunctionalityService {

    private static SimulationContext simulationContext = SimulationContext.getInstance();

    public SmartHomeCoreFunctionalityProxy smartHomeCoreFunctionalityProxy = SmartHomeCoreFunctionalityProxy.getInstance();
    public SmartHomeCoreFunctionality smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();


    /**
     * block a window or unblock it  - checks permission
     * @param roomName
     * @param id
     * @param state - blocked or unblocked - replace the state of the window
     * @return  - true if successful false if otherwise
     */
    public boolean blockUnblockWindow(String roomName, String id, boolean state) {
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeCoreFunctionalityProxy.blockUnblockWindow(user,roomName, id, state);
    }

    /**
     * open a window or close it  - checks permission
     * @param roomName
     * @param id
     * @param state - blocked or unblocked - replace the state of the window
     * @return  - true if successful false if otherwise
     */
    public boolean openCloseWindow(String roomName, String id, boolean state) {
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeCoreFunctionalityProxy.openCloseWindow(user, roomName, id, state);
    }

    /**
     * Turn lights on or off - checks permission
     * @param roomName
     * @param id
     * @param state - turn on or off - replace the state of the light
     * @return  - true if successful false if otherwise
     */
    public boolean onOffLights (String roomName, String id, boolean state) {
        User user = simulationContext.getCurrentSimulationUser();
        return smartHomeCoreFunctionalityProxy.onOffLights(user,roomName, id, state);
    }
    /**
     * open or close the doors - checks permission
     * @param roomName
     * @param id
     * @param state - open or close - replace the state dor
     * @return  - true if successful false if otherwise
     */
    public boolean openCloseDoors (String roomName, String id, boolean state) {
        User user = simulationContext.getCurrentSimulationUser();
        return permissionVerifier.verifyPermission(user,"door",roomName, id, state);
    }
}
