package com.project.SmartHomeSimulator.api;

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
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.blockUnblockWindow(roomName, objectID, true);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
        return response;
    }

    /**
     * API unblock a window
     * @param roomName
     * @param objectID
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/unblockWindow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI unblockWindow(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        ResponseAPI response = new ResponseAPI();
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.blockUnblockWindow(roomName, objectID, false);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
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
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.onOffLights(roomName, objectID, true);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
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
        if(smartHomeSecurity.getAwayModeConfig().isAwayMode()){
            response.success = false;
            response.awayMode = true;
            response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
            return response;
        }
        response.success = smartHomeCoreFunctionalityService.onOffLights(roomName, objectID, false);
        response.awayMode = false;
        response.timeBeforeAuthorities = smartHomeSecurity.getAwayModeConfig().getDuration();
        return response;
    }
}