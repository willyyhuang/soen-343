package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import com.project.SmartHomeSimulator.service.SmartHomeSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationHomeSecurityController {

    @Autowired
    private SmartHomeSecurityService smartHomeSecurityService;
    private ResponseAPI responseAPI = new ResponseAPI();

    @PutMapping(value = "/awayMode")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI startAwayMode(@RequestParam("awayMode") Boolean awayMode) {
        responseAPI.awayMode = SmartHomeSecurity.getInstance().getAwayModeConfig().isAwayMode();
        responseAPI.success = smartHomeSecurityService.setAwayMode(awayMode);
        responseAPI.timeBeforeAuthorities = SmartHomeSecurity.getInstance().getAwayModeConfig().getTimeBeforeAuthorities();
        responseAPI.timeToKeepLightsOn = SmartHomeSecurity.getInstance().getTimeToKeepLightsOn();
        responseAPI.consoleMessage = SmartHomeSecurity.getInstance().getConsoleMessage();
        return responseAPI;
    }

    @PutMapping(value = "/awayMode/timeBeforeAuthorities")
    @ResponseStatus(value = HttpStatus.OK)
    public void setTimeBeforeAuthorities(@RequestParam("timeBeforeAuthorities") int timeBeforeAuthorities) {
        smartHomeSecurityService.setTimeBeforeAuthorities(timeBeforeAuthorities);
    }

    @PutMapping(value = "/awayMode/lights")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setLightsToRemainOn(@RequestBody HashMap<String, String> lights, @RequestParam("timeToKeepLightsOn") String timeToKeepLightsOn) {
        return smartHomeSecurityService.setLightsToRemainOn(lights, timeToKeepLightsOn);
    }

    @PutMapping(value = "/awayMode/turnOnOffLights")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI turnOnOffLights(@RequestParam("status") Boolean status) {
        responseAPI.awayMode = SmartHomeSecurity.getInstance().getAwayModeConfig().isAwayMode();
        responseAPI.success = smartHomeSecurityService.turnOnOffLights(status);
        responseAPI.timeBeforeAuthorities = SmartHomeSecurity.getInstance().getAwayModeConfig().getTimeBeforeAuthorities();
        responseAPI.timeToKeepLightsOn = SmartHomeSecurity.getInstance().getTimeToKeepLightsOn();
        responseAPI.consoleMessage = SmartHomeSecurity.getInstance().getConsoleMessage();
        return responseAPI;
    }

}
