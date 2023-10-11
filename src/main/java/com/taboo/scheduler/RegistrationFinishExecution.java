package com.taboo.scheduler;

import com.taboo.entity.Card;
import com.taboo.entity.Game;
import com.taboo.entity.User;
import com.taboo.entity.WaitRoom;
import com.taboo.entity.enums.WaitRoomStatus;
import com.taboo.service.GameService;
import com.taboo.service.WaitRoomService;
import com.taboo.telegram.message.MessageBuilder;
import com.taboo.telegram.message.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Transactional
@RequiredArgsConstructor
public class RegistrationFinishExecution {
    private final WaitRoomService waitRoomService;
    private final MessageBuilder messageBuilder;
    private final MessageSender messageSender;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final GameService gameService;

    @Lazy
    @Autowired
    private RegistrationFinishExecution self;

    public void scheduleRegistrationFinish(Long telegramChatId) {
        taskScheduler.schedule(
                () -> self.registrationFinished(telegramChatId),
                Instant.now().plus(1, ChronoUnit.MINUTES)
        );
    }

    public void registrationFinished(Long telegramChatId) {
        WaitRoom waitRoom = waitRoomService.findAwaitingByTelegramChatId(telegramChatId);
        if (waitRoom == null) return;
        if (waitRoom.getUsers().size() < 2) {
            EditMessageText notEnoughUserMsg = messageBuilder.editNotEnoughUserMsg(waitRoom.getMessageId(), telegramChatId);
            messageSender.sendMessage(notEnoughUserMsg);
        } else {
            Game game = gameService.save(waitRoom);
            Card nextCard = gameService.getNextCard(game);
            User explainer = gameService.getExplainer(game);
            SendMessage nextTurnMsg = messageBuilder.buildNextTurnMsg(telegramChatId, explainer, nextCard.getId());
            SendMessage cardMsg = messageBuilder.buildCardMsg(explainer.getTelegramId(), nextCard);
            messageSender.sendMessage(nextTurnMsg);
            messageSender.sendMessage(cardMsg);

        }
        waitRoom.setStatus(WaitRoomStatus.REGISTRATION_FINISHED);
    }
}
