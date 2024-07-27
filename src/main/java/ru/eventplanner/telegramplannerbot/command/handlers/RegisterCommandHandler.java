package ru.eventplanner.telegramplannerbot.command.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eventplanner.telegramplannerbot.command.TelegramCommand;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandHandler;
import ru.eventplanner.telegramplannerbot.integration.UserManagementService.UserManager;

@Component
public class RegisterCommandHandler implements TelegramCommandHandler {
    private final UserManager userManager;

    @Autowired
    public RegisterCommandHandler(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        var userName = message.getFrom().getUserName();

        var chatId = message.getChatId();
        var textArr = message.getText()
                .trim()
                .split(" ");
        var messageBuilder = SendMessage.builder()
                .chatId(chatId);

        if (textArr.length != 2)
            return messageBuilder
                    .text("Укажите имя после команды /register")
                    .build();
        else
            userManager.saveUser(chatId, textArr[1], userName);

        return messageBuilder
                .text("Имя успешно зарегистрировано!")
                .build();
    }

    @Override
    public TelegramCommand getSupportedCommand() {
        return TelegramCommand.REGISTER_COMMAND;
    }
}
