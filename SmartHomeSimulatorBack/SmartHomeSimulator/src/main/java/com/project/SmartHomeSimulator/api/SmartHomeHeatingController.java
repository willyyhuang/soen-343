package com.project.SmartHomeSimulator.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API controller for everything related to the heating process
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("api/v1/simulation")
public class SmartHomeHeatingController {
}
