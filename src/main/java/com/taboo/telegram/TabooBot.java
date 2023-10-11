package com.taboo.telegram;

import com.taboo.telegram.command.BotCommandHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TabooBot extends TelegramLongPollingBot {

    private final String username;
    private final BotCommandHandler commandHandler;

    public TabooBot(@Value("${app.telegram.username}") String username,
                    @Value("${app.telegram.token}") String botToken,
                    BotCommandHandler commandHandler) {
        super(botToken);
        this.username = username;
        this.commandHandler = commandHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            if (text == null) return;
            if (text.startsWith("/")) {
                commandHandler.handleCommand(message);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
