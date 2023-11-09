package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.model.UserAccount;
import com.example.pomodoroApp.service.PomodoroAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/events")
public class PomodoroGoogleApiController {

        @Autowired
        PomodoroAppService pomodoroAppService;

        @CrossOrigin(origins = "http://localhost:5173")
        @PostMapping ("/users")
        public ResponseEntity<UserAccount> saveGoogleUserInfo(@RequestHeader("Authorization") String authToken)
                throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {
                UserAccount savedUser = pomodoroAppService.saveGoogleApiUserInfo(authToken);

                return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }

        @CrossOrigin(origins = "http://localhost:5173")
        @PostMapping("/")
        public ResponseEntity<String> saveGoogleCalendarEvents(@RequestHeader("Authorization") String authToken,
                                                               @RequestHeader("user") String googleUserId)
                throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {
                String savedTasks = pomodoroAppService.saveGoogleApiCalendarEvents(authToken, googleUserId);

                return new ResponseEntity<>(savedTasks, HttpStatus.CREATED);
        }
}