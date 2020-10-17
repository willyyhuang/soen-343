package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.SimulationContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationContextController {

    @Autowired
    private SimulationContextService simulationContextService;

    @PostMapping(value = "/start")
    @ResponseStatus(value = HttpStatus.OK)
    public void startSimulation() {
        simulationContextService.startSimulation();
    }

    @PostMapping(value = "/stop")
    @ResponseStatus(value = HttpStatus.OK)
    public void stopSimulation() {
        simulationContextService.stopSimulation();
    }

    @GetMapping(value = "/get")
    @ResponseStatus(value = HttpStatus.OK)
    public SimulationContext getSimulationContext() {
        return simulationContextService.getSimulationContext();
    }

    @PostMapping(value = "/setCurrentSimulationUser")
    @ResponseStatus(value = HttpStatus.OK)
    public void setCurrentUser(@RequestBody User user) {
        simulationContextService.setCurrentSimulationUser(user);
    }

    @PostMapping(value = "/setInsideTemp ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setInsideTemp(@RequestParam("insideTemp") int insideTemp) {
       return simulationContextService.setInsideTemp(insideTemp);
    }

    @PostMapping(value = "/setOutsideTemp ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setOutsideTemp(@RequestParam("outsideTemp") int outsideTemp) {
       return simulationContextService.setOutsideTemp(outsideTemp);
    }

    @PostMapping(value = "/setTime ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setTime(@RequestParam("time") String time) {
       return simulationContextService.setTime(time);
    }

    @PostMapping(value = "/editDate ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean setDate(@RequestParam("date") String date) {
        return simulationContextService.setDate(date);
    }

    @PostMapping(value = "/blockWindow ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean blockWindow(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        return simulationContextService.blockWindow(roomName, objectID, true);
    }

    @PostMapping(value = "/unblockWindow ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean unblockWindow(@RequestParam("roomName") String roomName,@RequestParam("objectID") String objectID) {
        return simulationContextService.blockWindow(roomName, objectID, false);
    }

    @PostMapping(value = "/loadLayout")
    @ResponseStatus(value = HttpStatus.OK)
    public HomeLayout loadLayout(@RequestBody String homeLayout) {
        return simulationContextService.loadLayout(homeLayout);
    }

}
