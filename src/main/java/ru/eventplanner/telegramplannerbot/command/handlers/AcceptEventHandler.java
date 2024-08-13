package ru.eventplanner.telegramplannerbot.command.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eventplanner.telegramplannerbot.command.TelegramCommand;
import ru.eventplanner.telegramplannerbot.command.TelegramCommandHandler;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.EventManager;
import ru.eventplanner.telegramplannerbot.integration.EventManagementService.Participant;

@Service
public class AcceptEventHandler implements TelegramCommandHandler {
    private final EventManager eventManager;

    @Autowired
    public AcceptEventHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        String[] textArr = message.getText().split(" ");
        Long chatId = message.getChatId();
        var messageBuilder = SendMessage.builder()
                .chatId(chatId);

        if (textArr.length != 2)
            return messageBuilder
                    .text("Формат команды: /accept <Идентификатор события>")
                    .build();

        Participant participant;
        try {
            participant = eventManager.getParticipantByPK(textArr[1], chatId.toString());
        } catch (HttpClientErrorException.NotFound e) {
            return messageBuilder
                    .text("Не найдено такого события для пользователя.")
                    .build();
        }
        
        participant.setStatus(Participant.Status.ACCEPTED);
        eventManager.updateParticipantStatus(participant);

        return messageBuilder
                .text("Приглашение принято!")
                .build();
    }

    @Override
    public TelegramCommand getSupportedCommand() {
        return TelegramCommand.ACCEPT_EVENT_COMMAND;
    }
}
