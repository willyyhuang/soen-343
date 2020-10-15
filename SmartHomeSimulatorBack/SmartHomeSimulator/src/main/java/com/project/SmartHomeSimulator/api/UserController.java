package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.APIResponseLogin;
import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;


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


    //returns user if it exists
    @GetMapping(value= "/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUser (@PathVariable String username)
    {
        return userService.getUser(username);
    }

}
