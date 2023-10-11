package com.taboo.telegram;

import com.taboo.entity.Chat;
import com.taboo.entity.enums.ChatBotStatus;
import com.taboo.service.ChatService;
import com.taboo.telegram.command.BotCommandHandler;
import com.taboo.telegram.message.MessageBuilder;
import com.taboo.telegram.message.UserMessageHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TabooBot extends TelegramLongPollingBot {

    private final String username;
    private final ChatService chatService;
    private final MessageBuilder messageBuilder;
    private final BotCommandHandler commandHandler;
    private final UserMessageHandler userMessageHandler;

    public TabooBot(@Value("${app.telegram.username}") String username,
                    @Value("${app.telegram.token}") String botToken,
                    ChatService chatService,
                    MessageBuilder messageBuilder,
                    BotCommandHandler commandHandler,
                    UserMessageHandler userMessageHandler) {
        super(botToken);
        this.username = username;
        this.chatService = chatService;
        this.messageBuilder = messageBuilder;
        this.commandHandler = commandHandler;
        this.userMessageHandler = userMessageHandler;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            if (text == null) return;
            if (text.startsWith("/")) {
                commandHandler.handleCommand(message);
            } else {
                userMessageHandler.handle(message);
            }
        } else if (update.hasMyChatMember()) {
            Chat chat = chatService.convert(update.getMyChatMember());
            chat = chatService.save(chat);
            if (chat.getChatBotStatus() == ChatBotStatus.ADMIN) {
                SendMessage message = messageBuilder.buildTextMsg(chat.getTelegramChatId(), "Hello there!\nLet's play the taboo game");
                execute(message);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
