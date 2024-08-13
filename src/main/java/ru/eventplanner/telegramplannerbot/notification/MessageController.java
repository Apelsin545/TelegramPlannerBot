package ru.eventplanner.telegramplannerbot.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.eventplanner.telegramplannerbot.TelegramBot;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final TelegramBot telegramBot;

    @Autowired
    public MessageController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PutMapping
    public void sendMessage(@RequestBody Notification notification) {
        log.info("Sending notification: {}", notification);

        var sendMessage = SendMessage.builder()
                .chatId(notification.getUserId())
                .text(notification.getMessage())
                .build();

        telegramBot.sendMessage(sendMessage);
    }
}
