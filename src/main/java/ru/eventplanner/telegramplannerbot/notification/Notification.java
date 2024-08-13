package ru.eventplanner.telegramplannerbot.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(force = true)
public class Notification {
    private final Long id;

    @JsonProperty("user_id")
    private final Long userId;

    @JsonProperty("event_id")
    private final Long eventId;

    private String message;

    @JsonProperty("send_at")
    private LocalDateTime sendAt;
}