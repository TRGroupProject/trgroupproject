package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;

import java.net.URL;
import java.util.List;

public interface PomodoroService {


    List<UserPomodoroTask> getAllTasks(String uid, boolean isCompleted);

    List<UserPomodoroTask> synchAndGetAllTasks(String uid, String s);

    String login(String authorisaztionCredentials, String uid);

    URL getMusicUrl(String uid);

    UserPomodoroTask updateTaskById(String uid, Long taskId, UserPomodoroTask task);

    UserPomodoroTask getTaskById(Long taskId);
}
