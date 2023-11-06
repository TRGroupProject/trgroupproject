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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
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

        @BeforeEach
        public void setup(){
            mockMvcController = MockMvcBuilders.standaloneSetup(pomodoroTasksApiController).build();
            mapper = new ObjectMapper();
        }

        @Test
        public void testGetAllTasksReturnsAllTasks() throws Exception {

            List<UserPomodoroTask> uncompletedTasks = new ArrayList<>();
            uncompletedTasks.add (UserPomodoroTask.builder().taskId(1l).googleUserId("123").googleEventId("EventID1").title("Title1").description("Description1")
                    .calendarStartDateTime(LocalDateTime.now()).build());
            uncompletedTasks.add (UserPomodoroTask.builder().taskId(2l).googleUserId("123").googleEventId("EventID2").title("Title2").description("Description2")
                    .calendarStartDateTime(LocalDateTime.now()).build());
            uncompletedTasks.add (UserPomodoroTask.builder().taskId(3l).googleUserId("123").googleEventId("EventID3").title("Title3").description("Description3")
                    .calendarStartDateTime(LocalDateTime.now()).build());
            uncompletedTasks.add (UserPomodoroTask.builder().taskId(4l).googleUserId("123").googleEventId("EventID4").title("Title4").description("Description4")
                    .calendarStartDateTime(LocalDateTime.now()).build());


            List<UserPomodoroTask> completedTasks = new ArrayList<>();
            completedTasks.add (UserPomodoroTask.builder().taskId(5l).googleUserId("123").googleEventId("EventID5").title("TitleCompleted1").description("Description1")
                    .calendarStartDateTime(LocalDateTime.now()).build());
            completedTasks.add (UserPomodoroTask.builder().taskId(6l).googleUserId("123").googleEventId("EventID6").title("TitleCompleted2").description("Description2")
                    .calendarStartDateTime(LocalDateTime.now()).build());

            when(mockPomodoroServiceImpl.getAllTasks("MyUser", false )).thenReturn(uncompletedTasks);
            when(mockPomodoroServiceImpl.getAllTasks("MyUser", true )).thenReturn(completedTasks);


            // No Query param
            this.mockMvcController.perform(
                     MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", "MyUser"))
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
                            MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", "MyUser").param("idCompleted", "false"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(1l))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Title1"));


            // Query Param isCompleted= true
            this.mockMvcController.perform(
                            MockMvcRequestBuilders.get("/api/v1/tasks/").header("user", "MyUser").param("isCompleted", "true"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].taskId").value(5l))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("TitleCompleted1"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].taskId").value(6l))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("TitleCompleted2"));


        }
}
