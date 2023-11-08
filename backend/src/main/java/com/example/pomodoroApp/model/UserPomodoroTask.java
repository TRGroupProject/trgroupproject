package com.example.pomodoroApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPomodoroTask {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long taskId;

    @Column(updatable = false, nullable = false)
    String googleUserId;

    @Column(updatable = false, nullable = false)
    String googleEventId;

    @Column(updatable = true, nullable = false)
    String title;

    @Column(updatable = true, nullable = true)
    String description;

    @Column(updatable = true, nullable = false)
    LocalDateTime calendarStartDateTime;

    @Column(nullable = true)
    LocalDateTime pomodoroStartDateTime;

    @Column(nullable = true)
    LocalDateTime pomodoroEndDateTime;
}
