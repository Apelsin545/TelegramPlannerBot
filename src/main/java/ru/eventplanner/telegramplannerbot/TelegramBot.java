package ru.eventplanner.telegramplannerbot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "EventPlannerBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        
    }
}
