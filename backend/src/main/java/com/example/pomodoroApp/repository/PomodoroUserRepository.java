package com.example.pomodoroApp.repository;

import com.example.pomodoroApp.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface PomodoroUserRepository extends CrudRepository<UserAccount, Long> {

    public UserAccount getUserAccountByGoogleUserId(String googleUserId);
}

