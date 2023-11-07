package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.service.PomodoroAppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/events")
public class PomodoroGoogleApiController {

        @Autowired
        PomodoroAppService pomodoroAppService;

        @GetMapping("/users")
        public ResponseEntity<String> getGoogleUserInfo(@RequestHeader("Authorization") String authToken)
                throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {
                String responseJson = pomodoroAppService.getGoogleApiUserInfo(authToken);
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }

        @GetMapping("/")
        public ResponseEntity<String> getGoogleCalendarEvents(@RequestHeader("Authorization") String authToken)
                throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException, JsonProcessingException {
                String responseJson = pomodoroAppService.getGoogleApiCalendarEvents(authToken);
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
}