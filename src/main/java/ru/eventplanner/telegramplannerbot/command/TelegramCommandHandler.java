package ru.eventplanner.telegramplannerbot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramCommandHandler {
    BotApiMethod<?> processCommand(Message message);
    TelegramCommand getSupportedCommand();
}
