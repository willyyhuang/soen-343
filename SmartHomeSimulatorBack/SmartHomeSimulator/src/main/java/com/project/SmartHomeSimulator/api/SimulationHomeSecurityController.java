package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.service.SmartHomeSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationHomeSecurityController {

    @Autowired
    private SmartHomeSecurityService smartHomeSecurityService;

    @PutMapping(value = "/awayMode")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean startAwayMode(@RequestParam("awayMode") Boolean awayMode) {
        return smartHomeSecurityService.setAwayMode(awayMode);
    }

    @PutMapping(value = "/awayMode/timeBeforeAuthorities")
    @ResponseStatus(value = HttpStatus.OK)
    public void setTimeBeforeAuthorities(@RequestParam("timeBeforeAuthorities") int timeBeforeAuthorities) {
        smartHomeSecurityService.setTimeBeforeAuthorities(timeBeforeAuthorities);
    }

}
