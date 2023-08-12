package com.taboo.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TabooBot extends TelegramLongPollingBot {

    private final String username;

    public TabooBot(@Value("${app.telegram.username}") String username,
                    @Value("${app.telegram.token}") String botToken) {
        super(botToken);
        this.username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
