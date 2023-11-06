package com.example.pomodoroApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/v1/events")
public class PomodoroGoogleApiController {

    @GetMapping("/users")
    public String getGoogleUserInfo(@RequestHeader("Authorization") String authToken)
            throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {
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

        String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

        return result;
    }

    @GetMapping("/")
    public String getGoogleCalendarEvents(@RequestHeader("Authorization") String authToken)
            throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {

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

        String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
        System.out.println("result" + result);

        return result;
    }
}
