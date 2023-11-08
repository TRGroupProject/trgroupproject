package com.example.pomodoroApp.service;


import com.example.pomodoroApp.exceptions.InvalidTaskIdException;
import com.example.pomodoroApp.exceptions.InvalidUserException;
import com.example.pomodoroApp.model.UserAccount;
import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.repository.PomodoroAppRepository;
import com.example.pomodoroApp.repository.PomodoroUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@DataJpaTest
public class PomodoroServiceImplTests {
    @Mock
    private PomodoroAppRepository mockPomodoroRepository;

    @Mock
    private PomodoroUserRepository mockPomodoroUserRepository;

    @InjectMocks
    private PomodoroAppServiceImpl pomodoroServiceImpl;

    List<UserPomodoroTask> uncompletedTasks;
    List<UserPomodoroTask> completedTasks;
    List<UserPomodoroTask> allTasks;

    UserAccount userAccount;

    final String VALID_USER = "Valid_User";
    final String INVALID_USER = "Invalid_User";

    @BeforeEach
    public void setup() {
//        mockMvcController = MockMvcBuilders.standaloneSetup(pomodoroTasksApiController).build();
//        mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());

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

        userAccount = UserAccount.builder().googleUserId(VALID_USER)
                .userEmail("blah@blah.com")
                .userName("blah")
                .build();
    }


    @Test
    public void testGetAllTasksReturnsListOfAllTasks() {

        when(mockPomodoroRepository.getTasksByGoogleUserId(VALID_USER)).thenReturn(allTasks);
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(VALID_USER)).thenReturn(userAccount);

        List<UserPomodoroTask> actualResult = pomodoroServiceImpl.getAllTasksByGoogleUserId(VALID_USER);

        assertThat(actualResult).hasSize(6);
        assertThat(actualResult).isEqualTo(allTasks);
    }

    @Test
    public void testGetAllTasksInvalidUserThrowsEsxception() {

        when(mockPomodoroRepository.getTasksByGoogleUserId(VALID_USER)).thenReturn(allTasks);
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(VALID_USER)).thenReturn(null);

        try {
            pomodoroServiceImpl.getAllTasksByGoogleUserId(INVALID_USER);
            fail("InvalidUserException should be thrown");
        } catch (InvalidUserException ex) {
            System.out.println(ex);
        }
    }


    @Test
    public void testGetCompletedTasksReturnsListOfCompletedTasks() {

        when(mockPomodoroRepository.findAllTasksCompleted(VALID_USER)).thenReturn(completedTasks);
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(VALID_USER)).thenReturn(userAccount);

        List<UserPomodoroTask> actualResult = pomodoroServiceImpl.getAllCompletedTasksByGoogleUserId(VALID_USER);

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(completedTasks);
    }

    @Test
    public void testGetCompletedTasksInvalidUserThrowsEsxception() {

        when(mockPomodoroRepository.findAllTasksCompleted(VALID_USER)).thenReturn(completedTasks);
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(VALID_USER)).thenReturn(null);

        try {
            pomodoroServiceImpl.getAllCompletedTasksByGoogleUserId(INVALID_USER);
            fail("InvalidUserException should be thrown");
        } catch (InvalidUserException ex) {
            System.out.println(ex);
        }
    }


    @Test
    public void testGetUnompletedTasksReturnsListOfUncompletedTasks() {

        when(mockPomodoroRepository.findAllTasksUncompleted(VALID_USER)).thenReturn(uncompletedTasks);
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(VALID_USER)).thenReturn(userAccount);

        List<UserPomodoroTask> actualResult = pomodoroServiceImpl.getAllUncompletedTasksByGoogleUserId(VALID_USER);

        assertThat(actualResult).hasSize(4);
        assertThat(actualResult).isEqualTo(uncompletedTasks);
    }

    @Test
    public void testGetUncompletedTasksInvalidUserThrowsEsxception() {

        when(mockPomodoroRepository.findAllTasksUncompleted(VALID_USER)).thenReturn(uncompletedTasks);
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(VALID_USER)).thenReturn(null);

        try {
            pomodoroServiceImpl.getAllUncompletedTasksByGoogleUserId(INVALID_USER);
            fail("InvalidUserException should be thrown");
        } catch (InvalidUserException ex) {
            System.out.println(ex);
        }
    }


    @Test
    public void testGetTaskByIdWithValidTaskId() {

        Long validTaskId = 5L;
        UserPomodoroTask task = uncompletedTasks.get(0);

        when(mockPomodoroRepository.findById(validTaskId)).thenReturn(Optional.of(task));

        UserPomodoroTask actualResult = pomodoroServiceImpl.getTaskByTaskId(validTaskId);
        assertThat(actualResult).isEqualTo(task);
    }


    @Test
    public void testGetTaskByIdWithInvalidTaskIdThrowsException() {

        Long invalidTaskId = 6L;

        when(mockPomodoroRepository.findById(invalidTaskId)).thenThrow(new InvalidTaskIdException("Invalid Task ID:" + invalidTaskId));

        try {
            UserPomodoroTask actualResult = pomodoroServiceImpl.getTaskByTaskId(invalidTaskId);
            fail("getTaskById should throw InvalidTaskIdException when give an invalid TaskId");
        } catch (InvalidTaskIdException ex) {
            System.out.println(ex);
        }

        verify(mockPomodoroRepository, times(1)).findById(invalidTaskId);
    }


    @Test
    public void testUpdateTaskByIdValidUIDandValidTaskId() {

        Long validTaskId = 5L;
        String validUid = "User123";

        UserPomodoroTask task = uncompletedTasks.get(1);

        when(mockPomodoroRepository.findById(validTaskId)).thenReturn(Optional.of(task));
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(validUid)).thenReturn(userAccount);


        pomodoroServiceImpl.updateTaskByTaskId(validUid, validTaskId, task);

        verify(mockPomodoroRepository, times(1)).findById(validTaskId);
        verify(mockPomodoroRepository, times(1)).save(task);
    }


    @Test
    public void testUpdateTaskByIdValidUIDandInvalidTaskId() {

        Long invalidTaskId = 5L;
        String validUid = "User123";

        UserPomodoroTask task = uncompletedTasks.get(1);

        when(mockPomodoroRepository.findById(invalidTaskId)).thenThrow(new InvalidTaskIdException("Invalid Task ID:" + invalidTaskId));
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(validUid)).thenReturn(userAccount);

        try {
            pomodoroServiceImpl.updateTaskByTaskId(validUid, invalidTaskId, task);
            fail("updateTaskById should throw InvalidTaskIdException when give an invalid TaskId");
        } catch (InvalidTaskIdException ex) {
            System.out.println(ex);
        }

        verify(mockPomodoroRepository, times(1)).findById(invalidTaskId);
    }


    @Test
    public void testUpdateTaskByIdInvalidUIDandValidTaskId() {

        Long validTaskId = 5L;
        String invalidUid = "User123";

        UserPomodoroTask task = uncompletedTasks.get(1);

        when(mockPomodoroRepository.findById(validTaskId)).thenReturn(Optional.of(task));
        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(invalidUid)).thenReturn(null);

        try {
            pomodoroServiceImpl.updateTaskByTaskId(invalidUid, validTaskId, task);
            fail("updateTaskById should throw InvalidUserException when give an invalid uid");
        } catch (InvalidUserException ex) {
            System.out.println(ex);
        }

        verify(mockPomodoroRepository, times(0)).findById(validTaskId);
    }


    //
    @Test
    public void testIsValidUserPositive() {

        String validUid = "User123Valid";

        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(validUid)).thenReturn(userAccount);

        assertThat (pomodoroServiceImpl.isValidUid(validUid)).isEqualTo(true);
    }


    @Test
    public void testIsValidUserNegative() {

        String invalidUid = "User123Invalid";

        when(mockPomodoroUserRepository.getUserAccountByGoogleUserId(invalidUid)).thenReturn(null);

        assertThat (pomodoroServiceImpl.isValidUid(invalidUid)).isEqualTo(false);
    }
}

