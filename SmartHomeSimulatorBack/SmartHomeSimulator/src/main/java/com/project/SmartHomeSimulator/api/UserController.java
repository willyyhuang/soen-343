package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.RegistrationService;
import com.project.SmartHomeSimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "api/v1/user/find")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String identifyUser(@RequestBody @Valid User user)
    {
        String username = userService.identifyUser(user);
        return username;
    }
}
