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
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
@RequestMapping("api/v1/user")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String identifyUser(@RequestBody @Valid User user)
    {
        String username = userService.identifyUser(user);
        return username;
    }

    //Returns 0 if not found, or 1 if successfully deleted
    @PostMapping(value = "/remove")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public long removeUser(@RequestParam("username") String username)
    {
        return userService.removeUser(username);
    }

    //Returns 0 if not found, or 1 if successfully edited
    @PostMapping(value = "/editPassword")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public int editPassword (@RequestBody @Valid User user)
    {
        return userService.editPassword(user);
    }

    //Returns 0 if not found, or 1 if successfully edited
    @PostMapping(value = "/editHomeLocation")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public int editHomeLocation (@RequestBody @Valid User user)
    {
        return userService.editHomeLocation(user);
    }
}
