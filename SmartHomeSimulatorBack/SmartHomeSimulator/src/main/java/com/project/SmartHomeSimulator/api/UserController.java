package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public boolean identifyUser(@RequestBody @Valid User user)
    {
        return userService.addUser(user);
    }

    @PostMapping(value = "/remove/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean removeUser(@PathVariable String name)
    {
        return userService.removeUser(name);
    }

    @PostMapping(value = "/edit/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean editUser(@PathVariable String name,@RequestParam("newName") String newName)
    {
        return userService.editUser(name, newName);
    }

    @PostMapping(value = "/editHomeLocation")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean editHomeLocation (@RequestParam("name") String name, @RequestParam("homeLocation") String homeLocation)
    {
        return userService.editHomeLocation(name,homeLocation);
    }


}
