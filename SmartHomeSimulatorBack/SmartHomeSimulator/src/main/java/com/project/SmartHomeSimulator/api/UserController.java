package com.project.SmartHomeSimulator.api;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartHomeSimulator.model.APIResponseLogin;
import com.project.SmartHomeSimulator.model.HouseLayout;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.UserService;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public APIResponseLogin identifyUser(@RequestBody @Valid User user)
    {
        return userService.identifyUser(user);
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

    //returns user if it exists
    @GetMapping(value= "/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public APIResponseLogin getUser (@PathVariable String username)
    {
        return userService.getUser(username);
    }
    
    //Return House Layout if it exists
    @PostMapping(value = "api/v1/user/loadLayout")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Transactional
    public HouseLayout loadLayout (@RequestBody @Valid User user)
    {
        return userService.loadLayout(user);
    }

}
