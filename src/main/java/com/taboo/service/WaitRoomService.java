package com.taboo.service;

import com.taboo.entity.Chat;
import com.taboo.entity.User;
import com.taboo.entity.WaitRoom;
import com.taboo.entity.enums.WaitRoomStatus;
import com.taboo.repository.ChatRepository;
import com.taboo.repository.UserRepository;
import com.taboo.repository.WaitRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitRoomService {
    private final WaitRoomRepository repository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public WaitRoom save(Long telegramChatId, String hash, Integer messageId) {
        var waitRoom = new WaitRoom();
        Chat chat = chatRepository.findByTelegramChatId(telegramChatId);
        waitRoom.setChat(chat);
        waitRoom.setHash(hash);
        waitRoom.setMessageId(messageId);
        return repository.save(waitRoom);
    }

    public WaitRoom join(String hash, Long telegramId) {
        WaitRoom waitRoom = repository.findByHash(hash);
        User user = userRepository.findByTelegramId(telegramId);
        waitRoom.getUsers().add(user);
        return waitRoom;
    }

    public WaitRoom findAwaitingByTelegramChatId(Long telegramChatId) {
        return repository.findByChat_telegramChatIdAndStatus(telegramChatId, WaitRoomStatus.AWAITING);
    }
}
