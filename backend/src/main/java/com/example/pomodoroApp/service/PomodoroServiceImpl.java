package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class PomodoroServiceImpl implements PomodoroService {

    @Override
    public List<UserPomodoroTask> getAllTasks(String uid, boolean isCompleted) {
        return null;
    }

    @Override
    public List<UserPomodoroTask> synchAndGetAllTasks(String uid, String s) {
        return null;
    }

    @Override
    public String login(String authorisaztionCredentials, String uid) {
        return null;
    }

    @Override
    public URL getMusicUrl(String uid) {
        return null;
    }

    @Override
    public UserPomodoroTask updateTaskById(String uid, Long taskId, UserPomodoroTask task) {
return null;
    }

    @Override
    public UserPomodoroTask getTaskById(Long taskId) {
        return null;
    }
}
