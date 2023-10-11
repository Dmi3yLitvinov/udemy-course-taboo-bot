package com.taboo.telegram.command;

import com.taboo.entity.User;
import com.taboo.entity.WaitRoom;
import com.taboo.service.UserService;
import com.taboo.service.WaitRoomService;
import com.taboo.telegram.message.MessageBuilder;
import com.taboo.telegram.message.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StartCommand implements BotCommand {
    private final UserService userService;
    private final MessageSender messageSender;
    private final MessageBuilder messageBuilder;
    private final WaitRoomService waitRoomService;

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        User user = convert(message.getFrom());
        user = userService.save(user);
        String hash = extractHash(message.getText());
        if (hash != null) {
            WaitRoom waitRoom = waitRoomService.join(hash, message.getFrom().getId());
            EditMessageText editWaitRoomMsg = messageBuilder.editAwaitingMsg(waitRoom);
            messageSender.sendMessage(editWaitRoomMsg);
            String txt = "You've joined the game in the <b>%s</b> group".formatted(waitRoom.getChat().getTitle());
            SendMessage joinMessage = messageBuilder.buildTextMsg(chatId, txt);
            messageSender.sendMessage(joinMessage);
        } else {
            SendMessage response = messageBuilder.buildWelcomeMsg(chatId, user);
            messageSender.sendMessage(response);
        }
    }

    private String extractHash(String text) {
        String[] split = text.split(" ");
        if (split.length > 1) {
            return split[1];
        }
        return null;
    }

    @Override
    public boolean isMatch(String command) {
        return command.startsWith("start");
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
