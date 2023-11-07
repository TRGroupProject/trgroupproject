package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.repository.PomodoroAppRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
public class PomodoroAppServiceTests {

    @Mock
    private PomodoroAppRepository mockPomodoroAppRepository;

    @InjectMocks
    private PomodoroAppServiceImpl pomodoroAppServiceImpl;

    @Test
    public void testGetAllTasksByGoogleUserId() {
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

        List<UserPomodoroTask> tasks = Arrays.asList(userPomodoroTask1, userPomodoroTask2, userPomodoroTask3);

        String userId = "123";

        when(mockPomodoroAppRepository.getTasksByGoogleUserId(userId)).thenReturn(tasks);

        List<UserPomodoroTask> actualResult = pomodoroAppServiceImpl.getAllTasksByGoogleUserId(userId);
        assertThat(actualResult).isEqualTo(tasks);
        assertThat(actualResult).hasSize(3);
    }

    @Test
    public void testGetAllCompletedTasksByGoogleUserId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime calendarDateTime = LocalDateTime.parse("2023-11-06 09:00:00",
                formatter);
        LocalDateTime pomodoroStartDT = LocalDateTime.parse("2023-11-05 14:00:00",
                formatter);
        LocalDateTime pomodoroEndDT = LocalDateTime.parse("2023-11-05 14:25:00", formatter);

        UserPomodoroTask userPomodoroTask1 = UserPomodoroTask.builder().googleUserId("123").googleEventId("1")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(pomodoroEndDT).build();

        List<UserPomodoroTask> completedTasks = Arrays.asList(userPomodoroTask1);

        String userId = "123";

        when(mockPomodoroAppRepository.findAllTasksCompleted(userId)).thenReturn(completedTasks);

        List<UserPomodoroTask> actualResult = pomodoroAppServiceImpl.getAllCompletedTasksByGoogleUserId(userId);
        assertThat(actualResult).isEqualTo(completedTasks);
        assertThat(actualResult).hasSize(1);
    }

    @Test
    public void testGetAllUncompletedTasksByGoogleUserId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime calendarDateTime = LocalDateTime.parse("2023-11-06 09:00:00",
                formatter);
        LocalDateTime pomodoroStartDT = LocalDateTime.parse("2023-11-05 14:00:00",
                formatter);

        UserPomodoroTask userPomodoroTask1 = UserPomodoroTask.builder().googleUserId("123").googleEventId("1")
                .title("workshop").description("learn react").calendarStartDateTime(calendarDateTime)
                .pomodoroStartDateTime(pomodoroStartDT).pomodoroEndDateTime(null).build();

        List<UserPomodoroTask> uncompletedTasks = Arrays.asList(userPomodoroTask1);

        String userId = "123";

        when(mockPomodoroAppRepository.findAllTasksUncompleted(userId)).thenReturn(uncompletedTasks);

        List<UserPomodoroTask> actualResult = pomodoroAppServiceImpl.getAllUncompletedTasksByGoogleUserId(userId);
        assertThat(actualResult).isEqualTo(uncompletedTasks);
        assertThat(actualResult).hasSize(1);
    }
}
