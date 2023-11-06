package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.service.PomodoroServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.security.sasl.AuthenticationException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(pomodoroTasksApiController).build();
        mapper = new ObjectMapper();


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


        final String VALID_USER = "Valid_User";
        final String INVALID_USER = "Invalid_User";

        when(mockPomodoroServiceImpl.getAllTasks(VALID_USER, false)).thenReturn(uncompletedTasks);
        when(mockPomodoroServiceImpl.getAllTasks(VALID_USER, true)).thenReturn(completedTasks);
//            when(mockPomodoroServiceImpl.getAllTasks(INVALID_USER, false )).thenThrow(new AuthenticationException("user is not authenticated"));

        doThrow(new RuntimeException("User has invalid credential:" + INVALID_USER))
                .when(mockPomodoroServiceImpl).getAllTasks(INVALID_USER, false);


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
                        MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", VALID_USER).param("idCompleted", "false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(1l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"));


        // Query Param isCompleted= true
        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", VALID_USER).param("isCompleted", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(5l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("TitleCompleted1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(6l))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("TitleCompleted2"));


        // Test No user value passed in header
        mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/tasks/"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


        // Test Invalid user value passed in header
//            assertThrows (RuntimeException.class,  () ->  mockMvcController.perform(
//                    MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", INVALID_USER)).andExpect(MockMvcResultMatchers.status().isOk()));
    }

    @Test
    public void testUpdateTasks() throws Exception {

        final String VALID_USER = "Valid_User";
        final String INVALID_USER = "Invalid_User";

        UserPomodoroTask task = uncompletedTasks.get(0);

        when(mockPomodoroServiceImpl.updateTaskById (VALID_USER, task.getTaskId(), task)).thenReturn(task);


        this.mockMvcController.perform(
                        MockMvcRequestBuilders.put("/api/v1/tasks/" + task.getTaskId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(task)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockPomodoroServiceImpl, times(1)).updateTaskById(VALID_USER, task.getTaskId(), task);

    }
}
