package com.taboo.repository;

import com.taboo.entity.Game;
import com.taboo.entity.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByChat_telegramChatIdAndStatus(Long telegramChatId, GameStatus inProgress);
}
