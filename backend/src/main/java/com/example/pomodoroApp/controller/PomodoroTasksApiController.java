package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.service.PomodoroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class PomodoroTasksApiController {
    @Autowired
    PomodoroService pomodoroService;

    @GetMapping(value = "/welcome")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<String>("Welcome to the Pomodoro API", HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    // Check if user already exists in database, if not then call google endpoint to get user details
    // and add to our database with a userAccount object
    public ResponseEntity<String> login(@RequestHeader("authorization") String authorization, @RequestHeader("user") String uid) {

        // TODO - move into PomodoroCalendarAPIController?

        String loginResponse = pomodoroService.login(authorization, uid);

        return new ResponseEntity<String>(loginResponse, HttpStatus.OK);
    }


    @GetMapping(value = "/")
    // Get a list of all tasks from out database:
    //  if isCompleted = false then return tasks[] having a deadline date in the future and competedDateTime = null
    //  if isCompleted = true then return tasks[] having the completedDateTime field not null
    public ResponseEntity<List<UserPomodoroTask>> getAllTasks(@RequestHeader("user") String uid, @RequestParam(name = "isCompleted", defaultValue = "false") String isCompletedParam) {

        boolean isCompleted = Boolean.parseBoolean(isCompletedParam);

        List<UserPomodoroTask> tasks = pomodoroService.getAllTasks(uid, isCompleted);
        return new ResponseEntity<List<UserPomodoroTask>>(tasks, HttpStatus.OK);
    }


    @GetMapping(value = "/calendar")
    // Sync to google calendar and return updated list of tasks (using pomodoroService.getAllTasks(uid, false);
    public ResponseEntity<List<UserPomodoroTask>> getSyncedTasks(@RequestHeader("authorization") String authorization, @RequestHeader("user") String uid) {

        List<UserPomodoroTask> tasks = pomodoroService.synchAndGetAllTasks(authorization, uid);
        return new ResponseEntity<List<UserPomodoroTask>>(tasks, HttpStatus.OK);
    }


    @GetMapping(value = "/music")
    public ResponseEntity<URL> getMusicURL(@RequestHeader("user") String uid) {

        URL musicURL = pomodoroService.getMusicUrl(uid);
        return new ResponseEntity<URL>(musicURL, HttpStatus.OK);
    }


    @PatchMapping("/{taskId}")
    // Update the task in our database
    // Do we expect a task in the body, or a field to update?
    public ResponseEntity<UserPomodoroTask> updateTask(@RequestHeader("user") String uid, @PathVariable Long taskId, @RequestBody UserPomodoroTask task) {

        pomodoroService.updateTaskById(uid, taskId, task);
        return new ResponseEntity<UserPomodoroTask>(pomodoroService.getTaskById(taskId), HttpStatus.OK);
    }
}