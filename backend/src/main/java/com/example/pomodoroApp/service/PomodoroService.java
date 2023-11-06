package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.PomodoroTask;

import java.net.URL;
import java.util.List;

public interface PomodoroService {


    List<PomodoroTask> getAllTasks(String uid, boolean isCompleted);

    List<PomodoroTask> synchAndGetAllTasks(String uid, String s);

    String login(String authorisaztionCredentials, String uid);

    URL getMusicUrl(String uid);

    void updateTaskById(String uid, Long taskId, PomodoroTask task);

    PomodoroTask getTaskById(Long taskId);
}
