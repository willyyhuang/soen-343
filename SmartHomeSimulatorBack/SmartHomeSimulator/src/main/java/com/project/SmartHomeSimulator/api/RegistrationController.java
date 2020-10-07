package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
@RequestMapping("/api/v1")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/user/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid User user)
    {
        registrationService.createUser(user);
    }
}
