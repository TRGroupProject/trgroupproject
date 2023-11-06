package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.exceptions.InvalidTaskIdException;
import com.example.pomodoroApp.exceptions.InvalidUserException;
import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.service.PomodoroServiceImpl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

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
    private PomodoroServiceImpl mockPomodoroServiceImpl;

    @InjectMocks
    private PomodoroTasksApiController pomodoroTasksApiController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    List<UserPomodoroTask> uncompletedTasks;
    List<UserPomodoroTask> completedTasks;

    final String VALID_USER = "Valid_User";
    final String INVALID_USER = "Invalid_User";

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
    }

    @Test
    public void testGetAllTasksReturnsAllTasks() throws Exception {

        when(mockPomodoroServiceImpl.getAllTasks(VALID_USER, false)).thenReturn(uncompletedTasks);
        when(mockPomodoroServiceImpl.getAllTasks(VALID_USER, true)).thenReturn(completedTasks);
//            when(mockPomodoroServiceImpl.getAllTasks(INVALID_USER, false )).thenThrow(new AuthenticationException("user is not authenticated"));

        doThrow(new InvalidUserException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).getAllTasks(INVALID_USER, false);
        doThrow(new InvalidUserException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).getAllTasks(INVALID_USER, true);


        // No Query param
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", VALID_USER))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(2l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].taskId").value(3l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Title3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].taskId").value(4l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].title").value("Title4"));

        // Query Param isCompleted= false
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", VALID_USER).queryParam("idCompleted", "false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"));


        // Query Param isCompleted= true
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", VALID_USER).queryParam("isCompleted", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(5l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("TitleCompleted1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(6l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("TitleCompleted2"));


        // Test No user value passed in header
        mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


        // Test expection thrown when invalid user value passed in header
        assertThrows (InvalidUserException.class, () -> mockPomodoroServiceImpl.getAllTasks(INVALID_USER, false));
        assertThrows (InvalidUserException.class, () -> mockPomodoroServiceImpl.getAllTasks(INVALID_USER, true));
    }

    @Test
    public void testUpdateTasks() throws Exception {

        UserPomodoroTask task = uncompletedTasks.get(0);

        when(mockPomodoroServiceImpl.updateTaskById (VALID_USER, task.getTaskId(), task)).thenReturn(task);

        doThrow(new InvalidTaskIdException("TaskId not found in database:"))
                .when(mockPomodoroServiceImpl).updateTaskById (VALID_USER, 0l, task);

        doThrow(new InvalidUserException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).updateTaskById (INVALID_USER, task.getTaskId(), task);


        this.mockMvcController.perform(
                        MockMvcRequestBuilders.patch("/api/v1/tasks/" + task.getTaskId()).header("user", VALID_USER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.taskId").value(task.getTaskId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(task.getTitle()))
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(task)));


        verify(mockPomodoroServiceImpl, times(1)).updateTaskById(VALID_USER, task.getTaskId(), task);

        // Invalid user in Header
        assertThrows (InvalidUserException.class, () -> mockPomodoroServiceImpl.updateTaskById(INVALID_USER, task.getTaskId(), task));

        // Invalid TaskID
        assertThrows (InvalidTaskIdException.class, () -> mockPomodoroServiceImpl.updateTaskById(VALID_USER, 0l, task));

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
