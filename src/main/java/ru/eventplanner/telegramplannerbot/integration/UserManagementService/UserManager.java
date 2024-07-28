package ru.eventplanner.telegramplannerbot.integration.UserManagementService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Slf4j
@Service
public class UserManager {
    private final RestTemplate restTemplate;

    @Autowired
    public UserManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User saveUser(Long chatId, String name, String userName) {
        String url = "http://localhost:8082/user";

        var user = User.builder()
                .id(chatId)
                .name(name)
                .userName(userName)
                .build();

        var postedUser = restTemplate.postForObject(url, user, User.class);
        log.info("saveUser() response: {}", postedUser);

        return postedUser;
    }
}
