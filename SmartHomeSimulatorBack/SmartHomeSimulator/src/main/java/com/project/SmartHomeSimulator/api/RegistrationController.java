package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "api/v1/user/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid User user)
    {
        registrationService.createUser(user);
    }
}
