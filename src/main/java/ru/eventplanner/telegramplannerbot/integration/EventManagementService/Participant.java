package ru.eventplanner.telegramplannerbot.integration.EventManagementService;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Participant {

    @JsonProperty("event_id")
    private final Long eventId;

    @JsonProperty("user_id")
    private final Long userId;

    @JsonProperty("status")
    private Status status;

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }
}

