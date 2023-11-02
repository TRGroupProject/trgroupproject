package com.example.pomodoroApp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class PomodoroApiController {

    @GetMapping("/")
    public String greeting() {
        return "this is tasks controller";
    }
}
