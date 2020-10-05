package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.RegistrationService;
import com.project.SmartHomeSimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    //Returns 0 if not found, or 1 if successfully deleted
    @PostMapping(value = "api/v1/user/remove")
    @ResponseStatus(value = HttpStatus.GONE)
    @ResponseBody
    @Transactional
    public long removeUser(@RequestParam("username") String username)
    {
        return userService.removeUser(username);
    }
}
