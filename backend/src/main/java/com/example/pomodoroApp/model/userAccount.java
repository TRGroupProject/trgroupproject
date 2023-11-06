package com.example.pomodoroApp.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long userId;

    @Column(updatable = false, nullable = false)
    String googleUserId;

    @Column(updatable = false, nullable = false)
    String userName;

    @Column(updatable = false, nullable = false)
    String userEmail;

}
