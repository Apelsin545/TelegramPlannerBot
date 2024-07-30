package ru.eventplanner.telegramplannerbot.integration.EventManagementService;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Data
public class Event {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location")
    private String location;

    @JsonProperty("created_by")
    private final Long createdBy;

    @JsonProperty("start_date_time")
    private final LocalDateTime startDateTime;

    @JsonProperty("end_date_time")
    private final LocalDateTime endDateTime;
}
