package com.example.pomodoroApp.repository;

import com.example.pomodoroApp.model.UserAccount;
import com.example.pomodoroApp.model.UserPomodoroTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PomodoroAppRepositoryTests {

    @Autowired
    private PomodoroAppRepository pomodoroAppRepository;

    @Autowired
    private PomodoroUserRepository pomodoroUserRepository;

    @Test
    public void testFindAllTasksReturnsTasks() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime calendarDateTime = LocalDateTime.parse("2023-11-06 09:00:00",
                formatter);
        LocalDateTime pomodoroStartDT = LocalDateTime.parse("2023-11-05 14:00:00",
                formatter);
        LocalDateTime pomodoroEndDT = LocalDateTime.parse("2023-11-05 14:25:00", formatter);

        UserPomodoroTask userPomodoroTask1 = UserPomodoroTask.builder().googleUserId("123").googleEventId("1")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(pomodoroEndDT).build();
        UserPomodoroTask userPomodoroTask2 = UserPomodoroTask.builder().googleUserId("123").googleEventId("2")
                .title("workshop").description("learn typescript").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(pomodoroEndDT).build();
        UserPomodoroTask userPomodoroTask3 = UserPomodoroTask.builder().googleUserId("444").googleEventId("3")
                .title("workshop").description("learn java").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(pomodoroEndDT).build();

        pomodoroAppRepository.save(userPomodoroTask1);
        pomodoroAppRepository.save(userPomodoroTask2);
        pomodoroAppRepository.save(userPomodoroTask3);

        List<UserPomodoroTask> tasks = pomodoroAppRepository.getTasksByGoogleUserId("123");
        assertThat(tasks.size()).isEqualTo(2);
    }

    @Test
    public void testFindAllTasksUncompleted() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime calendarDateTime = LocalDateTime.parse("2023-11-06 09:00:00",
                formatter);
        LocalDateTime pomodoroStartDT = LocalDateTime.parse("2023-11-05 14:00:00",
                formatter);
        LocalDateTime pomodoroEndDT = LocalDateTime.parse("2023-11-05 14:25:00", formatter);

        UserPomodoroTask userPomodoroTask1 = UserPomodoroTask.builder().googleUserId("123").googleEventId("1")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(pomodoroEndDT).build();
        UserPomodoroTask userPomodoroTask2 = UserPomodoroTask.builder().googleUserId("123").googleEventId("2")
                .title("workshop").description("learn typescript").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();
        UserPomodoroTask userPomodoroTask3 = UserPomodoroTask.builder().googleUserId("444").googleEventId("3")
                .title("workshop").description("learn java").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();

        pomodoroAppRepository.save(userPomodoroTask1);
        pomodoroAppRepository.save(userPomodoroTask2);
        pomodoroAppRepository.save(userPomodoroTask3);

        List<UserPomodoroTask> tasks = pomodoroAppRepository.findAllTasksUncompleted("123");
        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    public void testFindAllTasksCompleted() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime calendarDateTime = LocalDateTime.parse("2023-11-06 09:00:00",
                formatter);
        LocalDateTime pomodoroStartDT = LocalDateTime.parse("2023-11-05 14:00:00",
                formatter);
        LocalDateTime pomodoroEndDT = LocalDateTime.parse("2023-11-05 14:25:00", formatter);

        UserPomodoroTask userPomodoroTask1 = UserPomodoroTask.builder().googleUserId("123").googleEventId("1")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();
        UserPomodoroTask userPomodoroTask2 = UserPomodoroTask.builder().googleUserId("123").googleEventId("2")
                .title("workshop").description("learn typescript").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();
        UserPomodoroTask userPomodoroTask3 = UserPomodoroTask.builder().googleUserId("123").googleEventId("3")
                .title("workshop").description("learn java").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(pomodoroEndDT).build();

        pomodoroAppRepository.save(userPomodoroTask1);
        pomodoroAppRepository.save(userPomodoroTask2);
        pomodoroAppRepository.save(userPomodoroTask3);

        List<UserPomodoroTask> tasks = pomodoroAppRepository.findAllTasksCompleted("123");
        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    public void testInsertTask() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime calendarDateTime = LocalDateTime.parse("2023-11-06 09:00:00",
                formatter);
        LocalDateTime pomodoroStartDT = LocalDateTime.parse("2023-11-05 14:00:00",
                formatter);
        LocalDateTime pomodoroEndDT = LocalDateTime.parse("2023-11-05 14:25:00", formatter);

        UserPomodoroTask userPomodoroTask1 = UserPomodoroTask.builder().googleUserId("123").googleEventId("1")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();
        UserPomodoroTask userPomodoroTask2 = UserPomodoroTask.builder().googleUserId("123").googleEventId("2")
                .title("workshop").description("learn typescript").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();
        UserPomodoroTask userPomodoroTask3 = UserPomodoroTask.builder().googleUserId("123").googleEventId("3")
                .title("workshop").description("learn java").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(pomodoroEndDT).build();

        pomodoroAppRepository.save(userPomodoroTask1);
        pomodoroAppRepository.save(userPomodoroTask2);
        pomodoroAppRepository.save(userPomodoroTask3);

        assertThat(pomodoroAppRepository.count()).isEqualTo(3);
    }

    @Test
    public void testUpdateTask() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime calendarDateTime = LocalDateTime.parse("2023-11-06 09:00:00",
                formatter);
        LocalDateTime pomodoroStartDT = LocalDateTime.parse("2023-11-05 14:00:00",
                formatter);
        LocalDateTime pomodoroEndDT = LocalDateTime.parse("2023-11-05 14:25:00", formatter);

        UserPomodoroTask userPomodoroTask1 = UserPomodoroTask.builder().googleUserId("123").googleEventId("1")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();
        UserPomodoroTask userPomodoroTask2 = UserPomodoroTask.builder().googleUserId("123").googleEventId("2")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();

        pomodoroAppRepository.save(userPomodoroTask1);
        pomodoroAppRepository.save(userPomodoroTask2);

        UserPomodoroTask event = pomodoroAppRepository.getTasksByGoogleEventId("1");
        event.setPomodoroEndDateTime(pomodoroEndDT);

        assertThat(event.getPomodoroEndDateTime()).isEqualTo(pomodoroEndDT);
    }

    @Test
    public void testInsertNewUser() {

        UserAccount newUser = UserAccount.builder().googleUserId("123").userEmail("user@gmail.com").userName("jimmy").build();

        pomodoroUserRepository.save(newUser);

        UserAccount user = pomodoroUserRepository.getUserAccountByGoogleUserId("123");
        assertThat(user.getGoogleUserId()).isEqualTo("123");
    }
}
