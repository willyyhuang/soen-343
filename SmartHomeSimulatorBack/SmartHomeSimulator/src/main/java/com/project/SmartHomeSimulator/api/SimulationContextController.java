package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.HomeLayout;
import com.project.SmartHomeSimulator.model.SimulationContext;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.SimulationContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SimulationContextController {

    @Autowired
    private SimulationContextService simulationContextService;

    @PostMapping(value = "/start")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean startSimulation(@RequestBody @Valid SimulationContext simulationContext) {
        return simulationContextService.startSimulation(simulationContext);
    }

    @PostMapping(value = "/stop")
    @ResponseStatus(value = HttpStatus.OK)
    public void stopSimulation(@RequestBody @Valid SimulationContext simulationContext) {
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

    @PostMapping(value = "/editInsideTemp ")
    @ResponseStatus(value = HttpStatus.OK)
    public void editInsideTemp(@RequestParam("insideTemp") int insideTemp) {
        simulationContextService.editInsideTemp(insideTemp);
    }

    @PostMapping(value = "/editOutsideTemp ")
    @ResponseStatus(value = HttpStatus.OK)
    public void editOutsideTemp(@RequestParam("outsideTemp") int outsideTemp) {
        simulationContextService.editOutsideTemp(outsideTemp);
    }

    @PostMapping(value = "/editTime ")
    @ResponseStatus(value = HttpStatus.OK)
    public void editTime(@RequestParam("time") String time) {
        simulationContextService.editTime(time);
    }

    @PostMapping(value = "/editDate ")
    @ResponseStatus(value = HttpStatus.OK)
    public void editDate(@RequestParam("date") String date) {
        simulationContextService.editDate(date);
    }

    @PostMapping(value = "/blockWindow ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean blockWindow(@RequestParam("roomName") String roomName) {
        return simulationContextService.blockWindow(roomName);
    }

    @PostMapping(value = "/unblockWindow ")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean unblockWindow(@RequestParam("roomName") String roomName) {
        return simulationContextService.unblockWindow(roomName);
    }

    @PostMapping(value = "/loadLayout")
    @ResponseStatus(value = HttpStatus.OK)
    public HomeLayout loadLayout(@RequestBody String homeLayout) {
        return simulationContextService.loadLayout(homeLayout);
    }

}
