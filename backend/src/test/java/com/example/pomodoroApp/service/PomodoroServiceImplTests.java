package com.example.pomodoroApp.service;


import com.example.pomodoroApp.model.UserPomodoroTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    private PomodoroRepository mockPomodoroRepository;

    @InjectMocks
    private PomodoroServiceImpl pomodoroServiceImpl;

    List<UserPomodoroTask> uncompletedTasks;
    List<UserPomodoroTask> completedTasks;

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
    }


    @Test
    public void testGetAllTasksReturnsListOfTasks() {

        when(mockPomodoroRepository.findAll()).thenReturn(uncompletedTasks);

        List<UserPomodoroTask> actualResult = pomodoroServiceImpl.getAllTasks (VALID_USER, false);

        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(uncompletedTasks);
    }





//    @Test
//    public void testAddABook() {
//
//        var book = new Book(4L, "Book Four", "This is the description for Book Four", "Person Four", Genre.Fantasy);
//
//        when(mockBookManagerRepository.save(book)).thenReturn(book);
//
//        Book actualResult = bookManagerServiceImpl.insertBook(book);
//
//        assertThat(actualResult).isEqualTo(book);
//    }
//
//    @Test
//    public void testGetBookById() {
//
//        Long bookId = 5L;
//        var book = new Book(5L, "Book Five", "This is the description for Book Five", "Person Five", Genre.Fantasy);
//
//        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.of(book));
//
//        Book actualResult = bookManagerServiceImpl.getBookById(bookId);
//
//        assertThat(actualResult).isEqualTo(book);
//    }
//
//    //User Story 4 - Update Book By Id Solution
//    @Test
//    public void testUpdateBookById() {
//
//        Long bookId = 5L;
//        var book = new Book(5L, "Book Five", "This is the description for Book Five", "Person Five", Genre.Fantasy);
//
//        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.of(book));
//        when(mockBookManagerRepository.save(book)).thenReturn(book);
//
//        bookManagerServiceImpl.updateBookById(bookId, book);
//
//        verify(mockBookManagerRepository, times(1)).save(book);
//    }
//
//    @Test
//    public void testDeleteBookById() {
//
//        Long bookId = 1L;
//        var book = new Book(bookId, "Book Six", "This is the description for Book Six", "Person Six", Genre.Fantasy);
//
//        when(mockBookManagerRepository.findById(bookId)).thenReturn(Optional.of(book));
//
//        bookManagerServiceImpl.deleteByBookId (bookId);
//
//        verify (mockBookManagerRepository, times (1)).deleteById(bookId);
//    }
//
//    @Test
//    public void testDeleteBookByIdNoMatch() {
//
//        Long bookId = 1L;
//        var book = new Book(bookId, "Book Seven", "This is the description for Book 7", "Person 7", Genre.Fantasy);
//
//        when(mockBookManagerRepository.findById(bookId + 1)).thenReturn(Optional.ofNullable(null));
//
//        try {
//            bookManagerServiceImpl.deleteByBookId(bookId + 1);
//            fail ("BookNotFoundException should be raised");
//        } catch (BookNotFoundException ex) {
//            System.out.println(ex);
//        }
//
//        verify (mockBookManagerRepository, times (1)).findById(bookId + 1) ;
//    }



}
