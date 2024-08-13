package ru.eventplanner.telegramplannerbot.command.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eventplanner.telegramplannerbot.TelegramBot;
import ru.eventplanner.telegramplannerbot.command.TelegramCommand;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandHandler;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.Event;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.EventManager;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.Participant;
import ru.eventplanner.telegramplannerbot.integration.UserManagementService.UserManager;

import java.util.Objects;

@Slf4j
@Service
public class InviteCommandHandler implements TelegramCommandHandler {
    private final EventManager eventManager;
    private final UserManager userManager;
    private final TelegramBot telegramBot;

    @Autowired
    public InviteCommandHandler(EventManager eventManager, UserManager userManager, @Lazy TelegramBot telegramBot) {
        this.eventManager = eventManager;
        this.userManager = userManager;
        this.telegramBot = telegramBot;
    }

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        log.info("Processing command: {}", message.getText());

        String[] textArr = message.getText().trim().split(" ");
        Long chatId = message.getChatId();
        var messageBuilder = SendMessage.builder()
                .chatId(chatId);

        if (textArr.length != 3)
            return messageBuilder
                    .text("Формат ввода для приглашения на событие: /invite <Идентификатор события> <Юзернейм пользователя телеграм>")
                    .build();

        Event event = eventManager.getEventById(textArr[1]);
        if (!Objects.equals(event.getCreatedBy(), chatId))
            return messageBuilder
                    .text("Только создатель может приглашать пользователей!")
                    .build();

        Long invitedUserId = userManager.getByUserName(textArr[2])
                .getId();
        if (!Objects.equals(chatId, invitedUserId))
            return messageBuilder
                    .text("Вы уже учавствуете в событии!")
                    .build();

        var participant = Participant.builder()
                .eventId(Long.parseLong(textArr[1]))
                .userId(invitedUserId)
                .status(Participant.Status.PENDING)
                .build();

        Participant savedParticipant = eventManager.saveParticipant(participant);

        String invitationText = """
                 Вас пригласили на событие!
                 Идентификатор: %s
                 Название: %s
                 Время: %s
                 Чтобы принять приглашение напишите /accept <Идентификатор события>"
                """
                .formatted(
                        event.getId().toString(),
                        event.getName(),
                        event.getStartDateTime().toString()
                );

        telegramBot.sendMessage(
                SendMessage.builder()
                        .chatId(invitedUserId)
                        .text(invitationText)
                        .build()
        );

        return messageBuilder
                .text("Пользователь приглашен!")
                .build();
    }

    @Override
    public TelegramCommand getSupportedCommand() {
        return TelegramCommand.INVITE_COMMAND;
    }
}
