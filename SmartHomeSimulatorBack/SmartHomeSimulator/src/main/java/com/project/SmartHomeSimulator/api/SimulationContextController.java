package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.module.SimulationContext;
import com.project.SmartHomeSimulator.service.SimulationContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * API controller for everything related to the simulation context
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationContextController {

    @Autowired
    private SimulationContextService simulationContextService;

    /**
     * API start the simulation
     */
    @PostMapping(value = "/start")
    @ResponseStatus(value = HttpStatus.OK)
    public void startSimulation() {
        simulationContextService.startSimulation();
    }

    /**
     * API stop the simulation
     */
    @PostMapping(value = "/stop")
    @ResponseStatus(value = HttpStatus.OK)
    public void stopSimulation() {
        simulationContextService.stopSimulation();
    }

    /**
     * get the simulation context - the simulation data
     * @return the simulation context
     */
    @GetMapping(value = "/get")
    @ResponseStatus(value = HttpStatus.OK)
    public SimulationContext getSimulationContext() {
        return simulationContextService.getSimulationContext();
    }

    /**
     * sets the current user in charge of the simulation
     * @param name - name of the user in charge
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/setCurrentSimulationUser")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setCurrentUser(@RequestParam("name") String name) {
        return simulationContextService.setCurrentSimulationUser(name);
    }

    /**
     * API set Inside temp
     * @param insideTemp
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/setInsideTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setInsideTemp(@RequestParam("insideTemp") int insideTemp) {
       return simulationContextService.setInsideTemp(insideTemp);
    }

    /**
     * API set outside temp
     * @param outsideTemp
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/setOutsideTemp")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setOutsideTemp(@RequestParam("outsideTemp") int outsideTemp) {
       return simulationContextService.setOutsideTemp(outsideTemp);
    }

    /**
     * API set time
     * @param time
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/setTime")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setTime(@RequestParam("time") String time) {
       return simulationContextService.setTime(time);
    }

    /**
     * API set date
     * @param date
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/setDate")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setDate(@RequestParam("date") String date) {
        return simulationContextService.setDate(date);
    }

    /**
     *API load house layout
     * @param homeLayout - JSON file as a string
     * @return the house layout information
     */
    @PostMapping(value = "/loadLayout")
    @ResponseStatus(value = HttpStatus.OK)
    public HomeLayout loadLayout(@RequestBody String homeLayout) {
        return simulationContextService.loadLayout(homeLayout);
    }

}
