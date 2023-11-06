package com.example.pomodoroApp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
// @RequestMapping("/api/v1/tasks")
public class PomodoroGoogleApiController {

    static final String GOOGLE_CALENDAR_URL = "https://www.googleapis.com/calendar/v3/calendars/primary/events";
    static final int GOOGLE_REQUEST_TIMEOUT = 5; // In seconds

    @GetMapping(value = "/")
    public String welcome() {
        return "Welcome to the Pomodoro API";
    }

    @GetMapping("/uid")
    public String uid() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/user")
    public String user() {
        OAuth2User userDetails = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getAttributes().containsKey("name")
                ? userDetails.getAttribute("name")
                : "unknown user";
    }

    @GetMapping("/token")
    // public OAuth2AccessToken index(@RegisteredOAuth2AuthorizedClient("google")
    // OAuth2AuthorizedClient authorizedClient) {
    public OAuth2AccessToken index(@RegisteredOAuth2AuthorizedClient() OAuth2AuthorizedClient authorizedClient) {
        return authorizedClient.getAccessToken();
    }

    @GetMapping(value = "/events")
    // public String secured (@RegisteredOAuth2AuthorizedClient("google")
    // OAuth2AuthorizedClient authorizedClient) throws URISyntaxException,
    // IOException, InterruptedException, ExecutionException, TimeoutException {
    public String secured(@RegisteredOAuth2AuthorizedClient() OAuth2AuthorizedClient authorizedClient)
            throws URISyntaxException, IOException, InterruptedException, ExecutionException, TimeoutException {

        // TODO ADD ERROR HANDLING - CATCH THE THROWN EXCEPTIONS, AND OTHER EXCEPTION
        // TYPES
        // TODO MOVE THIS TO SEPARATE FUNCTION
        // TODO Parse the Json result, and extract the relevent event fields to match
        // our model

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String bearerToken = "Bearer " + authorizedClient.getAccessToken().getTokenValue();
        // System.out.println("Bearer token:" + bearerToken);
        HttpRequest googleRequest = HttpRequest
                .newBuilder(new URI(GOOGLE_CALENDAR_URL))
                .header("Authorization", bearerToken)
                .timeout(Duration.of(GOOGLE_REQUEST_TIMEOUT, ChronoUnit.SECONDS))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                .build()
                .sendAsync(googleRequest, HttpResponse.BodyHandlers.ofString());

        String result = response.thenApply(HttpResponse::body).get(GOOGLE_REQUEST_TIMEOUT, TimeUnit.SECONDS);

        // change this to sendAsync
        // HttpResponse<String> response = client.send (googleRequest,
        // HttpResponse.BodyHandlers.ofString());
        System.out.println("response:" + result);
        return result;
    }
}
