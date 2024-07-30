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

    public User saveUser(User user) {
        String url = "http://localhost:8082/user";
        log.info("Saving user: {}", user);

        return restTemplate.postForObject(url, user, User.class);
    }

    public User getByUserName(String userName) {
        String url = "http://localhost:8082/user/name/" + userName;
        log.info("Searching by user name: {}", userName);

        return restTemplate.getForObject(url, User.class);
    }
}
