package com.example.pomodoroApp.service;

import com.example.pomodoroApp.model.UserPomodoroTask;
import com.example.pomodoroApp.repository.PomodoroAppRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class PomodoroAppServiceImpl implements PomodoroAppService {

    @Autowired
    PomodoroAppRepository pomodoroAppRepository;

    @Override
    public List<UserPomodoroTask> getAllTasksByGoogleUserId(String googleUserId) {
        List<UserPomodoroTask> tasks = pomodoroAppRepository.getTasksByGoogleUserId(googleUserId);
        return tasks;
    }

    @Override
    public List<UserPomodoroTask> getAllCompletedTasksByGoogleUserId(String googleUserId) {
        List<UserPomodoroTask> tasks = pomodoroAppRepository.findAllTasksCompleted(googleUserId);
        return tasks;
    }

    @Override
    public List<UserPomodoroTask> getAllUncompletedTasksByGoogleUserId(String googleUserId) {
        List<UserPomodoroTask> tasks = pomodoroAppRepository.findAllTasksUncompleted(googleUserId);
        return tasks;
    }

    @Override
    public URL getGoogleApiUrl(String uid) {
        return null;
    }

    @Override
    public String getGoogleApiCalendarEvents(String authToken)
            throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {

        try {
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss-07:00");
            String formattedDateTime = now.format(formatter);

            String calendarApiUrl = "https://www.googleapis.com/calendar/v3/calendars/primary/events?&maxResults=10&timeMin="
                    + formattedDateTime;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI(calendarApiUrl))
                    .header("Content-Type", "application/JSON")
                    .header("Authorization", authToken)
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching events", e);
        }
    }

    @Override
    public String getGoogleApiUserInfo(String authToken) throws ExecutionException, InterruptedException, TimeoutException, URISyntaxException {

        try {

            String googleUserApiUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI(googleUserApiUrl))
                    .header("Content-Type", "application/JSON")
                    .header("Authorization", authToken)
                    .timeout(Duration.of(5, ChronoUnit.SECONDS))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            return jsonResponse;
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching user information", e);
        }
    }
}