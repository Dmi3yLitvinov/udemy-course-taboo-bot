package com.taboo.repository;

import com.taboo.entity.WaitRoom;
import com.taboo.entity.enums.WaitRoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitRoomRepository extends JpaRepository<WaitRoom, Long> {

    WaitRoom findByHash(String hash);

    WaitRoom findByChat_telegramChatIdAndStatus(Long telegramChatId, WaitRoomStatus status);
}
