package ru.eventplanner.telegramplannerbot.command.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eventplanner.telegramplannerbot.command.TelegramCommand;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandHandler;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.Event;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.EventManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CreateEventCommandHandler implements TelegramCommandHandler {
    private final EventManager eventManager;

    @Autowired
    public CreateEventCommandHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        log.info("Processing command: {}", message.getText());

        var textArr = message.getText().trim().split(" ");
        var chatId = message.getChatId();
        var messageBuilder = SendMessage.builder()
                .chatId(chatId);

        if (textArr.length != 3)
            return messageBuilder
                    .text("Неправильный ввод, введите команду /createevent <Название события> <Дата и время>")
                    .build();

        var startDateTime = LocalDateTime.parse(textArr[2]);
        if (startDateTime.isBefore(LocalDateTime.now()))
            return messageBuilder
                    .text("Эта дата уже прошла, введите заново")
                    .build();

        var event = Event.builder()
                .name(textArr[1])
                .startDateTime(startDateTime)
                .createdBy(message.getFrom().getId())
                .build();
        var savedEvent = eventManager.saveEvent(event);
        log.info("Saved event: {}", savedEvent);

        return messageBuilder
                .text("Событие сохранено!")
                .build();
    }

    @Override
    public TelegramCommand getSupportedCommand() {
        return TelegramCommand.CREATE_EVENT_COMMAND;
    }
}
