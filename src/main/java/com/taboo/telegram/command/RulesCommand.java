package com.taboo.telegram.command;

import com.taboo.telegram.message.MessageBuilder;
import com.taboo.telegram.message.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class RulesCommand implements BotCommand {
    private final MessageBuilder messageBuilder;
    private final MessageSender messageSender;

    @Override
    public void handle(Message message) {
        SendMessage response = messageBuilder.buildRulesMsg(message.getChatId());
        messageSender.sendMessage(response);
    }

    @Override
    public boolean isMatch(String command) {
        return "rules".equals(command);
    }
}
