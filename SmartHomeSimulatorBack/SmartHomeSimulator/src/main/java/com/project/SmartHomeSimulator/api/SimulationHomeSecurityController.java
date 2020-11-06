package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.ResponseAPI;
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

    @PutMapping(value = "/awayMode")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseAPI startAwayMode(@RequestParam("awayMode") Boolean awayMode) {
        ResponseAPI responseAPI = new ResponseAPI();

        responseAPI.awayMode = SmartHomeSecurity.getInstance().getAwayModeConfig().isAwayMode();
        responseAPI.success = smartHomeSecurityService.setAwayMode(awayMode);
        responseAPI.timeBeforeAuthorities = SmartHomeSecurity.getInstance().getAwayModeConfig().getTimeBeforeAuthorities();
        responseAPI.timeToKeepLightsOn = SmartHomeSecurity.getInstance().getTimeToKeepLightsOn();

        return responseAPI;
    }

    @PutMapping(value = "/awayMode/timeBeforeAuthorities")
    @ResponseStatus(value = HttpStatus.OK)
    public void setTimeBeforeAuthorities(@RequestParam("timeBeforeAuthorities") int timeBeforeAuthorities) {
        smartHomeSecurityService.setTimeBeforeAuthorities(timeBeforeAuthorities);
    }

    @PutMapping(value = "/awayMode/lights")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setLightsToRemainOn(@RequestParam("lightIDs") List<String> lightIDs,@RequestParam("timeToKeepLightsOn") int timeToKeepLightsOn) {
        return smartHomeSecurityService.setLightsToRemainOn(lightIDs,timeToKeepLightsOn);
    }

}
