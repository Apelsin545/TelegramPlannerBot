package ru.eventplanner.telegramplannerbot.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@AllArgsConstructor
public class TelegramCommandDispatcher {
    private final List<TelegramCommandHandler> commandHandlers;

    public BotApiMethod<?> processCommand(Message message) {
        if (!isCommand(message))
            throw new IllegalArgumentException("Передана не команда");

        var chatId = message.getChatId().toString();
        var text = message.getText();

        var commandHandler = commandHandlers.stream()
                .filter(t -> text.startsWith(t.getSupportedCommand().getCommand()))
                .findAny();

        if (commandHandler.isEmpty())
            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Нет такой команды")
                    .build();

        return commandHandler.orElseThrow().processCommand(message);
    }

    private boolean isCommand(Message message) {
        return message.hasText() && message.getText().startsWith("/");
    }
}
