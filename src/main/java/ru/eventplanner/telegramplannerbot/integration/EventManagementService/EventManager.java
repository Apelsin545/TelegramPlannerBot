package ru.eventplanner.telegramplannerbot.integration.EventManagementService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class EventManager {
    private final RestTemplate restTemplate;
    private static final String url = "http://localhost:8081";

    @Autowired
    public EventManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Event saveEvent(Event event) {
        log.info("Saving event: {}", event);
        return restTemplate.postForObject(url + "/event", event, Event.class);
    }

    public Participant saveParticipant(Participant participant) {
        log.info("Saving participant: {}", participant);
        return restTemplate.postForObject(url + "/participant", participant, Participant.class);
    }

    public Event getEventById(String id) {
        log.info("Searching participant with id: {}", id);
        return restTemplate.getForObject(url + "/event/" + id, Event.class);
    }
}
