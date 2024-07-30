package ru.eventplanner.telegramplannerbot.command;

public enum TelegramCommand {
    START_COMMAND("/start"),
    REGISTER_COMMAND("/register"),
    CREATE_EVENT_COMMAND("/createevent"),
    INVITE_COMMAND("/invite"),
    SHOW_EVENTS_COMMAND("/events"),
    ACCEPT_EVENT_COMMAND("/accept"),
    REJECT_EVENT_COMMAND("/reject");

    private final String command;

    TelegramCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
