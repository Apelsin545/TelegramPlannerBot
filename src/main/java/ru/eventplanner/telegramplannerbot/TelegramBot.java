package ru.eventplanner.telegramplannerbot;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandDispatcher;

import java.util.ArrayList;
import java.util.List;

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

    @SneakyThrows
    public void sendMessage(SendMessage sendMessage) {
        sendApiMethod(sendMessage);
    }


    @SneakyThrows
    @PostConstruct
    public void commandMenuInitialization() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "⭐ Начать"));
        commands.add(new BotCommand("/register", "📝 Регистрация"));
        commands.add(new BotCommand("/createevent", "🎉 Создать событие"));
        commands.add(new BotCommand("/invite", "✉️ Пригласить"));
        commands.add(new BotCommand("/events", "📅 Показать события"));
        commands.add(new BotCommand("/accept", "✅ Принять событие"));
        commands.add(new BotCommand("/reject", "❌ Отклонить событие"));

        SetMyCommands setMyCommands = new SetMyCommands(commands, new BotCommandScopeDefault(), null);
        execute(setMyCommands);
    }
}
