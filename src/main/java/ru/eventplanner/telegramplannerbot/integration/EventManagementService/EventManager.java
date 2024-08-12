package ru.eventplanner.telegramplannerbot.integration.EventManagementService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public Participant getParticipantByPK(String eventId, String userId) {
        log.info("Searching participant with event id - {} and user id - {}", eventId, userId);
        return restTemplate.getForObject(url + "/participant?eventId=" + eventId + "&userId=" + userId, Participant.class);
    }

    public List<Event> getAcceptedEventsByParticipant(Long chatId) {
        log.info("Searching accepted events by participant with id - {}", chatId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("chatId", chatId.toString());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Event>> response = restTemplate.exchange(
                url + "/event/accepted",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    public void updateParticipantStatus(Participant participant) {
        log.info("Updating participant status: {}", participant);
        restTemplate.put(url + "/participant", participant);
    }
}
