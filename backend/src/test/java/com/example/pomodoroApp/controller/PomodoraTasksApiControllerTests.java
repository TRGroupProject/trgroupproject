package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.exceptions.InvalidTaskIdException;
import com.example.pomodoroApp.exceptions.InvalidUserException;
import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.service.PomodoroAppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PomodoraTasksApiControllerTests {

    @Mock
    private PomodoroAppService mockPomodoroServiceImpl;

    @InjectMocks
    private PomodoroTasksApiController pomodoroTasksApiController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    List<UserPomodoroTask> uncompletedTasks;
    List<UserPomodoroTask> completedTasks;
    List<UserPomodoroTask> allTasks;

    final String VALID_USER = "Valid_User";
    final String INVALID_USER = "Invalid_User";

    final String VALID_AUTH = "Valid_auth";
    final String INVALID_AUTH = "Invalid_auth";

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(pomodoroTasksApiController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        uncompletedTasks = new ArrayList<>();
        uncompletedTasks.add(UserPomodoroTask.builder().taskId(1l).googleUserId("123").googleEventId("EventID1").title("Title1").description("Description1")
                .calendarStartDateTime(LocalDateTime.now()).build());
        uncompletedTasks.add(UserPomodoroTask.builder().taskId(2l).googleUserId("123").googleEventId("EventID2").title("Title2").description("Description2")
                .calendarStartDateTime(LocalDateTime.now()).build());
        uncompletedTasks.add(UserPomodoroTask.builder().taskId(3l).googleUserId("123").googleEventId("EventID3").title("Title3").description("Description3")
                .calendarStartDateTime(LocalDateTime.now()).build());
        uncompletedTasks.add(UserPomodoroTask.builder().taskId(4l).googleUserId("123").googleEventId("EventID4").title("Title4").description("Description4")
                .calendarStartDateTime(LocalDateTime.now()).build());


        completedTasks = new ArrayList<>();
        completedTasks.add(UserPomodoroTask.builder().taskId(5l).googleUserId("123").googleEventId("EventID5").title("TitleCompleted1").description("Description1")
                .calendarStartDateTime(LocalDateTime.now()).build());
        completedTasks.add(UserPomodoroTask.builder().taskId(6l).googleUserId("123").googleEventId("EventID6").title("TitleCompleted2").description("Description2")
                .calendarStartDateTime(LocalDateTime.now()).build());

        allTasks = new ArrayList<>();
        allTasks.addAll(uncompletedTasks);
        allTasks.addAll(completedTasks);
    }

    @Test
    public void testGetAllTasksReturnsAllTasks() throws Exception {

        when(mockPomodoroServiceImpl.getAllTasksByGoogleUserId(VALID_USER)).thenReturn(allTasks);
        when(mockPomodoroServiceImpl.getAllCompletedTasksByGoogleUserId(VALID_USER)).thenReturn(completedTasks);
        when(mockPomodoroServiceImpl.getAllUncompletedTasksByGoogleUserId(VALID_USER)).thenReturn(uncompletedTasks);

        doThrow(new InvalidUserException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).getAllTasksByGoogleUserId(INVALID_USER);
        doThrow(new InvalidUserException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).getAllTasksByGoogleUserId(INVALID_USER);


        // No Query param
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/")
                                .header("user", VALID_USER)
                                .header("authorization", VALID_AUTH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(2l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].taskId").value(3l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Title3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].taskId").value(4l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].title").value("Title4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].taskId").value(5l));

        // Query param tasks=all
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/")
                                .header("user", VALID_USER)
                                .header("authorization", VALID_AUTH)
                                .queryParam("tasks", "all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(2l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].taskId").value(3l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].taskId").value(4l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].taskId").value(5l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].taskId").value(6l));

        // Query Param: tasks=uncompleted
        this.mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/tasks/")
                        .header("user", VALID_USER)
                        .header("authorization", VALID_AUTH)
                        .queryParam("tasks", "uncompleted"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(2l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].taskId").value(3l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].taskId").value(4l));


        // Query Param tasks=completed
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/")
                                .header("user", VALID_USER)
                                .header("authorization", VALID_AUTH)
                                .queryParam("tasks", "completed"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(5l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(6l));


        // Test No user value passed in header
        mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/").header("authorization", VALID_AUTH))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Test No authorization value passed in header
        mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", VALID_USER))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


        // Test exception thrown when invalid user value passed in header
        assertThrows (InvalidUserException.class, () -> mockPomodoroServiceImpl.getAllTasksByGoogleUserId(INVALID_USER));
    }



    @Test
    public void testGetGoogleCalendarEvents() throws Exception {

        when(mockPomodoroServiceImpl.getAllTasksByGoogleUserId(VALID_USER)).thenReturn(allTasks);
        when (mockPomodoroServiceImpl.saveGoogleApiCalendarEvents(VALID_AUTH, VALID_USER)).thenReturn("OK");

        doThrow(new RuntimeException("User has invalid credential:" + INVALID_AUTH))
                .when(mockPomodoroServiceImpl).saveGoogleApiCalendarEvents(INVALID_AUTH, VALID_USER);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/")
                                .header("authorization", VALID_AUTH)
                                .header("user", VALID_USER)
                                .queryParam("tasks", "all")
                                .queryParam("google", "true"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        verify(mockPomodoroServiceImpl, times(1)).saveGoogleApiCalendarEvents(VALID_AUTH, VALID_USER);

//         Invalid token in Header
        assertThrows(RuntimeException.class, () -> mockPomodoroServiceImpl.saveGoogleApiCalendarEvents(INVALID_AUTH, VALID_USER));
    }


    @Test
    public void testUpdateTasks() throws Exception {

        UserPomodoroTask task = uncompletedTasks.get(0);

        when(mockPomodoroServiceImpl.updateTaskByTaskId (VALID_USER, task.getTaskId(), task)).thenReturn(task);

        doThrow(new InvalidTaskIdException("TaskId not found in database:"))
                .when(mockPomodoroServiceImpl).updateTaskByTaskId (VALID_USER, 0l, task);

        doThrow(new InvalidUserException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).updateTaskByTaskId (INVALID_USER, task.getTaskId(), task);


        this.mockMvcController.perform(
                        MockMvcRequestBuilders.patch("/api/v1/tasks/" + task.getTaskId()).header("user", VALID_USER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskId").value(task.getTaskId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.getTitle()))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(task)));


        verify(mockPomodoroServiceImpl, times(1)).updateTaskByTaskId(VALID_USER, task.getTaskId(), task);

        // Invalid user in Header
        assertThrows (InvalidUserException.class, () -> mockPomodoroServiceImpl.updateTaskByTaskId(INVALID_USER, task.getTaskId(), task));

        // Invalid TaskID
        assertThrows (InvalidTaskIdException.class, () -> mockPomodoroServiceImpl.updateTaskByTaskId(VALID_USER, 0l, task));

    }

    @Test
    public void testGetMusicURL () throws Exception {

        String musicURLString = "https://public.radio.co/playerapi/jquery.radiocoplayer.min.js";
        URL musicURL = new URL (musicURLString);

        when(mockPomodoroServiceImpl.getMusicUrl(VALID_USER)).thenReturn(musicURL);

        doThrow(new InvalidUserException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).getMusicUrl(INVALID_USER);


        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/music/").header("user", VALID_USER))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.format("\"%s\"", musicURLString)));

        // Invalid user in Header
        assertThrows (InvalidUserException.class, () -> mockPomodoroServiceImpl.getMusicUrl(INVALID_USER));

    }
}
