package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;
import java.util.List;

public interface PomodoroAppService {
    public List<UserPomodoroTask> getAllTasksByGoogleUserId(String googleUserId);

    public List<UserPomodoroTask> getAllCompletedTasksByGoogleUserId(String googleUserId);

    public List<UserPomodoroTask> getAllUncompletedTasksByGoogleUserId(String googleUserId);
}