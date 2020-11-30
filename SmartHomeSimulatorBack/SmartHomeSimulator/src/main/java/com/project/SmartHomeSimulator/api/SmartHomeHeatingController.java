package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.model.Zone;
import com.project.SmartHomeSimulator.module.*;
import com.project.SmartHomeSimulator.service.SmartHomeHeatingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * Add zone api
     * @param zone
     * @return
     */
    @PostMapping(value = "/createZone")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI addZone(@RequestBody @Valid Zone zone) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            smartHomeHeating.logMessage("[Alert] Someone is trying to change home settings while you are away!");
            response.consoleMessage = smartHomeHeating.getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeHeatingServices.addZone(zone);
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * change room temperature - override
     * @param roomName
     * @param newTemp
     * @return
     */
    @PostMapping(value = "/changeRoomTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI changeRoomTemp(@RequestParam("roomName") String roomName, @RequestParam("newTemp") int newTemp) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            smartHomeHeating.logMessage("[Alert] Someone is trying to change home settings while you are away!");
            response.consoleMessage = smartHomeHeating.getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeHeatingServices.changeRoomTemp(roomName, newTemp);
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     * @param zoneName
     * @param period - either morning evening or night
     * @return
     */
    @PostMapping(value = "/changeZoneTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI changeZoneTemp(@RequestParam("zoneName") String zoneName, @RequestParam("period") int period) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            smartHomeHeating.logMessage("[Alert] Someone is trying to change home settings while you are away!");
            response.consoleMessage = smartHomeHeating.getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeHeatingServices.changeZoneTemp(zoneName, period);
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * Set winter months
     * @param months
     * @return
     */
    @PostMapping(value = "/setWinterMonths")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setWinterMonths(@RequestParam("months") String months){
        simulationContext.setWinterMonths(months);
        return true;
    }

    /**
     * Set Summer months
     * @param months
     * @return
     */
    @PostMapping(value = "/setSummerMonths")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setSummerMonths(@RequestParam("months") String months){
        simulationContext.setSummerMonths(months);
        return true;
    }
}
