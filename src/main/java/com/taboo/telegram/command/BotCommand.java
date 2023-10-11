package com.taboo.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface BotCommand {

    void handle(Message message);

    boolean isMatch(String command);
}
