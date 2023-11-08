package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.service.PomodoroAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/tasks")
public class PomodoroTasksApiController {
    @Autowired
    PomodoroAppService pomodoroAppService;

    @GetMapping(value = "/welcome")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<String>("Welcome to the Pomodoro API", HttpStatus.OK);
    }


    @GetMapping(value = "/")
    // Get a list of all tasks from out database:
    //  @ user - unique google id of the user
    //  @ authorization - token to authenticate with google API
    //  @ tasks: valid options are: all, completed, uncompleted
    //  @ google: valid options are: true, false   true syncs the database with the users calendar before returning the list of tasks
    public ResponseEntity<List<UserPomodoroTask>>
    getAllTasks(@RequestHeader("user") String googleUserId,
                @RequestHeader("authorization") String authToken,
                @RequestParam(name = "tasks", defaultValue = "all") String tasksSelection,
                @RequestParam(name = "google", defaultValue = "false") String googleSync) throws RuntimeException, URISyntaxException, ExecutionException, InterruptedException, TimeoutException {

        boolean syncToGoogle = Boolean.parseBoolean(googleSync);
        if (syncToGoogle) {
            pomodoroAppService.saveGoogleApiCalendarEvents(authToken);
        }

        List<UserPomodoroTask> tasks;

        switch (tasksSelection) {
            case "all":
                tasks = pomodoroAppService.getAllTasksByGoogleUserId(googleUserId);
                break;
            case "completed":
                tasks = pomodoroAppService.getAllCompletedTasksByGoogleUserId(googleUserId);
                break;

            case "uncompleted":
                tasks = pomodoroAppService.getAllUncompletedTasksByGoogleUserId(googleUserId);
                break;

            default:
                throw new IllegalArgumentException("tasks parameter is invalid:" + tasksSelection);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping(value = "/music")
    public ResponseEntity<URL> getMusicURL(@RequestHeader("user") String uid) {

        URL musicURL = pomodoroAppService.getMusicUrl(uid);
        return new ResponseEntity<URL>(musicURL, HttpStatus.OK);
    }


    @PatchMapping("/{taskId}")
    // Update the task in our database
    // Do we expect a task in the body, or a field to update?
    public ResponseEntity<UserPomodoroTask> updateTask(@RequestHeader("user") String uid, @PathVariable Long taskId, @RequestBody UserPomodoroTask task) {

        return new ResponseEntity<UserPomodoroTask> (pomodoroAppService.updateTaskByTaskId(uid, taskId, task), HttpStatus.OK);
    }
}