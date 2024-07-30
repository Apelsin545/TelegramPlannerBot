package ru.eventplanner.telegramplannerbot.integration.UserManagementService;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Data
public class User {
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("registration_date")
    private final LocalDate creationDate;
}
