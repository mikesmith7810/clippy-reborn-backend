package com.clippy.service;

import com.clippy.model.DateRange;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private static final Logger log = LoggerFactory.getLogger(CalendarService.class);
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);

    @Value("${google.calendar.credentials-file}")
    private Resource credentialsFile;

    @Value("${google.calendar.tokens-dir}")
    private String tokensDir;

    @Value("${google.calendar.user}")
    private String user;

    public String getEvents(DateRange dateRange) {
        try {
            NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JSON_FACTORY, new InputStreamReader(credentialsFile.getInputStream()));

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    transport, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensDir)))
                    .setAccessType("offline")
                    .build();

            Credential credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize(user);

            Calendar service = new Calendar.Builder(transport, JSON_FACTORY, credential)
                    .setApplicationName("Clippy")
                    .build();

            ZoneId zone = ZoneId.systemDefault();
            com.google.api.client.util.DateTime timeMin = new com.google.api.client.util.DateTime(
                    Date.from(dateRange.start().atStartOfDay(zone).toInstant()));
            com.google.api.client.util.DateTime timeMax = new com.google.api.client.util.DateTime(
                    Date.from(dateRange.end().plusDays(1).atStartOfDay(zone).toInstant()));

            Events events = service.events().list("primary")
                    .setTimeMin(timeMin)
                    .setTimeMax(timeMax)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();

            List<Event> items = events.getItems();
            if (items == null || items.isEmpty()) {
                return "No events found for the requested period.";
            }

            return items.stream()
                    .map(event -> {
                        String start = event.getStart().getDateTime() != null
                                ? event.getStart().getDateTime().toString()
                                : event.getStart().getDate().toString();
                        return "- " + event.getSummary() + " at " + start;
                    })
                    .collect(Collectors.joining("\n"));

        } catch (Exception e) {
            log.error("Failed to fetch calendar events", e);
            return "Could not retrieve calendar events.";
        }
    }
}
