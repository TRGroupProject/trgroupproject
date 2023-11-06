package com.example.pomodoroApp.service;

import com.example.pomodoroApp.exceptions.InvalidTaskIdException;
import com.example.pomodoroApp.exceptions.InvalidUserException;
import com.example.pomodoroApp.model.UserPomodoroTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PomodoroServiceImpl implements PomodoroService {

    @Autowired
    PomodoroRepository pomodoroRepository;

    @Override
    public List<UserPomodoroTask> getAllTasks(String uid, boolean isCompleted) {

        // Validate uid
        if (!isValidUid(uid)) {
            throw new InvalidUserException("User ID is not valid");
        }

        List<UserPomodoroTask> tasks = new ArrayList<>();
        pomodoroRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    @Override
    public List<UserPomodoroTask> synchAndGetAllTasks(String authorization, String uid) {
        return null;
    }

    @Override
    public String login(String authorization, String uid) {
        return null;
    }

    @Override
    public URL getMusicUrl(String uid) {
        return null;
    }

    @Override
    public UserPomodoroTask updateTaskById(String uid, Long taskId, UserPomodoroTask task) {

        if (!isValidUid(uid)) {
            throw new InvalidUserException("User ID is not valid");
        }

        UserPomodoroTask retrievedTask = getTaskById(taskId);

        if (retrievedTask != null) {
            retrievedTask.setTitle(task.getTitle());
            retrievedTask.setDescription(task.getDescription());
            retrievedTask.setGoogleEventId(task.getGoogleEventId());
            retrievedTask.setGoogleUserId(task.getGoogleUserId());
            retrievedTask.setCalendarStartDateTime(task.getCalendarStartDateTime());
            retrievedTask.setPomodoroStartDateTime(task.getPomodoroStartDateTime());
            retrievedTask.setPomodoroEndDateTime(task.getPomodoroEndDateTime());
            pomodoroRepository.save(retrievedTask);
        } else {
            throw new InvalidTaskIdException ( "The task with id " + taskId + " cannot be found");
        }
        return retrievedTask;
    }

    @Override
    public UserPomodoroTask getTaskById(Long taskId) {
        return pomodoroRepository.findById(taskId)
                .orElseThrow(() -> new InvalidTaskIdException ("No task present with ID = " + taskId));
    }


    protected boolean isValidUid (String uid) {
        // TODO - call database to see if UID exists in db. True if it exists, false otherwise
        return pomodoroRepository.isValidUid (uid);
    }
}
