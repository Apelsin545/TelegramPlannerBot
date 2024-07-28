package ru.eventplanner.telegramplannerbot.command.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eventplanner.telegramplannerbot.command.TelegramCommand;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandHandler;

@Slf4j
@Component
public class StartCommandHandler implements TelegramCommandHandler {

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        log.info("Processing command: {}", message.getText());

        var introText = "Привет!";
        return new SendMessage(message.getChatId().toString(), introText);
    }

    @Override
    public TelegramCommand getSupportedCommand() {
        return TelegramCommand.START_COMMAND;
    }
}
