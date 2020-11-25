package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.module.*;
import com.project.SmartHomeSimulator.service.SmartHomeHeatingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API controller for everything related to the heating process
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SmartHomeHeatingController {
    @Autowired
    SmartHomeHeatingServices smartHomeHeatingServices;
    private SmartHomeSecurity smartHomeSecurity = SmartHomeSecurity.getInstance();
    public SimulationContext simulationContext = SimulationContext.getInstance();
    public SmartHomeHeating smartHomeHeating = SmartHomeHeating.getInstance();

    /**
     * Add a zone
     * @param morningTemp
     * @param eveningTemp
     * @param nightTemp
     * @param zone
     * @param roomNames
     * @return
     */
    @PostMapping(value = "/createZone")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI addZone(@RequestParam("morningTemp") int morningTemp, @RequestParam("eveningTemp") int eveningTemp, @RequestParam("nightTemp") int nightTemp, @RequestParam("zoneName") String zone, @RequestBody List<String> roomNames) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            smartHomeHeating.logMessage("[Alert] Someone is trying to change home settings.");
            response.consoleMessage = smartHomeHeating.getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeHeatingServices.addZone(morningTemp, eveningTemp, nightTemp, zone, roomNames);
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

}
