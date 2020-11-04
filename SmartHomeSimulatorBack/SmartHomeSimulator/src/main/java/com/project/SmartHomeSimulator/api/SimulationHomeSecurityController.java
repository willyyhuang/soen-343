package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.service.SmartHomeSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulationHomeSecurity")
public class SimulationHomeSecurityController {

    @Autowired
    private SmartHomeSecurityService smartHomeSecurityService;

    @PutMapping(value = "/awayMode")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean startSimulation(@RequestParam("awayMode") Boolean awayMode) {
        return smartHomeSecurityService.setAwayMode(awayMode);
    }

}
