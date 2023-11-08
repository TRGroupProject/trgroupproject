package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.model.UserAccount;
import com.example.pomodoroApp.service.PomodoroAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/events")
public class PomodoroGoogleApiController {

        @Autowired
        PomodoroAppService pomodoroAppService;

        @PostMapping ("/users")
        public ResponseEntity<UserAccount> saveGoogleUserInfo(@RequestHeader("Authorization") String authToken)
                throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {
                UserAccount savedUser = pomodoroAppService.saveGoogleApiUserInfo(authToken);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setLocation(URI.create("/api/v1/tasks" + savedUser.getUserId().toString()));

                return new ResponseEntity<>(savedUser, httpHeaders, HttpStatus.CREATED);
        }

        @PostMapping("/")
        public ResponseEntity<String> saveGoogleCalendarEvents(@RequestHeader("Authorization") String authToken)
                throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {
                String savedTasks = pomodoroAppService.saveGoogleApiCalendarEvents(authToken);

                return new ResponseEntity<>(savedTasks, HttpStatus.CREATED);
        }
}