package com.project.SmartHomeSimulator.api;

import com.project.SmartHomeSimulator.model.User;
import com.project.SmartHomeSimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * API controller for everything related to a user
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Add a user
     * @param user
     * @return  - true if successful false if otherwise
     */
    @PostMapping(value = "/add")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public boolean addUser(@RequestBody @Valid User user)
    {
        return userService.addUser(user);
    }

    /**
     * remove a user
     * @param name
     * @return  - true if successful false if otherwise
     */
    @PostMapping(value = "/remove/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean removeUser(@PathVariable String name)
    {
        return userService.removeUser(name);
    }

    /**
     * Edit a user's name
     * @param name
     * @param newName
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/edit/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean editUser(@PathVariable String name,@RequestParam("newName") String newName)
    {
        return userService.editUser(name, newName);
    }

    /**
     * Edit a user's homeLocation
     * @param name
     * @param homeLocation
     * @return - true if successful false if otherwise
     */
    @PostMapping(value = "/editHomeLocation")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean editHomeLocation (@RequestParam("name") String name, @RequestParam("homeLocation") String homeLocation)
    {
        return userService.editHomeLocation(name,homeLocation);
    }

}
