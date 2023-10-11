package com.taboo.telegram.command;

import com.taboo.telegram.message.MessageBuilder;
import com.taboo.telegram.message.MessageSender;
import com.taboo.telegram.util.BotUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class PlayCommand implements BotCommand {
    private final MessageBuilder messageBuilder;
    private final MessageSender messageSender;

    private static final String NOT_A_GROUP_CHAT = "The game should take place in a group chat rather then in a private chat!";

    @Override
    public void handle(Message message) {
        if (BotUtil.isPrivateMsg(message)) {
            SendMessage response = messageBuilder.buildTextMsg(message.getChatId(), NOT_A_GROUP_CHAT);
            messageSender.sendMessage(response);
        }
    }

    @Override
    public boolean isMatch(String command) {
        return "play".equals(command);
    }
}
