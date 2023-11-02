package com.example.pomodoroApp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class PomodoroCalendarApiController {

    @GetMapping("/")
    public String greeting() {
        return "this is events controller";
    }
}
