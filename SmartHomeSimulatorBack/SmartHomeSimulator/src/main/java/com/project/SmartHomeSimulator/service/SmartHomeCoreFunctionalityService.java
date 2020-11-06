package com.project.SmartHomeSimulator.service;

import com.project.SmartHomeSimulator.module.PermissionVerifier;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.module.SmartHomeCoreFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartHomeCoreFunctionalityService {

    private static SimulationContext simulationContext = SimulationContext.getInstance();

    public PermissionVerifier permissionVerifier = PermissionVerifier.getInstance();
    public SmartHomeCoreFunctionality smartHomeCoreFunctionality = SmartHomeCoreFunctionality.getInstance();


    /**
     * block a window or unblock it  - checks permission
     * @param roomName
     * @param id
     * @param state - blocked or unblocked - replace the state of the window
     * @return  - true if successful false if otherwise
     */
    public boolean blockUnblockWindow(String roomName, String id, boolean state) {
        return smartHomeCoreFunctionality.blockUnblockWindow(roomName, id, state);
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
        return permissionVerifier.verifyPermission(user,"window",roomName, id, state);
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
        return permissionVerifier.verifyPermission(user,"light",roomName, id, state);
    }
}
