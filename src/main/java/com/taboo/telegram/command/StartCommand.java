package com.taboo.telegram.command;

import com.taboo.entity.User;
import com.taboo.service.UserService;
import com.taboo.telegram.message.MessageBuilder;
import com.taboo.telegram.message.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StartCommand implements BotCommand {
    private final UserService userService;
    private final MessageSender messageSender;
    private final MessageBuilder messageBuilder;

    @Override
    public void handle(Message message) {
        User user = convert(message.getFrom());
        user = userService.save(user);
        SendMessage response = messageBuilder.buildWelcomeMsg(message.getChatId(), user);
        messageSender.sendMessage(response);
    }

    @Override
    public boolean isMatch(String command) {
        return "start".equals(command);
    }

    private User convert(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        var user = new User();
        user.setFirstName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setUsername(telegramUser.getUserName());
        user.setTelegramId(telegramUser.getId());
        return user;
    }
}
