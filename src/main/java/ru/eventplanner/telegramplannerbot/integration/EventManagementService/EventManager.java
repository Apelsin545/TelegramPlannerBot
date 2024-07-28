package ru.eventplanner.telegramplannerbot.integration.EventManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EventManager {
    private final RestTemplate restTemplate;

    @Autowired
    public EventManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Event saveEvent(Event event) {
        String url = "http://localhost:8081/event";

        return restTemplate.postForObject(url, event, Event.class);
    }
}
