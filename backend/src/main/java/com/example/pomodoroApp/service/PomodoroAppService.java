package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserAccount;
import com.example.pomodoroApp.model.UserPomodoroTask;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface PomodoroAppService {
    public List<UserPomodoroTask> getAllTasksByGoogleUserId(String googleUserId);

    public List<UserPomodoroTask> getAllCompletedTasksByGoogleUserId(String googleUserId);

    public List<UserPomodoroTask> getAllUncompletedTasksByGoogleUserId(String googleUserId);

    public URL getGoogleApiUrl(String uid);

    URL getMusicUrl(String uid);

    UserPomodoroTask updateTaskByTaskId(String googleUserId, Long taskId, UserPomodoroTask task);

    UserPomodoroTask getTaskByTaskId(Long taskId);

    public String saveGoogleApiCalendarEvents(String uid) throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException;

    public UserAccount saveGoogleApiUserInfo(String uid) throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException;
}
