package com.example.pomodoroApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/events")
public class PomodoroGoogleApiController {

    @GetMapping ("/")
    public String welcome () {
        return ("Welcome");
    }
}
