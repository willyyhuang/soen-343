package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.SimulationConfig;
import com.project.SmartHomeSimulator.model.SimulationProfile;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.SimulationProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationProfileController {

    @Autowired
    private SimulationProfileService simulationProfileService;

    // returns true if user was edited or false if user does not exist
    @PostMapping(value = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    @Transactional
    public boolean addsimulationProfile(@RequestBody @Valid SimulationProfile simulationProfile, @RequestParam("username") String username)
    {
        return simulationProfileService.addSimulationProfile(simulationProfile, username);
    }

    // returns true if user was removed or false if user does not exist
    @PostMapping(value = "/remove")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public boolean removeSimulationProfile(@RequestParam("name") String name, @RequestParam("username") String username)
    {
        return simulationProfileService.removeSimulationProfile(name, username);
    }


    // returns true if user was edited or false if user does not exist
    @PostMapping(value = "/editHomeLocation")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public boolean editHomeLocation (@RequestParam("name") String name, @RequestParam("homeLocation") String homeLocation,  @RequestParam("username") String username)
    {
        return simulationProfileService.editHomeLocation(name,homeLocation, username);
    }

    // returns true if user was edited or false if user does not exist
    @PostMapping(value = "/setCurrentSimulationProfile")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public boolean setCurrentProfile (@RequestParam("name") String name, @RequestParam("username") String username)
    {
        return simulationProfileService.setCurrentSimulationProfile(name, username);
    }

    @GetMapping(value = "/simulationConfig")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public SimulationConfig getSimulationConfig (@RequestParam("username") String username)
    {
        return simulationProfileService.getSimulationConfig(username);
    }


}
