package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.ResponseAPI;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.module.SmartHomeSecurity;
import com.project.SmartHomeSimulator.service.SmartHomeSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationHomeSecurityController {

    @Autowired
    private SmartHomeSecurityService smartHomeSecurityService;
    private ResponseAPI responseAPI = new ResponseAPI();
    private SimulationContext simulationContext = SimulationContext.getInstance();

    @PutMapping(value = "/awayMode")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI startAwayMode(@RequestParam("awayMode") Boolean awayMode) {
        responseAPI.success = smartHomeSecurityService.setAwayMode(awayMode);
        responseAPI.awayMode = SmartHomeSecurity.getInstance().getAwayModeConfig().isAwayMode();
        simulationContext.setAwaymode(awayMode);
        responseAPI.timeBeforeAuthorities = SmartHomeSecurity.getInstance().getAwayModeConfig().getTimeBeforeAuthorities();
        responseAPI.timeToKeepLightsOn = SmartHomeSecurity.getInstance().getTimeToKeepLightsOn();
        responseAPI.consoleMessage = SmartHomeSecurity.getInstance().getConsoleMessage();
        responseAPI.alertModeOn = false;
        return responseAPI;
    }

    @PutMapping(value = "/awayMode/timeBeforeAuthorities")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setTimeBeforeAuthorities(@RequestParam("timeBeforeAuthorities") int timeBeforeAuthorities) {
        smartHomeSecurityService.setTimeBeforeAuthorities(timeBeforeAuthorities);
        simulationContext.setTimeBeforeAuthoroties(timeBeforeAuthorities);
        return true;
    }

    @PutMapping(value = "/awayMode/setLights")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setLightIDs(@RequestBody List<String> lightIDs) {
        return smartHomeSecurityService.setLightIDs(lightIDs);
    }

    @PutMapping(value = "/awayMode/setTimeToKeepLightsOn")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setTimeToKeepLightsOn(@RequestParam("timeToKeepLightsOn") String timeToKeepLightsOn) {
        return smartHomeSecurityService.setTimeToKeepLightsOn(timeToKeepLightsOn);
    }

    @PutMapping(value = "/awayMode/turnOnOffLights")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI turnOnOffLights(@RequestParam("status") Boolean status) {
        responseAPI.awayMode = SmartHomeSecurity.getInstance().getAwayModeConfig().isAwayMode();
        responseAPI.success = smartHomeSecurityService.turnOnOffLights(status);
        responseAPI.timeBeforeAuthorities = SmartHomeSecurity.getInstance().getAwayModeConfig().getTimeBeforeAuthorities();
        responseAPI.timeToKeepLightsOn = SmartHomeSecurity.getInstance().getTimeToKeepLightsOn();
        responseAPI.consoleMessage = SmartHomeSecurity.getInstance().getConsoleMessage();
        responseAPI.alertModeOn = false;
        return responseAPI;
    }

}
