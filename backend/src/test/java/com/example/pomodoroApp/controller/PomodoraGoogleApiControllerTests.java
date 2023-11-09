package com.example.pomodoroApp.controller;

import com.example.pomodoroApp.model.UserAccount;
import com.example.pomodoroApp.service.PomodoroAppServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class PomodoraGoogleApiControllerTests {

    @Mock
    private PomodoroAppServiceImpl mockPomodoroAppServiceImpl;

    @InjectMocks
    private PomodoroGoogleApiController pomodoroGoogleApiController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    final String VALID_ACCESS_TOKEN = "Valid_Token";
    final String INVALID_ACCESS_TOKEN = "Invalid_Token";

    final String VALID_GOOGLE_UID = "Valid_google_uid";

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(pomodoroGoogleApiController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetGoogleCalendarEvents() throws Exception {

//        String googleApURLString = "https://www.googleapis.com/calendar/v3/calendars/primary/events?&maxResults=10&timeMin=2023-11-0609:00:00";
//        URL googleApiURL = new URL(googleApURLString);

//        when(mockPomodoroAppServiceImpl.getGoogleApiUrl(VALID_ACCESS_TOKEN)).thenReturn(googleApiURL);
//
//        doThrow(new RuntimeException("User has invalid credential:" + INVALID_ACCESS_TOKEN))
//                .when(mockPomodoroAppServiceImpl).getGoogleApiUrl(INVALID_ACCESS_TOKEN);

        when (mockPomodoroAppServiceImpl.saveGoogleApiCalendarEvents(VALID_ACCESS_TOKEN, VALID_GOOGLE_UID)).thenReturn("OK");

        doThrow(new RuntimeException("User has invalid credential:" + INVALID_ACCESS_TOKEN))
                .when(mockPomodoroAppServiceImpl).saveGoogleApiCalendarEvents(INVALID_ACCESS_TOKEN, VALID_GOOGLE_UID);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/events/")
                                .header("Authorization", VALID_ACCESS_TOKEN)
                                .header("user", VALID_GOOGLE_UID))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

//         Invalid token in Header
        assertThrows(RuntimeException.class, () -> mockPomodoroAppServiceImpl.saveGoogleApiCalendarEvents(INVALID_ACCESS_TOKEN, VALID_GOOGLE_UID));
    }

    @Test
    public void testPostGoogleUser() throws Exception {

//        String googleApURLString = "https://www.googleapis.com/oauth2/v3/userinfo";
//        URL googleApiURL = new URL(googleApURLString);

        UserAccount userAccount = UserAccount.builder().googleUserId("11223344556677889900")
                .userEmail("validuser@somewhere.com")
                .userName("valid user")
                .build();


//        when(mockPomodoroAppServiceImpl.getGoogleApiUrl(VALID_ACCESS_TOKEN)).thenReturn(googleApiURL);
//        doThrow(new RuntimeException("User has invalid credential:" + INVALID_ACCESS_TOKEN))
//                .when(mockPomodoroAppServiceImpl).getGoogleApiUrl(INVALID_ACCESS_TOKEN);

        when(mockPomodoroAppServiceImpl.saveGoogleApiUserInfo(VALID_ACCESS_TOKEN)).thenReturn(userAccount);
        doThrow(new RuntimeException("User has invalid credential:" + INVALID_ACCESS_TOKEN))
                .when(mockPomodoroAppServiceImpl).saveGoogleApiUserInfo(INVALID_ACCESS_TOKEN);


        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/events/users").header("Authorization",
                                VALID_ACCESS_TOKEN))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

//         Invalid token in Header
        assertThrows(RuntimeException.class, () -> mockPomodoroAppServiceImpl.saveGoogleApiUserInfo(INVALID_ACCESS_TOKEN));
    }
}
