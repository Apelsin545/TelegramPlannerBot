package ru.eventplanner.telegramplannerbot.command.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eventplanner.telegramplannerbot.command.TelegramCommand;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandHandler;

@Component
public class StartCommandHandler implements TelegramCommandHandler {

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        var introText = "Привет!";


        return new SendMessage(message.getChatId().toString(), introText);
    }

    @Override
    public TelegramCommand getSupportedCommand() {
        return TelegramCommand.START_COMMAND;
    }
}
