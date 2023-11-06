package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;

import java.net.URL;
import java.util.List;

public interface PomodoroService {


    List<UserPomodoroTask> getAllTasks(String uid, boolean isCompleted);

    List<UserPomodoroTask> synchAndGetAllTasks(String authorization, String uid);

    String login(String authorization, String uid);

    URL getMusicUrl(String uid);

    UserPomodoroTask updateTaskById(String uid, Long taskId, UserPomodoroTask task);

    UserPomodoroTask getTaskById(Long taskId);
}
