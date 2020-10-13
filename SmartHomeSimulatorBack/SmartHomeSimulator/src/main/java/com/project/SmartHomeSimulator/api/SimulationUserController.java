package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.SimulationConfig;
import com.project.SmartHomeSimulator.model.SimulationUser;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.SimulationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationUserController {

    @Autowired
    private SimulationUserService simulationUserService;

    // returns true if user was edited or false if user does not exist
    @PostMapping(value = "/add")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public SimulationConfig addSimulationUser(@RequestBody @Valid SimulationUser simulationUser)
    {
        return simulationUserService.addSimulationUser(simulationUser);
    }

    // returns true if user was removed or false if user does not exist
    @PostMapping(value = "/remove")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public SimulationConfig removeUser(@RequestParam("username") String username)
    {
        return simulationUserService.removeUser(username);
    }


    // returns true if user was edited or false if user does not exist
    @PostMapping(value = "/editHomeLocation")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public SimulationConfig editHomeLocation (@RequestParam("username") String username, @RequestParam("homeLocation") String homeLocation)
    {
        return simulationUserService.editHomeLocation(username,homeLocation);
    }

    // returns true if user was edited or false if user does not exist
    @PostMapping(value = "/editCurrentSimulationUser")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public SimulationConfig editCurrentUser (@RequestParam("simulatorUsername") String username, @RequestBody @Valid User user)
    {
        return simulationUserService.setCurrentSimulationUser(user, username);
    }


}
