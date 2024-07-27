package ru.eventplanner.telegramplannerbot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandDispatcher;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramCommandDispatcher commandDispatcher;

    @Autowired
    public TelegramBot(@Value("${bot.token}") String botToken,
                       TelegramCommandDispatcher commandDispatcher) {
        super(new DefaultBotOptions(), botToken);
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public String getBotUsername() {
        return "EventPlannerBot";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            var sendMessage = commandDispatcher.processCommand(update.getMessage());
            sendApiMethod(sendMessage);
        }
    }
}
