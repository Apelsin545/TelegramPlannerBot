package ru.eventplanner.telegramplannerbot.command.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eventplanner.telegramplannerbot.command.TelegramCommand;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandHandler;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.Event;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.EventManager;

import java.util.List;

@Service
public class ShowEventsCommandHandler implements TelegramCommandHandler {

    private final EventManager eventManager;

    @Autowired
    public ShowEventsCommandHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        String text = message.getText().trim();
        Long chatId = message.getChatId();
        var messageBuilder = SendMessage.builder()
                .chatId(chatId);

        if (text.contains(" "))
            return messageBuilder.text("Формат команды: /events")
                    .build();

        List<Event> acceptedEvents = eventManager.getAcceptedEventsByParticipant(chatId);

        StringBuilder formattedEvents = new StringBuilder();
        for (Event event : acceptedEvents) {
            formattedEvents.append(event.toString()).append("\n\n");
        }

        return messageBuilder.
                text(formattedEvents.toString())
                .build();
    }

    @Override
    public TelegramCommand getSupportedCommand() {
        return TelegramCommand.SHOW_EVENTS_COMMAND;
    }
}
