package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface PomodoroAppService {
    public List<UserPomodoroTask> getAllTasksByGoogleUserId(String googleUserId, boolean syncGoogleCalendar);

    public List<UserPomodoroTask> getAllCompletedTasksByGoogleUserId(String googleUserId, boolean syncGoogleCalendar);

    public List<UserPomodoroTask> getAllUncompletedTasksByGoogleUserId(String googleUserId, boolean syncGoogleCalendar);

    public URL getGoogleApiUrl(String uid);

    URL getMusicUrl(String uid);

    UserPomodoroTask updateTaskByGoogleUserId(String googleUserId, Long taskId, UserPomodoroTask task);

    UserPomodoroTask getTaskByTaskId(Long taskId);

//    public String getGoogleApiCalendarEvents(String uid) throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException;

    public String getGoogleApiUserInfo(String uid) throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException;
}
