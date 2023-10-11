package com.taboo.telegram.command;

import com.taboo.service.WaitRoomService;
import com.taboo.telegram.message.MessageBuilder;
import com.taboo.telegram.message.MessageSender;
import com.taboo.telegram.util.BotUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PlayCommand implements BotCommand {
    private final MessageBuilder messageBuilder;
    private final MessageSender messageSender;
    private final WaitRoomService waitRoomService;

    private static final String NOT_A_GROUP_CHAT = "The game should take place in a group chat rather then in a private chat!";

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        if (BotUtil.isPrivateMsg(message)) {
            SendMessage response = messageBuilder.buildTextMsg(chatId, NOT_A_GROUP_CHAT);
            messageSender.sendMessage(response);
        } else {
            String hash = UUID.randomUUID().toString();
            SendMessage awaitingMsg = messageBuilder.buildAwaitingMsg(chatId, hash);
            messageSender.sendMessage(awaitingMsg);
            waitRoomService.save(chatId, hash);
        }
    }

    @Override
    public boolean isMatch(String command) {
        return "play".equals(command);
    }
}
