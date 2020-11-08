package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.module.SmartHomeCoreFunctionality;
import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.service.SmartHomeCoreFunctionalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SmartHomeCoreFunctionalityController {

    @Autowired
    SmartHomeCoreFunctionalityService smartHomeCoreFunctionalityService;
    public SmartHomeSecurity smartHomeSecurity = SmartHomeSecurity.getInstance();
    public SimulationContext simulationContext = SimulationContext.getInstance();

    /**
     * API block a window
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/blockWindow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI blockWindow(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.blockUnblockWindow(roomName, objectID, true);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }
    /**
     * API block a window
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/unblockWindow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI unblockWindow(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.blockUnblockWindow(roomName, objectID, false);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * API open a window
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/openWindow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI openWindow(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.openCloseWindow(roomName, objectID, true);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * API close a window
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/closeWindow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI closeWindow(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.openCloseWindow(roomName, objectID, false);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }
    /**
     * API turn on a light
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/onLight")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI turnOnLight(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.onOffLights(roomName, objectID, true);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }

    /**
     * API turn off a light
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/offLight")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI turnOffLight(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.onOffLights(roomName, objectID, false);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }
    /**
     * API  open a door
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/openDoor")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI openDoor(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.openCloseDoors(roomName, objectID, true);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }


    /**
     * API  open a door
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/closeDoor")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI closeDoor(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode() && simulationContext.getCurrentSimulationUser() != simulationContext.getAwayModeUser()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
            response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
            SmartHomeCoreFunctionality.getInstance().logMessage("[Alert] Someone is trying to break in.");
            response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
            response.alertModeOn = true;
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.openCloseDoors(roomName, objectID, false);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getTimeBeforeAuthorities();
        response.timeToKeepLightsOn = smartHomeSecurity.getTimeToKeepLightsOn();
        response.consoleMessage = SmartHomeCoreFunctionality.getInstance().getConsoleMessage();
        response.alertModeOn = false;
        return response;
    }
}
