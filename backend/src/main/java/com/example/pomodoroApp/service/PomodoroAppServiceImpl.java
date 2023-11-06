package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.repository.PomodoroAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PomodoroAppServiceImpl implements PomodoroAppService {

    @Autowired
    PomodoroAppRepository pomodoroAppRepository;

    @Override
    public List<UserPomodoroTask> getAllTasksByGoogleUserId(String googleUserId) {
        List<UserPomodoroTask> tasks = pomodoroAppRepository.getTasksByGoogleUserId(googleUserId);
        return tasks;
    }

    @Override
    public List<UserPomodoroTask> getAllCompletedTasksByGoogleUserId(String googleUserId) {
        List<UserPomodoroTask> tasks = pomodoroAppRepository.findAllTasksCompleted(googleUserId);
        return tasks;
    }

    @Override
    public List<UserPomodoroTask> getAllUncompletedTasksByGoogleUserId(String googleUserId) {
        List<UserPomodoroTask> tasks = pomodoroAppRepository.findAllTasksUncompleted(googleUserId);
        return tasks;
    }
}
