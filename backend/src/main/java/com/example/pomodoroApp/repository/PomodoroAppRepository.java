package com.example.pomodoroApp.repository;

import com.example.pomodoroApp.model.UserPomodoroTask;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PomodoroAppRepository extends CrudRepository<UserPomodoroTask, Long>  {

    public List<UserPomodoroTask> getTasksByGoogleUserId(String googleUserId);

    public UserPomodoroTask getTasksByGoogleEventId(String googleEventId);

    @Query("SELECT u FROM UserPomodoroTask u WHERE u.pomodoroEndDateTime = null and u.googleUserId = :googleUserId")
    public List<UserPomodoroTask> findAllTasksUncompleted(@Param("googleUserId") String googleUserId);

    @Query("SELECT u FROM UserPomodoroTask u WHERE u.pomodoroEndDateTime != null and u.googleUserId = :googleUserId")
    public List<UserPomodoroTask> findAllTasksCompleted(@Param("googleUserId") String googleUserId);

}

