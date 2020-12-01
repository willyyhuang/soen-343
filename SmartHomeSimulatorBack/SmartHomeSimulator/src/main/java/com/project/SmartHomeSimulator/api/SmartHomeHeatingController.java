package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.model.Zone;
import com.project.SmartHomeSimulator.module.*;
import com.project.SmartHomeSimulator.service.SmartHomeHeatingService;
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
    SmartHomeHeatingService smartHomeHeatingService;
    private SmartHomeSecurity smartHomeSecurity = SmartHomeSecurity.getInstance();
    public SimulationContext simulationContext = SimulationContext.getInstance();
    public SmartHomeHeating smartHomeHeating = SmartHomeHeating.getInstance();

    /**
     * Add zone api
     *
     * @param zone
     * @return
     */
    @PostMapping(value = "/createZone")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI addZone(@RequestBody @Valid Zone zone) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        if (smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()) {
            response.success = false;
            smartHomeHeating.logMessage("[Alert] Someone is trying to change home settings while you are away!");
            response.consoleMessage = smartHomeHeating.getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeHeatingService.addZone(zone);
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * change room temperature - override
     *
     * @param roomName
     * @param newTemp
     * @return
     */
    @PostMapping(value = "/changeRoomTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI changeRoomTemp(@RequestParam("roomName") String roomName, @RequestParam("newTemp") int newTemp) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        if (smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()) {
            response.success = false;
            smartHomeHeating.logMessage("[Alert] Someone is trying to change home settings while you are away!");
            response.consoleMessage = smartHomeHeating.getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeHeatingService.changeRoomTemp(roomName, newTemp);
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * Change temperature of a zone when reaching a period in a day
     *
     * @param zoneName
     * @param period   - either morning evening or night
     * @return
     */
    @PostMapping(value = "/changeZoneTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI changeZoneTemp(@RequestParam("zoneName") String zoneName, @RequestParam("period") int period) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        if (smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()) {
            response.success = false;
            smartHomeHeating.logMessage("[Alert] Someone is trying to change home settings while you are away!");
            response.consoleMessage = smartHomeHeating.getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeHeatingService.changeZoneTemp(zoneName, period);
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * Set winter months
     *
     * @param months
     * @return
     */
    @PostMapping(value = "/setWinterMonths")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setWinterMonths(@RequestParam("months") String months) {
        simulationContext.setWinterMonths(months);
        return true;
    }

    /**
     * Set Summer months
     *
     * @param months
     * @return
     */
    @PostMapping(value = "/setSummerMonths")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setSummerMonths(@RequestParam("months") String months) {
        simulationContext.setSummerMonths(months);
        return true;
    }

    /**
     * Set Summer temperature
     *
     * @param temperature
     * @return
     */
    @PostMapping(value = "/setSummerTemperature")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setSummerTemperature(@RequestParam("temperature") int temperature) {
        simulationContext.setSummerTemp(temperature);
        return true;
    }

    /**
     * Set Winter temperature
     *
     * @param temperature
     * @return
     */
    @PostMapping(value = "/setWinterTemperature")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setWinterTemperature(@RequestParam("temperature") int temperature) {
        simulationContext.setWinterTemp(temperature);
        return true;
    }

    /**
     * Open or close window
     *
     * @param windowId
     * @param state
     * @return
     */
    @PostMapping(value = "/openCloseWindow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI openCloseWindow(@RequestParam("windowId") String windowId, @RequestParam("state") boolean state) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        response.success = smartHomeHeatingService.openCloseWindow(windowId, state);
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage() + smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * Set temperature threshold
     *
     * @param tempThreshold
     * @return
     */
    @PostMapping(value = "/setTempThreshold")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setTempThreshold(@RequestParam("tempThreshold") int tempThreshold) {
        simulationContext.setTempThreshold(tempThreshold);
        return true;
    }

    /**
     * Alert temperature threshold mode
     *
     * @return
     */
    @PostMapping(value = "/alertTempThreshold")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean alertTempThreshold() {
        smartHomeHeating.logMessage("There is something unusual with the temperature of your home.");
        return true;
    }
}
