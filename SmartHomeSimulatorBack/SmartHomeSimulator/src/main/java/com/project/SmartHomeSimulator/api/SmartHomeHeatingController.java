package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.*;
import com.project.SmartHomeSimulator.model.Zones.CompleteZones;
import com.project.SmartHomeSimulator.model.Zones.Zone;
import com.project.SmartHomeSimulator.model.Zones.ZoneObject;
import com.project.SmartHomeSimulator.module.*;
import com.project.SmartHomeSimulator.service.SmartHomeHeatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * API controller for everything related to the heating process
 */
@RestController
@CrossOrigin(origins = { "*" })
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
        if (smartHomeSecurity.getAwayModeConfig().isAwayMode()
                && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()) {
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
    public ResponseAPI changeRoomTemp(@RequestParam("roomName") String roomName, @RequestParam("newTemp") double newTemp) {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
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
        if (smartHomeSecurity.getAwayModeConfig().isAwayMode()
                && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()) {
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
     * Turn on AC 
     * @param roomName
     * @return
     */
    @PostMapping(value="/turnOnAc")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean turnOnAc(@RequestParam("roomName") String roomName){
        return smartHomeHeatingService.changeACStatus(roomName, true) ;
    }

    /**
     * Turn off AC
     * @param roomName
     * 
     * @return
     */
    @PostMapping(value="/turnOffAc")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean turnOffAc(@RequestParam("roomName") String roomName){
        return smartHomeHeatingService.changeACStatus(roomName, false) ;
    }

    /**
     * Turn on heater
     * @param roomName
     * 
     * @return
     */
    @PostMapping(value="/turnOnHeater")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean turnOnHeater(@RequestParam("roomName") String roomName){
        return smartHomeHeatingService.changeHeaterStatus(roomName, true) ;
    }

    /**
     * Turn off heater
     * @param roomName
     * @return
     */
    @PostMapping(value="/turnOffHeater")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean turnOffHeater(@RequestParam("roomName") String roomName) {
        return smartHomeHeatingService.changeHeaterStatus(roomName, false);
    }
    /**
     * Set Summer temperature
     *
     * @param temperature
     * @return
     */
    @PostMapping(value = "/setSummerTemperature")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setSummerTemperature(@RequestParam("temperature") double temperature) {
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
    public boolean setWinterTemperature(@RequestParam("temperature") double temperature) {
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
    public boolean setTempThreshold(@RequestParam("tempThreshold") double tempThreshold) {
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
    public ResponseAPI alertTempThreshold() {
        ResponseAPI response = new ResponseAPI();
        response.setDefaultValues();
        response.success = smartHomeHeating.logMessage("There is something unusual with the temperature of your home.");
        response.consoleMessage = smartHomeHeating.getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * update the season
     *
     * @return
     */
    @PostMapping(value = "/setSeason")
    @ResponseStatus(value = HttpStatus.OK)
    public void setSeason(@RequestParam("isSummer") boolean isSummer) {
        smartHomeHeatingService.setSeason(isSummer);
    }

    /**
     * Gets all the zones with the rooms inside
     * @return
     */
    @GetMapping(value = "/getAllZones")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ZoneObject> getAllZones(){
        return CompleteZones.zones;
    }

    /**
     * Set the currentTemp of a room
     * @param roomName
     * @param currentTemp
     */
    @PostMapping(value = "/setCurrentTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setCurrentTemp(@RequestParam("roomName") String roomName, @RequestParam("currentTemp") double currentTemp) {
        return smartHomeHeatingService.setCurrentTemp(roomName,currentTemp);
    }

    /**
     * set empty room temperature
     * @param emptyRoomTemp
     * @return
     */
    @PostMapping(value = "/setEmptyRoomTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setEmptyRoomTemp(@RequestParam("emptyRoomTemp") double emptyRoomTemp) {
        return smartHomeHeatingService.setEmptyRoomTemp(emptyRoomTemp);
    }
}
