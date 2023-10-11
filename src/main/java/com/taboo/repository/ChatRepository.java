package com.taboo.repository;

import com.taboo.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findByTelegramChatId(Long telegramChatId);
}
